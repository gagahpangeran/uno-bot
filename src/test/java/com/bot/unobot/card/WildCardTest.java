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
    public void WildCards_Test(){
        WildCard testCard = new WildCard(Color.SPECIAL);
        Assert.assertThat(testCard.getEffect(), CoreMatchers.is(Effect.CHANGE_COLOR));
        Assert.assertEquals(Effect.CHANGE_COLOR, testCard.getEffect());
        testCard.setColor(Color.BLUE);
        Assert.assertEquals("wild", testCard.getSymbol().toLowerCase());
        Assert.assertThat(testCard.getColor(), CoreMatchers.is(Color.BLUE));
    }
}
