
    create table person (
        certified_year_of_birth varchar(4),
        certified_year_of_death varchar(4),
        recorded_year_of_birth varchar(4),
        version integer,
        created_date datetime(6),
        update_date datetime(6),
        id varchar(36) not null,
        first_name varchar(128) not null,
        last_name varchar(128) not null,
        primary key (id)
    ) engine=InnoDB;
