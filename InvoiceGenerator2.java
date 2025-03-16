import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class InvoiceGenerator2 extends JFrame {
    private JTextField itemNameField, quantityField, priceField;
    private JTextArea invoiceTextArea;
    private double totalAmount;
    private JLabel totalLabel;

    public InvoiceGenerator2() {
        setTitle("Invoice Generator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create components
        JLabel itemNameLabel = new JLabel("Item Name:");
        JLabel quantityLabel = new JLabel("Quantity:");
        JLabel priceLabel = new JLabel("Price:");

        itemNameField = new JTextField(20);
        quantityField = new JTextField(10);
        priceField = new JTextField(10);

        JButton addItemButton = new JButton("Add Item");
        JButton generateInvoiceButton = new JButton("Generate Invoice");

        invoiceTextArea = new JTextArea();
        invoiceTextArea.setEditable(false);

        totalLabel = new JLabel("Grand Total: Rs.0.00");

        // Set layout
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(itemNameLabel);
        inputPanel.add(itemNameField);
        inputPanel.add(quantityLabel);
        inputPanel.add(quantityField);
        inputPanel.add(priceLabel);
        inputPanel.add(priceField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addItemButton);
        buttonPanel.add(generateInvoiceButton);

        JPanel totalPanel = new JPanel(new GridLayout(3,3));
          totalPanel.add(new Label(""));
         totalPanel.add(new Label(""));
         totalPanel.add(new Label(""));
         totalPanel.add(new Label(" "));
         totalPanel.add(totalLabel);
         totalPanel.add(new Label(" "));
         totalPanel.add(new Label(""));
         totalPanel.add(new Label(""));
         totalPanel.add(new Label(""));
                  
         

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(buttonPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(invoiceTextArea), BorderLayout.CENTER);
        centerPanel.add(totalPanel, BorderLayout.SOUTH);

        // Add components to the frame
        add(inputPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // Add action listeners
        addItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItemToInvoice();
            }
        });

        generateInvoiceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateInvoice();
            }
        });
    }

    private void addItemToInvoice() {
        String itemName = itemNameField.getText();
        String quantityStr = quantityField.getText();
        String priceStr = priceField.getText();

        if (!itemName.isEmpty() && !quantityStr.isEmpty() && !priceStr.isEmpty()) {
            try {
                int quantity = Integer.parseInt(quantityStr);
                double price = Double.parseDouble(priceStr);

                double itemTotal = quantity * price;
                String itemDetails = itemName + "\t\t" + quantity + "\t\tRs." + price + "\t\tRs." + itemTotal + "\n";
                invoiceTextArea.append(itemDetails);

                // Update total amount
                totalAmount += itemTotal;

                // Update total label
                totalLabel.setText("Grand Total: Rs." + String.format("%.2f", totalAmount));

                // Clear input fields
                itemNameField.setText("");
                quantityField.setText("");
                priceField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numeric values for quantity and price.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please fill in all the fields.");
        }
    }

    private void generateInvoice() {
        String invoiceContent = invoiceTextArea.getText();
        if (!invoiceContent.isEmpty()) {
            String fileName = "Invoice.txt";
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
                writer.write("Invoice:\n");
                writer.write("Item Name\tQuantity\tPrice\t\tTotal\n");
                writer.write(invoiceContent);
                writer.write("\nTotal Amount: Rs." + String.format("%.2f", totalAmount));
                JOptionPane.showMessageDialog(this, "Invoice generated successfully. Check " + fileName);
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error generating invoice.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please add items to the invoice before generating.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new InvoiceGenerator2().setVisible(true);
            }
        });
    }
}
