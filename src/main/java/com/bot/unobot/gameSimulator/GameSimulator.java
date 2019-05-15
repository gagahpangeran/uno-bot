package com.bot.unobot.gameSimulator;

import com.bot.unobot.card.*;
import com.bot.unobot.gameengine.GameMaster;
import com.bot.unobot.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GameSimulator {

    public static void main (String[] args){




        GameMaster gameMaster = new GameMaster();

      PlusTestCase(gameMaster);


        //------------------------------------------------------------------------------------------

        /*Testcase UNO* ---- /




        //------------------------------------------------------------------------------------------


        //------------------------------------------------------------------------------------------

        /*Testcase Reverse*----- PASS!!!!/




        //------------------------------------------------------------------------------------------


        //------------------------------------------------------------------------------------------

        /*Testcase SKIP* ---  PASS!!!/




        //------------------------------------------------------------------------------------------


        //------------------------------------------------------------------------------------------

        /*Testcase PLUS*/




        //------------------------------------------------------------------------------------------
        /*Cara Make GameSimulator:
        * exit --> keluar
        * mulai ---> mulai game
        * playernow ---> nyari tau pemain yang jalan sekarang siapa
        * daftar [id_pemain] --> daftarin pemain
        * kartu [id_pemain] ---> melihat daftar kartu milik [id_pemain]
        * uno [id_pemain] ---> mensimulasikan misalnya yang bilang uno adalah pemain ber-id [id_pemain]
        * draw ---> nge-draw kartu
        * rule ----> lihat rule
        * put [symbol];[warna] set;[warna_yang_ingin_di_set] ( berlaku kalau keluarin wildcard or plus 4) ----> buat ngeluarin kartu
        *
        * */
        boolean temp = true;
        Scanner tokenizer = new Scanner(System.in);
        String[] commands = tokenizer.nextLine().split(" ");
        String s = commands[0];

        while (temp){

            switch (s){
                case "exit":
                    System.exit(0);
                    //temp = false;
                    break;
                case "mulai":
                    gameMaster.initGame();
                    break;
                case "info":
                    System.out.println(gameMaster.getInfo());
                    break;
                case "playernow":
                    int playerPos = gameMaster.getCurrentState().getCurrPlayerIndex();
                    Player target = gameMaster.getPlayers().get(Math.floorMod(playerPos, gameMaster.getNrOfPlayers()));
                    System.out.println(target.getId());
                    break;
                case "daftar":
                    gameMaster.addPlayer(commands[1], "a");

                    break;
                case "kartu":
                    System.out.println(gameMaster.getMessageForPlayer(commands[1]));
                    break;
                case "uno":
                    gameMaster.getCurrentState().checkAndGetWinner(commands[1]);
                    System.out.println(gameMaster.getMessageToGroup());
                    break;
                case "draw":
                    String tempss = gameMaster.getPlayers().get(gameMaster.getCurrentState().getCurrPlayerIndex()).getId();
                    gameMaster.getCurrentState().draw();
                    System.out.println("yang nge-draw = "+tempss);
                    break;
                case "put":
                    ArrayList<String> arrayListOfCards = new ArrayList<>();
                    for (int i=1;i<commands.length-1;i++){
                        arrayListOfCards.add(commands[i]);
                    }
                    ArrayList <Card> temps;
                    if (arrayListOfCards.contains("Wild;SPECIAL".toLowerCase())||arrayListOfCards.contains("+4;SPECIAL".toLowerCase())){

                        String colorSetByPlayer = commands[commands.length-1].split(";")[1];
                        temps = gameMaster.converStringstoCards(arrayListOfCards,colorSetByPlayer);
                        gameMaster.put(temps);
                    }else{
                        arrayListOfCards.add(commands[commands.length-1]);
                        temps = gameMaster.converStringstoCards(arrayListOfCards);
                        //debug
                        System.out.println(arrayListOfCards);
                        gameMaster.put(temps);
                    }
                    System.out.println(gameMaster.getMessageToGroup());
                    break;
                case "rule":
                    System.out.println(gameMaster.getRule());

                    default:
                        System.out.println("command yang lu masukin salah! Coba Lagi!");

            }
            commands = tokenizer.nextLine().split(" ");
            s = commands[0];

        }








    }

    public static void UNOTestCase(GameMaster gameMaster){
        gameMaster.addPlayer("a", "a");
        gameMaster.addPlayer("b", "a");
        gameMaster.addPlayer("c", "a");
        gameMaster.initGame();
        System.out.println(gameMaster.getMessageToGroup());
        for(Player player:gameMaster.getPlayers()){
            switch (player.getId()){
                case "a":
                    Card[] zz = {new WildCard(Color.SPECIAL)
                    };
                    player.setCards(new ArrayList<>(Arrays.asList(zz)));
                    break;
                case "b":
                    Card[] zzz = {new OrdinaryCard("7", Color.RED),
                            new OrdinaryCard("6",Color.BLUE),
                            new SkipCard(Color.GREEN),
                            new OrdinaryCard("5",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzz)));
                    break;
                case "c":
                    Card[] zzzz = {new OrdinaryCard("5", Color.RED),
                            new OrdinaryCard("4",Color.BLUE),
                            new SkipCard(Color.RED),
                            new OrdinaryCard("3",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzzz)));
                    break;


            }
        }



    }

    public static void ReverseTestCase(GameMaster gameMaster){

        gameMaster.addPlayer("a", "a");
        gameMaster.addPlayer("b", "b");
        gameMaster.addPlayer("c", "b");
        gameMaster.initGame();
        System.out.println(gameMaster.getMessageToGroup());
        for(Player player:gameMaster.getPlayers()){
            switch (player.getId()){
                case "a":
                    Card[] zz = {new ReverseCard(Color.RED),
                            new WildCard(Color.SPECIAL),
                            new ReverseCard(Color.GREEN),
                            new ReverseCard(Color.BLUE),
                            new ReverseCard(Color.YELLOW),
                            new OrdinaryCard("6",Color.BLUE),
                    };
                    player.setCards(new ArrayList<>(Arrays.asList(zz)));
                    break;
                case "b":
                    Card[] zzz = {new OrdinaryCard("7", Color.RED),
                            new OrdinaryCard("6",Color.BLUE),
                            new SkipCard(Color.GREEN),
                            new OrdinaryCard("5",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzz)));
                    break;
                case "c":
                    Card[] zzzz = {new OrdinaryCard("5", Color.RED),
                            new OrdinaryCard("4",Color.BLUE),
                            new SkipCard(Color.RED),
                            new OrdinaryCard("3",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzzz)));
                    break;


            }
        }

    }

    public static void SkipTestCase(GameMaster gameMaster){
        gameMaster.addPlayer("a", "a");
        gameMaster.addPlayer("b", "a");
        gameMaster.addPlayer("c", "a");
        gameMaster.initGame();
        System.out.println(gameMaster.getMessageToGroup());
        for(Player player:gameMaster.getPlayers()){
            switch (player.getId()){
                case "a":
                    Card[] zz = {new OrdinaryCard("7", Color.RED),
                            new SkipCard(Color.BLUE),
                            new SkipCard(Color.RED),
                            new SkipCard(Color.GREEN),
                            new SkipCard(Color.YELLOW),
                            new WildCard(Color.SPECIAL)
                    };
                    player.setCards(new ArrayList<>(Arrays.asList(zz)));
                    break;
                case "b":
                    Card[] zzz = {new OrdinaryCard("7", Color.RED),
                            new OrdinaryCard("6",Color.BLUE),
                            new SkipCard(Color.GREEN),
                            new OrdinaryCard("5",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzz)));
                    break;
                case "c":
                    Card[] zzzz = {new OrdinaryCard("5", Color.RED),
                            new OrdinaryCard("4",Color.BLUE),
                            new SkipCard(Color.RED),
                            new OrdinaryCard("3",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzzz)));
                    break;


            }
        }

    }

    public static void PlusTestCase(GameMaster gameMaster){
        gameMaster.addPlayer("a", "a");
        gameMaster.addPlayer("b", "a");
        gameMaster.addPlayer("c", "a");
        gameMaster.initGame();
        System.out.println(gameMaster.getMessageToGroup());
        for(Player player:gameMaster.getPlayers()){
            switch (player.getId()){
                case "a":
                    Card[] zz = {new OrdinaryCard("7", Color.RED),
                            new PlusCard(Color.SPECIAL,4),
                            new PlusCard(Color.BLUE,2),
                            new PlusCard(Color.RED,2),
                            new PlusCard(Color.GREEN,2),
                            new PlusCard(Color.YELLOW,2)
                    };
                    player.setCards(new ArrayList<>(Arrays.asList(zz)));
                    break;
                case "b":
                    Card[] zzz = {new OrdinaryCard("7", Color.RED),
                            new PlusCard(Color.GREEN,2),
                            new PlusCard(Color.RED,2),
                            new OrdinaryCard("6",Color.BLUE),
                            new OrdinaryCard("5",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzz)));
                    break;
                case "c":
                    Card[] zzzz = {new OrdinaryCard("5", Color.RED),
                            new OrdinaryCard("4",Color.BLUE),
                            new SkipCard(Color.RED),
                            new OrdinaryCard("3",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzzz)));
                    break;


            }
        }

    }

    public void  WildCardTestCase(GameMaster gameMaster){
        gameMaster.addPlayer("a", "a");
        gameMaster.addPlayer("b", "a");
        gameMaster.addPlayer("c", "a");
        gameMaster.initGame();
        System.out.println(gameMaster.getMessageToGroup());
        for(Player player:gameMaster.getPlayers()){
            switch (player.getId()){
                case "a":
                    Card[] zz = {new OrdinaryCard("7", Color.YELLOW),
                            new WildCard(Color.SPECIAL),
                            new PlusCard(Color.SPECIAL,4),
                            new PlusCard(Color.BLUE,2),
                            new PlusCard(Color.RED,2)
                    };
                    player.setCards(new ArrayList<>(Arrays.asList(zz)));
                    break;
                case "b":
                    Card[] zzz = {new OrdinaryCard("7", Color.RED),
                            new OrdinaryCard("7", Color.BLUE),
                            new PlusCard(Color.GREEN,2),
                            new PlusCard(Color.RED,2),
                                    new OrdinaryCard("5",Color.RED),
                            new OrdinaryCard("6",Color.BLUE)
                           };
                    player.setCards(new ArrayList<>(Arrays.asList(zzz)));
                    break;
                case "c":
                    Card[] zzzz = {new OrdinaryCard("5", Color.RED),
                            new OrdinaryCard("4",Color.BLUE),
                            new SkipCard(Color.RED),
                            new OrdinaryCard("3",Color.RED)};
                    player.setCards(new ArrayList<>(Arrays.asList(zzzz)));
                    break;


            }
        }
    }

    public static void dataKartu(){
        GameMaster gameMaster =  new GameMaster();
        gameMaster.createNewCards();
        System.out.println(gameMaster.getNewCards().size());
        int ordinary_red= 0;
        int ordinary_blue = 0;
        int ordinary_green =0;
        int ordinary_yellow = 0;
        int plus  = 0;
        int ordinary = 0;
        int skip = 0;
        int reverse = 0;
        int wild = 0;
        while (!gameMaster.getNewCards().empty()){
            switch (gameMaster.getNewCards().peek().getEffect()){
                case PLUS:
                    plus+=1;
                    break;
                case STOP:
                    skip+=1;
                    break;
                case NOTHING:
                    ordinary+=1;
                    switch (gameMaster.getNewCards().peek().getColor()){
                        case RED:
                            ordinary_red+=1;
                            break;
                        case BLUE:
                            ordinary_blue+=1;
                            break;
                        case GREEN:
                            ordinary_green+=1;
                            break;
                        case YELLOW:
                            ordinary_yellow+=1;

                    }
                    break;
                case REVERSE:
                    reverse+=1;
                    break;
                    case CHANGE_COLOR:
                        wild+=1;
                        break;

            }
            gameMaster.getNewCards().pop();
        }

        System.out.println("ordinary red: "+ordinary_red+"\n"+"ordinary blue: "+ordinary_blue+"\n"+"ordinary green: "+ordinary_green+"\n"+"ordinary yellow: "+ordinary_yellow+"\n");
        System.out.println("plus: "+ plus+"\n"+"ordinary: "+ordinary+"\n"+"skip: "+skip+"\n"+"reverse: "+reverse+"\n"+"wild: "+wild+"\n")   ;


    }




    /*
    *
    *
    *
    *
    * */
}
