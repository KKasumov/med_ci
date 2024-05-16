package com.kasumov.med_ci.util;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Generator {

    public String getRandomString(int length) {
        String abc = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        Random rnd = new Random(System.nanoTime());

        StringBuilder builder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            builder.append(abc.charAt(rnd.nextInt(abc.length())));
        }
        return builder.toString();
    }
}
