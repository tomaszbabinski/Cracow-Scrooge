package pl.coderslab.Cracow_Scrooge2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.coderslab.Cracow_Scrooge2.entity.Purchase;
import pl.coderslab.Cracow_Scrooge2.entity.User;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase,Long> {
    List<Purchase> findAllByUser(User user);
}
