package org.suai.TicTacToeGame.view.gui;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.suai.TicTacToeGame.GameException;
import org.suai.TicTacToeGame.model.*;

public class View{
    public static String getViewElement( Element.TypeElement typeElement ){
        switch( typeElement ){
            case TYPE_ELEMENT_EMPTY : return "";
            case TYPE_ELEMENT_X : return "X";
            case TYPE_ELEMENT_0 : return "0";
        }
        throw new GameException( "is not correct typeElement : " + typeElement );
    }
    
    public static String getViewElement( Element element ){
        return View.getViewElement( element.getTypeElement() );
    }
    
    private JComboBox< String > selectCountRowsComboBox;
    private JComboBox< String > selectCountColsComboBox;
    private JComboBox< String > selectLengthWinComboBox;
    private int lastIndexCountRows = -1;
    private int lastIndexCountCols = -1;
    private int lastIndexLengthWin = -1;
    protected JFrame frame;
    protected JPanel panel;
    protected Model model;
    protected Field field;
    protected ButtonElement arrayButtonElement[][];
    protected JLabel statusLabel;
    protected ClickSelectSizeFieldListener clickSelectSizeFieldListener = new ClickSelectSizeFieldListener( this );
    protected ClickBackListener clickBackListener = new ClickBackListener( this );
    protected ClickButtonElementListener clickButtonElementListener = new ClickButtonElementListener( this );
    
    protected ButtonElement getButtonElement( int i, int j ){
        int countRows = this.field.getCountRows(),
            countCols = this.field.getCountCols();
        if( i < 0 || i > countRows )
            throw new GameException( "is not correct i : " + i );
        if( j < 0 || j > countCols )
            throw new GameException( "is not correct j : " + j );
        return this.arrayButtonElement[ i ][ j ];
    }
    
