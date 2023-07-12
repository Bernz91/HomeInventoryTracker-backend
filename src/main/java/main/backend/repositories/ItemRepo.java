package main.backend.repositories;

import main.backend.entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepo extends JpaRepository<Item, Integer> {

    @Query("SELECT i FROM Item i WHERE i.itemName= :itemName AND i.userId.userID= :userId")
    Item findItemByItemName(@Param("itemName") String itemName, @Param("userId") String userId);

    @Query("SELECT i FROM Item i WHERE  i.userId.userID= :userId")
    List<Item> findByUserId(@Param("userId") String userId);

    @Modifying
    @Query("UPDATE Item i SET i.totalQuantity = :totalQuantity WHERE i.itemNo= :itemNo")
    void changeTotalQuantity(@Param("totalQuantity") int totalQuantity, @Param("itemNo") int itemNo);

    @Modifying
    @Query("UPDATE Item i SET i.itemName = :itemName WHERE i.itemNo= :itemNo")
    void changeItemName(@Param("itemName") String itemName, @Param("itemNo") int itemNo);

    @Modifying
    @Query("UPDATE Item i SET i.categoryNo.categoryNo = :categoryNo WHERE i.itemNo= :itemNo")
    void changeCategory(@Param("categoryNo") int categoryNo, @Param("itemNo") int itemNo);

}

