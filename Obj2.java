import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Obj2 {
    public static JLabel getJLabel() {
        int width = 60;
        int height = 60;
        ImageIcon img = new ImageIcon("src/Obj2.png");
        JLabel label = new JLabel(img);
        label.setBounds(50,50, width, height);
        return label;
    }
}