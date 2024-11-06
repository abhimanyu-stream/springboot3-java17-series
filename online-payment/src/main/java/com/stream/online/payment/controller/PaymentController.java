package com.stream.online.payment.controller;


import com.stream.online.payment.dto.PaymentCreateRequest;
import com.stream.online.payment.dto.PaymentResponse;
import com.stream.online.payment.service.PaymentService;
import com.stream.online.payment.util.ConvertBytesToHex;
import com.stream.online.payment.util.KeyStoreUtil;
import com.stream.online.payment.util.UniqueTransactionIdGenerator;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.UUID;


/**
 * @Author Abhimanyu Kumar
 * */
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private final PaymentService paymentService;
    private final OkHttpClient okHttpClient;
    private final ConvertBytesToHex convertBytesToHex;
    private final UniqueTransactionIdGenerator uniqueTransactionIdGenerator;
    private final KeyStoreUtil keyStoreUtil;

    private int amount = 100;

    @Autowired
    public PaymentController(PaymentService paymentService, OkHttpClient okHttpClient, ConvertBytesToHex convertBytesToHex, UniqueTransactionIdGenerator uniqueTransactionIdGenerator, KeyStoreUtil keyStoreUtil) throws IOException {
        this.paymentService = paymentService;
        this.okHttpClient = okHttpClient;
        this.convertBytesToHex = convertBytesToHex;
        this.uniqueTransactionIdGenerator = uniqueTransactionIdGenerator;
        this.keyStoreUtil = keyStoreUtil;
    }

    @PostMapping("/create")
    public ResponseEntity<PaymentResponse> createPayment(@RequestBody PaymentCreateRequest paymentRequest) {
        PaymentResponse createdPayment = paymentService.createPayment(paymentRequest);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @PostMapping( "/pay")
    public String pay(@RequestBody PaymentCreateRequest inputBody) throws NoSuchAlgorithmException, InvalidKeyException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException {

        // 1. Extract user input
        String cardNumber = inputBody.getCardNumber();
        String CVV = inputBody.getCvc();
        String cardExpiryDate = inputBody.getCardExpiryDate();
        String amount = inputBody.getAmount();




        // 2. Define other payment-related parameters
        String cardEntryMethod = "X";                               // To denote that the card was keyed in manually
        String industryType = "E";                                  // To denote an e-commerce transaction
        boolean capture = true;                                     // To authorize and capture payment in the same go


        /**
         * below are payment gateway related ids can be configured in Configuration Object
         * epiId
         * epiKey
         * baseUrl
         * endpoint
         * returnUrl or callBackUrl
         * */
        String epiId = "apikeyid";
        String epiKey = "apikey";
        String baseUrl = "http://payments-service:8080";
        String endpoint = "/sale";

        // 3. Define request headers and body content
        String contentType = "application/json";

        String transactionId = uniqueTransactionIdGenerator.generateUniqueTransactionId();
        String orderNumber = UUID.randomUUID().toString();
        Double batchId = Math.random();

        JSONObject bodyJSON = new JSONObject();

        bodyJSON.put("account", cardNumber);
        bodyJSON.put("cvv2", CVV);
        bodyJSON.put("expirationDate", cardExpiryDate);
        bodyJSON.put("amount", BigDecimal.valueOf(Long.parseLong(amount)));
        bodyJSON.put("transaction", transactionId);
        bodyJSON.put("orderNumber", orderNumber);
        bodyJSON.put("capture", capture);
        bodyJSON.put("industryType", industryType);
        bodyJSON.put("cardEntryMethod", cardEntryMethod);
        bodyJSON.put("batchID", batchId);


        // 4. Create signature from epiKey, endpoint, and body
        String signature = createSignature(endpoint, bodyJSON.toJSONString(), epiKey);

        // 5. Prepare body to send with request
        okhttp3.RequestBody body = okhttp3.RequestBody.create(
                bodyJSON.toJSONString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        // 6. Build the request with correct headers and body content
        Request request = new Request.Builder()
                .url(baseUrl + endpoint)
                .addHeader("Content-Type", contentType)
                .addHeader("epi-Id", epiId)
                .addHeader("epi-signature", signature)
                .post(body)
                .build();

        // store payment created request object in DB and thereafter its status will only change as per response coming from payment [ as success or failed]
        // if required, from here, We can store it to a kafka topic

        // 7. Send the request and handle the response
        try (Response response = okHttpClient.newCall(request).execute()) {

            // Handling failure
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            // Handling success
            JSONParser jsonParser = new JSONParser();
            JSONObject responseBodyJSON = (JSONObject) jsonParser.parse(response.body().string());


            // update the payment response in DB, only status field of payment created request object
            // Sending the payment status back to the caller
            return ((JSONObject) responseBodyJSON.get("data")).get("text").toString();
        } catch (Exception e) {

            // Handling errors
            e.printStackTrace();
            return e.getMessage();
        }

    }

    // Create signature using the HMAC-SHA-256 algorithm from endpoint + payload and epiKey
    public String createSignature(@NotNull String endpoint, @NotNull String payload, @NotNull String epiKey) throws NoSuchAlgorithmException, InvalidKeyException, UnrecoverableKeyException, CertificateException, KeyStoreException, IOException {


        keyStoreUtil.processKeystorep12();

        String algorithm = "HmacSHA256";
        SecretKeySpec secretKeySpec = new SecretKeySpec(epiKey.getBytes(), algorithm);
        Mac mac = Mac.getInstance(algorithm);
        mac.init(secretKeySpec);
        String data = endpoint + payload;
        return convertBytesToHex.bytesToHex(mac.doFinal(data.getBytes()));

    }






}
