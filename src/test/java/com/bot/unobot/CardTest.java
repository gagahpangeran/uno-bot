package com.bot.unobot;

import com.bot.unobot.GameEngine.GameMaster;
import com.bot.unobot.Player.Player;
import com.bot.unobot.TestCards.Card;
import com.bot.unobot.TestCards.OrdinaryCard;
import com.bot.unobot.TestCards.PlusCard;
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
    public void Cards_Test(){
        card = new OrdinaryCard("1","yellow");
        Assert.assertThat(card.getColor(), CoreMatchers.is("yellow"));
        Assert.assertThat(card.getName(), CoreMatchers.is("1"));

        PlusCard card = new PlusCard("Draw4","black",4);
        Assert.assertThat(card.getPlus(), CoreMatchers.is(4));

    }



}
