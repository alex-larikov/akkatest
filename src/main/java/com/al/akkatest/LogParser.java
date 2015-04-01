package com.al.akkatest;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class LogParser extends AbstractActor {
    public static final String DONE_MSG = "LOG DONE";
    private final Logger logger = LoggerFactory.getLogger(LogParser.class);

    public LogParser() {
        receive(ReceiveBuilder.
            match(Log.class, log -> {
                logger.info("Received log message: {}", log.text);
                //logger.info("Sending msg: {} to sender: {}", DONE_MSG, sender());
                //sender().tell(DONE_MSG, self());
                parseRows(log);
            }).
            matchAny(o -> logger.info("Unknown message {}", o)).build()
        );

    }

    private void parseRows(Log l) {
        ActorRef logRowParser = getContext().actorOf(Props.create(LogRowParser.class), "logRowParser");
        for (String row: l.text.split("\\r?\\n")) {
            LogRow lr = new LogRow(row);
            logRowParser.tell(lr, self());
        }
    }
}
