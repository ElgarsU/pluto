CREATE TABLE IF NOT EXISTS sales_transaction_data
(
    id           IDENTITY    NOT NULL,
    total_amount VARCHAR(10) NOT NULL,

    created      TIMESTAMP   NOT NULL,
    modified     TIMESTAMP,

    CONSTRAINT sales_transaction_data_id_primary_key PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS sold_products
(
    id            IDENTITY  NOT NULL,
    quantity      LONG      NOT NULL,
    product_id    BIGINT    NOT NULL,
    sales_data_id BIGINT    NOT NULL,

    created       TIMESTAMP NOT NULL,
    modified      TIMESTAMP,

    CONSTRAINT sold_products_id_primary_key PRIMARY KEY (id),
    CONSTRAINT product_id_fk FOREIGN KEY (product_id) REFERENCES product (id),
    CONSTRAINT sales_data_id_fk FOREIGN KEY (sales_data_id) REFERENCES sales_transaction_data (id)
);

ALTER TABLE IF EXISTS customer_action
    ADD COLUMN sales_transaction_id BIGINT;

ALTER TABLE IF EXISTS customer_action
    ADD CONSTRAINT sales_transaction_id_fk FOREIGN KEY (sales_transaction_id) REFERENCES sales_transaction_data (id);
