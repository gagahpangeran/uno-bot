package com.bot.unobot.gameengine;

import com.bot.unobot.card.*;
import com.bot.unobot.player.Player;
import org.junit.Assert;
import org.junit.Before;
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

    @Before
    public void setUp(){
        gameMaster.getPlayers().clear();
        gameMaster.addPlayer("a","a");
        gameMaster.addPlayer("b","a");
        gameMaster.addPlayer("c","a");
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
        gameMaster.getTrashCards().push(new PlusCard(Color.BLUE,2));
        gameMaster.setCurrentState(gameMaster.getPlusState());
        gameMaster.getCurrentState().setLastCard(gameMaster.getTrashCards().peek());
    }

    @Test
    public void setterAndGettertest(){
        Assert.assertEquals(0,gameMaster.getCurrentState().getCurrPlayerIndex() );
        Assert.assertEquals(Direction.CW, gameMaster.getCurrentState().getDirection());
        Assert.assertEquals(Color.BLUE, gameMaster.getCurrentState().getLastCard().getColor());
        Assert.assertEquals("+2", gameMaster.getCurrentState().getLastCard().getSymbol());

    }

    @Test

    public void putTest1(){
        Card[] cards = {new PlusCard(Color.BLUE,2),
                new PlusCard(Color.RED,2)};
        ArrayList<Card> tempCards = new ArrayList<>(Arrays.asList(cards));
        gameMaster.getCurrentState().put(tempCards);
        Assert.assertEquals(gameMaster.putSucceed(), gameMaster.getMessageToPlayer());
    }
}
