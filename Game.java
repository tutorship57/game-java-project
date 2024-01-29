import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Game {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ImageIcon BirdImg;
    JLabel BirdLabel;
    JFrame frame;
    ImageIcon backgroundImage;
    JLabel backgroundLabel;
    int velocity = 25;
    int frameWidth = 800, frameHeight = 600;
    public static void main(String[] args) {
        new Game();
    }
    public Game() {
        frame = new JFrame("My Tutor Game");
        frame.setBounds(50, 50, frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add backgroundLabel to the frame
        backgroundImage = new ImageIcon("src/background.jpg");
        backgroundLabel = new JLabel(backgroundImage);
        frame.add(backgroundLabel);

        // Bird
        BirdImg =  new ImageIcon("src/bird.png");
        BirdLabel = new JLabel(BirdImg);
        BirdLabel.setBounds(50, 50, 51, 51); // Set size directly
        backgroundLabel.add(BirdLabel);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        // Display the frame
        frame.setVisible(true);
    }
    private void handleKeyPress(KeyEvent e) {
        int code = e.getKeyCode();
        // goUp
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if (BirdLabel.getY() > 0 )    
                moveLabel(BirdLabel, BirdLabel.getX(), BirdLabel.getY() - velocity);
        // goDown
        } else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if (BirdLabel.getY() < frameHeight - BirdLabel.getHeight())
                moveLabel(BirdLabel, BirdLabel.getX(), BirdLabel.getY() + velocity);
        // goLeft
        } else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (BirdLabel.getX() > 0)
                moveLabel(BirdLabel, BirdLabel.getX() - velocity, BirdLabel.getY());
        // goRight
        } else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (BirdLabel.getX() < frameWidth - BirdLabel.getWidth())
                moveLabel(BirdLabel, BirdLabel.getX() + velocity, BirdLabel.getY());
        }
        System.out.println("X:"+BirdLabel.getX() +" Y: "+ BirdLabel.getY() );
    }
    private void moveLabel(JLabel label, int newX, int newY) {
        label.setLocation(newX, newY);
    }
}
