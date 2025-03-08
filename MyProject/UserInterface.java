package MyProject;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class UserInterface {
    private JFrame frame;
    private JTextField idField, nameField, priceField, quantityField, categoryField;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private Connection connection;

    UserInterface() {
        this.connectToDatabase();

        this.frame = new JFrame("Mini Project");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(800, 800);
        this.frame.setResizable(false);
        this.frame.setLayout(new BorderLayout(10, 10));

        Font mainFont = new Font("Arial", Font.PLAIN, 16);

        JPanel inputPanel = new JPanel(new GridLayout(0, 2, 5, 5));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Product Details"));

        inputPanel.add(new JLabel("ID:"));
        this.idField = new JTextField();
        inputPanel.add(this.idField);

        inputPanel.add(new JLabel("Name:"));
        this.nameField = new JTextField();
        inputPanel.add(this.nameField);

        inputPanel.add(new JLabel("Price:"));
        this.priceField = new JTextField();
        inputPanel.add(this.priceField);

        inputPanel.add(new JLabel("Quantity:"));
        this.quantityField = new JTextField();
        inputPanel.add(this.quantityField);

        inputPanel.add(new JLabel("Category:"));
        this.categoryField = new JTextField();
        inputPanel.add(this.categoryField);

        this.frame.add(inputPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton addButton = new JButton("Add Product");
        JButton deleteButton = new JButton("Delete Product");
        JButton displayAllButton = new JButton("Show All Products");
        JButton displayOutButton = new JButton("Out-Of-Stock Products");
        JButton sellProduct = new JButton("Sell");
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(displayAllButton);
        buttonPanel.add(displayOutButton);
        buttonPanel.add(sellProduct);

        this.frame.add(buttonPanel, BorderLayout.CENTER);

        String[] tableCols = {"ID", "Name", "Price", "Quantity", "Category"};
        this.tableModel = new DefaultTableModel(tableCols, 0);
        
        this.productTable = new JTable(this.tableModel);
        this.productTable.setFont(mainFont);
        this.productTable.setRowHeight(25);
        
        this.frame.add(new JScrollPane(this.productTable), BorderLayout.SOUTH);

        addButton.addActionListener(e -> this.addProductToDatabase());
        deleteButton.addActionListener(e -> this.deleteProductFromDatabase());
        displayAllButton.addActionListener(e -> this.displayAllProductsFromDatabase());
        displayOutButton.addActionListener(e -> this.displayOutProductsFromDatabase());
        sellProduct.addActionListener(e -> this.sellProduct());

        this.frame.setVisible(true);
    }

    private void connectToDatabase() {
        try {
            String url = "jdbc:mysql://localhost:3306/superette";
            String user = "root";
            String password = "";
            
            this.connection = DriverManager.getConnection(url, user, password);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addProductToDatabase() {
        try {
            String id = this.idField.getText().trim();
            String name = this.nameField.getText().trim();
            String price = this.priceField.getText().trim();
            String quantity = this.quantityField.getText().trim();
            String category = this.categoryField.getText().trim();

            if(id.isEmpty() || name.isEmpty() || price.isEmpty() || quantity.isEmpty() || category.isEmpty()){
                JOptionPane.showMessageDialog(this.frame, "Please fill in all fields", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Float realPrice = Float.parseFloat(price);
            Integer realQuantity = Integer.parseInt(quantity);

            new Product(id, name, realPrice, realQuantity, category);

            String sql = "INSERT INTO products (id, name, price, quantity, category) VALUES (?, ?, ?, ?, ?)";
            
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, id);
            stmt.setString(2, name);
            stmt.setFloat(3, realPrice);
            stmt.setInt(4, realQuantity);
            stmt.setString(5, category);

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this.frame, "Product added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                idField.setText("");
                nameField.setText("");
                priceField.setText("");
                quantityField.setText("");
                categoryField.setText("");

                this.displayAllProductsFromDatabase();
            }
        } catch(InvalidValueException ex){
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteProductFromDatabase() {
        int selectedRow = this.productTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this.frame, "Please select a product to delete", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String productId = this.tableModel.getValueAt(selectedRow, 0).toString();

        try {
            String sql = "DELETE FROM products WHERE id = ?";

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, productId);

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(this.frame, "Product deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.displayAllProductsFromDatabase();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayAllProductsFromDatabase() {
        this.tableModel.setRowCount(0);
        
        try {
            String sql = "SELECT * FROM products";
            
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                this.tableModel.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void displayOutProductsFromDatabase() {
        this.tableModel.setRowCount(0);
        
        try {
            String sql = "SELECT * FROM products where quantity = 0";

            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                this.tableModel.addRow(new Object[]{
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getFloat("price"),
                        rs.getInt("quantity"),
                        rs.getString("category")
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void sellProduct(){
        int selectedRow = this.productTable.getSelectedRow();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this.frame, "Please select a product to sell", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String productId = this.tableModel.getValueAt(selectedRow, 0).toString();
        String productQuantity = this.tableModel.getValueAt(selectedRow, 3).toString();

        if(Integer.parseInt(productQuantity) <= 0){
            JOptionPane.showMessageDialog(this.frame, "This product is out of stock", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try{
            String sql = "UPDATE products SET quantity = quantity - 1 where id = ?";
            
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            stmt.setString(1, productId);
        
            if(stmt.executeUpdate() > 0){
                JOptionPane.showMessageDialog(this.frame, "Congrats on making a sale", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.displayAllProductsFromDatabase();
            }
        } catch(SQLException ex){
            JOptionPane.showMessageDialog(this.frame, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
