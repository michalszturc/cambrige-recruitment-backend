begin transaction;


CREATE TABLE shop.products
(
    id                  SERIAL      PRIMARY KEY     NOT NULL,
    name                CHARACTER VARYING (50)      NOT NULL,
    min_quantity        INTEGER                     NOT NULL,
    max_quantity        INTEGER                     NOT NULL,
    unit_price          DECIMAL                     NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL
);


alter table shop.products
    owner to dbadmin;

grant select, insert, update on shop.products to dbuser;;
grant usage, select on sequence shop.products_id_seq to dbuser;

CREATE TABLE shop.promo_codes
(
    code            CHARACTER   VARYING (8)        PRIMARY KEY  NOT NULL,
    created_at      TIMESTAMP   WITHOUT TIME ZONE               NOT NULL
);

alter table shop.promo_codes
    owner to dbadmin;

grant select, insert, update on shop.promo_codes to dbuser;;

CREATE TABLE shop.orders
(
    id                     SERIAL       PRIMARY KEY      NOT NULL,
    order_number_suffix    CHARACTER VARYING (3)         NOT NULL,
    email                  CHARACTER VARYING (50)        NOT NULL,
    product_id             INTEGER                       NOT NULL,
    promo_code             CHARACTER VARYING (8)         NULL,
    price                  DECIMAL                       NOT NULL,
    created_at             TIMESTAMP WITHOUT TIME ZONE   NOT NULL
);

alter table shop.orders
    owner to dbadmin;

grant select, insert, update on shop.orders to dbuser;;
grant usage, select on sequence shop.orders_id_seq to dbuser;

alter table shop.orders
    add constraint fk_orders_products foreign key (product_id)
        references shop.products (id)
        on delete restrict on update restrict;

alter table shop.orders
    add constraint fk_orders_promo_codes foreign key (promo_code)
        references shop.promo_codes (code)
        on delete restrict on update restrict;

commit;

