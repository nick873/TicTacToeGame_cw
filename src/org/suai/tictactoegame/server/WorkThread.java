package org.suai.TicTacToeGame.server;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.suai.TicTacToeGame.GameException;
import org.suai.TicTacToeGame.model.*;
import org.suai.TicTacToeGame.view.io.IONetModel;

public class WorkThread extends Thread{
    private Socket socketX;
    private Socket socket0;
    private BufferedReader inX;
    private BufferedReader in0;
    private PrintWriter outX;
    private PrintWriter out0;
    private Model model;
    private Field field;
    
    private void initConnect() throws Exception{
        this.inX = new BufferedReader( new InputStreamReader( this.socketX.getInputStream() ) );
        this.outX = new PrintWriter( this.socketX.getOutputStream(), true );
        this.in0 = new BufferedReader( new InputStreamReader( this.socket0.getInputStream() ) );
        this.out0 = new PrintWriter( this.socket0.getOutputStream(), true );
    }
    
    private void deinitConnect() throws Exception{
        this.inX.close();
        this.outX.close();
        this.socketX.close();
        this.in0.close();
        this.out0.close();
        this.socket0.close();
    }
    
    private void sendGameParams() throws Exception{
        ModelParams modelParams = this.model.getModelParams();
        modelParams.setMyPlayer( Element.TypeElement.TYPE_ELEMENT_X );
        IONetModel.write( this.outX, modelParams );
        modelParams.setMyPlayer( Element.TypeElement.TYPE_ELEMENT_0 );
        IONetModel.write( this.out0, modelParams );
    }
    
    private void execute() throws Exception{
        this.initConnect();
        this.sendGameParams();
        while( this.model.isPlayGame() ){
            CoordElement p = null;
            Element.TypeElement currentPlayer = this.model.getCurrentPlayer();
            if( currentPlayer == Element.TypeElement.TYPE_ELEMENT_X ){
                p = new CoordElement( Integer.valueOf( this.inX.readLine() ), Integer.valueOf( this.inX.readLine() ) );
                this.out0.println( p.getI() );
                this.out0.println( p.getJ() );
            }else if( currentPlayer == Element.TypeElement.TYPE_ELEMENT_0 ){
                p = new CoordElement( Integer.valueOf( this.in0.readLine() ), Integer.valueOf( this.in0.readLine() ) );
                this.outX.println( p.getI() );
                this.outX.println( p.getJ() );
            }else
                throw new GameException( "is not correct currentPlayer : " + currentPlayer );
            this.model.executeMove( p );
        }
        this.deinitConnect();
    }
    
    public WorkThread( Socket socketX, Socket socket0, ModelParams modelParams ){
        this.socketX = socketX;
        this.socket0 = socket0;
        this.model = new Model( modelParams );
        this.field = this.model.getField();
    }
    
    public void run(){
        try{
            this.execute();
        }catch( Exception ex ){
            ex.printStackTrace();
        }
    }
};
