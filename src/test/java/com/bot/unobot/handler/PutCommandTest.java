package com.bot.unobot.handler;

import com.bot.unobot.UnoBotApplication;
import com.bot.unobot.utils.EventTestUtility;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "line.bot.handler.enabled=false", classes = UnoBotApplication.class)
public class PutCommandTest {
    static {
        System.setProperty("line.bot.channelSecret", "DUMMYSECRET");
        System.setProperty("line.bot.channelToken", "DUMMYTOKEN");
    }

    @Autowired
    private HandlerController handlerController;

    private final EventTestUtility eventTestUtility = new EventTestUtility();


    @Before
    public void setUp() {
        handlerController = new HandlerController();
    }

    @Test
    public void testPutNormalCard() {
        MessageEvent<TextMessageContent> event = this.eventTestUtility.createDummyTextMessage(".create", "123", "abc");
        handlerController.handleTextMessageEvent(event);

        event = this.eventTestUtility.createDummyTextMessage(".join", "123", "abc");
        handlerController.handleTextMessageEvent(event);

        event = this.eventTestUtility.createDummyTextMessage(".join", "456", "abc");
        handlerController.handleTextMessageEvent(event);

        event = this.eventTestUtility.createDummyTextMessage(".start", "456", "abc");
        handlerController.handleTextMessageEvent(event);

        event = this.eventTestUtility.createDummyTextMessage(".put 3;blue", "456");
        String result = handlerController.handleTextMessageEvent(event);
        Assert.assertEquals("put", result);
    }
}
