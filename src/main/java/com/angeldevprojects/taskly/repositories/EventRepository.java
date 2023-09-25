package com.angeldevprojects.taskly.repositories;

import com.angeldevprojects.taskly.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}
