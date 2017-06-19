package org.suai.TicTacToeGame.model;
import org.suai.TicTacToeGame.GameException;

public class ModelParams{
    private int countRows;
    private int countCols;
    private int lengthWin;
    private Element.TypeElement myPlayer;
    
    public ModelParams( int countRows, int countCols, int lengthWin, Element.TypeElement myPlayer ){
        this.countRows = countRows;
        this.countCols = countCols;
        this.lengthWin = lengthWin;
        this.myPlayer = myPlayer;
    }
    
    public ModelParams( ModelParams params ){
        this.countRows = params.countRows;
        this.countCols = params.countCols;
        this.lengthWin = params.lengthWin;
    }
    
    public int getCountRows(){
        return this.countRows;
    }
    
    public void setCountRows( int countRows ){
        this.countRows = countRows;
    }
    
    public int getCountCols(){
        return this.countCols;
    }
    
    public void setCountCols( int countCols ){
        this.countCols = countCols;
    }
    
    public int getLengthWin(){
        return this.lengthWin;
    }
    
    public void setLengthWin( int lengthWin ){
        this.lengthWin = lengthWin;
    }
    
    public Element.TypeElement getMyPlayer(){
        return this.myPlayer;
    }
    
    public void setMyPlayer( Element.TypeElement myPlayer ){
        switch( myPlayer ){
            case TYPE_ELEMENT_X :
            case TYPE_ELEMENT_0 : break;
            default : throw new GameException( "is not correct myPlayer : " + myPlayer );
        }
        this.myPlayer = myPlayer;
    }
};

