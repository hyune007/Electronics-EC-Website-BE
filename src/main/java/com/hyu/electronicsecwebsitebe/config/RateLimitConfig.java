package com.hyu.electronicsecwebsitebe.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitConfig extends OncePerRequestFilter {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket createBucket(String path) {
        Bandwidth limit;

        if (path.startsWith("/api/ai/chat")) {
            limit = Bandwidth.classic(10, Refill.greedy(6, Duration.ofMinutes(1)));
        }
        else if(path.startsWith("/api/ai/compare")) {
            limit = Bandwidth.classic(6, Refill.greedy(3, Duration.ofMinutes(1)));
        }
        else {
            limit = Bandwidth.classic(100, Refill.greedy(100, Duration.ofMinutes(1)));
        }

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isBlank()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    private String getApiGroup(String path) {
        if (path.startsWith("/api/ai/")) return "ai";
        return "default";
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String ip = getClientIp(request);
        String path = request.getRequestURI();
        String apiGroup = getApiGroup(path);
        //Debug IP
//        System .out.println("Request from IP: " + ip + " to path: " + path);
        String key = ip + "_" + apiGroup;

        Bucket bucket = buckets.computeIfAbsent(key, k -> createBucket(path));
        //Debug Available Tokens
//        System .out.println("Bucket for key: " + key + " - Available tokens: " + bucket.getAvailableTokens());
        if (bucket.tryConsume(1)) {
            filterChain.doFilter(request, response);
        } else {
            response.setStatus(429);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write("""
            {
              "title": "Quá nhiều yếu cầu",
              "message": "Đã vượt quá giới hạn yêu cầu. Vui lòng thử lại sau một vài phút."
            }
            """);
        }
    }
}