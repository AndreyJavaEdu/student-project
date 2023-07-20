DROP TABLE if exists jc_register_office;
DROP TABLE if exists jc_passport_office;
DROP TABLE if exists jc_country_struct;
DROP TABLE if exists jc_street;

create table jc_street
(
    street_code integer,
    street_name varchar(300),
    PRIMARY KEY(street_code)
);

create table jc_country_struct
(
    area_id char(12) not null,
    area_name varchar(200),
    PRIMARY KEY(area_id)

);

create table jc_passport_office
(
    p_office_id integer not null,
    p_office_area_id char(12),
    p_office_name varchar(200),
    PRIMARY KEY(p_office_id),
    FOREIGN KEY (p_office_area_id)
        references jc_country_struct(area_id) ON DELETE RESTRICT
);

create table jc_register_office
(
    r_office_id integer not null,
    r_office_area_id char(12),
    r_office_name varchar(200),
    PRIMARY KEY(r_office_id),
    FOREIGN KEY (r_office_area_id)
        references jc_country_struct(area_id) ON DELETE RESTRICT

);