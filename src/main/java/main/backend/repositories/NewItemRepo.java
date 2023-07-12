package main.backend.repositories;

import main.backend.entities.NewItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NewItemRepo extends JpaRepository<NewItem, String> {
    @Query("SELECT n FROM NewItem n WHERE n.userId = :userId")
    List<NewItem> findByUserId(@Param("userId") String userId);
    @Query("SELECT n FROM NewItem n WHERE n.itemName = :itemName AND n.userId= :userId")
    NewItem findByItemNameAndUserId(@Param("itemName") String itemName, @Param("userId") String userId);
    @Modifying
    @Query("DELETE FROM NewItem n WHERE n.userId= :userId AND n.itemName = :itemName")
    void deleteByItemNameAndUserId(@Param("userId") String userId, @Param("itemName") String itemName);
}

