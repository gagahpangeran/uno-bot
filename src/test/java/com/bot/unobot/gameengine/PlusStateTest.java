package com.bot.unobot.gameengine;

import com.bot.unobot.card.*;
import com.bot.unobot.player.Player;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest

public class PlusStateTest {
    GameMaster gameMaster = new GameMaster();

    public void setUP(){
        gameMaster.getPlayers().clear();
        gameMaster.addPlayer("a");
        gameMaster.addPlayer("b");
        gameMaster.addPlayer("c");
        gameMaster.initGame();
        System.out.println(gameMaster.getMessageToGroup());
        for(Player player:gameMaster.getPlayers()){
            switch (player.getId()){
                case "a":
                    Card[] zz = {new WildCard(Color.SPECIAL)
                    };
                    player.setCards(new ArrayList<>(Arrays.asList(zz)));
                    break;
                case "b":
                    Card[] zzz = {new OrdinaryCard("7", Color.RED),
                            new OrdinaryCard("6",Color.BLUE),
                            new SkipCard(Color.GREEN),
                            new OrdinaryCard("5",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzz)));
                    break;
                case "c":
                    Card[] zzzz = {new OrdinaryCard("5", Color.RED),
                            new OrdinaryCard("4",Color.BLUE),
                            new SkipCard(Color.RED),
                            new OrdinaryCard("3",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzzz)));
                    break;


            }
        }
    }



    @Test
    public void UNOTest(){
        setUP();
        gameMaster.setCurrentState(gameMaster.getCurrentState());
        gameMaster.getCurrentState().checkAndGetWinner("a");
        Assert.assertEquals("Selamat .... Player a aman. Anda tidak perlu ambil 2 kartu karena sudah bilang UNO", gameMaster.getMessageToGroup());
        gameMaster.getCurrentState().checkAndGetWinner("b");
        Assert.assertEquals("Belum ada yang UNO bang wkwkwk", gameMaster.getMessageToGroup());

    }

    @Test
    public void UNOTest1(){
        setUP();
        gameMaster.setCurrentState(gameMaster.getCurrentState());
        gameMaster.getCurrentState().checkAndGetWinner("b");
        Assert.assertEquals("karena pemain a telat bilang uno... jadi otomatis dia dapet dua kartu deh\n" +
                "Makanya jangan telat bos! ngohahahahahaha", gameMaster.getMessageToGroup());

    }

    @Test
    public void UNOTest2(){
        setUP();
        gameMaster.setCurrentState(gameMaster.getCurrentState());
        gameMaster.getCurrentState().put(gameMaster.getSpecificPlayer("a").getCardsCollection());
        gameMaster.getCurrentState().checkAndGetWinner("a");
        Assert.assertEquals("Selamat... pemain a berhasil meraih peringkat - 1\n" +
                "Game akan secara otomatis meng-kick pemain a", gameMaster.getMessageToGroup());

    }

    @Test
    public void drawTest(){
        setUP();
        int numOfCardsBefore = gameMaster.getPlayers().get(gameMaster.getCurrentState().getCurrPlayerIndex()).getCardsCollection().size();
        gameMaster.getCurrentState().draw();
        Assert.assertEquals(numOfCardsBefore+1, gameMaster.getSpecificPlayer("a").getCardsCollection().size());

    }
    @Test
    public void setAndGetPlayer(){
        setUP();
        gameMaster.getCurrentState().setCurrPlayerIndex(2);
        Assert.assertEquals(2, gameMaster.getCurrentState().getCurrPlayerIndex());
        Assert.assertEquals(Direction.CW, gameMaster.getCurrentState().getDirection());
    }



}
