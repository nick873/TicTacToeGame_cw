package org.suai.TicTacToeGame.view.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickSelectSizeFieldListener implements ActionListener{
    private View view;
    
    public ClickSelectSizeFieldListener( View view ){
        this.view = view;
    }
    
    public void actionPerformed( ActionEvent event ){
        this.view.clickPlayGame();
    }
};
