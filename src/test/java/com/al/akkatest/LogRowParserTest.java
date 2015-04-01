package com.al.akkatest;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import akka.testkit.TestActorRef;
import akka.testkit.TestKit;
import com.typesafe.config.ConfigFactory;
import org.junit.Test;

public class LogRowParserTest extends JavaTestKit {

    static ActorSystem system = ActorSystem.create("TestSys", ConfigFactory.load().getConfig("TestSys"));

    public LogRowParserTest() {
        super(system);
    }

    @Test
    public void testLogParsing() throws Exception {
        final Props props = Props.create(LogRowParser.class);
        final TestActorRef<LogRowParser> ref = TestActorRef.create(system, props, "TestParser");
        //final LogRowParser actor = ref.underlyingActor();
        ref.receive(new LogRow("this is a log row"), getTestActor());
        //this.expectMsgEquals("does it work");
        ActorRef testActor = getTestActor();

    }
}