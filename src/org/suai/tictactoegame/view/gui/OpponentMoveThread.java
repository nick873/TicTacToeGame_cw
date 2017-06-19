package org.suai.TicTacToeGame.view.gui;

public class OpponentMoveThread extends Thread{
    private NetView view;
    
    public OpponentMoveThread( NetView view ){
        this.view = view;
    }
    
    public void run(){
        try{
            this.view.clickOpponentMove();
        }catch( Exception ex ){
            ex.printStackTrace();
        }
    }
};
