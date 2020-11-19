package softuni.jsonprocessing.domain.dto.export;

import com.google.gson.annotations.Expose;
import softuni.jsonprocessing.domain.entities.Customer;

public class SalesExportDto {
    @Expose
    private int discount;
    @Expose
    private Customer customer;
    @Expose
    private CarExportDto car;

    public SalesExportDto() {
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CarExportDto getCar() {
        return car;
    }

    public void setCar(CarExportDto car) {
        this.car = car;
    }
}
