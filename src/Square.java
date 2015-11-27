import javax.swing.*;
import java.awt.*;

/**
 * Created by Emre on 11/10/2015.
 */
public class Square extends JPanel {
    boolean isOccupied = false;
    Token occupand = null;
    boolean isHighlighted = false;

    public void placeToken(Token token) {
        isOccupied = true;
        occupand = token;
    }

    public void clearToken() {
        isOccupied = false;
        occupand = null;
    }

    public void highlightSquare(boolean state) {
        if (state) isHighlighted = true;
        else isHighlighted = false;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (this.isOccupied) {
            int squareHeight = this.getHeight();
            int height = (int) (squareHeight * 0.75);
            int diff = (squareHeight - height) / 2;
            if (occupand.player == Token.TokenPlayer.P1) {
                g.setColor(Color.black);
            } else {
                g.setColor(Color.red);
            }
            g.fillOval(diff, diff, height, height);

            //check if the token is a king
            if (occupand.tType == Token.TokenType.King) {
                height = (int) (squareHeight * 0.45);
                diff = (squareHeight - height) / 2;
                g.setColor(Color.orange);
                g.fillOval(diff, diff, height, height);
            }

            //check if the square is highlighted
            if (isHighlighted) {
                height = (int) (squareHeight * 0.9);
                diff = (squareHeight - height) / 2;
                g.setColor(Color.orange);
                g.drawRect(diff, diff, height, height);
            }
        }
    }
}
