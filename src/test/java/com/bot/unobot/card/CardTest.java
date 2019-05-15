package com.bot.unobot.card;


import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class CardTest {

    @Test
    public void cardsTest(){
        Card card = new OrdinaryCard("1",Color.YELLOW);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.YELLOW));
        Assert.assertThat(card.getSymbol(), CoreMatchers.is("1"));

        PlusCard plusCard = new PlusCard(Color.SPECIAL,4);
        Assert.assertThat(plusCard.getPlus(), CoreMatchers.is(4));
    }
}
