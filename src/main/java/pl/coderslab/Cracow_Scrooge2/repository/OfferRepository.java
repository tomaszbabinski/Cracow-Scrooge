package pl.coderslab.Cracow_Scrooge2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.Cracow_Scrooge2.entity.Offer;

public interface OfferRepository extends JpaRepository<Offer,Long> {

    @Query("SELECT AVG(o.price) FROM Offer o WHERE o.product.id = ?1")
    Double findAveragePriceByProductId(Long id);

}
