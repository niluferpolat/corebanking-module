package com.nilufer.minibank.model;

public enum TransactionStatus {
    SUCCESS("Success"),
    FAILED("Failed");

    private final String friendlyName;

    TransactionStatus(String friendlyName) {
        this.friendlyName = friendlyName;
    }

    public String getFriendlyName() {
        return friendlyName;
    }
}
