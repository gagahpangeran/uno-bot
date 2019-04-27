package com.bot.unobot.card;

import com.bot.unobot.card.PlusCard;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class PlusCardTest {

    public PlusCard card;

    @Test
    public void OrdinaryCards_Test(){
        card = new PlusCard("1",Color.YELLOW, 4);
        Assert.assertThat(card.getColor(), CoreMatchers.is(Color.YELLOW));
        Assert.assertThat(card.getName(), CoreMatchers.is("1"));
        Assert.assertThat(card.getPlus(), CoreMatchers.is(4));
    }
}
