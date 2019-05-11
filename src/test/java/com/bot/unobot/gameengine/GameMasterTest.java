//package com.bot.unobot.gameengine;
//
//import com.bot.unobot.gameengine.GameMaster;
//import com.bot.unobot.player.Player;
//import org.hamcrest.CoreMatchers;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import javax.validation.constraints.AssertFalse;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//
//public class GameMasterTest {
//
//    public GameMaster gameMaster;
//
//    @Before
//    public void setUp(){ gameMaster =  new GameMaster();}
//
//    @Test
//
//    public void test_init_game() {
//
//        String expected =
//                "Selamat bergabung di Game UNO dengan kearifan lokal by UNO Bot\n" +
//                        "\n" +
//                        "Kenapa ada kearifan lokalnya? Karena Game UNO ini diutak-atik sedikit peraturannya untuk menyesuaikan kebiasaan orang Indonesia \"Yang Menang Yang Keluar Dulu\" :v\n" +
//                        "\n" +
//                        "Untuk bergabung menjadi player, ketik .join\n" +
//                        "Untuk keluar dari permainan ketik .forfeit\n" +
//                        "Untuk mengetahui status game saat ini, ketik .status\n" +
//                        "Untuk mengatakan \"UNO!\", ketik .uno\n" +
//                        "Untuk mengeluarkan kartu, silahkan ketik [Nama_Kartu] spasi [Warna_Kartu]\n" +
//                        "Untuk menampilkan bantuan, ketik .help\n" +
//                        "Untuk menampilkan peraturan permainan,ketik .rules\n" +
//                        "Untuk memenangkan permainan, Anda harus jago (dan HOKI) tentunya :v\n" +
//                        "\n" +
//                        "Peraturan UNO sama dengan peraturan originalnya, hanya saja ketentuan pemenang diganti \"Yang Menang Yang Habis Duluan\"\n" +
//                        "\n" +
//                        "Selain itu semuanya sama\n" +
//                        "\n" +
//                        "Selamat bermain semuanya!\n" +
//                        "\n" +
//                        "\n"
//                ;
//        gameMaster.initGame();
//        Assert.assertThat(gameMaster.getStringOnDisplay(), CoreMatchers.is(expected));
//    }
//
//    @Test
//
//    public void test_add_player(){
//        gameMaster.addPlayer("1234");
//        Assert.assertEquals(gameMaster.players.size(), 1);
//    }
//
//    @Test
//    public void test_remove_player() {
//        gameMaster.addPlayer("1234");
//        Assert.assertEquals(gameMaster.players.size(), 1);
//
//        //gameMaster.removePlayer(gameMaster.findPlayer("1234"));
//       // Assert.assertEquals(gameMaster.players.size(), 0);
//    }
//
//    @Test
//    public void test_findPlayer_Null() {
//        gameMaster.addPlayer("1234");
////        Assert.assertEquals(gameMaster.findPlayer("1234").getId(), "1234");
//    }
//
//}