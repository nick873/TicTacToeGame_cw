package org.suai.TicTacToeGame.view.gui;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClickButtonElementListener implements ActionListener{
    private View view;
    
    public ClickButtonElementListener( View view ){
        this.view = view;
    }
    
    public void actionPerformed( ActionEvent event ){
        ButtonElement button = (ButtonElement)event.getSource();
        try{
            this.view.clickButtonElement( button );
        }catch( Exception ex ){
            ex.printStackTrace();
        }
    }
};
