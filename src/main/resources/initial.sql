create table if not exists product
(
    product_id serial primary key,
    name       varchar(128),
    article    varchar(32),
    bar        varchar(32),
    price      numeric(5, 2)
)