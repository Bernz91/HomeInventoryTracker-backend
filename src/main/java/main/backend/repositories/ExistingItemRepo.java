package main.backend.repositories;

import main.backend.entities.ExistingItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ExistingItemRepo extends JpaRepository<ExistingItem, Integer> {

    @Query("SELECT e FROM ExistingItem e WHERE e.userId.userID = :userId")
    List<ExistingItem> findByUserId(@Param("userId") UUID userId);
    @Query("SELECT e FROM ExistingItem e WHERE e.itemNo = :itemNo AND e.userId.userID= :userId")
    ExistingItem findByItemNoAndUserId(@Param("itemNo") int itemNo, @Param("userId") UUID userId);

    @Modifying
    @Query("UPDATE ExistingItem e SET e.quantity = :quantity WHERE e.itemNo= :itemNo")
    void changeQuantity(@Param("quantity") int quantity, @Param("itemNo") int itemNo);
}
