package com.nilufer.minibank.utils;

public class AccountNumberGeneratorUtil {

    //to generate 10 length number
    public static String generate() {
        long number = 1_000_000_000L + (long) (Math.random() * 9_000_000_000L);
        return String.valueOf(number);
    }
}