INSERT INTO product (product_id, product_name, product_price, price_currency, created)
VALUES (random_uuid(), 'Phone', 10, 'EUR', now());

INSERT INTO product (product_id, product_name, product_price, price_currency, created)
VALUES (random_uuid(), 'TV', 20, 'EUR', now());

INSERT INTO product (product_id, product_name, product_price, price_currency, created)
VALUES (random_uuid(), 'Laptop', 30, 'EUR', now());

--Customer actions
INSERT INTO customer_action (action_type, customer_id, product_id, session_id, created)
VALUES ('PRODUCT_VIEWED',
        random_uuid(),
        (SELECT product_id FROM product WHERE product_name = 'Phone'),
        RANDOM_UUID(),
        now());

INSERT INTO customer_action (action_type, customer_id, product_id, session_id, created)
VALUES ('PRODUCT_ADDED_TO_CART',
        random_uuid(),
        (SELECT product_id FROM product WHERE product_name = 'TV'),
        RANDOM_UUID(),
        now());

INSERT INTO customer_action (action_type, customer_id, product_id, session_id, created)
VALUES ('PRODUCT_ADDED_TO_CART',
        random_uuid(),
        (SELECT product_id FROM product WHERE product_name = 'Laptop'),
        RANDOM_UUID(),
        now());

INSERT INTO customer_action (action_type, customer_id, product_id, session_id, created)
VALUES ('CHECKOUT_STARTED',
        (SELECT customer_id
         FROM customer_action
         WHERE action_type = 'PRODUCT_VIEWED'
           AND product_id = (SELECT product_id FROM product WHERE product_name = 'Phone')),
        null,
        RANDOM_UUID(),
        now());

INSERT INTO customer_action (action_type, customer_id, product_id, session_id, created)
VALUES ('CHECKOUT_STARTED',
        (SELECT customer_id
         FROM customer_action
         WHERE action_type = 'PRODUCT_ADDED_TO_CART'
           AND product_id = (SELECT product_id FROM product WHERE product_name = 'Laptop')),
        null,
        'b3d5dc4a-95aa-4288-a075-cf876d702f0b',
        now());

--Customer action - sales
INSERT INTO sales_transaction (total_amount, created)
VALUES (200, now());

INSERT INTO sold_products (quantity, product_id, sales_transaction_id, created)
VALUES (2,
        (SELECT id FROM product WHERE product_name = 'Phone'),
        (SELECT id FROM sales_transaction WHERE total_amount = '200'),
        now());

INSERT INTO sold_products (quantity, product_id, sales_transaction_id, created)
VALUES (1,
        (SELECT id FROM product WHERE product_name = 'TV'),
        (SELECT id FROM sales_transaction WHERE total_amount = '200'),
        now());

INSERT INTO customer_action (action_type, customer_id, product_id, session_id, sales_transaction_id, created)
VALUES ('PURCHASE_COMPLETED',
        (SELECT customer_id FROM customer_action WHERE session_id = 'b3d5dc4a-95aa-4288-a075-cf876d702f0b'),
        null,
        RANDOM_UUID(),
        (SELECT id FROM sales_transaction WHERE total_amount = '200'),
        now());
