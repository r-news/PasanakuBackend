package com.example.demo.Repositories;

import com.example.demo.model.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendsRepository extends JpaRepository<Friendship, Long> {
    @Override
    List<Friendship> findAll();
}