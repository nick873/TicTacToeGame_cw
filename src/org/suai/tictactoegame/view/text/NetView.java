package org.suai.TicTacToeGame.view.text;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.suai.TicTacToeGame.model.*;
import org.suai.TicTacToeGame.view.io.IONetModel;
import org.suai.TicTacToeGame.server.Compatibility;

public class NetView extends View{
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    
    private void initConnect( String host, int port ) throws Exception{
        this.socket = new Socket( host, port );
        this.in = new BufferedReader( new InputStreamReader( this.socket.getInputStream() ) );
        this.out = new PrintWriter( this.socket.getOutputStream(), true );
    }
    
    private void deinitConnect() throws Exception{
        this.in.close();
        this.out.close();
        this.socket.close();
    }
    
    private void checkCopatibility() throws Exception{
        Compatibility.clientCheck( this.socket );
    }
    
    private void readGameParams() throws Exception{
        IONetModel.read( this.in, this.model );
    }
    
    protected CoordElement checkInputPoint() throws Exception{
        Element.TypeElement currentPlayer = this.model.getCurrentPlayer();
        if( currentPlayer == this.model.getMyPlayer() ){
            CoordElement p = super.checkInputPoint();
            this.out.println( p.getI() );
            this.out.println( p.getJ() );
            return p;
        }else{
            System.out.println( "please wait..." );
            return new CoordElement( Integer.valueOf( this.in.readLine() ), Integer.valueOf( this.in.readLine() ) );
        }
    }
    
    public NetView( Model model ){
        super( model );
    }
    
    public void run( String host, int port ) throws Exception{
        this.initConnect( host, port );
        this.checkCopatibility();
        System.out.println( "please wait..." );
        this.readGameParams();
        this.run();
        this.deinitConnect();
    }
};
