package com.bot.unobot;

import com.bot.unobot.handler.HandlerController;
import com.bot.unobot.utils.EventTestUtility;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest(properties = "line.bot.handler.enabled=false", classes = UnoBotApplication.class)
public class HandlerTest {
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
    public void testHandleTextMessageEvent() {
        MessageEvent<TextMessageContent> event = this.eventTestUtility.createDummyUserTextMessage("Test");
        String result = handlerController.handleTextMessageEvent(event);
        Assert.assertEquals("Test", result);

        event = this.eventTestUtility.createDummyUserTextMessage(".test");
        result = handlerController.handleTextMessageEvent(event);
        Assert.assertEquals("test", result);
    }
}