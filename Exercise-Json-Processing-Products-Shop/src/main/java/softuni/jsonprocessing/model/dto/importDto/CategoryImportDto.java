package softuni.jsonprocessing.model.dto.importDto;

import com.google.gson.annotations.Expose;
import org.hibernate.validator.constraints.Length;
import softuni.jsonprocessing.model.entities.Product;

import java.util.Set;

public class CategoryImportDto {

    @Expose
    private String name;
    @Expose
    private Set<Product> products;
    public CategoryImportDto() {
    }


    @Length(min=3, max = 15)
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }
}
