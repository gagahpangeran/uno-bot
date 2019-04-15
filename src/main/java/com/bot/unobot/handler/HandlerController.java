package com.bot.unobot.handler;

import com.bot.unobot.GameEngine.GameMaster;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.concurrent.ExecutionException;

@LineMessageHandler
public class HandlerController {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    private String token;
    private HashMap<String, GameMaster> gameMasters = new HashMap<>();

    @EventMapping
    public void handleDefaultMessageEvent(Event event) {
        System.out.println("event: " + event);
    }

    @EventMapping
    public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        this.token = event.getReplyToken();
        String userId = event.getSource().getUserId();
        String groupId = event.getSource().getSenderId();

        String msg = event.getMessage().getText();

        if (msg.charAt(0) == '.') {
            String command = msg.substring(1);
            execute(command, userId, groupId);
        }
    }

    public void execute(String command, String userId, String groupId) {
        switch(command) {
            case "create":
                gameMasters.put(groupId, new GameMaster());
                this.replyMessage("Game berhasil dibuat");
                break;
            case "join":
                GameMaster game = gameMasters.get(groupId);
                game.add_player(userId);
                this.replyMessage("Pemain " + userId + " berhasil bergabung");
                this.pushMessage(userId, "Kamu bergabung ke permainan UNO di grup " + groupId);
                break;
        }
    }

    public void replyMessage(String msg) {
        TextMessage reply = new TextMessage(msg);
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(this.token, reply)).get();
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("There's something wrong with reply message");
        }
    }

    public void pushMessage(String userId, String msg) {
        TextMessage reply = new TextMessage(msg);
        try {
            lineMessagingClient.pushMessage(new PushMessage(userId, reply)).get();
        } catch (InterruptedException | ExecutionException e) {
            this.replyMessage("Tolong add aku ya!");
            System.out.println("There's something wrong with push message");
            System.out.println(e);
        }
    }

}
