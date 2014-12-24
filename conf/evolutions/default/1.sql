# weather schema

# --- !Ups

CREATE TABLE weather (
        id serial,
        create_dt timestamp not null,
        data jsonb);

CREATE index ON weather USING gin(jsonb);

# --- !Downs

DROP TABLE weather;
