package main.backend.repositories;

import main.backend.entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Repository
public interface PurchaseRepo extends JpaRepository<Purchase, Integer> {
    @Modifying
    @Query("UPDATE Purchase p SET p.remainingQuantity = :quantity WHERE p.purchaseNo= :purchaseNo")
    void changeQuantity(@Param("quantity") int quantity, @Param("purchaseNo") int purchaseNo);

    @Modifying
    @Query("UPDATE Purchase p SET p.purchaseDate = :purchaseDate WHERE p.purchaseNo= :purchaseNo")
    void changePurchaseDate(@Param("purchaseDate") LocalDate purchaseDate, @Param("purchaseNo") int purchaseNo);

    @Modifying
    @Query("UPDATE Purchase p SET p.purchasedFrom = :purchasedFrom WHERE p.purchaseNo= :purchaseNo")
    void changePurchasedFrom(@Param("purchasedFrom") String purchasedFrom, @Param("purchaseNo") int purchaseNo);

    @Modifying
    @Query("UPDATE Purchase p SET p.expiryDate = :expiryDate WHERE p.purchaseNo= :purchaseNo")
    void changeExpiryDate(@Param("expiryDate") LocalDate expiryDate, @Param("purchaseNo") int purchaseNo);

    @Modifying
    @Query("UPDATE Purchase p SET p.originalQuantity = :quantity WHERE p.purchaseNo= :purchaseNo")
    void changeOriginalQuantity(@Param("quantity") int quantity, @Param("purchaseNo") int purchaseNo);

    @Modifying
    @Query("UPDATE Purchase p SET p.price = :price WHERE p.purchaseNo= :purchaseNo")
    void changePrice(@Param("price") BigDecimal price, @Param("purchaseNo") int purchaseNo);

}
