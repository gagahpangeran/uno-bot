package com.bot.unobot.gameengine;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DirectionTest {

    @Test
    public void Test(){
        Direction direction = Direction.CW;
        Assert.assertEquals("clockwise", direction.toString());
        Assert.assertEquals(1,direction.getValue() );
        Assert.assertEquals(Direction.CCW, direction.getOpposite());

    }

}
