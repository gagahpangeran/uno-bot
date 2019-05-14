package com.bot.unobot.card;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class PlusCardTest {

    @Test
    public void plusCardsTest(){
        PlusCard testCard = new PlusCard(Color.GREEN,2);
        Assert.assertThat(testCard.getEffect(), CoreMatchers.is(Effect.PLUS));
        Assert.assertEquals(Effect.PLUS, testCard.getEffect());
        Assert.assertEquals("+2", testCard.getSymbol());
        testCard.setColor(Color.BLUE);
        Assert.assertThat(testCard.getColor(), CoreMatchers.is(Color.BLUE));
    }
}
