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
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String description;

    @Entity
    @NoArgsConstructor
    @AllArgsConstructor
    @Setter
    @Getter
    @ToString
    @EqualsAndHashCode
    public class Subcategory{
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private int id;
        private int idCategory;
        private String description;
    }

}
