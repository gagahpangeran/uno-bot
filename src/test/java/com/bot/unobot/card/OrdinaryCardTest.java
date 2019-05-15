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

    @Test
<<<<<<< HEAD
    public void ordinaryCardsTest(){
=======

    public void OrdinaryCards_Test(){
>>>>>>> 5c03f0a61e934e8619dc350671d789cf8ad1284e
        OrdinaryCard testCard = new OrdinaryCard("7",Color.GREEN);
        Assert.assertThat(testCard.getEffect(), CoreMatchers.is(Effect.NOTHING));
        Assert.assertEquals(Effect.NOTHING, testCard.getEffect());
        testCard.setColor(Color.BLUE);
        Assert.assertThat(testCard.getColor(), CoreMatchers.is(Color.BLUE));
        testCard.setSymbol("8");
        Assert.assertThat(testCard.getSymbol(), CoreMatchers.is("1"));
    }
}
