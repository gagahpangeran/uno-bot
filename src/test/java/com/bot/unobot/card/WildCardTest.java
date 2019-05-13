package com.bot.unobot.card;

import com.bot.unobot.card.WildCard;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class WildCardTest {

    public WildCard card;

    @Test
    public void testWildCards(){
        card = new WildCard(Color.YELLOW);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.YELLOW));
        Assert.assertThat(card.getSymbol(), CoreMatchers.is("Wild"));
        Assert.assertThat(card.getEffect(), CoreMatchers.is(Effect.NOTHING));

        card.setColor(Color.BLUE);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.BLUE));
    }
}
