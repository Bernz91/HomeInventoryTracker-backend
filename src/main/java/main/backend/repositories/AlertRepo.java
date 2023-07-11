package main.backend.repositories;

import main.backend.entities.Alert;

import main.backend.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AlertRepo extends JpaRepository<Alert, Integer> {

    @Modifying
    @Query("UPDATE Alert a SET a.lowInventoryAlert = :alert WHERE a.itemNo= :itemNo")
    void changeLowInventoryAlert(@Param("alert") boolean alert, @Param("itemNo") int itemNo);

    @Modifying
    @Query("UPDATE Alert a SET a.inventoryThreshold = :threshold WHERE a.itemNo= :itemNo")
    void changeInventoryThreshold(@Param("threshold") int threshold, @Param("itemNo") int itemNo);

    @Modifying
    @Query("UPDATE Alert a SET a.expiryDateAlert = :alert WHERE a.itemNo= :itemNo")
    void changeExpiryDateAlert(@Param("alert") boolean alert, @Param("itemNo") int itemNo);

    @Modifying
    @Query("UPDATE Alert a SET a.daysBeforeExpiryDate = :days WHERE a.itemNo= :itemNo")
    void changeDaysBeforeExpiryDate(@Param("days") int days, @Param("itemNo") int itemNo);

}
