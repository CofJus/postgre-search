CREATE TABLE article
(
    id          BIGSERIAL PRIMARY KEY,
    article_id  BIGINT UNIQUE NOT NULL,
    title       TEXT NOT NULL,
    content     TEXT,
    author      VARCHAR(100),
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    status      SMALLINT  DEFAULT 1
);
CREATE INDEX idx_article_meta_create_time ON article (create_time);
CREATE INDEX idx_article_meta_author ON article (author);

CREATE TABLE article_search
(
    id          BIGSERIAL PRIMARY KEY,
    article_id  BIGINT UNIQUE NOT NULL,
    keywords    TEXT,
    tsv         tsvector      NOT NULL,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
-- GIN INDEX
CREATE INDEX idx_article_search_tsv ON article_search USING GIN (tsv);
CREATE INDEX idx_article_search_article_id ON article_search (article_id);