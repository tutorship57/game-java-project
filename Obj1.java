import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Obj1 {
    public static int getVelocity() {
        return 5;
    }
    public static JLabel getJLabel() {
        int width = 50;
        int height = 50;
        ImageIcon img = new ImageIcon("src/Obj1.png");
        JLabel label = new JLabel(img);
        label.setBounds(50,50, width, height);
        return label;
    }  
}