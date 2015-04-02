package com.al.akkatest;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

public class ParsedLog implements Serializable {
    private final LocalDateTime date;
    private final String severity;
    private final String text;

    public LocalDateTime getDate() {
        return date;
    }

    public String getSeverity() {
        return severity;
    }

    public String getText() {
        return text;
    }

    public ParsedLog(LocalDateTime date, String severity, String text) {
        this.date = date;
        this.severity = severity;
        this.text = text;
    }
}
