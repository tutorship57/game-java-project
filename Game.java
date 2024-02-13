import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class Game {
    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 755;

    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static Random rd = new Random();
    private Clip soundClip;
    private JLabel scoreBox;
    private JFrame frame;
    private Container container;
    private ImageIcon switchbackgroundImage;
    private ImageIcon StartImg;
    private JLabel switchbackgroundJLabel;
    private ImageIcon backgroundImage;
    private JLabel backgroundlabel;
    private int score = 0;
    private JButton playButton;
    private boolean isStart = false;
    private JLabel timeBox;
    private int timeScedule =60;

    MouseAdapter wrongLabelListener = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            JLabel pressedLabel = (JLabel)e.getSource();
            gameUpdate(pressedLabel);
            pressedLabel.setLocation(pressedLabel.getX(), FRAME_HEIGHT);
            playSound("src/sound/quack.wav");
        }
    };
    MouseAdapter startGamelistener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            if(playButton == (JButton)e.getSource()){
                container.remove(switchbackgroundJLabel);
                Components();
                scoreBox();
                container.repaint();
                startFalling(7);
            }
            // JLabel pressedLabel = (JLabel)e.getSource();
        }
    };
    MouseAdapter labelMouseListener = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            JLabel pressedLabel = (JLabel)e.getSource();
            gameUpdate(pressedLabel);
            playSound("src/sound/pop.wav");
        }
    };
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Game();
            }
        });
    }
    public Game() {
        frame = new JFrame("My Tutor Game");
        container = frame.getContentPane();

        backgroundImage = new ImageIcon("src/img/background.png");
        backgroundlabel = new JLabel(backgroundImage);
        backgroundlabel.setLayout(null);

        Components2();
        // startFalling(7);
        // scoreBox();


        frame.setBounds(0, 0, FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);//let the background in the middle
        frame.setVisible(true);
    }
    private void scoreBox() {
        Color textColor = new Color(255, 0, 0); // ตั้งค่าสี (RGB)
        Font font = new Font("Arial", Font.BOLD, 20);
        scoreBox = new JLabel();
        scoreBox.setBounds(20, 20, 150, 100);
        backgroundlabel.add(scoreBox);
        scoreBox.setFont(font);
        scoreBox.setForeground(textColor);
        scoreBox.setText("Score: " + score);
        Color textColor2 = new Color(255, 0, 0); // ตั้งค่าสี (RGB)
        Font font2 = new Font("Arial", Font.BOLD, 60);
        timeBox = new JLabel();
        timeBox.setBounds((FRAME_WIDTH/2)-30,FRAME_HEIGHT/16, 150, 100);
        backgroundlabel.add(timeBox);
        timeBox.setFont(font2);
        timeBox.setForeground(textColor2);
        timeBox.setText(""+timeScedule);
    }
    private void gameUpdate(JLabel clickedLabel) {
        if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Pomelo.getImage())) {
            score += 1;
        }
        else if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Banana.getImage())) {
            score += 2;
        }
        else if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Frog.getImage())) {
            score -= 1; 
        }
        else if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Tomato.getImage())) {
            score += 1; 
        }
        else if (((ImageIcon)(clickedLabel.getIcon())).getImage().equals(Peach.getImage())) {
            score += 1; 
        }
        scoreBox.setText("Score: " + score);
        if(score >= 50) {
            container.remove(backgroundlabel);
            Components2(); 
            container.repaint();
            score = 0;
            isStart = false;
        }
        clickedLabel.setVisible(false);

    }
    private void Components() {
        backgroundImage = new ImageIcon("src/img/background.png");
        backgroundlabel = new JLabel(backgroundImage);
        backgroundlabel.setLayout(null);
        container.add(backgroundlabel);
    }
    private void Components2() {
        switchbackgroundImage = new ImageIcon("src/img/background.png");
        switchbackgroundJLabel = new JLabel(backgroundImage);

        backgroundlabel.setLayout(null);
        container.add(switchbackgroundJLabel);
        StartImg = new ImageIcon("src/img/start.png");
        playButton = new JButton();
        playButton.setIcon(StartImg);
        playButton.setBounds(FRAME_WIDTH / 2 - StartImg.getIconWidth() / 2, FRAME_HEIGHT / 2 - StartImg.getIconHeight() / 2, StartImg.getIconWidth(), StartImg.getIconHeight());
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        switchbackgroundJLabel.add(playButton);
        playButton.addMouseListener(startGamelistener);
    }
    private void playSound(String soundFilePath) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(getClass().getResourceAsStream(soundFilePath));
            soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);

            soundClip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        event.getLine().close();
                    }
                }
            });

            soundClip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private static Object getRandomObject() {
        Object[] objects =  {   new Pomelo(), 
                                new Banana(), 
                                new Frog(),
                                new Frog(),
                                new Frog(),
                                new Tomato(),
                                new Peach()
                            };
        return objects[rd.nextInt(objects.length)];
    }
    private void startFalling(int amount) {
        isStart = true;
        int delay = 500;
        for(int i = 0; i < amount; i++) {
            Object obj = getRandomObject();
            fallingObject(obj, delay);
            delay += 300;
        }
    }
    private void fallingObject(Object obj, int delay){
        JLabel label = checkJLabel(obj);
        int velo = checkVelocity(obj);

        if (label.getMouseListeners().length == 0) {
            if (obj instanceof Frog)
                label.addMouseListener(wrongLabelListener);
            else
                label.addMouseListener(labelMouseListener);
        }
        final Icon icon = label.getIcon();
        int minX = 340;
        int maxX = FRAME_WIDTH - minX - 110;
        scheduler.schedule(() -> {
            int positionX = rd.nextInt(maxX - minX + 1) + minX;
            final int[] positionY = {0};
            final double[] velocity = {isStart ? velo : FRAME_HEIGHT};
            final double[] acceleration = {0.5};
            label.setVisible(true);
            Timer timer = new Timer(17, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    backgroundlabel.add(label);
                    positionY[0] += velocity[0] += (acceleration[0]);
                    label.setLocation(positionX, positionY[0]);
                    System.out.println("still");
                    if (positionY[0] >= FRAME_HEIGHT) {
                        label.setVisible(false);
                        label.setIcon(icon);
                        backgroundlabel.remove(label);
                        // backgroundlabel.repaint();
                        ((Timer) e.getSource()).stop();
                        if (isStart) {
                            fallingObject(getRandomObject(), delay);
                        }
                        else System.out.println("end");
                    }
                }
            });
            timer.start();
        }, delay, TimeUnit.MILLISECONDS);
    }
    private JLabel checkJLabel(Object obj) {
        if (obj instanceof Pomelo) 
            return Pomelo.getJLabel();
        else if (obj instanceof Banana) 
            return Banana.getJLabel();
        else if (obj instanceof Peach)
            return Peach.getJLabel();
        else if (obj instanceof Tomato)
            return Tomato.getJLabel();
        else 
            return Frog.getJLabel();
    }
    private int checkVelocity(Object obj) {
        if (obj instanceof Pomelo) 
            return Pomelo.getVelocity();
        else if (obj instanceof Banana) 
            return Banana.getVelocity();
        else if (obj instanceof Peach)
            return Peach.getVelocity();
        else if (obj instanceof Tomato)
            return Tomato.getVelocity();
        else 
            return Frog.getVelocity();
    }
}
