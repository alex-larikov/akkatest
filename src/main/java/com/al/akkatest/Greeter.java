package com.al.akkatest;

import akka.actor.UntypedActor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Greeter extends UntypedActor {

    public static enum Msg {
        GREET, DONE
    }

    final Logger logger = LoggerFactory.getLogger(Greeter.class);

    @Override
    public void onReceive(Object msg) {
        if (msg == Msg.GREET) {
            String saying = "Hello World!";
            System.out.println(saying);
            logger.info(saying);
            getSender().tell(Msg.DONE, getSelf());
        } else
            unhandled(msg);
    }

}