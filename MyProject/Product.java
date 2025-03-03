package MyProject;

public abstract class Product implements Taxable{
    private String id;
    private String name;
    private double price;
    private int quantity;
    private String category;

    public static final double taxRate = 5.0 / 100;
    public static final int exchangeRateEuro = 250;
    public static final int exchangeRateUsd = 235;

    private static int totalProducts = 0;

    public static int getTotalProducts(){
        return totalProducts;
    }

    abstract void specificInfo();

    Product(String id, String name, double price, int quantity, String category) throws InvalidValueException{
        if(price <= 0)
            throw new InvalidValueException("Price must be positive number!");
        
        if(quantity <= 0)
            throw new InvalidValueException("Price must be positive number!");
        
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
        
        totalProducts++;
    }
    
    public void displayInfo(){
        System.out.println("Product Name: " + this.name);
        System.out.printf("Product Price: %.2f DZD\n", this.price);
    }

    public void displayInfo(String currency){
        if(currency != null)
            if(currency.toUpperCase().equals("EUR"))
                System.out.printf("Product Price: %.2f EUR\n", this.price / exchangeRateEuro);
            else if(currency.toUpperCase().equals("USD"))
                System.out.printf("Product Price: %.2f USD\n", this.price / exchangeRateUsd);
    }

    public void displayInfo(boolean detailed){
        if(detailed){
            System.out.println("Detailed Information:");
            System.out.println("Product ID: " + this.id);
            System.out.println("Product Name: " + this.name);
            System.out.printf("Product Price: %.2f DZD\n", this.price);
            System.out.println("Product Quantity: " + this.quantity);
            System.out.println("Product Category: " + this.category);
        }
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
        if(id != null && !id.trim().isEmpty())
            this.id = id;
        else
            System.out.println("Id can't be an empty string!");
    }

    public void setName(String name){
        if(name != null && !name.trim().isEmpty())
            this.name = name;
        else
            System.out.println("Name can't be an empty string!");
    }

    public void setPrice(double price){
        if(price >= 0)
            this.price = price;
        else
            System.out.println("Price can't be less than or equal zero!");
    }

    public void setQuantity(int quantity){
        if(quantity >= 0)
            this.quantity = quantity;
        else
            System.out.println("Quantity can't be less than zero!");
    }

    public void setCategory(String category){
        if(category != null && !category.trim().isEmpty())
            this.category = category;
        else
            System.out.println("Category can't be an empty string!");
    }

    @Override
    public double calculateTax(){
        return taxRate * this.price;
    }
}
