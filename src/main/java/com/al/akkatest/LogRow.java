package com.al.akkatest;

import java.io.Serializable;

public class LogRow implements Serializable {
    public final String rowText;

    public LogRow(String rowText) {
        this.rowText = rowText;
    }
}
