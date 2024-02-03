import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.logging.Handler;

public class Game {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    ImageIcon BirdImg;
    ImageIcon TutorImg;
    JLabel BirdLabel;
    JLabel TutorLabel ;
    JFrame frame;
    ImageIcon backgroundImage;
    JLabel backgroundLabel;
    int velocity = 50;
    Toolkit t = Toolkit.getDefaultToolkit();
    Dimension d = t.getScreenSize();
    int frameWidth = 800, frameHeight = 600;
    public static void main(String[] args) {
        new Game();
        
    }
    public Game() {
        frame = new JFrame("My Tutor Game");
        frame.setBounds(50, 50, 800,600);
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add backgroundLabel to the frame
        backgroundImage = new ImageIcon("src/background.jpg");
        backgroundLabel = new JLabel(backgroundImage);
        frame.add(backgroundLabel);

        // Bird
        BirdImg =  new ImageIcon("src/bird.png");
        TutorImg =  new ImageIcon("src/icon.jpg");
        BirdLabel = new JLabel(BirdImg);
        TutorLabel = new JLabel(TutorImg);

        TutorLabel.setBounds(20, 20,50, 50);
        BirdLabel.setBounds(50, 50, 50, 50); // Set size directly
        backgroundLabel.add(BirdLabel);
        backgroundLabel.add(TutorLabel);
        
        frame.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
               int ClickX = e.getX();
               int ClickYScreen = e.getYOnScreen();
               int ClickY = e.getY();
                System.out.println( ClickY);
                System.out.println(ClickYScreen);
                
                // Toolkit t = Toolkit.getDefaultToolkit();  
                // System.out.println("Screen resolution = " + t.getScreenResolution());  
                // Dimension d = t.getScreenSize();  
                // System.out.println("Screen width = " + d.width);  
                // System.out.println("Screen height = " + d.height);
                  
            }

            @Override
            public void mousePressed(MouseEvent e) {
               
            }

            @Override
            public void mouseReleased(MouseEvent e) {
               
        }

            @Override
            public void mouseEntered(MouseEvent e) {
                
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                handleKeyPress(e);
                checkObjectPosition(BirdLabel, frame);
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
        Point locationOnScreen = BirdLabel.getLocationOnScreen();
        int x = locationOnScreen.x;
        int y = locationOnScreen.y;
        System.out.println(BirdLabel.getY()+ BirdLabel.getHeight()+(y-frameHeight));
        // System.out.println(frameHeight - BirdLabel.getHeight()-(y-frameHeight));
        if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            if (BirdLabel.getY() > 0 )
                BirdLabel.setLocation(BirdLabel.getX(), BirdLabel.getY() - velocity);
            else BirdLabel.setLocation(BirdLabel.getX(), 0);
        // goDown
        } else if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            if (BirdLabel.getY()+ BirdLabel.getHeight()+(y-frameHeight) < frameHeight - BirdLabel.getHeight()-(y-frameHeight))
                BirdLabel.setLocation(BirdLabel.getX(), BirdLabel.getY() + velocity);
            else BirdLabel.setLocation(BirdLabel.getX(), frameHeight - BirdLabel.getHeight()-(y-frameHeight));
        // goLeft
        } else if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
            if (BirdLabel.getX() > 0)
                BirdLabel.setLocation(BirdLabel.getX() - velocity, BirdLabel.getY());
            else BirdLabel.setLocation(0, BirdLabel.getY());
        // goRight
        } else if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
            if (BirdLabel.getX() < frameWidth - BirdLabel.getWidth())
                BirdLabel.setLocation(BirdLabel.getX() + velocity, BirdLabel.getY());
            else BirdLabel.setLocation(frameWidth - BirdLabel.getWidth(), BirdLabel.getY());
        }
        System.out.println("X:" + BirdLabel.getX() + " Y:" + BirdLabel.getY());
        
    }
    private static void checkObjectPosition(Component component, JFrame frame) {
        Point locationOnScreen = component.getLocationOnScreen();
        int x = locationOnScreen.x;
        int y = locationOnScreen.y;
        System.out.println("On screen X: "+x);
        System.out.println("On screen Y: "+y);
        System.out.println("Height: "+component.getHeight());
        System.out.println("Width: "+component.getWidth());
        int frameWidth = frame.getWidth();
        int frameHeight = frame.getHeight();

        if (x < 0 || y < 0 || x  > frameWidth || y  > frameHeight) {
            System.out.println("Object is out of frame!");
        } else {
            System.out.println("Object is within the frame.");
        }
    }
    
}