package com.manavault.manapumpservice.repository;
import com.manavault.manapumpservice.model.Card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardScribingRepository extends JpaRepository<Card, Long> {
}