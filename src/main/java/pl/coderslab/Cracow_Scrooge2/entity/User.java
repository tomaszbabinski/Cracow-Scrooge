package pl.coderslab.Cracow_Scrooge2.entity;

import lombok.*;
import org.mindrot.jbcrypt.BCrypt;
import pl.coderslab.Cracow_Scrooge2.dto.UserDto;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;

    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String hashedPassword;

    @OneToMany(mappedBy = "user")
    private List<Product> products;

    @OneToMany(mappedBy="user")
    private List<ProductGroup> productGroups;

    @OneToMany(mappedBy = "user")
    private List<Purchase> purchases;


    public User(UserDto userDto) {
        this.firstName = userDto.getFirstName();
        this.lastName = userDto.getLastName();
        this.email = userDto.getEmail();
        setHashedPassword(userDto.getPassword());
    }

    public String getFullName(){
        return firstName+" "+lastName;
    }

    public void setHashedPassword(String password) {
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public boolean isPasswordCorrect(String candidate) {
        return BCrypt.checkpw(candidate, this.hashedPassword);
    }
}
