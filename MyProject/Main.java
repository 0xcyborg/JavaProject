package MyProject;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Product cap = new Product("P1", "Blue Cap", 1200.0, 9, "Fashion");
        cap.displayInfo();
        printSeparator();

        Product shoes = new Product("P2", "Nike Shoes", 12000.0, 11, "Fashion");
        shoes.displayInfo();
        printSeparator();

        System.out.printf("Is %s Available: %b%n", shoes.getName(), shoes.isAvailable());
        printSeparator();

        System.out.println("Total Products: " + Product.getTotalProducts());
        printSeparator();

        FoodProduct ricamar = new FoodProduct("FP1", "Thon Ricamar", 110.0, 4, "Seafood", "2025-07-31");
        ricamar.displayInfo();
        printSeparator();

        ElectronicProduct ps4 = new ElectronicProduct("EP1", "PS4 Slim", 29000.0, 2, "Gaming", 2);
        ps4.displayInfo();
        printSeparator();

        ps4.displayInfo("USD");
        printSeparator();
        
        ps4.displayInfo(true);
        printSeparator();

        List<Product> products = new ArrayList<>();
        products.add(new FoodProduct("FP2", "Chocolate Maxon", 60.0, 98, "Sugars", "2027-01-31"));
        products.add(new ElectronicProduct("EP2", "PS2 Fat", 6500.0, 1, "Gaming", 6));

        for(Product product: products){
            product.displayInfo();
            printSeparator();
        }
    }

    private static void printSeparator() {
        System.out.println("<-------------------------------------------------->");
    }
}
