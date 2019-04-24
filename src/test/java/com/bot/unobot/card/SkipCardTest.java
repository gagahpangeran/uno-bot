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

public class SkipCardTest {

    public SkipCard card;

    @Test
    public void SkipCards_Test(){
        card = new SkipCard("skip","yellow");
        Assert.assertThat(card.getColor(), CoreMatchers.is("yellow"));
        Assert.assertThat(card.getName(), CoreMatchers.is("skip"));
    }
}
