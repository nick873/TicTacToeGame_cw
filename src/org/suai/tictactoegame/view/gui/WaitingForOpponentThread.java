package org.suai.TicTacToeGame.view.gui;

public class WaitingForOpponentThread extends Thread{
    private NetView view;
    
    public WaitingForOpponentThread( NetView view ){
        this.view = view;
    }
    
    public void run(){
        try{
            this.view.clickConnectOpponent();
        }catch( Exception ex ){
            ex.printStackTrace();
        }
    }
};
