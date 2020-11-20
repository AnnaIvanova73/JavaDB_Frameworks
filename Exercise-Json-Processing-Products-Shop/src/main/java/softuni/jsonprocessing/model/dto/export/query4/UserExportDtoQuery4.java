package softuni.jsonprocessing.model.dto.export.query4;

import com.google.gson.annotations.Expose;

import java.util.List;

public class UserExportDtoQuery4 {
    //    "firstName":"Carl",
//
//            "lastName":"Daniels",
//
//            "age":59,
//
//            "soldProducts":
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private int age;
    @Expose
    private int soldProducts;
    @Expose
    private List<ProductsExportDtoQuery4> products;

    public UserExportDtoQuery4() {
    }

    public UserExportDtoQuery4(String firstName,
                               String lastName,
                               int age, int soldProducts,
                               List<ProductsExportDtoQuery4> productsExportDtoQuery4) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.soldProducts = soldProducts;
        this.products = productsExportDtoQuery4;
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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(int soldProducts) {
        this.soldProducts = soldProducts;
    }

    public List<ProductsExportDtoQuery4> getProductsExportDtoQuery4() {
        return products;
    }

    public void setProductsExportDtoQuery4(List<ProductsExportDtoQuery4> productsExportDtoQuery4) {
        this.products = productsExportDtoQuery4;
    }
}
