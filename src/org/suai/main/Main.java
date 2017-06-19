package org.suai.main;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.UIManager;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.suai.TicTacToeGame.GameException;
import org.suai.TicTacToeGame.model.Element;
import org.suai.TicTacToeGame.server.ServerThread;
import org.suai.TicTacToeGame.model.Field;
import org.suai.TicTacToeGame.model.Model;
import org.suai.TicTacToeGame.model.ModelParams;

public class Main{
    private static ModelParams getMinModelParams(){
        return new ModelParams( Field.MIN_COUNT_ROWS, Field.MIN_COUNT_COLS, Model.MIN_LENGTH_WIN, Element.TypeElement.TYPE_ELEMENT_X );
    }
    
    private static String getTitle(){
        return "Крестики-нолики";
    }
    
    private static JFrame getFrame(){
        JFrame frame = new JFrame( Main.getTitle() );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setLocationRelativeTo( null );
        frame.setLayout( new BorderLayout() );
        frame.setResizable( false );
        frame.setMinimumSize( new Dimension( 100, 100 ) );
        return frame;
    }
    
    public static InputArgument getInputArgument( String args[] ){
        InputArgument inArgument = new InputArgument();
        int countArgument = args.length;
        if( countArgument == 0 ){
            inArgument.setTypeWork( InputArgument.TypeWork.TYPE_WORK_GUI );
        }else{
            String sBuf = args[ 0 ];
            if( sBuf.equals( "-c" ) ){
                if( countArgument != 4 )
                    throw new GameException( "is not correct count input argument" );
                inArgument.setTypeWork( InputArgument.TypeWork.TYPE_WORK_CONSOLE );
                inArgument.setCountRows( Integer.valueOf( args[ 1 ] ) );
                inArgument.setCountCols( Integer.valueOf( args[ 2 ] ) );
                inArgument.setLengthWin( Integer.valueOf( args[ 3 ] ) );
            }else if( sBuf.equals( "-cnc" ) ){
                if( countArgument != 3 )
                    throw new GameException( "is not correct count input argument" );
                inArgument.setTypeWork(InputArgument.TypeWork.TYPE_WORK_CONSOLE_NET_CLIENT );
                inArgument.setHost( args[ 1 ] );
                inArgument.setPort( Integer.valueOf( args[ 2 ] ) );
            }else if( sBuf.equals( "-cns" ) ){
                if( countArgument != 5 )
                    throw new GameException( "is not correct count input argument" );
                inArgument.setTypeWork(InputArgument.TypeWork.TYPE_WORK_CONSOLE_NET_SERVER );
                inArgument.setPort( Integer.valueOf( args[ 1 ] ) );
                inArgument.setCountRows( Integer.valueOf( args[ 2 ] ) );
                inArgument.setCountCols( Integer.valueOf( args[ 3 ] ) );
                inArgument.setLengthWin( Integer.valueOf( args[ 4 ] ) );
            }else if( sBuf.equals( "-g" ) ){
                inArgument.setTypeWork( InputArgument.TypeWork.TYPE_WORK_GUI );
            }else if( sBuf.equals( "-gnc" ) ){
                inArgument.setTypeWork(InputArgument.TypeWork.TYPE_WORK_GUI_NET_CLIENT );
            }else
                throw new GameException( "is not correct input argument : " + sBuf );
        }
        return inArgument;
    }
    
    public static void runConsoleTTTGame( ModelParams modelParams ) throws Exception{
        Model model = new Model( modelParams );
        org.suai.TicTacToeGame.view.text.View view = new org.suai.TicTacToeGame.view.text.View( model );
        view.run();
    }
    
    public static void runConsoleNetClientTTTGame( String host, int port ) throws Exception{
        Model model = new Model( Main.getMinModelParams() );
        org.suai.TicTacToeGame.view.text.NetView netView = new org.suai.TicTacToeGame.view.text.NetView( model );
        netView.run( host, port );
    }
    
    public static void runConsoleNetServerTTTGame( int port, ModelParams modelParams ) throws Exception{
        ServerThread serverThread = new ServerThread( port, 30, modelParams );
        serverThread.start();
        System.out.println( "server run..." );
        while( serverThread.isAlive() )
            Thread.sleep( 50 );
    }
    
    public static void runGuiTTTGame() throws Exception{
        UIManager.setLookAndFeel( new MetalLookAndFeel() );
        JFrame frame = Main.getFrame();
        JPanel panel = new JPanel();
        frame.add( panel, BorderLayout.CENTER );
        Model model = new Model( Main.getMinModelParams() );
        org.suai.TicTacToeGame.view.gui.View view = new org.suai.TicTacToeGame.view.gui.View( model, frame, panel );
        view.run();
        frame.setVisible( true );
    }
    
    public static void runGuiNetClientTTTGame() throws Exception{
        UIManager.setLookAndFeel( new MetalLookAndFeel() );
        JFrame frame = Main.getFrame();
        JPanel panel = new JPanel();
        frame.add( panel, BorderLayout.CENTER );
        Model model = new Model( Main.getMinModelParams() );
        org.suai.TicTacToeGame.view.gui.NetView netView = new org.suai.TicTacToeGame.view.gui.NetView( model, frame, panel );
        netView.run();
        frame.setVisible( true );
    }
    
    public static void main( String[] args ) throws Exception{
        InputArgument inArgument = Main.getInputArgument( args );
        InputArgument.TypeWork typeWork = inArgument.getTypeWork();
        if( typeWork == InputArgument.TypeWork.TYPE_WORK_CONSOLE ){
            Main.runConsoleTTTGame( new ModelParams( inArgument.getCountRows(), inArgument.getCountCols(), inArgument.getLengthWin(), Element.TypeElement.TYPE_ELEMENT_X ) );
        }else if( typeWork == InputArgument.TypeWork.TYPE_WORK_CONSOLE_NET_CLIENT ){
            Main.runConsoleNetClientTTTGame( inArgument.getHost(), inArgument.getPort() );
        }else if( typeWork == InputArgument.TypeWork.TYPE_WORK_CONSOLE_NET_SERVER ){
            Main.runConsoleNetServerTTTGame( inArgument.getPort(), new ModelParams( inArgument.getCountRows(), inArgument.getCountCols(), inArgument.getLengthWin(), Element.TypeElement.TYPE_ELEMENT_X ) );
        }else if( typeWork == InputArgument.TypeWork.TYPE_WORK_GUI ){
            Main.runGuiTTTGame();
        }else if( typeWork == InputArgument.TypeWork.TYPE_WORK_GUI_NET_CLIENT ){
            Main.runGuiNetClientTTTGame();
        }
    }
};
