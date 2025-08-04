package GUI;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;

public class MainApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Smart Devices GUI");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            JTabbedPane tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Smart Car", new GUISmartCar());
            tabbedPane.addTab("Smart Watch", new GUISmartWatch());
            tabbedPane.addTab("Mobile App", new GUIMobileApp());

            frame.setContentPane(tabbedPane);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

