package MyProject;

class Service implements Taxable {
    private String type;
    private double baseCost;
    private double taxRate;

    public Service(String type, double baseCost, double taxRate) {
        this.type = type;
        this.baseCost = baseCost;
        this.taxRate = taxRate;
    }

    String getType(){
        return this.type;
    }

    @Override
    public double calculateTax() {
        return baseCost * (taxRate / 100);
    }

    public double getTotalCost() {
        return baseCost + calculateTax();
    }
}
