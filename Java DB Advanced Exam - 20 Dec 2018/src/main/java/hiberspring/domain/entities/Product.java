package hiberspring.domain.entities;


import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product extends BaseEntity {
    private String name;
    private int clients;
    private Branch branch;


    public Product() {
    }

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(nullable = false)
    public int getClients() {
        return clients;
    }

    public void setClients(int population) {
        this.clients = population;
    }

    @ManyToOne
    @JoinColumn(name = "branch_id",nullable = false)
    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }
}
