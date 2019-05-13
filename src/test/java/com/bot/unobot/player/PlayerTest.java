package com.bot.unobot.player;

import com.bot.unobot.card.Card;
import com.bot.unobot.card.Color;
import com.bot.unobot.card.OrdinaryCard;
import com.bot.unobot.card.WildCard;
import com.bot.unobot.gameengine.GameMaster;
import com.bot.unobot.player.Player;
import org.hamcrest.CoreMatchers;
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

public class PlayerTest {

    public Player player;
    public GameMaster gameMaster;

    @Test
    public void testCreatePlayer(){
        Player player = new Player("1234");
        Assert.assertThat(player.getId(), CoreMatchers.is("1234"));
        Assert.assertThat(player.getCardsCollection().size(), CoreMatchers.is(0));
    }

    @Test
    public void testSuccessShowsPlayersCards(){
        Player player = new Player("1234");
        Card[] zz = {new WildCard(Color.RED)};
        player.setCards(new ArrayList<>(Arrays.asList(zz)));
        Assert.assertThat(player.getCardsCollection().size(), CoreMatchers.is(1));

        Assert.assertThat(player.showsPlayersCards(), CoreMatchers.containsString("1."));
    }
}
