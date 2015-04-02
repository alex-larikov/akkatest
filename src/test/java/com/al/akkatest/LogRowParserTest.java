package com.al.akkatest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import akka.testkit.TestKit;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Date;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
//import static org.hamcrest.core.

public class LogRowParserTest extends JavaTestKit {

    static ActorSystem system = ActorSystem.create("TestSys", ConfigFactory.load().getConfig("TestSys"));

    public LogRowParserTest() {
        super(system);
    }

//    @Test
//    public void testLogParsing() throws Exception {
//        final Props props = Props.create(LogRowParser.class);
//        final TestActorRef<LogRowParser> ref = TestActorRef.create(system, props, "TestParser");
//        //final LogRowParser actor = ref.underlyingActor();
//        ref.receive(new LogRow("this is a log row"), getTestActor());
//        //this.expectMsgEquals("does it work");
//        ActorRef testActor = getTestActor();
//
//    }


    @Test
    public void testSingleLineLogParsed() throws Exception {
        final Props props = Props.create(LogRowParser.class);
        final TestActorRef<LogRowParser> ref = TestActorRef.create(system, props, "TestParser");

        String logRow = "2000-09-07 14:07:41,508 [main] INFO  MyApp - Entering application.";
        String datePattern = "yyyy-MM-dd HH:mm:ss,SSS";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(datePattern);
        LocalDateTime dateTime = LocalDateTime.parse("2000-09-07 14:07:41,508", formatter);
        ParsedLog expectedParsedLog = new ParsedLog(dateTime, "INFO", "MyApp - Entering application.");
        ParsedLog parsedLog = ref.underlyingActor().parse(logRow);
        assertThat(parsedLog.getDate(), is(expectedParsedLog.getDate()));

    }
}