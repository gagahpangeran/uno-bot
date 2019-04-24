package com.bot.unobot;

import com.bot.unobot.handler.HandlerController;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.UserSource;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

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
        MessageEvent<TextMessageContent> event = this.eventTestUtility.createDummyTextMessage("Test");
        String result = handlerController.handleTextMessageEvent(event);
        Assert.assertEquals("Test", result);

        event = this.eventTestUtility.createDummyTextMessage(".test");
        result = handlerController.handleTextMessageEvent(event);
        Assert.assertEquals("test", result);
    }

    @Test
    public void testExecute() {
        String result = handlerController.execute("test", "userId", "groupId");
        Assert.assertEquals("test", result);

        result = handlerController.execute("create", "userId", "groupId");
        Assert.assertEquals("create", result);

        result = handlerController.execute("join", "userId", "groupId");
        Assert.assertEquals("join", result);
    }
}

class EventTestUtility {
    public MessageEvent<TextMessageContent> createDummyTextMessage(String text) {
        return new MessageEvent<>(
                "replyToken",
                new UserSource("userId"),
                new TextMessageContent("id", text),
                Instant.parse("2019-01-01T00:00:00.000Z")
        );
    }
}