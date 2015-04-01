package com.al.akkatest;

import akka.actor.AbstractActor;
import akka.japi.pf.ReceiveBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogWriter extends AbstractActor {
    private final Logger logger = LoggerFactory.getLogger(LogWriter.class);

    public LogWriter() {
        receive(ReceiveBuilder.
            match(ParsedLog.class, pl -> logger.info("Writing log to storage")).
            matchAny(o -> logger.info("Unknown message: {}", o)).build()
        );
    }
}
