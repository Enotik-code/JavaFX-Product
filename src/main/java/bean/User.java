package bean;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String surname;
    private String patronymic;
    private String email;
    private String password;
    private String number;
    private int idPosition;
    private int idCountry;
    private String cardNumber;
    private Date dateOfBirthday;
    private Date dateOfCreated;
    private Date dateOfModified;
}
