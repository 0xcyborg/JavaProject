package MyProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserInterface {
    private JFrame frame;
    private JTextField idField, nameField, priceField, quantityField, categoryField;
    private JTextArea productListArea;
    private List<Product> products;

    UserInterface() {
        this.products = new ArrayList<>();

        this.frame = new JFrame("Manager UI");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(600, 600);
        this.frame.setLayout(new BorderLayout(10, 10));

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(0, 2, 5, 5));

        inputPanel.add(new JLabel("ID:"));
        this.idField = new JTextField(20);
        inputPanel.add(this.idField);

        inputPanel.add(new JLabel("Name:"));
        this.nameField = new JTextField(50);
        inputPanel.add(this.nameField);

        inputPanel.add(new JLabel("Price:"));
        this.priceField = new JTextField(10);
        inputPanel.add(this.priceField);

        inputPanel.add(new JLabel("Quantity:"));
        this.quantityField = new JTextField(10);
        inputPanel.add(this.quantityField);

        inputPanel.add(new JLabel("Category:"));
        this.categoryField = new JTextField(50);
        inputPanel.add(this.categoryField);

        frame.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addButton = new JButton("Add Product");
        buttonPanel.add(addButton);

        JButton displayButton = new JButton("Display Products");
        buttonPanel.add(displayButton);

        this.frame.add(buttonPanel, BorderLayout.CENTER);

        this.productListArea = new JTextArea(10, 30);
        this.productListArea.setEditable(false);
        this.productListArea.setFont(new Font("Arial", Font.PLAIN, 20));
        this.frame.add(new JScrollPane(productListArea), BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = idField.getText().trim();
                String name = nameField.getText().trim();
                String priceText = priceField.getText().trim();
                String quantityText = quantityField.getText().trim();
                String category = categoryField.getText().trim();

                if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || category.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    float realPrice = Float.parseFloat(priceText);
                    int realQuantity = Integer.parseInt(quantityText);

                    products.add(new Product(id, name, realPrice, realQuantity, category));

                    idField.setText("");
                    nameField.setText("");
                    priceField.setText("");
                    quantityField.setText("");
                    categoryField.setText("");

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid number format for price or quantity!", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (InvalidValueException ex) {
                    JOptionPane.showMessageDialog(frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                productListArea.setText("");
                for (Product product : products) {
                    productListArea.append(String.format("%s | %s | %.2f | %d | %s\n", product.getId(), product.getName(), product.getPrice(), product.getQuantity(), product.getCategory()));
                }
            }
        });

        frame.setVisible(true);
    }
}
