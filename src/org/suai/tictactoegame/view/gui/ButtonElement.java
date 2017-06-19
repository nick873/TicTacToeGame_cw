package org.suai.TicTacToeGame.view.gui;
import javax.swing.JButton;

public class ButtonElement extends JButton{
    private int i;
    private int j;
    
    public ButtonElement( String text, int i, int j ){
        super( text );
        this.i = i;
        this.j = j;
    }
    
    public int getI(){
        return this.i;
    }
    
    public int getJ(){
        return this.j;
    }
};
