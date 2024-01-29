package oom;
import javax.swing.JFrame;

public class main {
    public static void main(String[] args) {
        JFrame window = new JFrame("Teteris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        gampanel gp = new gampanel();
        window.add(gp);
        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        
    }
    
}