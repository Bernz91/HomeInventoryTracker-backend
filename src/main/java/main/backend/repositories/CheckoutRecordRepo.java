package main.backend.repositories;

import main.backend.entities.CheckoutRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckoutRecordRepo extends JpaRepository<CheckoutRecord, Integer> {
    @Query("SELECT c FROM CheckoutRecord c WHERE c.purchaseNo.purchaseNo = :purchaseNo")
    List<CheckoutRecord> findCheckoutRecordsByPurchaseNo(@Param("purchaseNo") int purchaseNo);

    @Query("SELECT c FROM CheckoutRecord c WHERE c.itemNo = :itemNo")
    List<CheckoutRecord> findCheckoutRecordsByItemNo(@Param("itemNo") int itemNo);


}
