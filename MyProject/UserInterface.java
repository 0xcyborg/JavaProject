package MyProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UserInterface {
    private JFrame frame;
    private JTextField idField, nameField, priceField, quantityField, categoryField;
    private JTextArea productListArea;
    private Connection connection;

    UserInterface() {
        connectToDatabase();

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

        this.frame.add(inputPanel, BorderLayout.NORTH);

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
                addProductToDatabase();
            }
        });

        displayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayProductsFromDatabase();
            }
        });

        this.frame.setVisible(true);
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/superette";
            String user = "root";
            String password = "test";

            this.connection = DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProductToDatabase() {
        String id = this.idField.getText().trim();
        String name = this.nameField.getText().trim();
        String priceText = this.priceField.getText().trim();
        String quantityText = this.quantityField.getText().trim();
        String category = this.categoryField.getText().trim();

        if (id.isEmpty() || name.isEmpty() || priceText.isEmpty() || quantityText.isEmpty() || category.isEmpty()) {
            JOptionPane.showMessageDialog(this.frame, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            float realPrice = Float.parseFloat(priceText);
            int realQuantity = Integer.parseInt(quantityText);

            new Product(id, name, realPrice, realQuantity, category);

            String sql = "INSERT INTO products (id, name, price, quantity, category) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setFloat(3, realPrice);
            stmt.setInt(4, realQuantity);
            stmt.setString(5, category);

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) 
                JOptionPane.showMessageDialog(this.frame, "Product added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);

            idField.setText("");
            nameField.setText("");
            priceField.setText("");
            quantityField.setText("");
            categoryField.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this.frame, "Invalid number format for price or quantity", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (InvalidValueException ex){
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayProductsFromDatabase() {
        productListArea.setText("");

        try {
            String sql = "SELECT * FROM products";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String productInfo = String.format(
                        "%s | %s | %.2f | %d | %s\n",
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                );
                
                productListArea.append(productInfo);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
