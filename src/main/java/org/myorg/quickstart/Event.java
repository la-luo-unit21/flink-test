package org.myorg.quickstart;

import java.util.Objects;

public class Event {

    private long accountId;

    private long timestamp;

    private double amount;

    private String type;

    public Event() {}

    public Event(long accountId, long timestamp, double amount, String type) {
        this.accountId = accountId;
        this.timestamp = timestamp;
        this.amount = amount;
        this.type = "";
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }
}
