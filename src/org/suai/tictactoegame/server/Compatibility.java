package org.suai.TicTacToeGame.server;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import org.suai.TicTacToeGame.GameException;

public class Compatibility{
    public static final String QUESTION_STRING = "TTTGameServer";
    public static final String ANSWER_STRING = "TTTGameClient";
    
    public static void serverCheck( Socket socket ) throws Exception{
        BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
        PrintWriter out = new PrintWriter( socket.getOutputStream(), true );
        out.println( Compatibility.QUESTION_STRING );
        String sAnswer = "";
        try{
            socket.setSoTimeout( 100 );
            sAnswer = in.readLine();
            socket.setSoTimeout( 0 );
        }catch( Exception ex ){
            throw new GameException( "is not supported client" );
        }
        if( !sAnswer.equals( Compatibility.ANSWER_STRING ) )
            throw new GameException( "is not supported client" );
    }
    
    public static void clientCheck( Socket socket ) throws Exception{
        BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
        PrintWriter out = new PrintWriter( socket.getOutputStream(), true );
        String sRequest = "";
        try{
            socket.setSoTimeout( 100 );
            sRequest = in.readLine();
            socket.setSoTimeout( 0 );
        }catch( Exception ex ){
            throw new GameException( "is not supported client" );
        }
        if( !sRequest.equals( Compatibility.QUESTION_STRING ) )
            throw new GameException( "is not supported server" );
        out.println( Compatibility.ANSWER_STRING );
    }
};
