package pl.coderslab.Cracow_Scrooge2.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.coderslab.Cracow_Scrooge2.dto.ProductGroupDto;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "group")
    @JsonIgnore
    private List<Product> productList;

    @ManyToOne
    @JsonIgnore
    private User user;

    public ProductGroup(ProductGroupDto productGroupDto){
        this.name = productGroupDto.getName();
    }

    public ProductGroup(String name) {
        this.name = name;
    }
}
