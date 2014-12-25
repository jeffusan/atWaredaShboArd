# weather schema

# --- !Ups

CREATE TABLE weather (
        id serial,
        create_dt timestamp not null,
        data jsonb);

CREATE index ON weather USING gin(jsonb);

insert into weather (create_dt, data) values (timestamp '2014-12-25 09:00 JST', '{"main": {"temp": "281.75", "humidity": "20", "pressure": "1000"}, "clouds": {"all": "30"}}');
insert into weather (create_dt, data) values (timestamp '2014-12-25 09:00 JST', '{"main": {"temp": "282.75", "humidity": "20", "pressure": "1001"}, "clouds": {"all": "35"}}');
insert into weather (create_dt, data) values (timestamp '2014-12-25 10:00 JST', '{"main": {"temp": "284.00", "humidity": "30", "pressure": "1002"}, "clouds": {"all": "40"}}');
insert into weather (create_dt, data) values (timestamp '2014-12-25 11:00 JST', '{"main": {"temp": "285.50", "humidity": "40", "pressure": "1003"}, "clouds": {"all": "40"}}');
insert into weather (create_dt, data) values (timestamp '2014-12-25 12:00 JST', '{"main": {"temp": "286.75", "humidity": "50", "pressure": "1004"}, "clouds": {"all": "50"}}');
insert into weather (create_dt, data) values (timestamp '2014-12-25 13:00 JST', '{"main": {"temp": "287.85", "humidity": "60", "pressure": "1005"}, "clouds": {"all": "40"}}');
insert into weather (create_dt, data) values (timestamp '2014-12-25 14:00 JST', '{"main": {"temp": "289.25", "humidity": "50", "pressure": "1004"}, "clouds": {"all": "30"}}');
insert into weather (create_dt, data) values (timestamp '2014-12-25 15:00 JST', '{"main": {"temp": "288.45", "humidity": "40", "pressure": "1003"}, "clouds": {"all": "30"}}');
insert into weather (create_dt, data) values (timestamp '2014-12-25 16:00 JST', '{"main": {"temp": "286.55", "humidity": "30", "pressure": "1002"}, "clouds": {"all": "20"}}');
insert into weather (create_dt, data) values (timestamp '2014-12-25 17:00 JST', '{"main": {"temp": "284.75", "humidity": "20", "pressure": "1001"}, "clouds": {"all": "10"}}');

# --- !Downs

DROP TABLE weather;
