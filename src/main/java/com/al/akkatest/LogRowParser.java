package com.al.akkatest;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class LogRowParser extends AbstractActor {
    private final Logger logger = LoggerFactory.getLogger(LogRowParser.class);

    public LogRowParser() {
        receive(ReceiveBuilder.
            match(LogRow.class, lr -> {
                logger.info("Received log row: {}", lr.rowText);
                ParsedLog log = parse(lr.rowText);
                final ActorRef logWriter = getContext().actorOf(Props.create(LogWriter.class));
                logWriter.tell(log, self());
            }).
            matchAny(o -> logger.info("Unknown message")).build()
        );
    }

    private ParsedLog parse(String rowText) {
        return new ParsedLog(new Date(), "", "");
    }
}
