package pl.coderslab.Cracow_Scrooge2.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.coderslab.Cracow_Scrooge2.entity.Offer;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Long> {

    @Query("SELECT AVG(o.price) FROM Offer o WHERE o.product.id = ?1")
    Double findAveragePriceByProductId(Long id);

//    @Query(value = "SELECT t1.* FROM offers t1 WHERE product_id\n" +
//            "JOIN (\n" +
//            "  SELECT MIN(price) AS min_value, product_id\n" +
//            "  FROM offers \n" +
//            "  GROUP BY product_id\n" +
//            ") AS t2 ON t1.product_id = t2.product_id AND t1.price = t2.min_value", nativeQuery = true)
//    List<Offer> findBestOffersForAgivenProduct();

    @Query("SELECT o FROM Offer o WHERE o.price = (SELECT MIN(oo.price) FROM Offer oo WHERE oo.product = o.product) AND o.product.user.id=?1")
    List<Offer> findBestOffersForAgivenProductAndUserId(Long id);


//    SELECT e FROM Entity e
//    WHERE e.timestamp = (SELECT MAX(ee.timestamp) FROM Entity ee WHERE ee.entityId = e.entityId)

}
