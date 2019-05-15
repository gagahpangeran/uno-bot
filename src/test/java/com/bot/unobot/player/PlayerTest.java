package com.bot.unobot.player;

import com.bot.unobot.card.*;
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

    @Before
    public void setUp(){player =  new Player("1234");}

    @Test
    public void playerTest(){
        Assert.assertThat(player.getId(), CoreMatchers.is("1234"));
        Assert.assertThat(player.getCardsCollection().size(), CoreMatchers.is(0));
        Card[] cards = {new OrdinaryCard("7", Color.YELLOW),
                new WildCard(Color.SPECIAL),
                new PlusCard(Color.SPECIAL, 4),
                new PlusCard(Color.BLUE, 2),
                new PlusCard(Color.RED, 2)
        };
        player.setCards(new ArrayList<>(Arrays.asList(cards)));
        Assert.assertEquals(5, player.getCardsCollection().size());
        Assert.assertEquals(false, player.isUNO());
        player.setUNO(true);
        Assert.assertEquals(true, player.isUNO());
    }

    @Test
    public void testSuccessShowsPlayersCards() {
        Player player = new Player("1234");
        Card[] zz = {new WildCard(Color.RED)};
        player.setCards(new ArrayList<>(Arrays.asList(zz)));
        Assert.assertThat(player.getCardsCollection().size(), CoreMatchers.is(1));
    }
}
