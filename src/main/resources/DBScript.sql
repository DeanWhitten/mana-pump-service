

CREATE TABLE file_metadata (
                               id SERIAL PRIMARY KEY,
                               file_name VARCHAR(255),
                               file_url VARCHAR(255),
                               local_path VARCHAR(255),
                               download_time TIMESTAMP,
                               status VARCHAR(50)
);

CREATE TABLE cards (
                       card_id VARCHAR(1000) PRIMARY KEY,
                       oracle_id VARCHAR(1000),
                       mtgo_id INTEGER,
                       mtgo_foil_id INTEGER,
                       tcgplayer_id INTEGER,
                       cardmarket_id INTEGER,
                       name VARCHAR(1000),
                       lang VARCHAR(1000),
                       released_at DATE,
                       uri VARCHAR(1000),
                       scryfall_uri VARCHAR(1000),
                       layout VARCHAR(1000),
                       highres_image BOOLEAN,
                       image_status VARCHAR(1000),
                       mana_cost VARCHAR(1000),
                       cmc NUMERIC,
                       type_line VARCHAR(1000),
                       oracle_text VARCHAR(5000),
                       colors TEXT[],
                       color_identity TEXT[],
                       keywords TEXT[],
                       games TEXT[],
                       finishes TEXT[],
                       reserved BOOLEAN,
                       foil BOOLEAN,
                       nonfoil BOOLEAN,
                       oversized BOOLEAN,
                       promo BOOLEAN,
                       reprint BOOLEAN,
                       variation BOOLEAN,
                       set_id VARCHAR(1000),
                       set_name VARCHAR(1000),
                       set_type VARCHAR(1000),
                       set_uri VARCHAR(1000),
                       set_search_uri VARCHAR(1000),
                       scryfall_set_uri VARCHAR(1000),
                       rulings_uri VARCHAR(1000),
                       prints_search_uri VARCHAR(1000),
                       collector_number VARCHAR(1000),
                       digital BOOLEAN,
                       rarity VARCHAR(1000),
                       flavor_text VARCHAR(1000),
                       card_back_id VARCHAR(1000),
                       artist VARCHAR(1000),
                       artist_ids TEXT[],
                       illustration_id VARCHAR(1000),
                       border_color VARCHAR(1000),
                       frame VARCHAR(1000),
                       full_art BOOLEAN,
                       textless BOOLEAN,
                       booster BOOLEAN,
                       story_spotlight BOOLEAN,
                       edhrec_rank INTEGER,

    -- Prices as BigDecimals
                       usd_price DECIMAL,
                       usd_foil_price DECIMAL,
                       usd_etched_price DECIMAL,
                       eur_price DECIMAL,
                       eur_foil_price DECIMAL,
                       tix_price DECIMAL,

    -- Related URIs
                       tcgplayer_infinite_articles_uri VARCHAR(1000),
                       tcgplayer_infinite_decks_uri VARCHAR(1000),
                       edhrec_uri VARCHAR(1000),

    -- Purchase URIs
                       tcgplayer_uri VARCHAR(1000),
                       cardmarket_uri VARCHAR(1000),
                       cardhoarder_uri VARCHAR(1000),

    -- Legalities as Booleans
                       standard_legal BOOLEAN,
                       future_legal BOOLEAN,
                       historic_legal BOOLEAN,
                       timeless_legal BOOLEAN,
                       gladiator_legal BOOLEAN,
                       pioneer_legal BOOLEAN,
                       explorer_legal BOOLEAN,
                       modern_legal BOOLEAN,
                       legacy_legal BOOLEAN,
                       pauper_legal BOOLEAN,
                       vintage_legal BOOLEAN,
                       penny_legal BOOLEAN,
                       commander_legal BOOLEAN,
                       oathbreaker_legal BOOLEAN,
                       brawl_legal BOOLEAN,
                       alchemy_legal BOOLEAN,
                       paupercommander_legal BOOLEAN,
                       duel_legal BOOLEAN,
                       oldschool_legal BOOLEAN,
                       premodern_legal BOOLEAN,
                       predh_legal BOOLEAN,

    -- Image URIs
                       small_image_uri VARCHAR(1000),
                       normal_image_uri VARCHAR(1000),
                       large_image_uri VARCHAR(1000),
                       png_image_uri VARCHAR(1000),
                       art_crop_image_uri VARCHAR(1000),
                       border_crop_image_uri VARCHAR(1000)
);

-- Normalization
-- Create the new tables
CREATE TABLE colors (
                        color_id SERIAL PRIMARY KEY,
                        card_id VARCHAR(1000),
                        color VARCHAR(1000),
                        FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE
);

CREATE TABLE color_identity (
                                identity_id SERIAL PRIMARY KEY,
                                card_id VARCHAR(1000),
                                color_identity VARCHAR(1000),
                                FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE
);

CREATE TABLE keywords (
                          keyword_id SERIAL PRIMARY KEY,
                          card_id VARCHAR(1000),
                          keyword VARCHAR(1000),
                          FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE
);

CREATE TABLE games (
                       game_id SERIAL PRIMARY KEY,
                       card_id VARCHAR(1000),
                       game VARCHAR(1000),
                       FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE
);

CREATE TABLE finishes (
                          finish_id SERIAL PRIMARY KEY,
                          card_id VARCHAR(1000),
                          finish VARCHAR(1000),
                          FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE
);

