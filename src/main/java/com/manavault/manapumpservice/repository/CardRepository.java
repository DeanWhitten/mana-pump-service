package com.manavault.manapumpservice.repository;

import com.manavault.manapumpservice.model.Card;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {

    @Query("SELECT c FROM Card c WHERE "
            + "(:search = '' OR LOWER(c.name) LIKE LOWER(CONCAT('%', :search, '%'))) AND "
            + "(:rarity = '' OR c.rarity = :rarity) AND "
            + "(:type = '' OR c.typeLine = :type) AND "
            + "(:set = '' OR c.setName = :set)")
    Page<Card> findAllWithFilters(
            @Param("search") String search,
            @Param("rarity") String rarity,
            @Param("type") String type,
            @Param("set") String set,
            @Param("pageRequest") PageRequest pageRequest
    );

}
