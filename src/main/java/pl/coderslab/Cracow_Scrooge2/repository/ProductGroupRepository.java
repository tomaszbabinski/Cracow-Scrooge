package pl.coderslab.Cracow_Scrooge2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.Cracow_Scrooge2.entity.ProductGroup;

public interface ProductGroupRepository extends JpaRepository<ProductGroup,Long> {
}