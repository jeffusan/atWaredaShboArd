#--- !Ups

create table weather (
        id serial,
        temperature decimal(8,2) not null,
        create_dt int not null,
        humidity int not null,
        pressure int not null,
        wind_speed decimal(3,1) not null,
        wind_degree int not null,
        clouds_percent int not null
        );

#--- !Downs

drop table weather;
