import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;

/// Claire Battelle
/// October 5, 2026
/// This creates the pizza order form
/// creates the topping options
public class PizzaGUIFrame extends JFrame {
    private JRadioButton thinCrust, regularCrust, deepDishCrust;
    private ButtonGroup crustGroup;
    private JComboBox<String> sizeComboBox;
    private JCheckBox[] toppingBoxes;
    private final String[] toppingNames = {
            "Pepperoni", "Mushrooms", "Sausage", "Olives", "Chicken", "Ranch"
    };

    private JTextArea receiptArea;
    private JButton orderButton, clearButton, quitButton;
    private final double[] sizePrices = {8.00, 12.00, 16.00, 20.00};
    public PizzaGUIFrame(){
        setLayout(new BorderLayout(10, 10));
        buildGUI();
    }

    /// creates top panel
    /// creates crust order section (radio buttons)
    /// creates the size order selection (drop down menu)
    /// creates the topping order selection (multi select check boxes)
    /// creates and formats the recipet area that calculates the final cost and tax
    /// makes the clear button, order button, and the quit button
    private void buildGUI(){
        JPanel topInputPanel = new JPanel(new GridLayout(1, 3, 10, 10));

        JPanel crustPanel = new JPanel(new GridLayout(3, 1));
        crustPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Type of Crust", TitledBorder.LEFT, TitledBorder.TOP));
        thinCrust = new JRadioButton("Thin");
        regularCrust = new JRadioButton("Regular");
        deepDishCrust = new JRadioButton("Deep-dish");

        crustGroup = new ButtonGroup();
        crustGroup.add(thinCrust);
        crustGroup.add(regularCrust);
        crustGroup.add(deepDishCrust);
        crustPanel.add(thinCrust);
        crustPanel.add(regularCrust);
        crustPanel.add(deepDishCrust);

        JPanel sizePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        sizePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Size", TitledBorder.LEFT, TitledBorder.TOP));
        String[] sizes = {"Small ($8.00)", "Medium ($12.00)", "Large ($16.00)", "Super ($20.00"};
        sizeComboBox = new JComboBox<>(sizes);
        sizePanel.add(sizeComboBox);
        JPanel toppingsPanel = new JPanel(new GridLayout(3, 2));
        toppingsPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Toppings ($1.00 ea)", TitledBorder.LEFT, TitledBorder.TOP));

        toppingBoxes = new JCheckBox[toppingNames.length];
        for (int i = 0; i < toppingNames.length; i++) {
            toppingBoxes[i] = new JCheckBox(toppingNames[i]);
            toppingsPanel.add(toppingBoxes[i]);
        }

        topInputPanel.add(crustPanel);
        topInputPanel.add(sizePanel);
        topInputPanel.add(toppingsPanel);

        JPanel receiptPanel = new JPanel(new BorderLayout());
        receiptPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Order Receipt", TitledBorder.LEFT, TitledBorder.TOP));

        receiptArea = new JTextArea(12, 45);
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        receiptPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        orderButton.addActionListener((ActionEvent e) -> processOrder());
        clearButton.addActionListener((ActionEvent e) -> clearForm());
        quitButton.addActionListener((ActionEvent e) -> quitApplication());

        add(topInputPanel, BorderLayout.NORTH);
        add(receiptPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    /// when the order button is selected, it creates the receipt
    private void processOrder() {
        String crust = "";
        if (thinCrust.isSelected()) crust = "Thin";
        else if (regularCrust.isSelected()) crust = "Regular";
        else if (deepDishCrust.isSelected()) crust = "Deep-dish";

        if (crust.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a crust type!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int sizeIndex = sizeComboBox.getSelectedIndex();
        String sizeName = (String) sizeComboBox.getSelectedItem();
        double basePrice = sizePrices[sizeIndex];

        double toppingsCost = 0.0;
        StringBuilder toppingDetails = new StringBuilder();
        int selectedToppingsCount = 0;

        for (JCheckBox cb : toppingBoxes) {
            if (cb.isSelected()) {
                selectedToppingsCount++;
                toppingsCost += 1.00;
                toppingDetails.append(String.format("%-30s $%6.2f\n", cb.getText(), 1.00));
            }
        }

        if (selectedToppingsCount == 0) {
            JOptionPane.showMessageDialog(this, "Please select at least one topping!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        double subTotal = basePrice + toppingsCost;
        double tax = subTotal * 0.07;
        double total = subTotal + tax;

        StringBuilder receipt = new StringBuilder();
        receipt.append("=========================================\n");
        receipt.append(String.format("%-30s $%6.2f\n", crust + " & " + sizeName.split(" ")[0], basePrice));
        receipt.append(toppingDetails.toString());
        receipt.append("\n");
        receipt.append(String.format("%-30s $%6.2f\n", "Sub-total:", subTotal));
        receipt.append(String.format("%-30s $%6.2f\n", "Tax (7%):", tax));
        receipt.append("-----------------------------------------\n");
        receipt.append(String.format("%-30s $%6.2f\n", "Total:", total));
        receipt.append("=========================================\n");

        receiptArea.setText(receipt.toString());
    }

    /// when the clear button is selected, all of the options are set back to their default, clears the recipet section
    private void clearForm() {
        crustGroup.clearSelection();
        sizeComboBox.setSelectedIndex(0);
        for (JCheckBox cb : toppingBoxes) {
            cb.setSelected(false);
        }
        receiptArea.setText("");
    }

    /// when the quit button is hit, the application will close
    private void quitApplication() {
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to quit?",
                "Confirm Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        if (confirm == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}


