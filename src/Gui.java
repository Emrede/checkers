import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * Created by Emre on 11/8/2015.
 */

public class Gui extends JFrame {
    Container container = getContentPane();
    JPanel gridPanel = new JPanel();
    JPanel optionsPanel = new JPanel();
    ArrayList<Move> allowedMoves;
    int selectedX = 0;
    int selectedY = 0;
    private JPanel borderPanel;
    private JPanel flowPanel;
    String infoText = "";
    JLabel labelInfo = new JLabel("Game State: ...");
    Game game;
    int targetX, targetY;
    Component frame;
    boolean helpClicked = false;
    final int SleepDuration = 500;//[ms]


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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
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
        JButton pauseButton = new JButton("Pause");
        JButton helpButton = new JButton("Help");
        JButton restartButton = new JButton("Restart");

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
                    game.difficultyLevel = 5;
                }
            }
        });

        difMedium.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() > 0) {
                    game.difficultyLevel = 10;
                }
            }
        });

        difHard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getClickCount() > 0) {
                    game.difficultyLevel = 20;
                }
            }
        });

        helpButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (allowedMoves == null)
                    allowedMoves = Game.getAllAllowedMoves(game.actualGameState);
                else
                    allowedMoves = null;
                helpClicked = true;
                refreshTheGui(game.actualGameState);


            }
        });
        restartButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game.restartGame();
                refreshTheGui(game.actualGameState);
                super.mouseClicked(e);
            }
        });

        //Pause button listener.
        pauseButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);

                if (game.isGamePaused == false) {
                    game.isGamePaused = true;
                    pauseButton.setText("Continue");

                } else {
                    game.isGamePaused = false;
                    pauseButton.setText("Pause");
                }
            }
        });

        //Adds the buttons and label to the flow panel
        flowPanel.add(labelDif);
        flowPanel.add(difEasy);
        flowPanel.add(difMedium);
        flowPanel.add(difHard);
        flowPanel.add(bRules);
        flowPanel.add(helpButton);
        flowPanel.add(pauseButton);
        flowPanel.add(restartButton);
        flowPanel.add(labelInfo);
        //Orders the buttons left to right.
        flowPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);

        //Puts the flow panel which contains the buttons into the south of a borderpanel.
        borderPanel.add(flowPanel, BorderLayout.SOUTH);
        //Puts the game board grid panel to the center of a border panel.
        borderPanel.add(gridPanel, BorderLayout.CENTER);
        //Adds the border panel into the container.
        container.add(borderPanel);

        this.setVisible(true);
    }

    //Create 8x8 grid board
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

    //Debug function. Used for test.
