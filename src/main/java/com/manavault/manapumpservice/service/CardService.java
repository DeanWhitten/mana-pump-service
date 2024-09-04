package com.manavault.manapumpservice.service;

import com.manavault.manapumpservice.model.Card;
import com.manavault.manapumpservice.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Page<Card> getAllCardsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return cardRepository.findAll(pageable);
    }

    public Page<Card> getAllCardsPaginatedFiltered(int page, int size, String search, String rarity, String type, String set) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return cardRepository.findAllWithFilters(search, rarity, type, set, pageRequest);
    }


    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }
}
