package org.suai.TicTacToeGame.view.io;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.suai.TicTacToeGame.GameException;
import org.suai.TicTacToeGame.model.*;

public class IONetModel{
    public static void write( PrintWriter out, ModelParams modelParams ) throws Exception{
        out.println( modelParams.getCountRows() );
        out.println( modelParams.getCountCols() );
        out.println( modelParams.getLengthWin() );
        Element.TypeElement myPlayer = modelParams.getMyPlayer();
        if( myPlayer == Element.TypeElement.TYPE_ELEMENT_X ){
            out.println( "X" );
        }else if( myPlayer == Element.TypeElement.TYPE_ELEMENT_0 ){
            out.println( "0" );
        }else
            throw new GameException( "is not correct myPlayer : " + myPlayer );
    }
    
    public static void write( PrintWriter out, Model model ) throws Exception{
        IONetModel.write( out, model.getModelParams() );
    }
    
    public static ModelParams read( BufferedReader in ) throws Exception{
        int countRows = Integer.valueOf( in.readLine() ),
            countCols = Integer.valueOf( in.readLine() ),
            lengthWin = Integer.valueOf( in.readLine() );
        Element.TypeElement myPlayer = Element.TypeElement.TYPE_ELEMENT_X;
        String sBuf = in.readLine();
        if( sBuf.equals( "X" ) ){
            myPlayer = Element.TypeElement.TYPE_ELEMENT_X;
        }else if( sBuf.equals( "0" ) ){
            myPlayer = Element.TypeElement.TYPE_ELEMENT_0;
        }else
            throw new GameException( "is not correct input data" );
        return new ModelParams( countRows, countCols, lengthWin, myPlayer );
    }
    
    public static void read( BufferedReader in, Model model ) throws Exception{
        model.set( IONetModel.read( in ) );
    }
};
