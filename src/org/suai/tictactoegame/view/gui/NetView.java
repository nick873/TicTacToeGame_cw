package org.suai.TicTacToeGame.view.gui;
import java.net.Socket;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.suai.TicTacToeGame.model.*;
import org.suai.TicTacToeGame.view.io.IONetModel;
import org.suai.TicTacToeGame.server.Compatibility;

public class NetView extends View{
    private JTextField hostTextField;
    private JTextField portTextField;
    private String lastValueHost = "";
    private String lastValuePort = "";
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private WaitingForOpponentThread waitingForOpponentThread;
    private OpponentMoveThread opponentMoveThread;
    
    private void initConnect( String host, int port ) throws Exception{
        this.socket = new Socket( host, port );
        this.in = new BufferedReader( new InputStreamReader( this.socket.getInputStream() ) );
        this.out = new PrintWriter( this.socket.getOutputStream(), true );
    }
    
    private void deinitConnect() throws Exception{
        if( !this.socket.isClosed() ){
            this.socket.close();
            this.in.close();
            this.out.close();
        }
    }
    
    private void checkCopatibility() throws Exception{
        Compatibility.clientCheck( this.socket );
    }
    
    protected void createElementsOfInputParams(){
        //инициализация + очистка панели
        int xHostLabel = 10,
            yHostLabel = 10,
            hHostLabel = 20,
            wHostLabel = 220,
            xHostTextField = xHostLabel,
            yHostTextField = yHostLabel + hHostLabel,
            hHostTextField = hHostLabel,
            wHostTextField = wHostLabel,
            xPortLabel = xHostLabel,
            yPortLabel = yHostTextField + hHostTextField + 10,
            hPortLabel = hHostLabel,
            wPortLabel = wHostLabel,
            xPortTextField = xHostLabel,
            yPortTextField = yPortLabel + hPortLabel,
            hPortTextField = hHostLabel,
            wPortTextField = wHostLabel,
            xStartGameButton = xHostLabel,
            yStartGameButton = yPortTextField + hPortTextField + 10,
            hStartGameButton = hPortTextField,
            wStartGameButton = wPortTextField,
            hPanel = yStartGameButton + hStartGameButton + 10,
            wPanel = xHostLabel + wHostLabel + 10;
        this.panel.removeAll();
        this.panel.setLayout( null );
        //добавление надписи ввода адреса сервера
        JLabel hostLabel = new JLabel( "Введите адрес сервера:" );
        hostLabel.setBounds( xHostLabel, yHostLabel, wHostLabel, hHostLabel );
        this.panel.add( hostLabel );
        //добавление элемента ввода адреса сервера
        this.hostTextField = new JTextField();
        this.hostTextField.setText( this.lastValueHost );
        this.hostTextField.setBounds( xHostTextField, yHostTextField, wHostTextField, hHostTextField );
        this.panel.add( this.hostTextField );
        //добавление надписи ввода порта сервера
        JLabel portLabel = new JLabel( "Введите порт сервера:" );
        portLabel.setBounds( xPortLabel, yPortLabel, wPortLabel, hPortLabel );
        this.panel.add( portLabel );
        //добавление элемента ввода адреса сервера
        this.portTextField = new JTextField();
        this.portTextField.setText( this.lastValuePort );
        this.portTextField.setBounds( xPortTextField, yPortTextField, wPortTextField, hPortTextField );
        this.panel.add( this.portTextField );
        //добавление кнопки
        JButton startGameButton = new JButton( "Начать игру" );
        startGameButton.setBounds( xStartGameButton, yStartGameButton, wStartGameButton, hStartGameButton );
        startGameButton.addActionListener( this.clickSelectSizeFieldListener );
        this.panel.add( startGameButton );
        //установка размера для панели
        this.panel.setPreferredSize( new Dimension( wPanel, hPanel ) );
        this.panel.setMaximumSize( this.panel.getPreferredSize() );
        this.panel.setMinimumSize( this.panel.getPreferredSize() );
        //упаковка окна
        this.frame.pack();
    }
    
