import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Game {
    static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    static Timer timer = new Timer();
    static Random rd = new Random();
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ImageIcon BirdImg;
    JLabel BirdLabel;
    JFrame frame;
    ImageIcon backgroundImage;
    JLabel backgroundLabel;
    int velocity = 15; // Adjusted the velocity for testing
    int frameWidth = 800, frameHeight = 600;
    Set<Integer> pressedKeys = new HashSet<>();

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        frame = new JFrame("My Tutor Game");
        frame.setBounds(50, 50, frameWidth, frameHeight);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = frame.getContentPane();

        // Add backgroundLabel to the frame with null layout manager
        backgroundImage = new ImageIcon("src/background.jpg");
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setLayout(null);
        container.add(backgroundLabel);
        
        
        // Bird
        BirdImg = new ImageIcon("src/bird.png");
        BirdLabel = new JLabel(BirdImg);
        BirdLabel.setBounds(50, 50, 51, 51); // Set size directly
        backgroundLabel.add(BirdLabel);

        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();
                pressedKeys.add(code);
                handleMovement();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                int code = e.getKeyCode();
                pressedKeys.remove(code);
                handleMovement();
            }
        });
        fallingObject1(backgroundLabel);
        fallingObject2(backgroundLabel);
        // Display the frame
        frame.setVisible(true);
    }

    private void handleMovement() {
        // goUp
        if (pressedKeys.contains(KeyEvent.VK_W) || pressedKeys.contains(KeyEvent.VK_UP)) {
            if (BirdLabel.getY() > 0)
                BirdLabel.setLocation(BirdLabel.getX(), BirdLabel.getY() - velocity);
            else BirdLabel.setLocation(BirdLabel.getX(), 0);
        }
        // goDown
        if (pressedKeys.contains(KeyEvent.VK_S) || pressedKeys.contains(KeyEvent.VK_DOWN)) {
            if (BirdLabel.getY() < frameHeight - BirdLabel.getHeight())
                BirdLabel.setLocation(BirdLabel.getX(), BirdLabel.getY() + velocity);
            else BirdLabel.setLocation(BirdLabel.getX(), frameHeight - BirdLabel.getHeight());

        }
        // goLeft
        if (pressedKeys.contains(KeyEvent.VK_A) || pressedKeys.contains(KeyEvent.VK_LEFT)) {
            if (BirdLabel.getX() > 0)
                BirdLabel.setLocation(BirdLabel.getX() - velocity, BirdLabel.getY());
            else BirdLabel.setLocation(0, BirdLabel.getY());
        }
        // goRight
        if (pressedKeys.contains(KeyEvent.VK_D) || pressedKeys.contains(KeyEvent.VK_RIGHT)) {
            if (BirdLabel.getX() < frameWidth - BirdLabel.getWidth())
                BirdLabel.setLocation(BirdLabel.getX() + velocity, BirdLabel.getY());
            else BirdLabel.setLocation(frameWidth - BirdLabel.getWidth(), BirdLabel.getY());
        }
        System.out.println("X:" + BirdLabel.getX() + " Y:" + BirdLabel.getY());
    }

    private void fallingObject1(JLabel label) {
        //test 
        JLabel o = Obj1.getJLabel();
        int minX = 250;
        int maxX = frameWidth - minX;
        int minY = 100;
        int maxY = frameHeight - minY;
        scheduler.scheduleAtFixedRate(() -> {
            label.add(o);
            o.setVisible(true);
            int positionX = rd.nextInt(maxX - minX + 1) + minX;
            int positionY = rd.nextInt(maxY - minY + 1) + minY;
            o.setLocation(positionX, positionY);
            scheduler.schedule(() -> {
                o.setVisible(false);
            }, 700, TimeUnit.MILLISECONDS);
        }, 1000, 500, TimeUnit.MILLISECONDS);
    }
    private void fallingObject2(JLabel label) {
        //test 
        JLabel o = Obj2.getJLabel();
        int minX = 250;
        int maxX = frameWidth - minX;
        int minY = 100;
        int maxY = frameHeight - minY;
        scheduler.scheduleAtFixedRate(() -> {
            label.add(o);
            o.setVisible(true);
            int positionX = rd.nextInt(maxX - minX + 1) + minX;
            int positionY = rd.nextInt(maxY - minY + 1) + minY;
            o.setLocation(positionX, positionY);
            scheduler.schedule(() -> {
                o.setVisible(false);
            }, 700, TimeUnit.MILLISECONDS);
        }, 700, 500, TimeUnit.MILLISECONDS);
    }
}