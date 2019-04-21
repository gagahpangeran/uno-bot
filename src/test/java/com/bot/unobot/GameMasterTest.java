package com.bot.unobot;

import com.bot.unobot.gameengine.GameMaster;
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

public class GameMasterTest {

    public GameMaster gameMaster;

    @Before
    public void setUp(){ gameMaster =  new GameMaster();}

    @Test

    public void test_init_game() {

        String expected =
                "Selamat bergabung di Game UNO dengan Kearifan Lokal by UNOBot\n" +
                        "\n" +
                        "Kenapa ada Kearifan Lokanya? Karena Game UNO ini diutak-atik sedikit peraturannya untuk menyesuaikan kebiasaan orang Indonesia \"Yang Menang Yang Keluar Dulu\" :v\n" +
                        "\n" +
                        "Untuk bergabung menjadi player ketik .join\n" +
                        "Untuk keluar permainan ketik .forfeit\n" +
                        "Untuk mengetahui status game saat ini, ketik saja .status\n" +
                        "Untuk mengatakan \"UNO!\" ketik .uno\n" +
                        "Untuk mengeluarkan kartu, silahkan ketik [Nama_Kartu] spasi [Warna_Kartu]\n" +
                        "Untuk menampilkan kembali tampilan ini ketik .help\n" +
                        "Untuk memenangkan permainan, anda harus jago (dan HOKI) tentunya :v\n" +
                        "\n" +
                        "Peraturan UNO sama dengan peraturan originalnya, hanya saja ketentuan pemenang diganti \"Yang Menang Yang Habis Duluan\"\n" +
                        "\n" +
                        "Selain itu semuanya sama\n" +
                        "\n" +
                        "Selamat bermain semuanya!\n" +
                        "\n" +
                        "\n";
        gameMaster.initGame();
        Assert.assertThat(gameMaster.getStringOnDisplay(), CoreMatchers.is(expected));
    }

    @Test

    public void test_add_player(){
        gameMaster.addPlayer("Jono");
        Assert.assertEquals(gameMaster.getPlayers().size(), 1);
    }

    @Test
    public void test_remove_player() {
        gameMaster.addPlayer("Jono");
        Assert.assertEquals(gameMaster.getPlayers().size(), 1);

        gameMaster.removePlayer(Jono);
        Assert.assertEquals(gameMaster.getPlayers().size(), 0);
    }

}