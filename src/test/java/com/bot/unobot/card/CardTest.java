package com.bot.unobot.card;

import com.bot.unobot.gameengine.GameMaster;
import com.bot.unobot.player.Player;
import com.bot.unobot.card.Card;
import com.bot.unobot.card.OrdinaryCard;
import com.bot.unobot.card.PlusCard;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CardTest {

    public Card card;

    @Test
    public void cards_Test(){
        card = new OrdinaryCard("1",Color.YELLOW);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.YELLOW));
        Assert.assertThat(card.getName(), CoreMatchers.is("1"));

        PlusCard card = new PlusCard("Draw4",Color.SPECIAL,4);
        Assert.assertThat(card.getPlus(), CoreMatchers.is(4));
    }
}
