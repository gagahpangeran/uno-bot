package com.bot.unobot.gameengine;

import com.bot.unobot.gameengine.GameMaster;
import com.bot.unobot.gameengine.OrdinaryCardState;
import com.bot.unobot.gameengine.State;
import com.bot.unobot.player.Player;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest

public class StateTest {
    GameMaster gameMaster;
    State testState;

    @Before
    public void setUp(){
        gameMaster =  new GameMaster();;
        testState = new OrdinaryCardState(gameMaster);
    }

    @Test
    public void OrdinaryStateTest(){
//        OrdinaryCardState ordinaryCardState = (OrdinaryCardState)testState;
//        ordinaryCardState.setNextColor("yellow");
//        ordinaryCardState.gameMaster.add_player(new Player("Joni"));
//        ordinaryCardState.get_current_player();
//        String display = "Kini giliran Anda untuk bermain :\n" +
//                "\n" +
//                "Kartu Anda adalah sebagai berikut: \n";
//        Assert.assertTrue( ordinaryCardState.display.contains(display));
//        Assert.assertThat(ordinaryCardState.color_set_by_player, CoreMatchers.is("yellow"));
//        //Assert.assertThat(ordinaryCardState.display, CoreMatchers.is(""));
    }
}
