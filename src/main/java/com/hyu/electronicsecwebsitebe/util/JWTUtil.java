package com.hyu.electronicsecwebsitebe.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JWTUtil {

    private static final String ISSUER = "ubraintech";
    private static final long TOKEN_EXPIRATION_DAYS = 30;
    @Value("${spring.jwt.signerKey}")
    private String secretKey;

    /**
     * Generate JWT token
     *
     * @param id     User ID
     * @param roleId User role ID
     * @return JWT token string
     */
    public String generateToken(String id, String roleId) {
        JWSHeader jwsHeader = new JWSHeader (JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder ()
                .subject (id)
                .claim ("roleId", roleId)
                .issueTime (new Date ())
                .issuer (ISSUER)
                .expirationTime (new Date (Instant.now ().plus (TOKEN_EXPIRATION_DAYS, ChronoUnit.DAYS).toEpochMilli ()))
                .build ();

        Payload payload = new Payload (jwtClaimsSet.toJSONObject ());
        JWSObject jwsObject = new JWSObject (jwsHeader, payload);

        try {
            jwsObject.sign (new MACSigner (secretKey.getBytes ()));
            return jwsObject.serialize ();
        } catch (JOSEException e) {
            throw new RuntimeException ("Error generating JWT token", e);
        }
    }

    /**
     * Validate JWT token
     *
     * @param token JWT token string
     * @return true if token is valid, false otherwise
     */
    public boolean validateToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse (token);
            JWSVerifier verifier = new MACVerifier (secretKey.getBytes ());

            // Verify signature and check expiration
            return signedJWT.verify (verifier) && !isTokenExpired (signedJWT);
        } catch (ParseException | JOSEException e) {
            return false;
        }
    }

    /**
     * Check if token is expired
     *
     * @param signedJWT Signed JWT object
     * @return true if token is expired
     */
    private boolean isTokenExpired(SignedJWT signedJWT) {
        try {
            Date expirationTime = signedJWT.getJWTClaimsSet ().getExpirationTime ();
            return expirationTime != null && expirationTime.before (new Date ());
        } catch (ParseException e) {
            return true;
        }
    }

    /**
     * Get user ID (subject) from token
     *
     * @param token JWT token string
     * @return User ID
     */
    public String getUserIdFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse (token);
            return signedJWT.getJWTClaimsSet ().getSubject ();
        } catch (ParseException e) {
            throw new RuntimeException ("Error parsing JWT token", e);
        }
    }

    /**
     * Get role ID from token
     *
     * @param token JWT token string
     * @return Role ID
     */
    public String getRoleIdFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse (token);
            return (String) signedJWT.getJWTClaimsSet ().getClaim ("roleId");
        } catch (ParseException e) {
            throw new RuntimeException ("Error parsing JWT token", e);
        }
    }

    /**
     * Get all claims from token
     *
     * @param token JWT token string
     * @return JWTClaimsSet containing all claims
     */
    public JWTClaimsSet getClaimsFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse (token);
            return signedJWT.getJWTClaimsSet ();
        } catch (ParseException e) {
            throw new RuntimeException ("Error parsing JWT token", e);
        }
    }

    /**
     * Get expiration date from token
     *
     * @param token JWT token string
     * @return Expiration date
     */
    public Date getExpirationDateFromToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse (token);
            return signedJWT.getJWTClaimsSet ().getExpirationTime ();
        } catch (ParseException e) {
            throw new RuntimeException ("Error parsing JWT token", e);
        }
    }
}
