//package com.hyu.electronicsecwebsitebe.service.impl;
//
//import com.hyu.electronicsecwebsitebe.dto.request.payment.SePayWebhookPayload;
//import com.hyu.electronicsecwebsitebe.dto.response.payment.PaymentStatusResponse;
//import com.hyu.electronicsecwebsitebe.dto.response.payment.SePaySessionResponse;
//import com.hyu.electronicsecwebsitebe.dto.response.payment.SePayWebhookResponse;
//import com.hyu.electronicsecwebsitebe.model.Bill;
//import com.hyu.electronicsecwebsitebe.repository.BillRepository;
//import com.hyu.electronicsecwebsitebe.repository.DetailBillRepository;
//import com.hyu.electronicsecwebsitebe.service.PaymentService;
//import jakarta.transaction.Transactional;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.net.URLEncoder;
//import java.nio.charset.StandardCharsets;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//@Service
//@RequiredArgsConstructor
//public class PaymentServiceImpl implements PaymentService {
//    private static final Pattern BILL_ID_PATTERN = Pattern.compile ("HD[0-9A-Z]+", Pattern.CASE_INSENSITIVE);
//    private static final String PAID_STATUS = "Chờ xác nhận";
//
//    private final BillRepository billRepository;
//    private final DetailBillRepository detailBillRepository;
//
//    @Value("${sepay.bank-code}")
//    private String bankCode;
//
//    @Value("${sepay.account-no}")
//    private String accountNumber;
//
//    @Value("${sepay.account-name}")
//    private String accountName;
//
//    @Value("${sepay.qr.template:compact}")
//    private String qrTemplate;
//
//    @Value("${sepay.webhook.api-key:}")
//    private String webhookApiKey;
//
//    @Override
//    public SePaySessionResponse createPaymentSession(String billId) {
//        Bill bill = billRepository.findById (billId)
//                .orElseThrow (() -> new RuntimeException ("Không tìm thấy hóa đơn"));
//
//        BigDecimal total = detailBillRepository.sumTotalByBillId (billId);
//        if (total == null || total.compareTo (BigDecimal.ZERO) <= 0) {
//            throw new RuntimeException ("Tổng tiền không hợp lệ");
//        }
//
//        String amount = total.setScale (0, RoundingMode.HALF_UP).toPlainString ();
//        String description = bill.getId ();
//        String qrUrl = buildQrUrl (accountNumber, bankCode, amount, description, qrTemplate);
//
//        return new SePaySessionResponse (bill.getId (), description, bankCode, accountNumber, accountName, amount, qrUrl);
//    }
//
//    @Override
//    @Transactional
//    public SePayWebhookResponse handlePaymentWebhook(SePayWebhookPayload payload, String authHeader) {
//        if (webhookApiKey != null && !webhookApiKey.isBlank ()) {
//            String expected = "Apikey " + webhookApiKey;
//            if (authHeader == null || !authHeader.equals (expected)) {
//                return new SePayWebhookResponse (false, "Unauthorized");
//            }
//        }
//
//        if (payload == null || payload.getTransferType () == null
//                || !"in".equalsIgnoreCase (payload.getTransferType ())) {
//            return new SePayWebhookResponse (true, "Ignore");
//        }
//
//        String billId = extractBillId (payload);
//        if (billId == null) {
//            return new SePayWebhookResponse (true, "Bill not found in content");
//        }
//
//        Bill bill = billRepository.findById (billId).orElse (null);
//        if (bill == null) {
//            return new SePayWebhookResponse (true, "Bill not found");
//        }
//
//        BigDecimal expected = detailBillRepository.sumTotalByBillId (billId);
//        BigDecimal received = payload.getTransferAmount () == null
//                ? BigDecimal.ZERO
//                : BigDecimal.valueOf (payload.getTransferAmount ());
//        if (expected == null || expected.compareTo (BigDecimal.ZERO) <= 0) {
//            return new SePayWebhookResponse (true, "Invalid bill amount");
//        }
//
//        BigDecimal expectedRounded = expected.setScale (0, RoundingMode.HALF_UP);
//        if (received.compareTo (expectedRounded) != 0) {
//            return new SePayWebhookResponse (true, "Amount mismatch");
//        }
//
//        if (!PAID_STATUS.equalsIgnoreCase (bill.getStatus ())) {
//            bill.setStatus (PAID_STATUS);
//            billRepository.save (bill);
//        }
//
//        return new SePayWebhookResponse (true, "Updated bill " + billId);
//    }
//
//    @Override
//    public PaymentStatusResponse getPaymentStatus(String billId) {
//        Bill bill = billRepository.findById (billId)
//                .orElseThrow (() -> new RuntimeException ("Không tìm thấy hóa đơn"));
//        String status = bill.getStatus ();
//        boolean paid =
//                PAID_STATUS.equalsIgnoreCase (status)
//                        || "Đang giao".equalsIgnoreCase (status)
//                        || "Đã giao".equalsIgnoreCase (status)
//                        || "Đã thanh toán".equalsIgnoreCase (status);
//        return new PaymentStatusResponse (billId, status, paid);
//    }
//
//    private String extractBillId(SePayWebhookPayload payload) {
//        if (payload.getCode () != null && payload.getCode ().toUpperCase ().startsWith ("HD")) {
//            return payload.getCode ().toUpperCase ();
//        }
//        String content = payload.getContent ();
//        if (content == null) return null;
//        Matcher matcher = BILL_ID_PATTERN.matcher (content);
//        return matcher.find () ? matcher.group ().toUpperCase () : null;
//    }
//
//    private String buildQrUrl(String accountNumber, String bankCode, String amount, String description, String template) {
//        String acc = URLEncoder.encode (accountNumber, StandardCharsets.UTF_8);
//        String bank = URLEncoder.encode (bankCode, StandardCharsets.UTF_8);
//        String des = URLEncoder.encode (description, StandardCharsets.UTF_8);
//
//        StringBuilder url = new StringBuilder ("https://qr.sepay.vn/img?acc=")
//                .append (acc)
//                .append ("&bank=")
//                .append (bank)
//                .append ("&amount=")
//                .append (amount)
//                .append ("&des=")
//                .append (des);
//
//        if (template != null && !template.isBlank ()) {
//            url.append ("&template=").append (URLEncoder.encode (template, StandardCharsets.UTF_8));
//        }
//
//        return url.toString ();
//    }
//}
