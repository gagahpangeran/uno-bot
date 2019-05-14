package com.bot.unobot.gameengine;

import com.bot.unobot.card.*;
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
import java.util.List;

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
        gameMaster1.initGame();
        Assert.assertEquals(0, gameMaster1.getPlayers().size());
        Assert.assertEquals(107, gameMaster1.getNewCards().size());
        Assert.assertEquals(1, gameMaster1.getTrashCards().size());
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
        ReverseCard tempCard = new ReverseCard(Color.BLUE);
        gameMaster.getTrashCards().push(tempCard);
        gameMaster.getSpecificPlayer("1234").getCardsCollection().add(tempCard);
        ArrayList<String> tempArrayList = new ArrayList<>();
        tempArrayList.add("reverse;blue");
        gameMaster.put(gameMaster.converStringstoCards(tempArrayList));
        temp = gameMaster.getInfo();
        Assert.assertEquals(temp, gameMaster.getInfo());
        temp = gameMaster.getMessageForPlayer(gameMaster.getPlayers().get(0).getId());
        Assert.assertEquals(temp, gameMaster.getMessageForPlayer("1234"));
        Assert.assertEquals(true, gameMaster.getRule().toLowerCase().contains("final"));
        Assert.assertEquals(true, gameMaster.unoSafeString("1234").toLowerCase().contains("ambil 2 kartu"));
        Assert.assertEquals(true, gameMaster.failedToWin("1234").toLowerCase().contains("jangan telat"));
        Assert.assertEquals(true, gameMaster.winnerString("1234").toLowerCase().contains("selamat"));

        String rule = "FINALRULE!!!\n" +
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
        Assert.assertEquals(rule, gameMaster.getRule());
        Assert.assertEquals(true, gameMaster.putSucceed().toLowerCase().contains("sukses"));
        Assert.assertEquals(true, gameMaster.putFailed().toLowerCase().contains("tidak valid"));








    }

    @Test
    public void  displayInfoTest(){
        GameMaster gameMaster =  new GameMaster();
        gameMaster.addPlayer("1234");
        gameMaster.initGame();
        String string1 =  "4. Combo hanya berlaku jika dia sejenis. Jika tidak maka akan ditolak\n" +
                "5. \n" ;
        String string2 =  "- Pemain yang kartunya abis duluan, dialah pemenangnya\n" +
                "- Aturan Khusus WildCard:\n" ;
        String string3 = "\n" +
                "put Wildcard;special set;green\n" ;
        String string4 = "\n" +
                "Untuk Wildcard selalu ketik di awal kalimat\n";
        String string5 =   "Misal kartu terakhir yang dikeluarkan : 7 Red\n" +
                "Kartu yang ada punya: 6 Yellow dan WildCard\n" ;
        Assert.assertEquals(true, gameMaster.getRule().contains(string1));
        Assert.assertEquals(true, gameMaster.getRule().contains(string2));
        Assert.assertEquals(true, gameMaster.getRule().contains(string3));
        Assert.assertEquals(true, gameMaster.getRule().contains(string4));
        Assert.assertEquals(true, gameMaster.getRule().contains(string5));

    }

    @Test
    public void restOfTheMethodsTest(){
        GameMaster gameMaster = new GameMaster();
        gameMaster.addPlayer("123");
        gameMaster.initGame();
        gameMaster.addToTrash(gameMaster.getSpecificPlayer("123").getCardsCollection());
        Assert.assertEquals(8, gameMaster.getTrashCards().size());
        gameMaster.recycleTrashCards();
        Assert.assertEquals(108, gameMaster.getNewCards().size());



    }

    @Test
    public void convertStringToCardsTest(){
        GameMaster gameMasters1 = new GameMaster();
        gameMasters1.addPlayer("1");
        gameMasters1.initGame();
        //debug
        Card tempCard = new WildCard(Color.SPECIAL);
        Card tempCard1 = gameMasters1.getSpecificPlayer("1").getCardsCollection().get(0);

        gameMasters1.getSpecificPlayer("1").getCardsCollection().add(tempCard);
        //debug
        Assert.assertEquals(true, gameMasters1.getSpecificPlayer("1").getCardsCollection().contains(tempCard));
        Assert.assertEquals(true, gameMasters1.getSpecificPlayer("1").getCardsCollection().contains(tempCard1));
        gameMasters1.getCurrentState().setLastCard(gameMasters1.getTrashCards().peek());
        String[] inputanUser = {tempCard.getSymbol()+";"+tempCard.getColor(),
                tempCard1.getSymbol()+";"+tempCard1.getColor()};
        List<String> temps =  new ArrayList<>(Arrays.asList(inputanUser));
        List<Card> result = gameMasters1.converStringstoCards(temps,gameMasters1.getSpecificPlayer("1").getCardsCollection().get(0).getColor()+"");
        Assert.assertEquals(2, result.size());

    }




