package com.stream.online.payment.util;

import lombok.Getter;
import lombok.Setter;

import java.util.regex.Pattern;

@Setter
@Getter
public class SecureUpiMaskerAndValidator {

    // Regular expression for UPI ID validation
    private static final String UPI_REGEX = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+$";
    //private static final String UPI_REGEX = "^[\\w.-]+@[\\w.-]+$"

    //regex \\w meaning
    // \\w in regex stands for "word character." It matches any alphanumeric character, including underscores (_). Specifically, it matches:
    //Any letter (a-z and A-Z)
    //Any digit (0-9)
    //An underscore (_)
    //So, it's a shorthand for [a-zA-Z0-9_].

    // Method to validate UPI ID
    public static boolean validateUpiId(String upiId) {
        // Check if UPI ID matches the pattern
        return upiId != null && Pattern.matches(UPI_REGEX, upiId);
    }

    // Method to securely mask the UPI ID, showing only the first few and last few characters
    public static String maskUpiId(String upiId) {
        if (upiId == null || !validateUpiId(upiId)) {
            throw new IllegalArgumentException("Invalid UPI ID format");
        }

        // Split the UPI ID into local part and domain part
        String[] parts = upiId.split("@");
        String localPart = parts[0];  // The part before the '@'
        String domainPart = parts[1];  // The part after the '@'

        // Apply masking logic based on the length of the local part
        String maskedLocalPart;
        int length = localPart.length();

        if (length <= 4) {
            // If the local part is very short, mask all but the first character
            maskedLocalPart = localPart.charAt(0) + "***";
        } else {
            // Mask all characters except the first two and last two characters for longer local parts
            maskedLocalPart = localPart.substring(0, 2) + "****" + localPart.substring(length - 2);
        }

        // Return the securely masked UPI ID
        return maskedLocalPart + "@" + domainPart;
    }
    
}
