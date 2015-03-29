package com.al.akkatest;

import java.io.Serializable;


public class Log implements Serializable {
    public final String text;

    public Log(String text) {
        this.text = text;
    }
}
