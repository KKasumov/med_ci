package com.kasumov.med_ci.util;


import org.springframework.stereotype.Component;

@Component
public class ContactFormat {
    public String formatPhone(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 4; i < str.length()-2; i++) {
            stringBuilder.setCharAt(i, '#');
        }
        return stringBuilder.toString();
    }
    public String formatTelegram(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = str.length() / 2; i < str.length(); i++) {
            stringBuilder.setCharAt(i, '#');
        }
        return stringBuilder.toString();
    }
    public String formatAddress(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = str.length() / 2; i < str.length(); i++) {
            stringBuilder.setCharAt(i, '#');
        }
        return stringBuilder.toString();
    }

    public String formatMail(String str) {
        StringBuilder stringBuilder = new StringBuilder(str);
        for (int i = 3; i < str.length(); i++) {
            if (str.charAt(i) != '@') {
                stringBuilder.setCharAt(i, '#');
            } else break;
        }
        return stringBuilder.toString();
    }
}

