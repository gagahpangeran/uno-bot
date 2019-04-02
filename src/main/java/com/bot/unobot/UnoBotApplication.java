package com.bot.unobot;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.xml.soap.Text;
import java.util.concurrent.ExecutionException;

@SpringBootApplication
@LineMessageHandler
public class UnoBotApplication {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    public static void main(String[] args) {
        SpringApplication.run(UnoBotApplication.class, args);
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        System.out.println("event: " + event);
        String msg = event.getMessage().getText();
        String replyToken = event.getReplyToken();

        // Placeholder
        if (msg.charAt(0) == '!') {
            replyMessage(replyToken, msg.substring(1));
        }
    }

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }

    public void replyMessage(String token, String msg) {
        TextMessage reply = new TextMessage(msg);
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(token, reply)).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("There's something wrong");
        }
    }
}
