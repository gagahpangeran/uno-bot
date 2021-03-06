package com.bot.unobot.handler;

import com.bot.unobot.card.Card;
import com.bot.unobot.gameengine.GameMaster;

import com.bot.unobot.player.Player;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

@LineMessageHandler
public class HandlerController {

    @Autowired
    private LineMessagingClient lineMessagingClient;

    private String token;
    private String userId;
    private String groupId;
    private String content;
    private Source source;
    private HashMap<String, GameMaster> gameMasters = new HashMap<>();
    private HashMap<String, String> playerGroupGame = new HashMap<>();


    @EventMapping
    public String handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
        this.token = event.getReplyToken();
        this.source = event.getSource();
        this.userId = source.getUserId();
        this.groupId = source.getSenderId();

        String msg = event.getMessage().getText();

        if (msg.charAt(0) == '.') {
            String command = msg.substring(1).split(" ")[0];
            this.content = msg.substring(1+command.length());
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
            case "start":
                this.start();
                break;
            case "info":
                this.info();
                break;
            case "put":
                this.put();
                break;
            case "draw":
                this.draw();
                break;
            case "uno":
                this.uno();
                break;
            case "stop":
                this.stop();
                break;
            case "leave":
                this.leave();
                break;
             default:
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
            if(gameMasters.get(this.groupId) == null) {
                gameMasters.put(this.groupId, new GameMaster());
                this.replyMessage("Game berhasil dibuat");
            } else {
                this.replyMessage("Sudah ada game dibuat di grup ini");
            }
        } else {
            this.replyMessage("Tidak bisa membuat game selain di grup");
        }
    }

    public void join() {
        String group = playerGroupGame.get(this.userId);
        GameMaster game = gameMasters.get(this.groupId);
        String name = this.getUserDisplayName(this.userId);

        if(game != null) {
            if (game.isStart()) {
                this.replyMessage(name + " tidak bisa ikut karena game sudah dimulai");
            } else if (group != null) {
                this.replyMessage("Anda sedang bermain di grup lain");
            } else if (game != null) {
                game.addPlayer(this.userId, name);
                playerGroupGame.put(userId, this.groupId);

                this.replyMessage("Pemain " + name + " berhasil bergabung");
                this.pushMessage(this.userId, "Kamu bergabung ke permainan UNO");
            }
        } else {
            this.replyMessage("Belum ada game dibuat di grup ini");
        }
    }

    public void start() {
        GameMaster game = gameMasters.get(this.groupId);
        if (game != null) {
            if(game.isStart()) {
                this.replyMessage("Game sudah pernah dimulai");
            } else if (game.getPlayers().size() < 2) {
                this.replyMessage("Minimal 2 pemain untuk memulai permainan");
            } else {
                game.initGame();
                this.replyMessage(game.getMessageToGroup() + "\n\n" + game.getInfo());

                ArrayList<Player> players = game.getPlayers();
                for (Player player : players) {
                    this.pushMessage(player.getId(), game.getMessageForPlayer(player.getId()));
                }
            }
        } else {
            this.replyMessage("Belum ada game dibuat di grup ini");
        }
    }

    public void info() {
        GameMaster game = gameMasters.get(this.groupId);
        this.replyMessage(game.getInfo());
    }

    public void put() {
        String groupId = playerGroupGame.get(this.userId);
        GameMaster game = gameMasters.get(groupId);

        String[] cardString = this.content.substring(1).toLowerCase().split(" ");
        ArrayList<String> cards = new ArrayList<>(Arrays.asList(cardString));

        System.out.println(cards);

        if (cards.contains("wild;special") || cards.contains("+4;special")) {
            String colorSetByPlayer = cards.get(cards.size() - 1).split(";")[1];
            cards.remove(cards.size() - 1);
            ArrayList<Card> tesss = game.converStringstoCards(cards, colorSetByPlayer);
//            System.out.println(tesss.get(0).getColor());
            game.put(tesss);
        } else {
            game.put(game.converStringstoCards(cards));
        }
        String result = game.getMessageToPlayer();

        if (result.equals(game.putSucceed())) {
            this.pushMessage(groupId, game.getInfo());
            this.replyMessage(result);

            String nextPlayer = game.getCurrentPlayerID();
            this.pushMessage(nextPlayer, "Sekarang giliranmu\n\n" + game.getMessageForPlayer(nextPlayer));
        } else {
            this.replyMessage(result);
        }
    }

    public void draw() {
        String groupId = playerGroupGame.get(this.userId);

        if (groupId != null) {
            GameMaster game = gameMasters.get(groupId);
            String result = game.getCurrentState().draw(this.userId);
            this.replyMessage(result);
            this.pushMessage(groupId, game.getInfo());
        }
    }

    public void uno() {
        GameMaster game = gameMasters.get(this.groupId);
        game.getCurrentState().checkAndGetWinner(this.userId);
        this.replyMessage(game.getMessageToGroup());
    }

    public void stop() {
        GameMaster game = gameMasters.get(this.groupId);
        if (game != null) {
            gameMasters.remove(this.groupId);
            playerGroupGame.remove(this.userId);
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

    public void hardcode() {

    }
}
