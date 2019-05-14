package com.bot.unobot.gameengine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class StateTest {
    GameMaster gameMaster;
    GameState testState;

    @Before
    public void setUp(){
        gameMaster =  new GameMaster();
    }

    @Test
    public void ordinaryStateTest(){
    }
}
