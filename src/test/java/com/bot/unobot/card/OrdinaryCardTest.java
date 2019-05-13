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

    public void OrdinaryCards_Test(){
        OrdinaryCard testCard = new OrdinaryCard("7",Color.GREEN);
        Assert.assertThat(testCard.getEffect(), CoreMatchers.is(Effect.NOTHING));
        Assert.assertEquals(Effect.NOTHING, testCard.getEffect());
        testCard.setColor(Color.BLUE);
        Assert.assertThat(testCard.getColor(), CoreMatchers.is(Color.BLUE));
    }
}
