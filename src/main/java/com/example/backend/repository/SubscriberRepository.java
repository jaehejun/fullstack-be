package com.example.backend.repository;


import com.example.backend.entity.Subscriber;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SubscriberRepository extends JpaRepository<Subscriber, Integer> {
    @EntityGraph(attributePaths = {"subscriberRoleList"})
    @Query("select s from Subscriber s where s.email =:email")
    Subscriber getWithRoles(@Param("email") String email);
}
