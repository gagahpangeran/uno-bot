package com.bot.unobot.card;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class OrdinaryCardTest {

    public OrdinaryCard card;

    @Test
    public void testOrdinaryCards(){
        card = new OrdinaryCard("1",Color.YELLOW);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.YELLOW));
        Assert.assertThat(card.getSymbol(), CoreMatchers.is("1"));
        Assert.assertThat(card.getEffect(), CoreMatchers.is(Effect.NOTHING));

        card.setColor(Color.BLUE);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.BLUE));
    }
}
