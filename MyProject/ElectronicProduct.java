package MyProject;

public class ElectronicProduct extends Product{
    private int warrantyPeriod;
    
    ElectronicProduct(String id, String name, double price, int quantity, String category, int warrantyPeriod){
        super(id, name, price, quantity, category);
        this.warrantyPeriod = warrantyPeriod;
    }

    @Override
    public void displayInfo(){
        super.displayInfo();
        System.out.printf("Warranty Period: %d Year\n", this.warrantyPeriod);
    }
}