package com.angeldevprojects.taskly.repositories;

import com.angeldevprojects.taskly.models.Contact;
import com.angeldevprojects.taskly.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    @Query("SELECT c FROM Contact c WHERE c.user = :user")
    List<Contact> findByUser(User user);
}
