CREATE TABLE IF NOT EXISTS product
(
    id                IDENTITY          NOT NULL,
    product_id        UUID              NOT NULL,
    product_name      VARCHAR(500)      NOT NULL,
    product_price     DOUBLE PRECISION  NOT NULL,
    price_currency    VARCHAR(10)       NOT NULL,
    created           TIMESTAMP         NOT NULL,
    modified          TIMESTAMP,

    CONSTRAINT product_id_primary_key PRIMARY KEY (id),
    CONSTRAINT product_id_unique UNIQUE (product_id)
);

COMMENT ON COLUMN product.product_id IS 'Unique product ID';
COMMENT ON COLUMN product.product_name IS 'Name of the product';
COMMENT ON COLUMN product.product_price IS 'Price per one unit of product';
COMMENT ON COLUMN product.price_currency IS 'Currency for provided price';
COMMENT ON COLUMN product.created IS 'Date when product was added';
COMMENT ON COLUMN product.modified IS 'Date when product was modified';
