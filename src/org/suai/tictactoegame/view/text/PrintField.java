package org.suai.TicTacToeGame.view.text;
import org.suai.TicTacToeGame.GameException;
import org.suai.TicTacToeGame.model.*;

public class PrintField{
    public static final String getElementView( Element.TypeElement t ){
        switch( t ){
            case TYPE_ELEMENT_EMPTY : return ".";
            case TYPE_ELEMENT_0 : return "0";
            case TYPE_ELEMENT_X : return "X";
        }
        throw new GameException( "is not correct t : " + t );
    }
    
    public static final String print( Field field ){
        StringBuilder sb = new StringBuilder();
        int n = field.getCountRows(),
            m = field.getCountCols();
        for( int i = 0; i < n; ++i ){
            for( int j = 0; j < m; ++j ){
                Element element = field.getElement( i, j );
                sb.append( PrintField.getElementView( element.getTypeElement() ) );
                sb.append( " " );
            }
            sb.append( "\n" );
        }
        return sb.toString();
    }
};
