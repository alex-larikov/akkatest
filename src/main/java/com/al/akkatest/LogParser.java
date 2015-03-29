package com.al.akkatest;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogParser extends AbstractActor {
    public static final String DONE_MSG = "LOG DONE";
    private final Logger logger = LoggerFactory.getLogger(LogParser.class);

    public LogParser() {
        receive(ReceiveBuilder.
            match(Log.class, l -> {
                logger.info("Received log message: {}", l.text);
                logger.info("Sending msg: {} to sender: {}", DONE_MSG, sender());
                sender().tell(DONE_MSG, self());
            }).
            matchAny(o -> logger.info("Unknown message")).build()
        );

    }
}
