package org.suai.main;

public class InputArgument{
    public enum TypeWork{
        TYPE_WORK_CONSOLE,
        TYPE_WORK_CONSOLE_NET_CLIENT,
        TYPE_WORK_CONSOLE_NET_SERVER,
        TYPE_WORK_GUI,
        TYPE_WORK_GUI_NET_CLIENT
    };
    
    private TypeWork typeWork;
    private int countRows;
    private int countCols;
    private int lengthWin;
    private String host;
    private int port;
    
    public TypeWork getTypeWork(){
        return this.typeWork;
    }
    
    public void setTypeWork( TypeWork t ){
        this.typeWork = t;
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
    
    public String getHost(){
        return this.host;
    }
    
    public void setHost( String host ){
        this.host = host;
    }
    
    public int getPort(){
        return this.port;
    }
    
    public void setPort( int port ){
        this.port = port;
    }
};
