package com.al.akkatest;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogRowParser extends AbstractActor {
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";
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

    ParsedLog parse(String rowText) {
        Pattern pattern = Pattern.compile("(?<date>\\d{4}-\\d{2}-\\d{2}\\s\\d{2}:\\d{2}:\\d{2},\\d{3})\\s" +
                "\\[(?<thread>.*)\\]\\s(?<severity>[A-Z]*)\\s+(?<class>.+?)-\\s(?<text>.*)");
        Matcher m = pattern.matcher(rowText);
        if (m.matches()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
            LocalDateTime dateTime = LocalDateTime.parse(m.group("date"), formatter);
            //LocalDateTime dateTime = LocalDateTime.parse(m.group(1), formatter);
            return new ParsedLog(dateTime, m.group("severity"), m.group("text"));
        }

        throw new IllegalArgumentException(String.format("Log row \"{}\" didn't match the pattern {}", rowText, pattern.pattern()));
    }
}
