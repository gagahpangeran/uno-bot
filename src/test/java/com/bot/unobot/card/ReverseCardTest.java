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
    public void ReverseCards_Test(){
        ReverseCard testCard = new ReverseCard(Color.GREEN);
        Assert.assertThat(testCard.getEffect(), CoreMatchers.is(Effect.REVERSE));
        Assert.assertEquals(Effect.REVERSE, testCard.getEffect());
        testCard.setColor(Color.BLUE);
        Assert.assertEquals("reverse", testCard.getSymbol().toLowerCase());
        Assert.assertThat(testCard.getColor(), CoreMatchers.is(Color.BLUE));
    }
}
