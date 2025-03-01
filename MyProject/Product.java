package MyProject;

public class Product{
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String category;

    private static int totalProducts = 0;

    Product(String id, String name, double price, int quantity, String category){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        
        totalProducts++;
    }

    private boolean isNotEmptyString(String text){
        return !text.trim().isEmpty();
    }

    public static int getTotalProducts(){
        return totalProducts;
    }

    public void displayInfo(){
        System.out.println("Product ID: " + this.id);
        System.out.println("Product Name: " + this.name);
        System.out.println("Product Price: " + this.price + " DA");
        System.out.println("Product Quantity: " + this.quantity);
        System.out.println("Product Category: " + this.category);
    }

    public boolean isAvailable(){
        return this.quantity > 0;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

    public double getPrice(){
        return this.price;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public String getCategory(){
        return this.category;
    }

    public void setId(String id){
        if(id instanceof String && isNotEmptyString(id)){
            this.id = id;
        }
        else{
            System.out.println("Id can't be an empty string!");
        }
    }

    public void setName(String name){
        if(name instanceof String && isNotEmptyString(name)){
            this.name = name;
        }
        else{
            System.out.println("Name can't be an empty string!");
        }
    }

    public void setPrice(double price){
        if(price >= 0){
            this.price = price;
        }
        else{
            System.out.println("Price can't be less than or equal zero!");
        }
    }

    public void setQuantity(int quantity){
        if(quantity >= 0){
            this.quantity = quantity;
        }
        else{
            System.out.println("Quantity can't be less than zero!");
        }
    }

    public void setCategory(String category){
        if(category instanceof String && isNotEmptyString(category)){
            this.category = category;
        }
        else{
            System.out.println("Category can't be an empty string!");
        }
    }
}
