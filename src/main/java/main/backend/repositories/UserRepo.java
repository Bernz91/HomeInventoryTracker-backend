package main.backend.repositories;

import main.backend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends JpaRepository<User, String> {

    @Query("SELECT u FROM User u WHERE u.email= :email")
    User findUserByEmail(@Param("email") String email);


}
