package com.bot.unobot.gameengine;

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

@RunWith(SpringRunner.class)
@SpringBootTest

public class GameMasterTest {

    public GameMaster gameMaster;

    @Before
    public void setUp(){ gameMaster =  new GameMaster();}

    @Test
    public void testAddPlayerSuccess(){
        gameMaster.addPlayer("1234");
        Assert.assertEquals(gameMaster.getNrOfPlayers(), 1);
        Assert.assertEquals(gameMaster.getPlayers().size(), 1);
    }

    @Test
    public void testNormalState() {
        gameMaster.setCurrentState(gameMaster.getNormalState());
        Assert.assertEquals(gameMaster.getCurrentState(), gameMaster.getNormalState());

    }

    @Test
    public void testPlusState() {
        gameMaster.setCurrentState(gameMaster.getPlusState());
        Assert.assertEquals(gameMaster.getCurrentState(), gameMaster.getPlusState());
    }

    @Test
    public void testGetRules() {
        String expectedOutput =
                "FINALRULE!!!\n" +
                        "- Syarat kartu anda diterima:\n" +
                        "1. kartu anda memiliki warna yang sama atau symbol yang sama dengan apa yang ditaruh pemain sebelumnya\n" +
                        "2. kartu angka tidak bisa dicombo.\n" +
                        "3. kartu yang bisa dicombo hanyalah plus,reverse,dan skip.\n" +
                        "4. Combo hanya berlaku jika dia sejenis. Jika tidak maka akan ditolak\n" +
                        "5. \n" +
                        "- Jika Pemain terkena skip, namun dia punya skip, dia tetap gabisa main dan tetap di skip\n" +
                        "- Jika pemain gak punya kartu, maka di harus draw. Setelah draw pemain tidak bisa jalan lagi. Harus tunggu gilirannya lagi.\n" +
                        "- Pemain yang kartunya abis duluan, dialah pemenangnya\n" +
                        "- Aturan Khusus WildCard:\n" +
                        "\n" +
                        "1. Jika anda mendapatkan WildCard atau Plus 4 maka cara menggunakannya adalah dengan mengetik set;[warna yang diinginkan] di akhir kalimat\n" +
                        "\n" +
                        "put Wildcard;special set;green\n" +
                        "put Wildcard;special 7;blue set blue\n" +
                        "put +2;green +2;yellow +4;special set;blue\n" +
                        "\n" +
                        "Untuk Wildcard selalu ketik di awal kalimat\n" +
                        "Untuk +4 selalu ketik di akhir kalimat\n" +
                        "\n" +
                        "Misal kartu terakhir yang dikeluarkan : 7 Red\n" +
                        "Kartu yang ada punya: 6 Yellow dan WildCard\n" +
                        "Cara memakai : put Wildcard;special 6;yellow set;yellow\n";
        Assert.assertEquals(gameMaster.getRule(), expectedOutput);
    }

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


}