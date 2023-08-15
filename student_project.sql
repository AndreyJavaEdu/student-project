DROP TABLE if exists jc_student_child;
DROP TABLE if exists jc_student_order;
DROP TABLE if exists jc_passport_office;
DROP TABLE if exists jc_register_office;
DROP TABLE if exists jc_country_struct;
DROP TABLE if exists jc_university;
DROP TABLE if exists jc_street;

create table jc_street
(
    street_code integer not null ,
    street_name varchar(300),
    PRIMARY KEY(street_code)
);

create table jc_university
(
    university_id integer not null ,
    university_name varchar(300),
    PRIMARY KEY(university_id)
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
    FOREIGN KEY (p_office_area_id) references jc_country_struct(area_id) ON DELETE RESTRICT
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

create table jc_student_order
(
    student_order_id SERIAL,
    student_order_status int not null ,
    student_order_date timestamp not null ,


    h_surname varchar(100) not null,
    h_given_name varchar(100) not null,
    h_patronomyc varchar(100) not null,
    h_date_of_birth date not null,
    h_pasport_seria varchar(10) not null,
    h_passport_number varchar(10) not null,
    h_passport_date date not null,
    h_passport_office_id integer not null,
    h_post_index varchar(10),
    h_street_code integer not null,
    h_building varchar(10) not null,
    h_extension varchar(10),
    h_apartment varchar(10),
    h_university_id integer not null,
    h_student_number varchar(30),

    w_surname varchar(100) not null,
    w_given_name varchar(100) not null,
    w_patronomyc varchar(100) not null,
    w_date_of_birth date not null,
    w_pasport_seria varchar(10) not null,
    w_passport_number varchar(10) not null,
    w_passport_date date not null,
    w_passport_office_id integer not null,
    w_post_index varchar(10),
    w_street_code integer not null,
    w_building varchar(10) not null,
    w_extension varchar(10),
    w_apartment varchar(10),
    w_university_id integer not null,
    w_student_number varchar(30),

    certificate_id varchar(20) not null,
    register_office_id integer not null,
    marriage_date date not null,
    PRIMARY KEY (student_order_id),
    FOREIGN KEY (h_street_code) references jc_street(street_code) ON DELETE RESTRICT,
    FOREIGN KEY (h_passport_office_id) references jc_passport_office(p_office_id) ON DELETE RESTRICT,
    FOREIGN KEY (h_university_id) references jc_university(university_id) ON DELETE RESTRICT,
    FOREIGN KEY (w_street_code) references jc_street(street_code) ON DELETE RESTRICT,
    FOREIGN KEY (w_passport_office_id) references jc_passport_office(p_office_id) ON DELETE RESTRICT,
    FOREIGN KEY (w_university_id) references jc_university(university_id) ON DELETE RESTRICT,
    FOREIGN KEY (register_office_id) references jc_register_office(r_office_id) ON DELETE RESTRICT
);

create table jc_student_child
(
    student_child_id SERIAL,
    student_order_id integer not null,
    с_surname varchar(100) not null,
    с_gсiven_name varchar(100) not null,
    с_patronomyc varchar(100) not null,
    с_date_of_birth date not null,
    с_certificate_number varchar(10) not null,
    с_certificate_date date not null,
    с_register_office_id integer not null,
    с_post_index varchar(10),
    с_street_code integer not null,
    с_building varchar(10) not null,
    с_extension varchar(10),
    с_apartment varchar(10),
    PRIMARY KEY (student_child_id),
    FOREIGN KEY (с_street_code) references jc_street (street_code) ON DELETE RESTRICT,
    FOREIGN KEY (с_register_office_id) references jc_register_office (r_office_id) ON DELETE RESTRICT,
    FOREIGN KEY (student_order_id) references jc_student_order(student_order_id) ON DELETE RESTRICT
);

CREATE INDEX idx_student_order_status on jc_student_order(student_order_status);
CREATE INDEX idx_student_order_id on jc_student_child(student_order_id);