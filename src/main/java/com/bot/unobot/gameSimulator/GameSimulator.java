package com.bot.unobot.gameSimulator;

import com.bot.unobot.card.*;
import com.bot.unobot.gameengine.GameMaster;
import com.bot.unobot.player.Player;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class GameSimulator {

    public static void main (String[] args) throws IOException{

        //------------------------------------------------------------------------------------------

        GameMaster gameMaster = new GameMaster();
        gameMaster.addPlayer("a");
        gameMaster.addPlayer("b");
        gameMaster.addPlayer("c");
        gameMaster.initGame();
        for(Player player:gameMaster.getPlayers()){
            switch (player.getId()){
                case "a":
                    Card[] zz = {new OrdinaryCard("7", Color.RED),
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

        //------------------------------------------------------------------------------------------

        boolean temp = true;
       // GameMaster gameMaster =  new GameMaster();
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
                    Player target = gameMaster.getPlayers().get(playerPos%gameMaster.getPlayers().size());
                    System.out.println(target.getId());
                    break;
                case "daftar":
                    gameMaster.addPlayer(commands[1]);

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
//                    int playerPoss = gameMaster.getCurrentState().getCurrPlayerIndex();
//                    Player targets = gameMaster.getPlayers().get(playerPoss);
                    ArrayList<String> arrayListOfCards = new ArrayList<>();
                    for (int i=1;i<commands.length;i++){
                        arrayListOfCards.add(commands[i]);
                    }
                    ArrayList <Card> temps;
                    if (arrayListOfCards.contains("Wild;SPECIAL")||arrayListOfCards.contains("+4;SPECIAL")){
                        String colorSetByPlayer = arrayListOfCards.get(arrayListOfCards.size()-1).split(";")[1];
                        temps = gameMaster.converStringstoCards((ArrayList<String>)arrayListOfCards.subList(0, arrayListOfCards.size()-1));
                        gameMaster.put(gameMaster.setColorFromWildCardByPlayer(temps, colorSetByPlayer));
                    }else{
                        temps = gameMaster.converStringstoCards(arrayListOfCards);
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
//            System.out.println("zzzzzz");
        }








    }

    /*
    *
    *
    *
    *
    * */
}
