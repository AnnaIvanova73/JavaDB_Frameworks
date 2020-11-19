package softuni.jsonprocessing.domain.dto.export.query6;

import com.google.gson.annotations.Expose;

public class CustomerExportDtoQuery6 {

    @Expose
    private String name;
    @Expose
    private double discount;
    @Expose
    private double price;
    @Expose
    private double priceWithDiscount;

    public CustomerExportDtoQuery6() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPriceWithDiscount() {
        return priceWithDiscount;
    }

    public void setPriceWithDiscount(double priceWithDiscount) {
        this.priceWithDiscount = priceWithDiscount;
    }
}
