package com.bot.unobot.card;

import com.bot.unobot.card.PlusCard;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class PlusCardTest {

    public PlusCard card;

    @Test
    public void PlusCards_Test(){
        PlusCard testCard = new PlusCard(Color.GREEN,2);
        Assert.assertThat(testCard.getEffect(), CoreMatchers.is(Effect.PLUS));
        Assert.assertEquals(Effect.PLUS, testCard.getEffect());
        Assert.assertEquals("+2", testCard.getSymbol());
        testCard.setColor(Color.BLUE);
        Assert.assertThat(testCard.getColor(), CoreMatchers.is(Color.BLUE));
    }
}
