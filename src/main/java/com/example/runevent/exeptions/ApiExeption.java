package com.example.runevent.exeptions;

import java.time.Instant;
import java.util.List;

public class ApiExeption {
    private Instant timestamp = Instant.now();
    private String message;
    private List<String> details;

    public ApiExeption() {}
    public ApiExeption(String message, List<String> details) {
        this.message = message;
        this.details = details;
    }

    public Instant getTimestamp()
    { return timestamp; }

    public String getMessage()
    { return message; }

    public void setMessage(String message)
    { this.message = message; }

    public List<String> getDetails()
    { return details; }

    public void setDetails(List<String> details)
    { this.details = details; }

}
