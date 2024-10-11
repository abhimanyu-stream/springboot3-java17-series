package com.stream.online.payment.util;

import java.util.regex.Pattern;

public class IFSCValidator {
    // Regex pattern for IFSC validation
    private static final String IFSC_REGEX = "^[A-Z]{4}0[A-Z0-9]{6}$";

    // Method to validate IFSC code
    public static boolean validateIFSC(String ifscCode) {
        // Check if IFSC code matches the pattern
        return Pattern.matches(IFSC_REGEX, ifscCode);
    }
}
