package org.suai.TicTacToeGame.view.text;
import java.util.Scanner;
import org.suai.TicTacToeGame.model.*;

public class View{
    protected Model model;
    protected Field field;
    
    protected String getUserName( Element.TypeElement t ){
        return PrintField.getElementView( t );
    }
    
    protected String getUserName(){
        return PrintField.getElementView( this.model.getCurrentPlayer() );
    }
    
    protected CoordElement inputPoint(){
        System.out.println( "'" + this.getUserName() + "' -> Input coord point (x,y): " );
        Scanner s = new Scanner( System.in );
        return new CoordElement( s.nextInt(), s.nextInt() );
    }
    
    protected CoordElement checkInputPoint() throws Exception{
        CoordElement p = null;
        while( true ){
            boolean error = false;
            try{
                p = this.inputPoint();
            }catch( Exception ex ){
                error = true;
            }
            if( error ){
                System.out.println( "Is not correct point. try again." );
                continue;
            }
            if( this.field.isCorrectCoord( p ) == false ){
                System.out.println( "Is not correct point. try again." );
                continue;
            }
            if( this.field.getElement( p ).getTypeElement() != Element.TypeElement.TYPE_ELEMENT_EMPTY ){
                System.out.println( "Is not empty cell. try again." );
                continue;
            }
            break;
        }
        return p;
    }
    
    protected void executeGame() throws Exception{
        while( this.model.isPlayGame() ){
            System.out.println( PrintField.print( this.field ) );
            CoordElement p = this.checkInputPoint();
            this.model.executeMove( p );
        }
    }
    
    protected void printResult(){
        System.out.println( "----------------------------------------" );
        System.out.println( PrintField.print( this.field ) );
        GameResult gameResult = this.model.getGameResult();
        GameResult.TypeResult typeResult = gameResult.getTypeResult();
        if( typeResult == GameResult.TypeResult.TYPE_RESULT_WIN )
            System.out.println( "'" + this.getUserName( gameResult.getWinnerPlayer() ) + "' win. End game." );
        else if( typeResult == GameResult.TypeResult.TYPE_RESULT_DRAW )
            System.out.println( "No winner. End game." );
    }
    
    public View( Model model ){
        this.model = model;
        this.field = this.model.getField();
    }
    
    public void run() throws Exception{
        this.executeGame();
        this.printResult();
    }
};
