package softuni.jsonprocessing.model.dto.export.query2;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserExportDto {


    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    @SerializedName("soldProducts")
    private List<ProductExportDtoQuery2> products;

    public UserExportDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductExportDtoQuery2> getProducts() {
        return products;
    }

    public void setProducts(List<ProductExportDtoQuery2> products) {
        this.products = products;
    }
}
