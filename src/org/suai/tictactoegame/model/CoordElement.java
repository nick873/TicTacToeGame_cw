package org.suai.TicTacToeGame.model;
import org.suai.TicTacToeGame.GameException;

public class CoordElement{
    public enum TypeMove{
        TYPE_MOVE_UP,
        TYPE_MOVE_DOWN,
        TYPE_MOVE_LEFT,
        TYPE_MOVE_RIGHT,
        TYPE_MOVE_UP_LEFT,
        TYPE_MOVE_UP_RIGHT,
        TYPE_MOVE_DOWN_LEFT,
        TYPE_MOVE_DOWN_RIGHT
    };
    
    public static final TypeMove ARRAY_LINE[][] = {
        { TypeMove.TYPE_MOVE_UP, TypeMove.TYPE_MOVE_DOWN },
        { TypeMove.TYPE_MOVE_UP_RIGHT, TypeMove.TYPE_MOVE_DOWN_LEFT },
        { TypeMove.TYPE_MOVE_RIGHT, TypeMove.TYPE_MOVE_LEFT },
        { TypeMove.TYPE_MOVE_DOWN_RIGHT, TypeMove.TYPE_MOVE_UP_LEFT }
    };
    
    public static int getMoveI( int i, TypeMove tm ){
        switch( tm ){
            case TYPE_MOVE_UP :
            case TYPE_MOVE_UP_LEFT :
            case TYPE_MOVE_UP_RIGHT : return i - 1;
            case TYPE_MOVE_DOWN :
            case TYPE_MOVE_DOWN_LEFT :
            case TYPE_MOVE_DOWN_RIGHT : return i + 1;
            case TYPE_MOVE_LEFT :
            case TYPE_MOVE_RIGHT : return i;
        }
        throw new GameException( "is not correct tm : " + tm );
    }
    
    public static int getMoveJ( int j, TypeMove tm ){
       switch( tm ){
            case TYPE_MOVE_LEFT :
            case TYPE_MOVE_UP_LEFT :
            case TYPE_MOVE_DOWN_LEFT : return j - 1;
            case TYPE_MOVE_RIGHT :
            case TYPE_MOVE_UP_RIGHT :
            case TYPE_MOVE_DOWN_RIGHT : return j + 1;
            case TYPE_MOVE_UP :
            case TYPE_MOVE_DOWN : return j;
        }
        throw new GameException( "is not correct tm : " + tm );
    }
    
    private int i;
    private int j;
    
    public CoordElement(){
    }
    
    public CoordElement( int i, int j ){
        this.i = i;
        this.j = j;
    }
    
    public void setI( int i ){
        this.i = i;
    }
    
    public int getI(){
        return this.i;
    }
    
    public void setJ( int j ){
        this.j = j;
    }
    
    public int getJ(){
        return this.j;
    }
    
    public CoordElement getMove( TypeMove tm ){
        return new CoordElement( CoordElement.getMoveI( this.i, tm ), CoordElement.getMoveJ( this.j, tm ) );
    }
    
    public String toString(){
        return "i = " + this.i + " j = " + this.j;
    }
};
