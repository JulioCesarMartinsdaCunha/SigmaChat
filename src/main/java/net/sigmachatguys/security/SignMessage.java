package net.sigmachatguys.security;

public class SignMessage {
    public final String message;
    public final long timestamp;
    public final String signature;

    public SignMessage(String message, long timestamp, String signature) {
        this.message = message;
        this.timestamp = timestamp;
        this.signature = signature;
    }
}