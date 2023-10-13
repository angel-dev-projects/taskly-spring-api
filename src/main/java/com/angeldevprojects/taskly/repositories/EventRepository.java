package com.angeldevprojects.taskly.repositories;

import com.angeldevprojects.taskly.models.Event;
import com.angeldevprojects.taskly.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query("SELECT e FROM Event e WHERE e.user = :user")
    List<Event> findByUser(User user);
}