    protected void createElementsOfMessage( String message ){
        //очистка панели
        this.panel.removeAll();
        this.panel.setLayout( new BorderLayout() );
        //добавление элемента для вывода сообщения
        JLabel messageLabel = new JLabel( message );
        this.panel.add( messageLabel, BorderLayout.CENTER );
        //установка размера для панели
        this.panel.setPreferredSize( messageLabel.getPreferredSize() );
        this.panel.setMaximumSize( this.panel.getPreferredSize() );
        this.panel.setMinimumSize( this.panel.getPreferredSize() );
        //упаковка окна
        this.frame.pack();
    }
    
    public NetView( Model model, JFrame frame, JPanel panel ){
        super( model, frame, panel );
    }
    
    public synchronized boolean isPlayGame(){
        return this.model.isPlayGame();
    }
    
    public synchronized boolean isMyMove(){
        return this.model.getCurrentPlayer() == this.model.getMyPlayer();
    }
    
    public synchronized void clickPlayGame(){
        this.lastValueHost = this.hostTextField.getText();
        this.lastValuePort = this.portTextField.getText();
        String  sServerHost = this.lastValueHost,
                sServerPort = this.lastValuePort;
        int serverPort = -1;
        try{
            serverPort = Integer.valueOf( sServerPort );
        }catch( NumberFormatException ex ){
            JOptionPane.showMessageDialog( this.frame, "Неверное формат ввода порта.", "Внимание", JOptionPane.INFORMATION_MESSAGE );
            return;
        }
        if( serverPort < 1 || serverPort > 65535 ){
            JOptionPane.showMessageDialog( this.frame, "Неверное значение порта.", "Внимание", JOptionPane.INFORMATION_MESSAGE );
            return;
        }
        try{
            this.initConnect( sServerHost, serverPort );
        }catch( Exception ex ){
            JOptionPane.showMessageDialog( this.frame, "Сервер не найден.", "Внимание", JOptionPane.INFORMATION_MESSAGE );
            return;
        }
        try{
            this.checkCopatibility();
        }catch( Exception ex ){
            JOptionPane.showMessageDialog( this.frame, "Неподдерживаемый сервер.", "Внимание", JOptionPane.ERROR_MESSAGE );
            return;
        }
        this.createElementsOfMessage( "Ожидание подключения второго игрока..." );
        this.waitingForOpponentThread = new WaitingForOpponentThread( this );
        this.waitingForOpponentThread.start();
    }
    
    public void clickConnectOpponent() throws Exception{
        ModelParams modelParams = IONetModel.read( this.in );
        this.clickConnectOpponent( modelParams );
    }
    
    public synchronized void clickConnectOpponent( ModelParams modelParams ) throws Exception{
        this.model.set( modelParams );
        this.createElementsOfPlayGame();
        this.opponentMoveThread = new OpponentMoveThread( this );
        this.opponentMoveThread.start();
    }
    
    public synchronized void clickBack() throws Exception{
        if( this.model.isPlayGame() ){
            if( JOptionPane.showConfirmDialog( this.frame, "Текущий прогресс игры будет утерян. Продолжить?", "Внимание", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ) == JOptionPane.CANCEL_OPTION )
                return;
            this.deinitConnect();
        }
        this.createElementsOfInputParams();
    }
    
    public synchronized void clickButtonElement( ButtonElement button ) throws Exception{
        if( this.model.isPlayGame() ){
            if( this.model.getMyPlayer() != this.model.getCurrentPlayer() )
                return;
            int i = button.getI(),
                j = button.getJ();
            Element element = this.field.getElement( i, j );
            if( element.getTypeElement() != Element.TypeElement.TYPE_ELEMENT_EMPTY )
                return;
            this.out.println( i );
            this.out.println( j );
            super.clickButtonElement( button );
            if( !this.model.isPlayGame() )
                this.deinitConnect();
        }else
            super.clickButtonElement( button );
    }
    
    public synchronized void clickButtonElement( int i, int j ) throws Exception{
        super.clickButtonElement( this.getButtonElement( i, j ) );
        if( !this.model.isPlayGame() )
            this.deinitConnect();
    }
    
    public void clickOpponentMove() throws Exception{
        while( this.isPlayGame() ){
            if( this.isMyMove() ){
                Thread.sleep( 50 );
            }else{
                int i = Integer.valueOf( this.in.readLine() ),
                    j = Integer.valueOf( this.in.readLine() );
                this.clickButtonElement( i, j );
            }
        }
    }
};
