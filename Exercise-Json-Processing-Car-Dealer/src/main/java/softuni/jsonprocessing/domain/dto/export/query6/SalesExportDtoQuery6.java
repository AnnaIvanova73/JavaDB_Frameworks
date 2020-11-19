package softuni.jsonprocessing.domain.dto.export.query6;

import com.google.gson.annotations.Expose;

public class SalesExportDtoQuery6 {


    @Expose
    private CarExportDtoQuery6 car;

    @Expose
    private CustomerExportDtoQuery6 customer;


    public SalesExportDtoQuery6() {
    }


    public CustomerExportDtoQuery6 getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerExportDtoQuery6 customer) {
        this.customer = customer;
    }

    public CarExportDtoQuery6 getCar() {
        return car;
    }

    public void setCar(CarExportDtoQuery6 car) {
        this.car = car;
    }
}
