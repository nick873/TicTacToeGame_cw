package org.suai.TicTacToeGame.model;

public class GameResult{
    public enum TypeResult{
        TYPE_RESULT_WIN,
        TYPE_RESULT_DRAW
    };
    
    private TypeResult typeResult;
    private Element.TypeElement winnerPlayer;
    
    public TypeResult getTypeResult(){
        return this.typeResult;
    }
    
    public void setTypeResult( TypeResult t ){
        this.typeResult = t;
    }
    
    public Element.TypeElement getWinnerPlayer(){
        return this.winnerPlayer;
    }
    
    public void setWinnerPlayer( Element.TypeElement t ){
        this.winnerPlayer = t;
    }
};