//    void placeToken(int x, int y) {
//        int squareIndex = getGridIndexFromCoordinates(x, y);
//        Square square = (Square) this.gridPanel.getComponent(squareIndex);
//        square.isOccupied = true;
//        this.gridPanel.updateUI();
//    }

    //Converts x,y coordinates to index of the gridPanel
    static int getGridIndexFromCoordinates(int x, int y) {
        int squareIndex;
        squareIndex = 8 - y;
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
        boolean newTokenSelected = false;
        coordinate = panel.getAccessibleContext().getAccessibleName();
        //Creates coordinate names.
        coordinateArray = coordinate.split(",");
        x = Integer.parseInt(coordinateArray[0]);
        y = Integer.parseInt(coordinateArray[1]);
        System.out.format("Square clicked. x=%d  y=%d \n", x, y);
        selectedX = x;
        selectedY = y;
        ArrayList<Move> tmpMoves;
        //Checks all allowed moves and puts them into allowedMoves list.
        tmpMoves = game.getAllAllowedMoves(game.actualGameState);
        if (tmpMoves != null) {
            for (Move move : tmpMoves) {
                //if (move.isAnEatingMove == false) { //If is there any compulsory movement, can't select the token. If not select it.
                Token anyToken = game.isThereAnyTokenAtLocation(selectedX, selectedY, game.actualGameState.tokenList);
                if (anyToken != null && anyToken.player == game.actualGameState.currentPlayer) {//If is there a token,
                    game.actualGameState.selectedToken = anyToken; //Select it.
                    game.actualGameState.isAnyTokenSelected = true;
                    targetX = 0;
                    targetY = 0;
                    newTokenSelected = true;
                    infoText = "";
                }
//                    else {
//                        JOptionPane.showMessageDialog(frame, "You have a compulsory move, can't move this piece.");
//                        break;
//                    }
                //}
            }
        }
        //first select: selectedToken and target reset
        //second select: target x,y
        //if availMoves contains a move with selected token and target move
        if (game.actualGameState.isAnyTokenSelected) {
            targetX = x;
            targetY = y;
            // Move tmpMove=null;
            if (tmpMoves != null) {
                boolean foundCorrectMove = false;
                for (Move move : tmpMoves) {
                    if (move.targetX == targetX
                            && move.targetY == targetY
                            && move.token == game.actualGameState.selectedToken) {
                        System.out.format("Move. tx=%d  ty=%d to x=%d y=%d", game.actualGameState.selectedToken.x, game.actualGameState.selectedToken.y, targetX, targetY);
                        Move.move(game.actualGameState, move);
                        infoText = "";
                        foundCorrectMove = true;
                        game.actualGameState.isAnyTokenSelected = false;
                        game.actualGameState.selectedToken = null;
                        refreshTheGui(game.actualGameState);

                        Token.TokenPlayer currentPlayer = game.actualGameState.currentPlayer;
                        do {
                            try {
                                Thread.sleep(SleepDuration);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                            Minimax minimax = Minimax.minimax(game.difficultyLevel, game.actualGameState, Integer.MAX_VALUE, Integer.MIN_VALUE);
                            if (minimax.move != null) {
                                game.actualGameState.selectedToken = minimax.move.token;
                                Move.move(game.actualGameState,minimax.move);
                                refreshTheGui(game.actualGameState);
                            }

                            GameState.GameResult gameResult = GameState.getResult(game.actualGameState);
                            if (gameResult != GameState.GameResult.Continue) {
                                if (gameResult == GameState.GameResult.P1Wins) infoText = "P1 wins. Congrats";
                                else infoText = "P2 wins.";
                            }
                        }while(game.actualGameState.currentPlayer != currentPlayer);//Move until current player changes. In case of multistep move
                    }
                }
                if (!foundCorrectMove && !newTokenSelected) {
                    infoText = "Cannot move there!";
                    System.out.println("Cannot move there");
                }
            }

        }
        refreshTheGui(game.actualGameState);
    }

    void refreshTheGui(GameState gameState) {//ArrayList<Token> tokenList
        //Clears the gridPanel.
        int squareCount = this.gridPanel.getComponentCount();
        for (int i = 0; i < squareCount; i++) {
            Square square = (Square) this.gridPanel.getComponent(i);
            square.clearToken();
            square.highlightSquare(false);
            square.greenHighlightSquare(false);
        }

        //Places the tokens on the grid.
        for (Token token : gameState.tokenList) {
            Square square = (Square) this.gridPanel.getComponent(getGridIndexFromCoordinates(token.x, token.y));
            square.placeToken(token);
        }

        //Checks if square is highlighted.
        if (game.actualGameState.selectedToken != null) {
            Square square = (Square) this.gridPanel.getComponent(getGridIndexFromCoordinates(gameState.selectedToken.x, gameState.selectedToken.y));
            square.highlightSquare(true);
            //square.greenHighlightSquare(true);

            //if (square.isGreenHighlighted == true) {
//                ArrayList<Move> visMoveList = game.getPossibleMovesForToken(gameState, game.actualGameState.selectedToken);
//
//                if (visMoveList != null) {//If null there are no possible moves for this token
//                    for (Move move : visMoveList) {
//                        Square targetSquare = (Square) this.gridPanel.getComponent(getGridIndexFromCoordinates(move.targetX, move.targetY));
//                        targetSquare.greenHighlightSquare(true);
//
//                    }
//                }

            //}
        }

        if (helpClicked) {//show all available moves
            if (allowedMoves != null) {
                for (Move move : allowedMoves) {
                    Square targetSquare = (Square) gridPanel.getComponent(getGridIndexFromCoordinates(move.targetX, move.targetY));
                    targetSquare.greenHighlightSquare(true);
                    //targetSquare = null;
                    targetSquare = (Square) gridPanel.getComponent(getGridIndexFromCoordinates(move.token.x, move.token.y));
                    targetSquare.highlightSquare(true);
                }
            }
            helpClicked = false;//show moves only once
        } else {//show only selected tokens moves
            ArrayList<Move> visMoveList = Game.getAllAllowedMoves(gameState);
            if (visMoveList != null) {
                for (Move move : visMoveList) {
                    if (move.token == gameState.selectedToken) {
                        Square targetSquare = (Square) gridPanel.getComponent(getGridIndexFromCoordinates(move.targetX, move.targetY));
                        targetSquare.greenHighlightSquare(true);
//                        targetSquare = null;
//                    targetSquare = (Square) gridPanel.getComponent(getGridIndexFromCoordinates(move.token.x, move.token.y));
//                    targetSquare.highlightSquare(true);
                    }
                }
            }
        }


        if (game.actualGameState.currentPlayer == Token.TokenPlayer.P1) {
            labelInfo.setText("Black moves " + infoText);
        } else {
            labelInfo.setText("Red moves " + infoText);
        }


        this.gridPanel.updateUI();
    }
}


