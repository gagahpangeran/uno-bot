package com.bot.unobot.handler;

import com.bot.unobot.gameengine.GameMaster;
import com.bot.unobot.player.Player;
import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.profile.UserProfileResponse;
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
    public String handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        this.token = event.getReplyToken();
        String userId = event.getSource().getUserId();
        String groupId = event.getSource().getSenderId();

        String msg = event.getMessage().getText();

        if (msg.charAt(0) == '.') {
            String command = msg.substring(1);
            execute(command, userId, groupId);
            return command;
        }

        return event.getMessage().getText();
    }

    public String execute(String command, String userId, String groupId) {
        switch(command) {
            case "create":
                gameMasters.put(groupId, new GameMaster());
                this.replyMessage("Game berhasil dibuat");
                break;
            case "join":
                GameMaster game = gameMasters.get(groupId);
                game.addPlayer(userId);
                String name = this.getUserDisplayName(userId);
                this.replyMessage("Pemain " + name + " berhasil bergabung");
                this.pushMessage(userId, "Kamu bergabung ke permainan UNO di grup " + groupId);
                break;
        }
        return command;
    }

    public String replyMessage(String msg) {
        TextMessage reply = new TextMessage(msg);
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(this.token, reply)).get();
        } catch (Exception e) {
            System.out.println("There's something wrong with reply message");
        }
        return msg;
    }

    public String pushMessage(String userId, String msg) {
        TextMessage reply = new TextMessage(msg);
        try {
            lineMessagingClient.pushMessage(new PushMessage(userId, reply)).get();
        } catch (Exception e) {
            System.out.println("There's something wrong with push message");
        }
        return msg;
    }

    public String getUserDisplayName(String userId) {
        String name = "";
        try {
            name = lineMessagingClient.getProfile(userId).get().getDisplayName();
        } catch (Exception e) {
                System.out.println(e);
        }
        return name;
    }
}
