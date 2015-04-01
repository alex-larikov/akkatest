package com.al.akkatest;

import java.io.Serializable;
import java.util.Date;

public class ParsedLog implements Serializable {
    private final Date date;
    private final String severity;
    private final String text;

    public ParsedLog(Date date, String severity, String text) {
        this.date = date;
        this.severity = severity;
        this.text = text;
    }
}
