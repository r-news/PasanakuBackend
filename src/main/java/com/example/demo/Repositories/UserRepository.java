package com.example.demo.Repositories;

import com.example.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findById(@Param("id") Long id);

    //@Query("SELECT u FROM User u where u.email= :email")
    User findByEmail(@Param("email") String email);
     User findByName(String username);

}
