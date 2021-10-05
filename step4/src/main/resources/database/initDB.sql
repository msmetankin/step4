

CREATE TABLE IF NOT EXISTS content
(
    id SERIAL PRIMARY KEY NOT NULL,
    text VARCHAR(160) NOT NULL ,
    status boolean  NOT NULL,
    created_At VARCHAR(100) NOT NULL ,
    updated_At VARCHAR(100)  NOT NULL
);