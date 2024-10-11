package com.stream.online.payment.util;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Random;

public class UniqueOrderNumber {

    public static String generateOrderNumber() {
        // Get current timestamp in the format "yyyyMMddHHmmss"
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String timestamp = dateFormat.format(new Date());

        // Generate a random number (4 digits)
        Random random = new Random();
        int randomNumber = 1000 + random.nextInt(9000); // Random number between 1000 and 9999

        // Combine timestamp and random number to create the order number
        return "ORD-" + timestamp + "-" + String.valueOf(randomNumber)+String.valueOf(Instant.now().getEpochSecond());
        //ORD-20241012013540-42671728677140
        //ORD-20241012013615-73541728677175
        //ORD-20241012013640-98121728677200
    }
}
