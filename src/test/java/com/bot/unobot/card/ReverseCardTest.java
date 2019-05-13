package com.bot.unobot.card;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class ReverseCardTest {

    public ReverseCard card;

    @Test
    public void testReverseCards(){
        card = new ReverseCard(Color.YELLOW);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.YELLOW));
        Assert.assertThat(card.getSymbol(), CoreMatchers.is("Reverse"));
        Assert.assertThat(card.getEffect(), CoreMatchers.is(Effect.REVERSE));

        card.setColor(Color.BLUE);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.BLUE));
    }
}
