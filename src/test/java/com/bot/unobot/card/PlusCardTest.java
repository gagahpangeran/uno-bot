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
    public void testPlusCards(){
        card = new PlusCard(Color.YELLOW, 4);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.YELLOW));
        Assert.assertThat(card.getSymbol(), CoreMatchers.is("+4"));
        Assert.assertThat(card.getPlus(), CoreMatchers.is(4));
        Assert.assertThat(card.getEffect(), CoreMatchers.is(Effect.PLUS));

        card.setColor(Color.BLUE);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.BLUE));
    }
}
