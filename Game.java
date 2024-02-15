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
import java.io.File;
import java.io.IOException;


public class Game {
    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 755;
    private static ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private static Random rd = new Random();
    private ImageIcon gameIcon;
    private Clip soundClip;
    private JFrame frame;
    private Container container;
    private ImageIcon switchbackgroundImage;
    private ImageIcon StartImg;
    private ImageIcon backgroundImage;
    private JLabel switchbackgroundJLabel;
    private JLabel scoreBox;
    private JLabel backgroundlabel;
    private JLabel timeBox;
    private JLabel countStart ;
    private JButton playButton;
    private Timer timeCounting ;
    private Timer gameStartCounting ;
    private int MaxScore;
    private int timeScedule =60;
    private int score = 0;
    private boolean isStart = false;
    private String [] gameStartCount = {"  3","  2","  1"," GO!"};
    private int ind = 0;

    MouseAdapter wrongLabelListener = new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
            JLabel pressedLabel = (JLabel)e.getSource();
            gameUpdate(pressedLabel);
            pressedLabel.setLocation(pressedLabel.getX(), FRAME_HEIGHT);
            playSound("src/sound/quack.wav");
        }
    };
    MouseAdapter  startGamelistener = new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
            timeCounting = new Timer(1000,new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                decreaseTimeCount();
                
            }
        });
            gameStartCounting =  new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e ){
                letsStart();
            } 
        });
            if(playButton == (JButton)e.getSource()){
                container.remove(switchbackgroundJLabel);
                Components();
                scoreBox();
                textStartCount();
                gameStartCounting.start();
                container.repaint();
            }
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
        frame = new JFrame("Fruit NOT Frog.");

        gameIcon = new ImageIcon("src/img/icon.png");
        frame.setIconImage(gameIcon.getImage());
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
    private void decreaseTimeCount() {
        timeScedule -= 1;
        timeBox.setText("" + timeScedule);
        System.out.println(timeScedule);
        if(timeScedule == 0) {
            timeCounting.stop();
            container.remove(backgroundlabel);
            Components2(); 
            MaxScore = score;
            showScoreMetod();
            container.repaint();
            score = 0;
            isStart = false;
            timeScedule = 60;
        }
        if(timeScedule < 6){
            timeBox.setForeground(new Color(255, 0, 0));
        }
    }
    private void letsStart(){
        ind++;
        if(ind == gameStartCount.length) {
            gameStartCounting.stop();
            backgroundlabel.remove(countStart);
            container.repaint();
            
            startFalling(7);
            timeCounting.start();
            ind = 0;
        }else {
            countStart.setText(gameStartCount[ind]);
        }
    }
    private void textStartCount() {
        countStart = new JLabel(gameStartCount[ind]);
        countStart.setBounds(FRAME_WIDTH/2-(30*gameStartCount[3].length()),(FRAME_HEIGHT/2)-200,300,300);
        countStart.setForeground(new Color(0, 0, 0));
        countStart.setFont(new Font("Comic Sans MS", Font.BOLD, 100));
        backgroundlabel.add(countStart, BorderLayout.CENTER);
    }
    private void showScoreMetod() {
        String socoreLabel = "Score";
        JLabel maxScoreLabel = new JLabel(socoreLabel);
        String maxCount = "" + MaxScore;
        JLabel showScore = new JLabel(maxCount);
        Font fontMax = new Font("Comic Sans MS", Font.BOLD, 60);
        maxScoreLabel.setBounds((FRAME_WIDTH / 2) - ((socoreLabel.length() * 30 ) / 2) - 10, FRAME_HEIGHT / 10 ,500, 200);
        maxScoreLabel.setFont(fontMax);
        showScore.setBounds(((FRAME_WIDTH / 2) - (maxCount.length() * 30 ) / 2) - 10, FRAME_HEIGHT / 4 ,500, 150);
        showScore.setFont(fontMax);
        switchbackgroundJLabel.add(showScore, BorderLayout.CENTER);
        switchbackgroundJLabel.add(maxScoreLabel, BorderLayout.CENTER);
    }
    private void scoreBox() {
        Color textColor = new Color(255, 255, 255); // ตั้งค่าสี (RGB)
        Font font = new Font("Comic Sans MS", Font.BOLD, 25);
        scoreBox = new JLabel();
        scoreBox.setBounds(60, 20, 150, 100);
        backgroundlabel.add(scoreBox);
        scoreBox.setFont(font);
        scoreBox.setForeground(textColor);
        scoreBox.setText("Score: " + score);

        Color textColor2 = new Color(0, 0, 0); // ตั้งค่าสี (RGB)
        Font font2 = new Font("Comic Sans MS", Font.BOLD, 60);
        timeBox = new JLabel();
        timeBox.setBounds((FRAME_WIDTH / 2) - 30, FRAME_HEIGHT / 20, 150, 100);
        backgroundlabel.add(timeBox, BorderLayout.CENTER);
        timeBox.setFont(font2);
        timeBox.setForeground(textColor2);
        timeBox.setText("" + timeScedule);
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
                    // System.out.println("still");
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
