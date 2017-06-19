package org.suai.TicTacToeGame.model;
import org.suai.TicTacToeGame.GameException;

public class Field{
    public static final int MIN_COUNT_ROWS = 3;
    public static final int MIN_COUNT_COLS = 3;
    public static final int MAX_COUNT_ROWS = 10;
    public static final int MAX_COUNT_COLS = 10;
    
    private int countRows;
    private int countCols;
    private Element fieldData[][];
    
    public Field( int countRows, int countCols ){
        this.setField( countRows, countCols );
    }
    
    public final void setField( int countRows, int countCols ){
        if( countRows < Field.MIN_COUNT_ROWS || countRows > Field.MAX_COUNT_ROWS )
            throw new GameException( "is not correct countRows : " + countRows );
        if( countCols < Field.MIN_COUNT_COLS || countCols > Field.MAX_COUNT_COLS )
            throw new GameException( "is not correct countCols : " + countCols );
        this.countRows = countRows;
        this.countCols = countCols;
        this.fieldData = new Element[ this.countRows ][ this.countCols ];
        for( int i = 0; i < this.countRows; ++i ){
            for( int j = 0; j < this.countCols; ++j )
                this.fieldData[ i ][ j ] = new Element();
        }
    }
    
    public int getCountRows(){
        return this.countRows;
    }
    
    public int getCountCols(){
        return this.countCols;
    }
    
    public boolean isCorrectCoord( int i, int j ){
        return !( i < 0 || j < 0 || i >= this.getCountRows() || j >= this.getCountCols() );
    }
    
    public boolean isCorrectCoord( CoordElement p ){
        return this.isCorrectCoord( p.getI(), p.getJ() );
    }
    
    public Element getElement( int i, int j ){
        if( this.isCorrectCoord( i, j ) == false )
            throw new GameException( "is not correct coord point [" + i + "][" + j + "]" );
        return this.fieldData[ i ][ j ];
    }
    
    public Element getElement( CoordElement p ){
        return this.getElement( p.getI(), p.getJ() );
    }
};
