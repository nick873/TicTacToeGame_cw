package org.suai.TicTacToeGame.view.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickBackListener implements ActionListener{
    private View view;
    
    public ClickBackListener( View view ){
        this.view = view;
    }
    
    public void actionPerformed( ActionEvent event ){
        try{
            this.view.clickBack();
        }catch( Exception ex ){
            ex.printStackTrace();
        }
    }
};
