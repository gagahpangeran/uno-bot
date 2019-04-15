package com.bot.unobot.handler;

import com.bot.unobot.command.CommandController;
import com.bot.unobot.command.HelpCommand;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.flex.component.Box;
import com.linecorp.bot.model.message.flex.component.Text;
import com.linecorp.bot.model.message.flex.container.Bubble;
import com.linecorp.bot.model.message.flex.unit.FlexLayout;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class HandlerController {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    private CommandController command;
    private String token;

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        System.out.println("event: " + event);
        this.token = event.getReplyToken();

        String msg = event.getMessage().getText();

        if (msg.charAt(0) == '!') {
            String command = msg.substring(1);
            this.addCommand(command);
            this.command.execute();
        }
    }

    public void addCommand(String command) {
        switch(command) {
            case "help":
                this.command = new HelpCommand(this);
                break;
        }
    }

    public void replyMessage(String msg) {
        TextMessage reply = new TextMessage(msg);
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(this.token, reply)).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("There's something wrong");
        }


    }

}
