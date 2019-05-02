package pl.coderslab.Cracow_Scrooge2.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.Cracow_Scrooge2.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findAllByUserId(Long id);
    List<Product> findAllByNameAndUserId(String name,Long id);


}
