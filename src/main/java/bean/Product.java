package bean;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String subcategory;
    private int amount;
    private float start_price;
    private float discount;
    private float price;
    private int idManufacturer;


    public Product(String name, String subcategory, int amount, float discount, float price) {
        this.name = name;
        this.subcategory = subcategory;
        this.amount = amount;
        this.discount = discount;
        this.price = price;
    }
}
