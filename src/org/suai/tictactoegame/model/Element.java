package org.suai.TicTacToeGame.model;

public class Element{
    public enum TypeElement{
        TYPE_ELEMENT_EMPTY,
        TYPE_ELEMENT_X,
        TYPE_ELEMENT_0
    };
    
    private TypeElement typeElement = TypeElement.TYPE_ELEMENT_EMPTY;
    
    public TypeElement getTypeElement(){
        return this.typeElement;
    }
    
    public void setTypeElement( TypeElement t ){
        this.typeElement = t;
    }
};
