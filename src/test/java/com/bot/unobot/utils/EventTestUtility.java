package com.bot.unobot.utils;

import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.UserSource;

import java.time.Instant;

public class EventTestUtility {
    public MessageEvent<TextMessageContent> createDummyUserTextMessage(String text) {
        return new MessageEvent<>(
                "replyToken",
                new UserSource("userId"),
                new TextMessageContent("id", text),
                Instant.parse("2019-01-01T00:00:00.000Z")
        );
    }

    public MessageEvent<TextMessageContent> createDummyGroupTextMessage(String text) {
        return new MessageEvent<>(
                "replyToken",
                new GroupSource("groupId", "userId"),
                new TextMessageContent("id", text),
                Instant.parse("2019-01-01T00:00:00.000Z")
        );
    }
}
