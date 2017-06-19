package org.suai.TicTacToeGame.server;
import java.util.ArrayList;
import java.net.Socket;
import java.net.ServerSocket;
import org.suai.TicTacToeGame.GameException;
import org.suai.TicTacToeGame.model.*;

public class ServerThread extends Thread{
    private int port;
    private int maxCountThread;
    private ArrayList< Thread > arrayClientThread = new ArrayList< Thread >();
    private ServerSocket serverSocket;
    private ModelParams modelParams;
    
    private int getCountActiveThread(){
        int size = this.arrayClientThread.size(),
            count = 0;
        for( int i = 0; i < size; ++i ){
            Thread t = this.arrayClientThread.get( i );
            if( t.isAlive() )
                ++count;
        }
        return count;
    }
    
    private void clearNotActiveThread(){
        ArrayList< Thread > newArrayClientThread = new ArrayList< Thread >();
        int size = this.arrayClientThread.size();
        for( int i = 0; i < size; ++i ){
            Thread t = this.arrayClientThread.get( i );
            if( t.isAlive() )
                newArrayClientThread.add( t );
        }
        this.arrayClientThread = newArrayClientThread;
    }
    
    private Socket getTrueClient() throws Exception{
        while( true ){
            Socket socket = this.serverSocket.accept();
            try{
                Compatibility.serverCheck( socket );
            }catch( GameException ex ){
                continue;
            }
            return socket;
        }
    }
    
    private void execute() throws Exception{
        this.serverSocket = new ServerSocket( this.port );
        while( true ){
            Socket  clientSocketX = this.getTrueClient(),
                    clientSocket0 = null;
            while( true ){
                clientSocket0 = this.getTrueClient();
                if( clientSocketX.isConnected() )
                    break;
                clientSocketX = clientSocket0;
            }
            int countActiveThread = this.getCountActiveThread();
            if( countActiveThread < this.arrayClientThread.size() )
                this.clearNotActiveThread();
            if( countActiveThread >= this.maxCountThread ){
                clientSocketX.close();
                clientSocket0.close();
                continue;
            }
            WorkThread clientThread = new WorkThread( clientSocketX, clientSocket0, this.modelParams );
            this.arrayClientThread.add( clientThread );
            clientThread.start();
        }
    }
    
    public ServerThread( int port, int maxCountThread, ModelParams modelParams ){
        this.port = port;
        this.maxCountThread = maxCountThread;
        this.modelParams = new ModelParams( modelParams );
    }
    
    public void run(){
        try{
            this.execute();
        }catch( Exception ex ){
            ex.printStackTrace();
        }
    }
};
