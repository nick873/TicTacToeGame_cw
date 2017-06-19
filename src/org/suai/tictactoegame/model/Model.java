package org.suai.TicTacToeGame.model;
import org.suai.TicTacToeGame.GameException;

public class Model{
    public static final int MIN_LENGTH_WIN = 3;
    public static final int MAX_LENGTH_WIN = 10;
    
    private Field field = new Field( Field.MIN_COUNT_ROWS, Field.MIN_COUNT_COLS );
    private Element.TypeElement currentPlayer;
    private Element.TypeElement winnerPlayer;
    private Element.TypeElement myPlayer;
    private int lengthWin;
    private int totalNumberOfEmptyElements;
    private boolean isEndGame;
    private ModelParams modelParams;
    
    private int getCountElement( int i, int j, CoordElement.TypeMove tm ){
        Element element = this.field.getElement( i, j );
        Element.TypeElement tBegin = element.getTypeElement();
        int countElement = 0;
        while( this.field.isCorrectCoord( i, j ) ){
            element = this.field.getElement( i, j );
            if( element.getTypeElement() != tBegin )
                break;
            i = CoordElement.getMoveI( i, tm );
            j = CoordElement.getMoveJ( j, tm );
            ++countElement;
        }
        return countElement;
    }
    
    private boolean isEndGame( int i, int j ){
        for( int z = 0; z < CoordElement.ARRAY_LINE.length; ++z ){
            int currentLength = this.getCountElement( i, j, CoordElement.ARRAY_LINE[ z ][ 0 ] )
                    + this.getCountElement( i, j, CoordElement.ARRAY_LINE[ z ][ 1 ] )
                    - 1;
            if( currentLength >= this.lengthWin )
                return true;
        }
        return false;
    }
    
    public Model( ModelParams params ){
        this.set( params );
    }
    
    public final void set( ModelParams modelParams ){
        int countRows = modelParams.getCountRows(),
            countCols = modelParams.getCountCols(),
            lengthWin = modelParams.getLengthWin();
        if( lengthWin < Model.MIN_LENGTH_WIN || lengthWin > Model.MAX_LENGTH_WIN )
            throw new GameException( "is not correct lengthWin : " + lengthWin );
        this.field.setField( countRows, countCols );
        this.lengthWin = lengthWin;
        this.currentPlayer = Element.TypeElement.TYPE_ELEMENT_X;
        this.totalNumberOfEmptyElements = countRows * countCols;
        this.isEndGame = false;
        this.myPlayer = modelParams.getMyPlayer();
        this.modelParams = new ModelParams( modelParams );
    }
    
    public Field getField(){
        return this.field;
    }
    
    public Element.TypeElement getCurrentPlayer(){
        return this.currentPlayer;
    }
    
    public Element.TypeElement getMyPlayer(){
        return this.myPlayer;
    }
    
    public ModelParams getModelParams(){
        return this.modelParams;
    }
    
    public void executeMove( int i, int j ){
        Element element = this.field.getElement( i, j );
        if( element.getTypeElement() != Element.TypeElement.TYPE_ELEMENT_EMPTY )
            throw new GameException( "element is not empty" );
        element.setTypeElement( this.currentPlayer );
        if( this.isEndGame = this.isEndGame( i, j ) )
            this.winnerPlayer = this.currentPlayer;
        --this.totalNumberOfEmptyElements;
        if( this.currentPlayer == Element.TypeElement.TYPE_ELEMENT_X )
            this.currentPlayer = Element.TypeElement.TYPE_ELEMENT_0;
        else
            this.currentPlayer = Element.TypeElement.TYPE_ELEMENT_X;
    }
    
    public void executeMove( CoordElement p ){
        this.executeMove( p.getI(), p.getJ() );
    }
    
    public boolean isPlayGame(){
        if( this.isEndGame )
            return false;
        return this.totalNumberOfEmptyElements > 0;
    }
    
    public GameResult getGameResult(){
        GameResult gameResult = new GameResult();
        if( this.isEndGame ){
            gameResult.setTypeResult( GameResult.TypeResult.TYPE_RESULT_WIN );
            gameResult.setWinnerPlayer( this.winnerPlayer );
        }else
            gameResult.setTypeResult( GameResult.TypeResult.TYPE_RESULT_DRAW );
        return gameResult;
    }
};
