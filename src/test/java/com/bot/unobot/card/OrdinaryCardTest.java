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

public class OrdinaryCardTest {

    public OrdinaryCard card;

    @Test
    public void OrdinaryCards_Test(){
        card = new OrdinaryCard("1",Color.YELLOW);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.YELLOW));
        Assert.assertThat(card.getName(), CoreMatchers.is("1"));
    }
}
