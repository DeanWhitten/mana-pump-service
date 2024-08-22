package com.manavault.manapumpservice.model;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards")
public class Card {

    @Id
    @Column(name = "card_id", length = 1000)
    private String cardId;

    @Column(name = "oracle_id", length = 1000)
    private String oracleId;

    @Column(name = "mtgo_id")
    private Integer mtgoId;

    @Column(name = "mtgo_foil_id")
    private Integer mtgoFoilId;

    @Column(name = "tcgplayer_id")
    private Integer tcgplayerId;

    @Column(name = "cardmarket_id")
    private Integer cardmarketId;

    @Column(name = "name", length = 1000)
    private String name;

    @Column(name = "lang", length = 1000)
    private String lang;

    @Column(name = "released_at")
    private LocalDate releasedAt;

    @Column(name = "uri", length = 1000)
    private String uri;

    @Column(name = "scryfall_uri", length = 1000)
    private String scryfallUri;

    @Column(name = "layout", length = 1000)
    private String layout;

    @Column(name = "highres_image")
    private Boolean highresImage;

    @Column(name = "image_status", length = 1000)
    private String imageStatus;

    // Image URIs
    @Column(name = "small_image_uri", length = 1000)
    private String smallImageUri;

    @Column(name = "normal_image_uri", length = 1000)
    private String normalImageUri;

    @Column(name = "large_image_uri", length = 1000)
    private String largeImageUri;

    @Column(name = "png_image_uri", length = 1000)
    private String pngImageUri;

    @Column(name = "art_crop_image_uri", length = 1000)
    private String artCropImageUri;

    @Column(name = "border_crop_image_uri", length = 1000)
    private String borderCropImageUri;

    @Column(name = "mana_cost", length = 1000)
    private String manaCost;

    @Column(name = "cmc")
    private Double cmc;

    @Column(name = "type_line", length = 1000)
    private String typeLine;


    @Column(name = "oracle_text", length = 5000)
    private String oracleText;

    //@ElementCollection
    //@CollectionTable(name = "card_colors", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "color", length = 1000)
    private Set<String> colors;

    //@ElementCollection
    //@CollectionTable(name = "card_color_identity", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "color_identity", length = 1000)
    private Set<String> colorIdentity;

    //@ElementCollection
    //@CollectionTable(name = "card_keywords", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "keywords", length = 1000)
    private Set<String> keywords;

    //@ElementCollection
    //@CollectionTable(name = "card_games", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "games", length = 1000)
    private Set<String> games;

    //@ElementCollection
    //@CollectionTable(name = "card_finishes", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "finishes", length = 1000)
    private Set<String> finishes;

    @Column(name = "reserved")
    private Boolean reserved;

    @Column(name = "foil")
    private Boolean foil;

    @Column(name = "nonfoil")
    private Boolean nonfoil;

    @Column(name = "oversized")
    private Boolean oversized;

    @Column(name = "promo")
    private Boolean promo;

    @Column(name = "reprint")
    private Boolean reprint;

    @Column(name = "variation")
    private Boolean variation;

    @Column(name = "set_id", length = 1000)
    private String setId;

    @Column(name = "set_name", length = 1000)
    private String setName;

    @Column(name = "set_type", length = 1000)
    private String setType;

    @Column(name = "set_uri", length = 1000)
    private String setUri;

    @Column(name = "set_search_uri", length = 1000)
    private String setSearchUri;

    @Column(name = "scryfall_set_uri", length = 1000)
    private String scryfallSetUri;

    @Column(name = "rulings_uri", length = 1000)
    private String rulingsUri;

    @Column(name = "prints_search_uri", length = 1000)
    private String printsSearchUri;

    @Column(name = "collector_number", length = 1000)
    private String collectorNumber;

    @Column(name = "digital")
    private Boolean digital;

    @Column(name = "rarity", length = 1000)
    private String rarity;


    @Column(name = "flavor_text", columnDefinition = "TEXT")
    private String flavorText;

    @Column(name = "card_back_id", length = 1000)
    private String cardBackId;

    @Column(name = "artist", length = 1000)
    private String artist;

    //@ElementCollection
   // @CollectionTable(name = "card_artist_ids", joinColumns = @JoinColumn(name = "card_id"))
    @Column(name = "artist_id", length = 1000)
    private Set<String> artistIds;

    @Column(name = "illustration_id", length = 1000)
    private String illustrationId;

    @Column(name = "border_color", length = 1000)
    private String borderColor;

    @Column(name = "frame", length = 1000)
    private String frame;

    @Column(name = "full_art")
    private Boolean fullArt;

    @Column(name = "textless")
    private Boolean textless;

    @Column(name = "booster")
    private Boolean booster;

    @Column(name = "story_spotlight")
    private Boolean storySpotlight;

    @Column(name = "edhrec_rank")
    private Integer edhrecRank;

    // Prices
    @Column(name = "usd_price", length = 1000)
    private String usdPrice;

    @Column(name = "usd_foil_price", length = 1000)
    private String usdFoilPrice;

    @Column(name = "usd_etched_price", length = 1000)
    private String usdEtchedPrice;

    @Column(name = "eur_price", length = 1000)
    private String eurPrice;

    @Column(name = "eur_foil_price", length = 1000)
    private String eurFoilPrice;

    @Column(name = "tix_price", length = 1000)
    private String tixPrice;

    // Related URIs
    @Column(name = "tcgplayer_infinite_articles_uri", length = 1000)
    private String tcgplayerInfiniteArticlesUri;

    @Column(name = "tcgplayer_infinite_decks_uri", length = 1000)
    private String tcgplayerInfiniteDecksUri;

    @Column(name = "edhrec_uri", length = 1000)
    private String edhrecUri;

    // Purchase URIs
    @Column(name = "tcgplayer_uri", length = 1000)
    private String tcgplayerUri;

    @Column(name = "cardmarket_uri", length = 1000)
    private String cardmarketUri;

    @Column(name = "cardhoarder_uri", length = 1000)
    private String cardhoarderUri;

    // Legalities
    @Column(name = "standard_legal")
    private Boolean standardLegal;

    @Column(name = "future_legal")
    private Boolean futureLegal;

    @Column(name = "historic_legal")
    private Boolean historicLegal;

    @Column(name = "timeless_legal")
    private Boolean timelessLegal;

    @Column(name = "gladiator_legal")
    private Boolean gladiatorLegal;

    @Column(name = "pioneer_legal")
    private Boolean pioneerLegal;

    @Column(name = "explorer_legal")
    private Boolean explorerLegal;

    @Column(name = "modern_legal")
    private Boolean modernLegal;

    @Column(name = "legacy_legal")
    private Boolean legacyLegal;

    @Column(name = "pauper_legal")
    private Boolean pauperLegal;

    @Column(name = "vintage_legal")
    private Boolean vintageLegal;

    @Column(name = "penny_legal")
    private Boolean pennyLegal;

    @Column(name = "commander_legal")
    private Boolean commanderLegal;

    @Column(name = "oathbreaker_legal")
    private Boolean oathbreakerLegal;

    @Column(name = "brawl_legal")
    private Boolean brawlLegal;

    @Column(name = "alchemy_legal")
    private Boolean alchemyLegal;

    @Column(name = "paupercommander_legal")
    private Boolean paupercommanderLegal;

    @Column(name = "duel_legal")
    private Boolean duelLegal;

    @Column(name = "oldschool_legal")
    private Boolean oldschoolLegal;

    @Column(name = "premodern_legal")
    private Boolean premodernLegal;

    @Column(name = "predh_legal")
    private Boolean predhLegal;
}



