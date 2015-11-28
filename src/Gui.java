import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by Emre on 11/8/2015.
 */

public class Gui extends JFrame {
    Container container = getContentPane();
    JPanel gridPanel = new JPanel();
    JPanel optionsPanel = new JPanel();
    int selectedX = 0;
    int selectedY = 0;
    private JPanel borderPanel;
    private JPanel flowPanel;
    Game game;


    public Gui(Game instance) throws HeadlessException {
        gameGui("Checkers");
        game = instance;
    }

    public void gameGui(String title) {

        //super(title);
        this.setResizable(false);
        createBoard();
        this.setSize(800, 860);
        this.setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        gridPanel.setLayout(new GridLayout(8, 8));

        borderPanel = new JPanel(new BorderLayout());
        borderPanel.setBorder(BorderFactory.createTitledBorder("BorderLayout"));
        borderPanel.setOpaque(true);
        borderPanel.setBackground(Color.WHITE);
        borderPanel.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        //Defining flow panel.
        flowPanel = new JPanel(new FlowLayout(
                FlowLayout.CENTER, 4, 1));
        flowPanel.setBorder(
                BorderFactory.createTitledBorder("Options"));
        flowPanel.setOpaque(true);
        flowPanel.setBackground(Color.lightGray);

        //Initialize the buttons.
        JButton bRules = new JButton("Game Rules");
        JLabel labelDif = new JLabel("Difficulty:");
        JLabel labelInfo = new JLabel("Game Info: ...");
        JButton pButton = new JButton("Pause");

        //Radio buttons for 3 difficulty level.
        JRadioButton difEasy = new JRadioButton("Easy");
        JRadioButton difMedium = new JRadioButton("Medium");
        JRadioButton difHard = new JRadioButton("Hard");
        difMedium.setSelected(true); //Default difficulty: Medium.
        //Underlines the first letter and assign a keyboard shortcut(Alt+E).
        difEasy.setMnemonic(KeyEvent.VK_E);
        difMedium.setMnemonic(KeyEvent.VK_M);
        difHard.setMnemonic(KeyEvent.VK_H);

        //Groups the radio buttons.
        ButtonGroup group = new ButtonGroup();
        group.add(difEasy);
        group.add(difMedium);
        group.add(difHard);

        // Opens the page with the default internet browser which contains the gameplay info.
        bRules.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() > 0) {
                    if (Desktop.isDesktopSupported()) {
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            URI uri = new URI("http://www.indepthinfo.com/checkers/play.shtml");
                            desktop.browse(uri);
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        } catch (URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            }
        });

        //Radio button listener functions. They change the difficultyLevel.
        difEasy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() > 0) {
                    game.difficultyLevel = 1;
                }
            }
        });

        difMedium.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() > 0) {
                    game.difficultyLevel = 2;
                }
            }
        });

        difHard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() > 0) {
                    game.difficultyLevel = 3;
                }
            }
        });

        //Pause button listener.
        pButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (game.isGamePaused == false) {
                    game.isGamePaused = true;
                    pButton.setText("Continue");

                } else {
                    game.isGamePaused = false;
                    pButton.setText("Pause");
                }
            }
        });


//        Register action listeners for the radio buttons.
        //difEasy.addActionListener();
        //difMedium.addActionListener();
        //difHard.addActionListener((ActionListener) difHard);

//        public void actionPerformed(ActionEvent e) {
//            picture.setIcon(new ImageIcon("images/"
//                    + e.getActionCommand()
//                    + ".gif"));}


        //Adds the buttons and label to the flow panel
        flowPanel.add(labelDif);
        flowPanel.add(difEasy);
        flowPanel.add(difMedium);
        flowPanel.add(difHard);
        flowPanel.add(bRules);
        flowPanel.add(pButton);
        flowPanel.add(labelInfo);
        //Orders the buttons left to right.
        flowPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        //Puts the flow panel which contains the buttons into the south of a borderpanel.
        borderPanel.add(flowPanel, BorderLayout.SOUTH);
        //Puts the gameboard grid panel to the center of a border panel.
        borderPanel.add(gridPanel, BorderLayout.CENTER);
        //Adds the border panel into the container.
        container.add(borderPanel);

        this.setVisible(true);
    }

    //Create 8x8 grid board.
    void createBoard() {
        for (int y = 8; y > 0; y--) {
            for (int x = 1; x < 9; x++) {
                //JPanel square = new JPanel();
                Square square = new Square();
                if ((x + y) % 2 == 0)
                    square.setBackground(Color.DARK_GRAY);
                else
                    square.setBackground(Color.LIGHT_GRAY);


                square.getAccessibleContext().setAccessibleName(x + "," + y);//give each square a name which includes its coordinates
                square.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {//get clicked square on the board

                        squareClicked((JPanel) e.getComponent());//get the coordinates from square context);
                    }
                });
                gridPanel.add(square);
            }
        }
    }

    void placeToken(int x, int y) {
        int squareIndex = getGridIndexFromCoordinates(x, y);
        Square square = (Square) this.gridPanel.getComponent(squareIndex);
        square.isOccupied = true;
        this.gridPanel.updateUI();
    }

    //converts x,y coordinates to index of the gridPanel
    static int getGridIndexFromCoordinates(int x, int y) {
        int squareIndex;
        squareIndex = 8 - y;
        //squareIndex -=1;
        squareIndex *= 8;
        squareIndex += x;
        squareIndex -= 1;
        return squareIndex;
    }

    /**
     * @param panel
     */
    void squareClicked(JPanel panel) {
        String coordinate;
        String[] coordinateArray;
        int x, y;
        coordinate = panel.getAccessibleContext().getAccessibleName();
        coordinateArray = coordinate.split(",");
        x = Integer.parseInt(coordinateArray[0]);
        y = Integer.parseInt(coordinateArray[1]);
        System.out.format("Square clicked. x=%d  y=%d \n", x, y);
        selectedX = x;
        selectedY = y;

        //this.placeToken(x, y);
    }

    void refreshTheGui(GameState gameState) {//ArrayList<Token> tokenList
        //Clears the gridPanel.
        int squareCount = this.gridPanel.getComponentCount();
        for (int i = 0; i < squareCount; i++) {
            Square square = (Square) this.gridPanel.getComponent(i);
            square.clearToken();
            square.highlightSquare(false);
        }

        //Places the tokens on the grid.
        for (Token token : gameState.tokenList) {
            Square square = (Square) this.gridPanel.getComponent(getGridIndexFromCoordinates(token.x, token.y));
            square.placeToken(token);
        }

        //Checks if square is highlighted.
        if (selectedX != 0) {
            Square square = (Square) this.gridPanel.getComponent(getGridIndexFromCoordinates(selectedX, selectedY));
            //if(selected square == selectedX, selectedY for token.P1 in tokens.P1):
            square.highlightSquare(true);
        }

        this.gridPanel.updateUI();

    }

}
