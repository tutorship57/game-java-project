import java.awt.Container;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import oom.gampanel;

public class test {
    public static void main(String[] args) {
        new Gametest();
    }
}
// import javax.swing.*;

// import java.awt.Color;
// import java.awt.event.MouseAdapter;
// import java.awt.event.MouseEvent;

// public class Gametest {
    
//         Gametest(){
//             JFrame frame = new JFrame("Mouse Interaction Example");
//             frame.setSize(500, 500);
//             frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//             JPanel panel = new JPanel(null);
//             panel.setBounds(0, 0, 20, 20);
//             panel.setBackground(Color.BLUE);
//             frame.add(panel);

//             // Create a custom component that responds to mouse events
//             CustomComponent customComponent = new CustomComponent(panel);
//             frame.add(customComponent);

//             frame.setVisible(true);
//         }
    
// }

// class CustomComponent extends JComponent {
//     public CustomComponent( JPanel p) {
//         JLabel object1 = new JLabel(new ImageIcon("src/icon.png"));
//         object1.setBounds(20, 20, 50, 50);
//         p.add(object1);
//         addMouseListener(new MouseAdapter() {
//             @Override
//             public void mousePressed(MouseEvent e) {
//                 // Handle mouse click event
//                 System.out.println("Mouse Clicked at (" + e.getX() + ", " + e.getY() + ")");
//             }
//         });
//     }
// }
