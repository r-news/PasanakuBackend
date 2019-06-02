package com.example.demo.Repositories;

import com.example.demo.model.Meet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MeetRepository extends JpaRepository<Meet, Long> {
    Meet findById(@Param("id") Long id);
    Meet findByName(@Param("name") String name);
}
