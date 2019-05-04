package pl.coderslab.Cracow_Scrooge2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
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
    private List<Product> productList;

    public ProductGroup(ProductGroupDto productGroupDto){
        this.name = productGroupDto.getName();
    }
}
