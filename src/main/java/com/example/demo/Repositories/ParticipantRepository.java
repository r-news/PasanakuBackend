
package com.example.demo.Repositories;

import com.example.demo.model.Meet;
import com.example.demo.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

   // @Query("SELECT p FROM Participant p where p.meet= :meet")
    List<Participant> findByMeet(@Param("meet") Meet meet);
}
