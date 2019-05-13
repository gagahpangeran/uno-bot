package com.bot.unobot.gameengine;

import com.bot.unobot.card.Card;
import com.bot.unobot.card.Color;
import com.bot.unobot.card.OrdinaryCard;
import com.bot.unobot.gameengine.GameMaster;
import com.bot.unobot.player.Player;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.constraints.AssertFalse;
import java.util.ArrayList;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest

public class GameMasterTest {

    public GameMaster gameMaster;

    @Before
    public void setUp(){ gameMaster =  new GameMaster();}

    @Test

    public void test_init_game() {

        String expected =
                "Selamat bergabung di Game UNO dengan kearifan lokal by UNO Bot\n" +
                        "\n" +
                        "Kenapa ada kearifan lokalnya? Karena Game UNO ini diutak-atik sedikit peraturannya untuk menyesuaikan kebiasaan orang Indonesia \"Yang Menang Yang Keluar Dulu\" :v\n" +
                        "\n" +
                        "Untuk bergabung menjadi player, ketik .join\n" +
                        "Untuk keluar dari permainan ketik .forfeit\n" +
                        "Untuk mengetahui status game saat ini, ketik .status\n" +
                        "Untuk mengatakan \"UNO!\", ketik .uno\n" +
                        "Untuk mengeluarkan kartu, silahkan ketik [Nama_Kartu] spasi [Warna_Kartu]\n" +
                        "Untuk menampilkan bantuan, ketik .help\n" +
                        "Untuk menampilkan peraturan permainan,ketik .rules\n" +
                        "Untuk memenangkan permainan, Anda harus jago (dan HOKI) tentunya :v\n" +
                        "\n" +
                        "Peraturan UNO sama dengan peraturan originalnya, hanya saja ketentuan pemenang diganti \"Yang Menang Yang Habis Duluan\"\n" +
                        "\n" +
                        "Selain itu semuanya sama\n" +
                        "\n" +
                        "Selamat bermain semuanya!\n" +
                        "\n" +
                        "\n"
                ;
        gameMaster.initGame();

    }

    @Test
    public void setteAndGetterTest(){
        GameMaster gameMaster1 = new GameMaster();
        gameMaster1.setMessageToGroup("Halo");
        gameMaster1.setMessageToPlayer("Hi");
        gameMaster1.setChampionPosition(2);
        Assert.assertEquals("Halo", gameMaster1.getMessageToGroup());
        Assert.assertEquals("Hi", gameMaster1.getMessageToPlayer());
        Assert.assertEquals(2, gameMaster1.getChampionPosition());
        Assert.assertEquals(0, gameMaster1.getNrOfPlayers());
        Assert.assertEquals(gameMaster1.getNormalState(), gameMaster1.getCurrentState());
        Assert.assertEquals(gameMaster1.getCurrentState(), gameMaster1.getNormalState());
        PlusState tempState = new PlusState(gameMaster1);
        gameMaster1.setCurrentState(tempState);
        Assert.assertEquals(tempState, gameMaster1.getCurrentState());
        int temp = gameMaster1.getPlusState().hashCode();
        Assert.assertEquals(temp, gameMaster1.getPlusState().hashCode());
       GameState state = gameMaster1.getCurrentState();
        Assert.assertEquals(state.hashCode(), gameMaster1.getCurrentState().hashCode());
        Assert.assertEquals(gameMaster1.getPlayers().size(), gameMaster1.getNrOfPlayers());
        Assert.assertEquals(0, gameMaster1.getPlayers().size());
        Assert.assertEquals(0, gameMaster1.getNewCards().size());
        Assert.assertEquals(0, gameMaster1.getTrashCards().size());




    }
    @Test
    public void isPuttableTest(){
       GameMaster gameMaster = new GameMaster();
       gameMaster.initGame();
        Card testCard = new OrdinaryCard("7",Color.RED);
        gameMaster.getTrashCards().push(testCard);
       Card[] temp = {testCard,new OrdinaryCard("7",Color.BLUE)};
       Assert.assertEquals(true, gameMaster.isPuttable(gameMaster.getTrashCards().peek(), new ArrayList<>(Arrays.asList(temp))));


    }

    @Test
    public void addPlayerTest(){
        GameMaster gameMaster = new GameMaster();
        gameMaster.addPlayer("1234");
        Assert.assertEquals(1, gameMaster.getPlayers().size());


    }

    @Test
    public void displayStringTest(){
        GameMaster gameMaster = new GameMaster();
        gameMaster.addPlayer("1234");
        gameMaster.initGame();
        String temp = gameMaster.getInfo();
        Assert.assertEquals(temp, gameMaster.getInfo());
    }



}