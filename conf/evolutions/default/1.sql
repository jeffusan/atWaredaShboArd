#---

create table Weather (
        id serial,
        temp varchar(20) not null
        );

#--- !Downs

drop table Weather;
