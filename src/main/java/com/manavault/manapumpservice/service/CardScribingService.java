package com.manavault.manapumpservice.service;

import com.manavault.manapumpservice.model.Card;
import com.manavault.manapumpservice.repository.CardScribingRepository;
import com.manavault.manapumpservice.service.FileDownloadService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CardScribingService {

    private final FileDownloadService fileDownloadService;

    private final CardScribingRepository cardRepository;
    private final ObjectMapper objectMapper;

    public void saveCardsFromJsonFile(String filePath) throws IOException {
        fileDownloadService.downloadDefaultCardsFile();
        cardRepository.deleteAll();

        // Read the JSON file
        int excludedCards = 0;
        int includedCards = 0;
        File jsonFile = new File(filePath);
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        List<Card> cards = new ArrayList<>();
        for (JsonNode cardNode : rootNode) {
            Card card = mapJsonToCard(cardNode);


            if((card.getNormalImageUri() != null || card.getLargeImageUri() != null
                    || card.getPngImageUri() != null || card.getArtCropImageUri() != null
                    || card.getBorderCropImageUri() != null) && card.getGames().contains("paper")) {  // Check if any image URI is not null
                cards.add(card);

            }else {
                excludedCards++;
            }

            // Save in batches
            if (cards.size() == 50000) {
                cardRepository.saveAll(cards);
                includedCards += cards.size();
                cards.clear();
            }
        }
        // Save remaining cards
        if (!cards.isEmpty()) {
            cardRepository.saveAll(cards);
        }

        System.out.println("Saved " + includedCards + " cards");
        System.out.println("Excluded " + excludedCards + " cards");
    }

    private Card mapJsonToCard(JsonNode cardNode) {
        Card card = new Card();

        card.setCardId(getTextValue(cardNode, "id"));
        card.setOracleId(getTextValue(cardNode, "oracle_id"));
        card.setMtgoId(getIntegerValue(cardNode, "mtgo_id"));
        card.setMtgoFoilId(getIntegerValue(cardNode, "mtgo_foil_id"));
        card.setTcgplayerId(getIntegerValue(cardNode, "tcgplayer_id"));
        card.setCardmarketId(getIntegerValue(cardNode, "cardmarket_id"));
        card.setName(getTextValue(cardNode, "name"));
        card.setLang(getTextValue(cardNode, "lang"));
        card.setReleasedAt(getDateValue(cardNode, "released_at"));
        card.setUri(getTextValue(cardNode, "uri"));
        card.setScryfallUri(getTextValue(cardNode, "scryfall_uri"));
        card.setLayout(getTextValue(cardNode, "layout"));
        card.setHighresImage(getBooleanValue(cardNode, "highres_image"));
        card.setImageStatus(getTextValue(cardNode, "image_status"));
        card.setManaCost(getTextValue(cardNode, "mana_cost"));
        card.setCmc(getNumericValue(cardNode, "cmc"));
        card.setTypeLine(getTextValue(cardNode, "type_line"));
        card.setOracleText(getTextValue(cardNode, "oracle_text"));
        card.setColors(getStringSet(cardNode, "colors"));
        card.setColorIdentity(getStringSet(cardNode, "color_identity"));
        card.setKeywords(getStringSet(cardNode, "keywords"));
        card.setGames(getStringSet(cardNode, "games"));
        card.setFinishes(getStringSet(cardNode, "finishes"));
        card.setReserved(getBooleanValue(cardNode, "reserved"));
        card.setFoil(getBooleanValue(cardNode, "foil"));
        card.setNonfoil(getBooleanValue(cardNode, "nonfoil"));
        card.setOversized(getBooleanValue(cardNode, "oversized"));
        card.setPromo(getBooleanValue(cardNode, "promo"));
        card.setReprint(getBooleanValue(cardNode, "reprint"));
        card.setVariation(getBooleanValue(cardNode, "variation"));
        card.setSetId(getTextValue(cardNode, "set_id"));
        card.setSetName(getTextValue(cardNode, "set_name"));
        card.setSetType(getTextValue(cardNode, "set_type"));
        card.setSetUri(getTextValue(cardNode, "set_uri"));
        card.setSetSearchUri(getTextValue(cardNode, "set_search_uri"));
        card.setScryfallSetUri(getTextValue(cardNode, "scryfall_set_uri"));
        card.setRulingsUri(getTextValue(cardNode, "rulings_uri"));
        card.setPrintsSearchUri(getTextValue(cardNode, "prints_search_uri"));
        card.setCollectorNumber(getTextValue(cardNode, "collector_number"));
        card.setDigital(getBooleanValue(cardNode, "digital"));
        card.setRarity(getTextValue(cardNode, "rarity"));
        card.setFlavorText(getTextValue(cardNode, "flavor_text"));
        card.setCardBackId(getTextValue(cardNode, "card_back_id"));
        card.setArtist(getTextValue(cardNode, "artist"));
        card.setArtistIds(getStringSet(cardNode, "artist_ids"));
        card.setIllustrationId(getTextValue(cardNode, "illustration_id"));
        card.setBorderColor(getTextValue(cardNode, "border_color"));
        card.setFrame(getTextValue(cardNode, "frame"));
        card.setFullArt(getBooleanValue(cardNode, "full_art"));
        card.setTextless(getBooleanValue(cardNode, "textless"));
        card.setBooster(getBooleanValue(cardNode, "booster"));
        card.setStorySpotlight(getBooleanValue(cardNode, "story_spotlight"));
        card.setEdhrecRank(getIntegerValue(cardNode, "edhrec_rank"));

        // Prices (BigInts)
        Map<String, String> prices = (Map<String, String>) getJsonMap(cardNode, "prices");
        card.setUsdPrice(prices.get("usd"));
        card.setUsdFoilPrice(prices.get("usd_foil"));
        card.setUsdEtchedPrice(prices.get("usd_etched"));
        card.setEurPrice(prices.get("eur"));
        card.setEurFoilPrice(prices.get("eur_foil"));
        card.setTixPrice(prices.get("tix"));

        // Related URIs
        Map<String, String> relatedUris = (Map<String, String>) getJsonMap(cardNode, "related_uris");
        card.setTcgplayerInfiniteArticlesUri(relatedUris.get("tcgplayer_infinite_articles"));
        card.setTcgplayerInfiniteDecksUri(relatedUris.get("tcgplayer_infinite_decks"));
        card.setEdhrecUri(relatedUris.get("edhrec"));

        // Purchase URIs
        Map<String, String> purchaseUris = (Map<String, String>) getJsonMap(cardNode, "purchase_uris");
        card.setTcgplayerUri(purchaseUris.get("tcgplayer"));
        card.setCardmarketUri(purchaseUris.get("cardmarket"));
        card.setCardhoarderUri(purchaseUris.get("cardhoarder"));

        // Legalities (Booleans)
        Map<String, String> legalities = (Map<String, String>) getJsonMap(cardNode, "legalities");
        card.setStandardLegal(mapLegalValue(legalities.get("standard")));
        card.setFutureLegal(mapLegalValue(legalities.get("future")));
        card.setHistoricLegal(mapLegalValue(legalities.get("historic")));
        card.setTimelessLegal(mapLegalValue(legalities.get("timeless")));
        card.setGladiatorLegal(mapLegalValue(legalities.get("gladiator")));
        card.setPioneerLegal(mapLegalValue(legalities.get("pioneer")));
        card.setExplorerLegal(mapLegalValue(legalities.get("explorer")));
        card.setModernLegal(mapLegalValue(legalities.get("modern")));
        card.setLegacyLegal(mapLegalValue(legalities.get("legacy")));
        card.setPauperLegal(mapLegalValue(legalities.get("pauper")));
        card.setVintageLegal(mapLegalValue(legalities.get("vintage")));
        card.setPennyLegal(mapLegalValue(legalities.get("penny")));
        card.setCommanderLegal(mapLegalValue(legalities.get("commander")));
        card.setOathbreakerLegal(mapLegalValue(legalities.get("oathbreaker")));
        //card.setStandardbrawlLegal(mapLegalValue(legalities.get("standardbrawl")));
        card.setBrawlLegal(mapLegalValue(legalities.get("brawl")));
        card.setAlchemyLegal(mapLegalValue(legalities.get("alchemy")));
        card.setPaupercommanderLegal(mapLegalValue(legalities.get("paupercommander")));
        card.setDuelLegal(mapLegalValue(legalities.get("duel")));
        card.setOldschoolLegal(mapLegalValue(legalities.get("oldschool")));
        card.setPremodernLegal(mapLegalValue(legalities.get("premodern")));
        card.setPredhLegal(mapLegalValue(legalities.get("predh")));

        // Image URIs (Strings)
        Map<String, String> imageUris = (Map<String, String>) getJsonMap(cardNode, "image_uris");
        card.setSmallImageUri(imageUris.get("small"));
        card.setNormalImageUri(imageUris.get("normal"));
        card.setLargeImageUri(imageUris.get("large"));
        card.setPngImageUri(imageUris.get("png"));
        card.setArtCropImageUri(imageUris.get("art_crop"));
        card.setBorderCropImageUri(imageUris.get("border_crop"));

        return card;
    }

    private String getTextValue(JsonNode node, String fieldName) {
        return node.has(fieldName) ? node.get(fieldName).asText() : null;
    }

    private Integer getIntegerValue(JsonNode node, String fieldName) {
        return node.has(fieldName) ? node.get(fieldName).asInt() : null;
    }

    private Double getNumericValue(JsonNode node, String fieldName) {
        return node.has(fieldName) ? node.get(fieldName).asDouble() : null;
    }

    private Boolean getBooleanValue(JsonNode node, String fieldName) {
        return node.has(fieldName) ? node.get(fieldName).asBoolean() : null;
    }

    private LocalDate getDateValue(JsonNode node, String fieldName) {
        return node.has(fieldName) ? LocalDate.parse(node.get(fieldName).asText()) : null;
    }


    private Set<String> getStringSet(JsonNode node, String fieldName) {
        if (node.has(fieldName) && node.get(fieldName).isArray()) {
            return StreamSupport.stream(node.get(fieldName).spliterator(), false)
                    .map(JsonNode::asText)
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }

    private Map<String, ?> getJsonMap(JsonNode node, String fieldName) {
        if (node.has(fieldName) && node.get(fieldName).isObject()) {
            return objectMapper.convertValue(node.get(fieldName), Map.class);
        }
        return Map.of();
    }


    private Boolean mapLegalValue(String value) {
        return "legal".equals(value);
    }
}


