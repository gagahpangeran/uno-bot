package com.bot.unobot;

import com.bot.unobot.GameEngine.GameMaster;
import com.bot.unobot.Player.Player;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class PlayerTest {

    public Player player;

    @Before
    public void setUp(){ player =  new Player("1234");;}

    @Test
    public void Player_Test(){

        Assert.assertThat(player.getId(), CoreMatchers.is("1234"));
        Assert.assertThat(player.getCards_collection().size(), CoreMatchers.is(0));



    }
}