CREATE TABLE legalities (
                            legality_id SERIAL PRIMARY KEY,
                            card_id VARCHAR(1000),
                            standard_legal BOOLEAN,
                            future_legal BOOLEAN,
                            historic_legal BOOLEAN,
                            timeless_legal BOOLEAN,
                            gladiator_legal BOOLEAN,
                            pioneer_legal BOOLEAN,
                            explorer_legal BOOLEAN,
                            modern_legal BOOLEAN,
                            legacy_legal BOOLEAN,
                            pauper_legal BOOLEAN,
                            vintage_legal BOOLEAN,
                            penny_legal BOOLEAN,
                            commander_legal BOOLEAN,
                            oathbreaker_legal BOOLEAN,
                            brawl_legal BOOLEAN,
                            alchemy_legal BOOLEAN,
                            paupercommander_legal BOOLEAN,
                            duel_legal BOOLEAN,
                            oldschool_legal BOOLEAN,
                            premodern_legal BOOLEAN,
                            predh_legal BOOLEAN,
                            FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE
);

CREATE TABLE image_uris (
                            image_id SERIAL PRIMARY KEY,
                            card_id VARCHAR(1000),
                            small_image_uri VARCHAR(1000),
                            normal_image_uri VARCHAR(1000),
                            large_image_uri VARCHAR(1000),
                            png_image_uri VARCHAR(1000),
                            art_crop_image_uri VARCHAR(1000),
                            border_crop_image_uri VARCHAR(1000),
                            FOREIGN KEY (card_id) REFERENCES cards(card_id) ON DELETE CASCADE
);

-- Insert data into `colors` table
INSERT INTO colors (card_id, color)
SELECT card_id, unnest(colors) AS color
FROM cards
WHERE colors IS NOT NULL;

-- Insert data into `color_identity` table
INSERT INTO color_identity (card_id, color_identity)
SELECT card_id, unnest(color_identity) AS color_identity
FROM cards
WHERE color_identity IS NOT NULL;

-- Insert data into `keywords` table
INSERT INTO keywords (card_id, keyword)
SELECT card_id, unnest(keywords) AS keyword
FROM cards
WHERE keywords IS NOT NULL;

-- Insert data into `games` table
INSERT INTO games (card_id, game)
SELECT card_id, unnest(games) AS game
FROM cards
WHERE games IS NOT NULL;

-- Insert data into `finishes` table
INSERT INTO finishes (card_id, finish)
SELECT card_id, unnest(finishes) AS finish
FROM cards
WHERE finishes IS NOT NULL;

-- Insert data into `legalities` table
INSERT INTO legalities (
    card_id, standard_legal, future_legal, historic_legal, timeless_legal,
    gladiator_legal, pioneer_legal, explorer_legal, modern_legal, legacy_legal,
    pauper_legal, vintage_legal, penny_legal, commander_legal, oathbreaker_legal,
    brawl_legal, alchemy_legal, paupercommander_legal, duel_legal, oldschool_legal,
    premodern_legal, predh_legal
)
SELECT
    card_id, standard_legal, future_legal, historic_legal, timeless_legal,
    gladiator_legal, pioneer_legal, explorer_legal, modern_legal, legacy_legal,
    pauper_legal, vintage_legal, penny_legal, commander_legal, oathbreaker_legal,
    brawl_legal, alchemy_legal, paupercommander_legal, duel_legal, oldschool_legal,
    premodern_legal, predh_legal
FROM cards;

-- Insert data into `image_uris` table
INSERT INTO image_uris (
    card_id, small_image_uri, normal_image_uri, large_image_uri, png_image_uri,
    art_crop_image_uri, border_crop_image_uri
)
SELECT
    card_id, small_image_uri, normal_image_uri, large_image_uri, png_image_uri,
    art_crop_image_uri, border_crop_image_uri
FROM cards;


ALTER TABLE cards
    DROP COLUMN colors,
    DROP COLUMN color_identity,
    DROP COLUMN keywords,
    DROP COLUMN games,
    DROP COLUMN finishes,
    DROP COLUMN standard_legal,
    DROP COLUMN future_legal,
    DROP COLUMN historic_legal,
    DROP COLUMN timeless_legal,
    DROP COLUMN gladiator_legal,
    DROP COLUMN pioneer_legal,
    DROP COLUMN explorer_legal,
    DROP COLUMN modern_legal,
    DROP COLUMN legacy_legal,
    DROP COLUMN pauper_legal,
    DROP COLUMN vintage_legal,
    DROP COLUMN penny_legal,
    DROP COLUMN commander_legal,
    DROP COLUMN oathbreaker_legal,
    DROP COLUMN brawl_legal,
    DROP COLUMN alchemy_legal,
    DROP COLUMN paupercommander_legal,
    DROP COLUMN duel_legal,
    DROP COLUMN oldschool_legal,
    DROP COLUMN premodern_legal,
    DROP COLUMN predh_legal,
    DROP COLUMN small_image_uri,
    DROP COLUMN normal_image_uri,
    DROP COLUMN large_image_uri,
    DROP COLUMN png_image_uri,
    DROP COLUMN art_crop_image_uri,
    DROP COLUMN border_crop_image_uri;

-- Adding indexes if needed
CREATE INDEX idx_colors_card_id ON colors (card_id);
CREATE INDEX idx_color_identity_card_id ON color_identity (card_id);
CREATE INDEX idx_keywords_card_id ON keywords (card_id);
CREATE INDEX idx_games_card_id ON games (card_id);
CREATE INDEX idx_finishes_card_id ON finishes (card_id);
CREATE INDEX idx_legalities_card_id ON legalities (card_id);
CREATE INDEX idx_image_uris_card_id ON image_uris (card_id);