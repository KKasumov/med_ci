package com.kasumov.med_ci.util;

import org.springframework.stereotype.Component;

@Component
public class PolisFormat {
    public String formatSerial(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 0; i < str.length() - 2; i++) {
            stringBuilder.setCharAt(i, '#');
        }
        return stringBuilder.toString();
    }

    public String formatNumber(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 0; i < str.length() - 4; i++) {
            stringBuilder.setCharAt(i, '#');
        }
        return stringBuilder.toString();
    }
}
