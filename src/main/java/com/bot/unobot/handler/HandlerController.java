package com.bot.unobot.handler;

import com.bot.unobot.gameengine.GameMaster;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.PushMessage;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@LineMessageHandler
public class HandlerController {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    private String token;
    private String userId;
    private String groupId;
    private Source source;
    private HashMap<String, GameMaster> gameMasters = new HashMap<>();


    @EventMapping
    public String handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        this.token = event.getReplyToken();
        this.source = event.getSource();
        this.userId = source.getUserId();
        this.groupId = source.getSenderId();

        String msg = event.getMessage().getText();

        if (msg.charAt(0) == '.') {
            String command = msg.substring(1);
            execute(command);
            return command;
        }

        return event.getMessage().getText();
    }

    public String execute(String command) {
        switch(command) {
            case "help":
                this.help();
                break;
            case "create":
                this.create();
                break;
            case "join":
                this.join();
                break;
            case "stop":
                this.stop();
                break;
            case "leave":
                this.leave();
                break;
        }
        return command;
    }

    public String replyMessage(String message) {
        TextMessage reply = new TextMessage(message);
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(this.token, reply)).get();
        } catch (Exception e) {
            System.out.println("There's something wrong with reply message");
        }
        return message;
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

    public void help() {
        String help = "Semua perintah diawali dengan . (titik)\n" +
                "help : Memunculkan pesan help\n" +
                "create : Membuat game di dalam grup\n" +
                "join : Ikut ke suatu game di dalam grup\n" +
                "start : Memulai permainan\n" +
                "stop : Menghentikan paksa permainan\n" +
                "leave : Bot keluar grup";
        this.replyMessage(help);
    }

    public void create() {
        if (this.source instanceof GroupSource || this.source instanceof RoomSource) {
            gameMasters.put(this.groupId, new GameMaster());
            this.replyMessage("Game berhasil dibuat");
        } else {
            this.replyMessage("Tidak bisa membuat game selain di grup");
        }
    }

    public void join() {
        GameMaster game = gameMasters.get(this.groupId);
        if (game != null) {
            game.addPlayer(this.userId);
            String name = this.getUserDisplayName(this.userId);
            this.replyMessage("Pemain " + name + " berhasil bergabung");
            this.pushMessage(this.userId, "Kamu bergabung ke permainan UNO");
        } else {
            this.replyMessage("Belum ada game dibuat di grup ini");
        }
    }

    public void stop() {
        GameMaster game = gameMasters.get(this.groupId);
        if (game != null) {
            gameMasters.remove(this.groupId);
            this.replyMessage("Game dihentikan");
        } else {
            this.replyMessage("Belum ada game dibuat di grup ini");
        }
    }

    public void leave() {
        if (this.source instanceof GroupSource) {
            this.replyMessage("Sampai jumpa!");

            try {
                lineMessagingClient.leaveGroup(groupId).get();
            } catch (Exception e) {
                System.out.println(e);
            }
        } else {
            this.replyMessage("Tidak bisa keluar selain di grup.");
        }
    }
}
