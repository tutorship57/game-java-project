package oom;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

public class gampanel extends JPanel {
    private final int width = 1280;
    private final int height= 720;

    public gampanel(){

        this.setPreferredSize(new Dimension(width,height));
        this.setBackground(Color.black);
        this.setLayout(null);
    }
}
