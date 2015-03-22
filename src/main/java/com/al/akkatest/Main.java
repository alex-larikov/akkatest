package com.al.akkatest;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    final static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        BasicConfigurator.configure();
        logger.info("test log");
    }
}
