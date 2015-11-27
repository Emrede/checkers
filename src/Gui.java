import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created by Emre on 11/8/2015.
 */


public class Gui extends JFrame {
    Container container = getContentPane();
    JPanel gridPanel = new JPanel();
    JPanel optionsPanel = new JPanel();
    int selectedX = 0;
    int selectedY = 0;

    public Gui() throws HeadlessException {
        gameGui("GAME");
    }

    public void gameGui(String title) {

        //super(title);
        this.setSize(800, 800);
        this.setTitle(title);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        gridPanel.setLayout(new GridLayout(8, 8));
        optionsPanel.setLayout(new FlowLayout());
        createBoard();
        container.add(gridPanel);

        this.setVisible(true);
    }

    void createBoard() { //create 8x8 grid
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
        //clear the gridPanel
        int squareCount = this.gridPanel.getComponentCount();
        for (int i = 0; i < squareCount; i++) {
            Square square = (Square) this.gridPanel.getComponent(i);
            square.clearToken();
            square.highlightSquare(false);
        }

        //place the tokens on the grid
        for (Token token : gameState.tokenList) {
            Square square = (Square) this.gridPanel.getComponent(getGridIndexFromCoordinates(token.x, token.y));
            square.placeToken(token);
        }

        //check if square is highlighted
        if (selectedX != 0) {
            Square square = (Square) this.gridPanel.getComponent(getGridIndexFromCoordinates(selectedX, selectedY));
            square.highlightSquare(true);
        }

        this.gridPanel.updateUI();

    }

}
