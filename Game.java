import javax.swing.*;
import java.awt.*;

public class Game {

    public Game() {
        JFrame frame = new JFrame("My Tutor Game");
        ImageIcon icon = new ImageIcon("src/icon.jpg");

        frame.setTitle("My Tutor Game");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setIconImage(icon.getImage());
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        Game tutor = new Game();
    }
}