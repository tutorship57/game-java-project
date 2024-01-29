import javax.swing.*;
import java.awt.*;

public class Game {

    public Game() {
        JFrame frame = new JFrame();
        
        frame.setTitle("My Tutor Game");
        frame.setBounds(50, 50, 200, 30);
        frame.setSize(1080, 720);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set Icon
        ImageIcon icon = new ImageIcon("src/icon.jpg");
        frame.setIconImage(icon.getImage());

        // set Background
        ImageIcon background = new ImageIcon("src/background.jpg");
        JLabel backgroundLabel = new JLabel(background);
        backgroundLabel.setHorizontalAlignment(JLabel.CENTER);
        backgroundLabel.setBounds(0, 0, frame.getWidth(), frame.getHeight());

        frame.add(backgroundLabel);
        frame.setVisible(true);
    }
}