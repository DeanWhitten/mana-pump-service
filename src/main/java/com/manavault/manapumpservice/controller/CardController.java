package com.manavault.manapumpservice.controller;

import com.manavault.manapumpservice.model.Card;
import com.manavault.manapumpservice.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestController
@RequestMapping("/api/cards")
@CrossOrigin(origins = "any") // Allows requests from this origin
public class CardController {

    @Autowired
    private CardService cardService;

    @GetMapping("/")
    public Page<Card> getAllCardsPaginated(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "rarity", required = false) String rarity,
            @RequestParam(value = "type", required = false) String type,
            @RequestParam(value = "set", required = false) String set
    ) {
        if(search.isEmpty() && rarity.isEmpty() && type.isEmpty() && set.isEmpty()){
            return cardService.getAllCardsPaginated(page, size);
        }
        return cardService.getAllCardsPaginatedFiltered(page, size, search, rarity, type, set);
    }


    @GetMapping("/all")
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> allCards = cardService.getAllCards();
        return ResponseEntity.ok(allCards);
    }
}

