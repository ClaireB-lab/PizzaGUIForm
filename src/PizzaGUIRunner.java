import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/// Claire Battelle
/// October 5, 2026
/// Runs the pizza form and lets the user well use it
/// when the X button is clicked, the form closes and program stops running
public class PizzaGUIRunner {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame frame = new PizzaGUIFrame();
            frame.setTitle("Pizza Order Form");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