    protected void createElementsOfInputParams(){
        //инициализация + очистка панели
        int xFieldSizeLabel = 10,
            yFieldSizeLabel = 10,
            hFieldSizeLabel = 20,
            wFieldSizeLabel = 210,
            xCountRowsComboBox = xFieldSizeLabel,
            yCountRowsComboBox = yFieldSizeLabel + hFieldSizeLabel + 10,
            hCountRowsComboBox = hFieldSizeLabel,
            wCountRowsComboBox = ( wFieldSizeLabel - 20 ) / 2,
            xXLabel = xCountRowsComboBox + wCountRowsComboBox + 5,
            yXLabel = yCountRowsComboBox,
            hXLabel = hCountRowsComboBox,
            wXLabel = 10,
            xCountColsComboBox = xXLabel + wXLabel + 5,
            yCountColsComboBox = yXLabel,
            hCountColsComboBox = hXLabel,
            wCountColsComboBox = wCountRowsComboBox,
            xLengthWinLabel = xFieldSizeLabel,
            yLengthWinLabel = yCountRowsComboBox + hCountRowsComboBox + 10,
            hLengthWinLabel = hFieldSizeLabel,
            wLengthWinLabel = wFieldSizeLabel,
            xLengthWinComboBox = xFieldSizeLabel,
            yLengthWinComboBox = yLengthWinLabel + hLengthWinLabel + 10,
            hLengthWinComboBox = hFieldSizeLabel,
            wLengthWinComboBox = wFieldSizeLabel,
            xStartGameButton = xFieldSizeLabel,
            yStartGameButton = yLengthWinComboBox + hLengthWinComboBox + 10,
            hStartGameButton = hFieldSizeLabel,
            wStartGameButton = wFieldSizeLabel,
            hPanel = yStartGameButton + hStartGameButton + 10,
            wPanel = xFieldSizeLabel + wFieldSizeLabel + 10;
        this.panel.removeAll();
        this.panel.setLayout( null );
        //добавление надписи выбора размера поля
        JLabel fieldSizeLabel = new JLabel( "Выберите размер поля:" );
        fieldSizeLabel.setBounds( xFieldSizeLabel, yFieldSizeLabel, wFieldSizeLabel, hFieldSizeLabel );
        this.panel.add( fieldSizeLabel );
        //добавление выпадающего списка
        this.selectCountRowsComboBox = new JComboBox< String >();
        this.selectCountColsComboBox = new JComboBox< String >();
        this.selectLengthWinComboBox = new JComboBox< String >();
        for( int i = Field.MIN_COUNT_ROWS; i <= Field.MAX_COUNT_ROWS; ++i ){
            String sBuf = String.valueOf( i );
            this.selectCountRowsComboBox.addItem( sBuf );
            this.selectCountColsComboBox.addItem( sBuf );
            this.selectLengthWinComboBox.addItem( sBuf );
        }
        this.selectCountRowsComboBox.setBounds( xCountRowsComboBox, yCountRowsComboBox, wCountRowsComboBox, hCountRowsComboBox );
        this.selectCountColsComboBox.setBounds( xCountColsComboBox, yCountColsComboBox, wCountColsComboBox, hCountColsComboBox );
        this.selectLengthWinComboBox.setBounds( xLengthWinComboBox, yLengthWinComboBox, wLengthWinComboBox, hLengthWinComboBox );
        if( this.lastIndexCountRows != -1 )
            this.selectCountRowsComboBox.setSelectedIndex( this.lastIndexCountRows );
        if( this.lastIndexCountCols != -1 )
            this.selectCountColsComboBox.setSelectedIndex( this.lastIndexCountCols );
        if( this.lastIndexLengthWin != -1 )
            this.selectLengthWinComboBox.setSelectedIndex( this.lastIndexLengthWin );
        this.panel.add( this.selectCountRowsComboBox );
        this.panel.add( this.selectCountColsComboBox );
        this.panel.add( this.selectLengthWinComboBox );
        //добавление надписи с крестиком между выпадающими списками
        JLabel xLabel = new JLabel( "X" );
        xLabel.setBounds( xXLabel, yXLabel, wXLabel, hXLabel );
        this.panel.add( xLabel );
        //добавление надписи выбора длины ряда для победы
        JLabel lengthWinLabel = new JLabel( "Выберите длину ряда для победы:" );
        lengthWinLabel.setBounds( xLengthWinLabel, yLengthWinLabel, wLengthWinLabel, hLengthWinLabel );
        this.panel.add( lengthWinLabel );
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
    
    protected void createElementsOfPlayGame(){
        //инициализация + очистка панели
        int countRows = this.field.getCountRows(),
            countCols = this.field.getCountCols(),
            hPanel = countRows * 40,
            wPanel = countCols * 45;
        this.panel.removeAll();
        this.panel.setLayout( new BorderLayout() );
        //добавление панели для меню
        JPanel menuPanel = new JPanel( new BorderLayout() );
        this.panel.add( menuPanel, BorderLayout.PAGE_START );
        //добавление полоски меню
        JMenuBar menuBar = new JMenuBar();
        menuPanel.add( menuBar, BorderLayout.CENTER );
        //добавление пунктов меню
        JMenu menu = new JMenu( "Меню" );
        menuBar.add( menu );
        JMenuItem menuItem = menu.add( "Назад" );
        menuItem.addActionListener( this.clickBackListener );
        //добавление элементов поля
        JPanel elementsPanel = new JPanel( new GridLayout( countRows, countCols ) );
        this.panel.add( elementsPanel, BorderLayout.CENTER );
        this.arrayButtonElement = new ButtonElement[ countRows ][ countCols ];
        for( int i = 0; i < countRows; ++i ){
            for( int j = 0; j < countCols; ++j ){
                Element element = this.field.getElement( i, j );
                ButtonElement button = new ButtonElement( View.getViewElement( element ), i, j );
                button.addActionListener( this.clickButtonElementListener );
                elementsPanel.add( button );
                this.arrayButtonElement[ i ][ j ] = button;
            }
        }
        //добавлние текстового поля для вывода статуса игры
        this.statusLabel = new JLabel( "Ход " + View.getViewElement( this.model.getCurrentPlayer() ) );
        this.panel.add( this.statusLabel, BorderLayout.PAGE_END );
        //установка размера для панели
        this.panel.setPreferredSize( new Dimension( wPanel, hPanel ) );
        this.panel.setMaximumSize( this.panel.getPreferredSize() );
        this.panel.setMinimumSize( this.panel.getPreferredSize() );
        //упаковка окна
        this.frame.pack();
    }
    
    public View( Model model, JFrame frame, JPanel panel ){
        this.frame = frame;
        this.panel = panel;
        this.model = model;
        this.field = this.model.getField();
    }
    
    public synchronized Model getModel(){
        return this.model;
    }
    
    public synchronized void clickPlayGame(){
        this.lastIndexCountRows = this.selectCountRowsComboBox.getSelectedIndex();
        this.lastIndexCountCols = this.selectCountColsComboBox.getSelectedIndex();
        this.lastIndexLengthWin = this.selectLengthWinComboBox.getSelectedIndex();
        int countRows = Field.MIN_COUNT_ROWS + this.lastIndexCountRows,
            countCols = Field.MIN_COUNT_COLS + this.lastIndexCountCols,
            lengthWin = Field.MIN_COUNT_COLS + this.lastIndexLengthWin;
        this.model.set( new ModelParams( countRows, countCols, lengthWin, Element.TypeElement.TYPE_ELEMENT_X ) );
        this.createElementsOfPlayGame();
    }
    
    public synchronized void clickBack() throws Exception{
        if( this.model.isPlayGame() ){
            if( JOptionPane.showConfirmDialog( this.frame, "Текущий прогресс игры будет утерян. Продолжить?", "Внимание", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE ) == JOptionPane.CANCEL_OPTION )
                return;
        }
        this.createElementsOfInputParams();
    }
    
    public synchronized void clickButtonElement( ButtonElement button ) throws Exception{
        if( this.model.isPlayGame() ){
            int i = button.getI(),
                j = button.getJ();
            Element element = this.field.getElement( i, j );
            if( element.getTypeElement() != Element.TypeElement.TYPE_ELEMENT_EMPTY )
                return;
            this.model.executeMove( i, j );
            button.setText( View.getViewElement( element ) );
            if( this.model.isPlayGame() ){
                this.statusLabel.setText( "Ход " + View.getViewElement( this.model.getCurrentPlayer() ) );
            }else{
                GameResult gameResult = this.model.getGameResult();
                GameResult.TypeResult typeResult = gameResult.getTypeResult();
                String sBuf = "";
                if( typeResult == GameResult.TypeResult.TYPE_RESULT_WIN ){
                    sBuf = "Победил " + View.getViewElement( gameResult.getWinnerPlayer() );
                }else if( typeResult == GameResult.TypeResult.TYPE_RESULT_DRAW ){
                    sBuf = "Ничья";
                }
                this.statusLabel.setText( sBuf );
                JOptionPane.showMessageDialog( this.frame, sBuf, "Внимание", JOptionPane.INFORMATION_MESSAGE );
            }
        }else
            JOptionPane.showMessageDialog( this.frame, "Игра окончена.", "Внимание", JOptionPane.INFORMATION_MESSAGE );
    }
    
    public synchronized void clickButtonElement( int i, int j ) throws Exception{
        this.clickButtonElement( this.getButtonElement( i, j ) );
    }
    
    public synchronized void run(){
        this.createElementsOfInputParams();
    }
};
