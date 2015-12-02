import javax.swing.*;
import java.awt.*;

/**
 * Created by Emre on 11/10/2015.
 */
public class Square extends JPanel {
    boolean isOccupied = false;
    Token occupant = null;
    boolean isHighlighted = false;
    boolean isGreenHighlighted = false;

    public void placeToken(Token token) {
        isOccupied = true;
        occupant = token;
    }

    public void clearToken() {
        isOccupied = false;
        occupant = null;
    }

    public void highlightSquare(boolean state) {
        if (state) isHighlighted = true;
        else isHighlighted = false;
    }

    public void greenHighlightSquare(boolean state) {
        if (state) isGreenHighlighted = true;
        else isGreenHighlighted = false;
    }


    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int squareHeight = this.getHeight();
        int height = (int) (squareHeight * 0.75);
        int diff = (squareHeight - height) / 2;
        if (this.isOccupied) {
            if (occupant.player == Token.TokenPlayer.P1) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.red);
            }
            g.fillOval(diff, diff, height, height);

            //Checks if the token is a king
            if (occupant.tType == Token.TokenType.King) {
                height = (int) (squareHeight * 0.45);
                diff = (squareHeight - height) / 2;
                g.setColor(Color.orange);
                g.fillOval(diff, diff, height, height);
            }

            //Checks if the square is highlighted

            if (isGreenHighlighted) {
                height = (int) (squareHeight * 0.9);
                diff = (squareHeight - height) / 2;
                g.setColor(Color.green);
                g.drawRect(diff, diff, height, height);
            }
            else if(isHighlighted){
                height = (int) (squareHeight * 0.9);
                diff = (squareHeight - height) / 2;
                g.setColor(Color.orange);
                g.drawRect(diff, diff, height, height);
            }

        } else {

            if (isGreenHighlighted) {
                height = (int) (squareHeight * 0.9);
                diff = (squareHeight - height) / 2;
                g.setColor(Color.green);
                g.drawRect(diff, diff, height, height);
            }
        }
    }
}
