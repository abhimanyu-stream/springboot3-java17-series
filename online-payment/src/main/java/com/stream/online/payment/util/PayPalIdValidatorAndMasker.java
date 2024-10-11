package com.stream.online.payment.util;


import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Getter
@Setter
public class PayPalIdValidatorAndMasker {

    // Regular expression for PayPal ID validation (email format)
    private static final String PAYPAL_ID_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    // Method to validate PayPal ID
    public static boolean validatePayPalId(String paypalId) {
        // Check if PayPal ID matches the pattern
        return paypalId != null && Pattern.matches(PAYPAL_ID_REGEX, paypalId);
    }

    // Method to securely mask the PayPal ID, showing only the first part and the last part
    public static String maskPayPalId(String paypalId) {
        if (paypalId == null || !validatePayPalId(paypalId)) {
            throw new IllegalArgumentException("Invalid PayPal ID format");
        }

        // Split the PayPal ID into local part and domain part
        String[] parts = paypalId.split("@");
        String localPart = parts[0];  // The part before the '@'
        String domainPart = parts[1];  // The part after the '@'

        // Masking logic: keep the first 2 characters and last 2 characters of the local part
        String maskedLocalPart;
        int length = localPart.length();

        if (length <= 4) {
            // If the local part is very short, mask all but the first character
            maskedLocalPart = localPart.charAt(0) + "***";
        } else {
            // Mask all characters except the first two and last two characters for longer local parts
            maskedLocalPart = localPart.substring(0, 2) + "****" + localPart.substring(length - 2);
        }

        // Return the securely masked PayPal ID
        return maskedLocalPart + "@" + domainPart;
    }
    
}
