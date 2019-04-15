package com.bot.unobot.gameengine;

import com.bot.unobot.player.Player;

/**
 * Uno State Class
 */
public class UNOState implements State {

    //Variables
    GameMaster gameMaster;
    String display;
    Player currentPlayer;

    /**
     * Uno State Constructor
     * @param gameMaster
     */
    public UNOState(GameMaster gameMaster){}

    public UNOState(GameMaster gameMaster,Player player){
        this.gameMaster=gameMaster;
        this.display = "";
        this.currentPlayer = player;
    }

    /*
    * Cara Kerja UNO State
    *
    * UNO State ini beda dengan state lainnya karena UNO state tidak butuh user untuk ngeluarin kartu
    *
    * Mekanisme kerja UNO State:
    *
    * Jika terdapat 1 player yang kartunya tinggal 1, maka akan langsung masuk ke UNO State ( Ini bakal di cek di game engine)
    * Ketika di initiate, langsung ditampilin display berupa nama pemain dan jumlah kartu
    * Kemudian Handler akan mendapatkan Id pemain yang bilang UNO duluan
    * Id dicocokkan dengan  pemain yang jumlah kartunya 1
    * Jika ya, maka pemain tersebut menang
    * Jika tidak, maka pemain tersebut ambil 2 kartu
    *
    * Dia bakal nerima argumen berupa
    *
    *
    * */

    /**
     * Get Current Player
     * It returns the current player's ID playing the game.
     * @return current player's ID
     */
    public String getCurrentPlayer(){
        return this.currentPlayer.getId();
    }

    /**
     * Accept User Card
     *
     * @param cardName
     * @param cardColor
     */
    @Override
    public void acceptUsersCard(String cardName, String cardColor) {

    }

    /**
     * Take Another Card
     * It adds a new card from the card collection to the player's card collection.
     * Then it ends the player's turn directly after taking the card.
     */
    @Override
    public void takeAnotherCard() {
        this.currentPlayer.getCardsCollection().add(this.gameMaster.cardStack.pop());

    }

    /*
    * handler bisa menggunakan method ini denganc cara memasukan string berupa id user yang bilang UNO
    *
    * */
    @Override
    public void cardChecking(String userInput) {
        String UNOId = userInput;
        Player Winner = null;

        for (Player player : this.gameMaster.players){
            if (player.getId().equals(UNOId)){
                Winner = player;
            }
        }

        if (!Winner.equals(null)){
            this.gameMaster.removePlayer(Winner);
            this.display = "Selamat!!! Ternyata Kartu Kamu udah habis\n" +
                    "\n" +
                    "Itu artinya kamu menjadi pemenang!\n" +
                    "\n" +
                    "Kamu mendapatkan Juara ";
        }else{
            for(int i=0; i<2;i++){
                takeAnotherCard();
            }
            this.display = "Aduh....... Kamu telat Bilang UNO! \n" +
                    "\n" +
                    "Kamu ternyata kena kartu plus ... :'(\n" +
                    "\n" +
                    "UNOBot turut berduka cita\n" +
                    "\n" +
                    "Sebagai bentuk dukacita UNOBot, UNOBot akan secara otomatis mengambilkan kamu kartu dari deck\n" +
                    "\n" +
                    "Semangat Player!!! " +
                    "\n"+
                    endTurn();
        }
    }
    /**
     * End Turn
     * It returns a message saying that the player's turn has finished.
     * @return
     */
    @Override
    public String endTurn(){
        return "Giliran Anda sudah selesai!"+" \n"+
                "Tunggu giliran selanjutnya ya :) !";
    }
}