//    @Test
//    public void convertStringToCardsTest_RED(){
//        GameMaster gameMasters1 = new GameMaster();
//        gameMasters1.addPlayer("1");
//        gameMasters1.initGame();
//        //debug
//        gameMasters1.getTrashCards().push(new OrdinaryCard("3",Color.RED));
//
//        gameMasters1.getSpecificPlayer("1").getCardsCollection().get(0).setColor(Color.RED);
//        Card tempCard = new WildCard(Color.SPECIAL);
//        Card tempCard1 = gameMasters1.getSpecificPlayer("1").getCardsCollection().get(0);
//
//        gameMasters1.getSpecificPlayer("1").getCardsCollection().add(tempCard);
//        //debug
//        Assert.assertEquals(true, gameMasters1.getSpecificPlayer("1").getCardsCollection().contains(tempCard));
//        Assert.assertEquals(true, gameMasters1.getSpecificPlayer("1").getCardsCollection().contains(tempCard1));
//        gameMasters1.getCurrentState().setLastCard(gameMasters1.getTrashCards().peek());
//        String[] inputanUser = {tempCard.getSymbol()+";"+tempCard.getColor(),
//                                tempCard1.getSymbol()+";"+tempCard1.getColor()};
//        ArrayList<String> temps =  new ArrayList<>(Arrays.asList(inputanUser));
//        ArrayList<Card> result = gameMasters1.converStringstoCards(temps,Color.RED+"");
//        Assert.assertEquals(2, result.size());
//
//    }
//
//    @Test
//    public void convertStringToCardsTest_BLUE(){
//        GameMaster gameMasters2 = new GameMaster();
//        gameMasters2.addPlayer("12");
//        gameMasters2.initGame();
//        //debug
//       gameMasters2.getTrashCards().push(new OrdinaryCard("0",Color.RED));
//
//        gameMasters2.getSpecificPlayer("12").getCardsCollection().get(0).setColor(Color.BLUE);
//        Card tempCard = new WildCard(Color.SPECIAL);
//        Card tempCard1 = gameMasters2.getSpecificPlayer("12").getCardsCollection().get(0);
//        gameMasters2.getSpecificPlayer("12").getCardsCollection().add(tempCard);
//        //debug
//        Assert.assertEquals(true, gameMasters2.getSpecificPlayer("12").getCardsCollection().contains(tempCard));
//        Assert.assertEquals(true, gameMasters2.getSpecificPlayer("12").getCardsCollection().contains(tempCard1));
//        gameMasters2.getCurrentState().setLastCard(gameMasters2.getTrashCards().peek());
//        String[] inputanUser = {tempCard.getSymbol()+";"+tempCard.getColor(),
//                tempCard1.getSymbol()+";"+tempCard1.getColor()};
//        ArrayList<String> temps =  new ArrayList<>(Arrays.asList(inputanUser));
//        ArrayList<Card> result = gameMasters2.converStringstoCards(temps,Color.BLUE+"");
//        Assert.assertEquals(2, result.size());
//
//    }
//
//    @Test
//    public void convertStringToCardsTest_GREEN(){
//        GameMaster gameMasters3 = new GameMaster();
//        gameMasters3.addPlayer("123");
//        gameMasters3.initGame();
//        //debug
//       gameMasters3.getTrashCards().push(new OrdinaryCard("0",Color.RED));
//
//        gameMasters3.getSpecificPlayer("123").getCardsCollection().get(0).setColor(Color.GREEN);
//        Card tempCard = new WildCard(Color.SPECIAL);
//        Card tempCard1 = gameMasters3.getSpecificPlayer("123").getCardsCollection().get(0);
//
//        gameMasters3.getSpecificPlayer("123").getCardsCollection().add(tempCard);
//        //debug
//        Assert.assertEquals(true, gameMasters3.getSpecificPlayer("123").getCardsCollection().contains(tempCard));
//        Assert.assertEquals(true, gameMasters3.getSpecificPlayer("123").getCardsCollection().contains(tempCard1));
//        gameMasters3.getCurrentState().setLastCard(gameMasters3.getTrashCards().peek());
//        String[] inputanUser = {tempCard.getSymbol()+";"+tempCard.getColor(),
//                tempCard1.getSymbol()+";"+tempCard1.getColor()};
//        ArrayList<String> temps =  new ArrayList<>(Arrays.asList(inputanUser));
//        ArrayList<Card> result = gameMasters3.converStringstoCards(temps,Color.GREEN+"");
//        Assert.assertEquals(2, result.size());
//
//    }
//
//    @Test
//    public void convertStringToCardsTest_YELLOW(){
//        GameMaster gameMastersz = new GameMaster();
//        gameMastersz.addPlayer("1234");
//        gameMastersz.initGame();
//        //debug
//        gameMastersz.getTrashCards().push(new OrdinaryCard("0",Color.RED));
//
//        gameMastersz.getSpecificPlayer("1234").getCardsCollection().get(0).setColor(Color.YELLOW);
//        Card tempCard = new WildCard(Color.SPECIAL);
//        Card tempCard1 = gameMastersz.getSpecificPlayer("1234").getCardsCollection().get(0);
//        gameMastersz.getSpecificPlayer("1234").getCardsCollection().add(tempCard);
//        //debug
//        Assert.assertEquals(true, gameMastersz.getSpecificPlayer("1234").getCardsCollection().contains(tempCard));
//        Assert.assertEquals(true, gameMastersz.getSpecificPlayer("1234").getCardsCollection().contains(tempCard1));
//
//        gameMastersz.getCurrentState().setLastCard(gameMastersz.getTrashCards().peek());
//        String[] inputanUser = {tempCard.getSymbol()+";"+tempCard.getColor(),
//                tempCard1.getSymbol()+";"+tempCard1.getColor()};
//        ArrayList<String> temps =  new ArrayList<>(Arrays.asList(inputanUser));
//        ArrayList<Card> result = gameMastersz.converStringstoCards(temps,Color.YELLOW+"".toLowerCase());
//        Assert.assertEquals(2, result.size());
//
//    }





}