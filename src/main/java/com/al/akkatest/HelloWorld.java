package com.al.akkatest;

import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.ActorRef;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorld extends UntypedActor {

    final Logger logger = LoggerFactory.getLogger(HelloWorld.class);

    @Override
    public void preStart() {
        // create the greeter actor
        final ActorRef greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
        // tell it to perform the greeting
        logger.info("sending message GREET");
        greeter.tell(Greeter.Msg.GREET, getSelf());
    }

    @Override
    public void onReceive(Object msg) {
        logger.info("received some message", msg);
        if (msg == Greeter.Msg.DONE) {
            logger.info("message is DONE");
            // when the greeter is done, stop this actor and with it the application
            getContext().stop(getSelf());
        } else
            unhandled(msg);
    }
}