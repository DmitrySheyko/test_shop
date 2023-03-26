DROP TABLE IF EXISTS PUBLIC.RATES;
DROP TABLE IF EXISTS PUBLIC.COMMENTS;
DROP TABLE IF EXISTS PUBLIC.NOTIFICATIONS;
DROP TABLE IF EXISTS PUBLIC.PURCHASES;
DROP TABLE IF EXISTS PUBLIC.PRODUCTS;
DROP TABLE IF EXISTS PUBLIC.COMPANIES;
DROP TABLE IF EXISTS PUBLIC.DISCOUNTS;
DROP TABLE IF EXISTS PUBLIC.USERS;

CREATE TABLE PUBLIC.USERS
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    username    VARCHAR(255)   NOT NULL UNIQUE,
    email       VARCHAR(255)   NOT NULL,
    password    VARCHAR(255)   NOT NULL,
    balance     DECIMAL(20, 2) NOT NULL,
    user_status VARCHAR(50)    NOT NULL,
    role        VARCHAR(50)    NOT NULL,
    created_on  TIMESTAMP,
    updated_on  TIMESTAMP
);

CREATE TABLE PUBLIC.COMPANIES
(
    id             BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name           VARCHAR(255)  NOT NULL,
    description    VARCHAR(1000) NOT NULL,
    owner_id       BIGINT DEFAULT NULL REFERENCES USERS (ID) ON DELETE SET DEFAULT,
    logo_url       VARCHAR(1000) NOT NULL UNIQUE,
    company_status VARCHAR(255)  NOT NULL
);

CREATE TABLE PUBLIC.DISCOUNTS
(
    id               BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    description      VARCHAR(1000),
    discount_value   DECIMAL(20, 2) NOT NULL,
    start_date_time  TIMESTAMP      NOT NULL,
    finish_date_time TIMESTAMP      NOT NULL
);

CREATE TABLE PUBLIC.PRODUCTS
(
    id              BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name            VARCHAR(255)   NOT NULL,
    description     VARCHAR(1000),
    company_id      BIGINT         NOT NULL REFERENCES COMPANIES (id) ON DELETE CASCADE,
    price           DECIMAL(20, 2) NOT NULL,
    quantity        INTEGER        NOT NULL,
    discount_id     BIGINT REFERENCES DISCOUNTS (id),
    key_words       VARCHAR(1000),
    characteristics VARCHAR(1000),
    product_status  VARCHAR(50)    NOT NULL
);

CREATE TABLE PUBLIC.PURCHASES
(
    id                         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    type_of_purchase           VARCHAR(50)    NOT NULL,
    company_id                 BIGINT DEFAULT NULL REFERENCES COMPANIES (id) ON DELETE SET DEFAULT,
    seller_id                  BIGINT DEFAULT NULL REFERENCES USERS (id) ON DELETE SET DEFAULT,
    buyer_id                   BIGINT DEFAULT NULL REFERENCES USERS (id) ON DELETE SET DEFAULT,
    product_id                 BIGINT DEFAULT NULL REFERENCES PRODUCTS (id) ON DELETE SET DEFAULT,
    company_id_copy            BIGINT         NOT NULL,
    company_name               VARCHAR(255)   NOT NULL,
    product_id_copy            BIGINT         NOT NULL,
    product_name               VARCHAR(255)   NOT NULL,
    quantity                   INTEGER        NOT NULL,
    price_for_unit             DECIMAL(20, 2) NOT NULL,
    total_sum_without_discount DECIMAL(20, 2) NOT NULL,
    total_sum_with_discount    DECIMAL(20, 2) NOT NULL,
    shop_commission_sum        DECIMAL(20, 2) NOT NULL,
    discount_sum               DECIMAL(20, 2) NOT NULL,
    seller_income_sum          DECIMAL(20, 2) NOT NULL,
    purchase_date_time         TIMESTAMP      NOT NULL,
    is_rejected                BOOLEAN        NOT NULL,
    reject_for_purchase_id     BIGINT,
    rejection_id               BIGINT
);

CREATE TABLE PUBLIC.NOTIFICATIONS
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    text       VARCHAR(1000) NOT NULL,
    user_id    BIGINT        NOT NULL REFERENCES USERS (id),
    product_id BIGINT        NOT NULL REFERENCES PRODUCTS (id),
    created_on TIMESTAMP     NOT NULL
);

CREATE TABLE PUBLIC.COMMENTS
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    comment_text VARCHAR(1000) NOT NULL,
    user_id      BIGINT        NOT NULL REFERENCES USERS (id),
    product_id   BIGINT        NOT NULL REFERENCES PRODUCTS (id),
    created_on   TIMESTAMP     NOT NULL
);

CREATE TABLE PUBLIC.RATES
(
    id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    rate       INTEGER   NOT NULL,
    user_id    BIGINT    NOT NULL REFERENCES USERS (id),
    product_id BIGINT    NOT NULL REFERENCES PRODUCTS (id),
    created_on TIMESTAMP NOT NULL
);

INSERT INTO PUBLIC.USERS (username, email, password, balance, user_status, role, created_on)
VALUES ('TestName_1', 'Test_1@email.ru', 'Test_password_1', 10000.0, 'ACTIVE', 'ROLE_USER', '2018-01-01T10:10:10'),
       ('TestName_2', 'Test_2@email.ru', 'Test_password_2', 20000.0, 'ACTIVE', 'ROLE_USER', '2018-01-01T10:10:10'),
       ('TestName_3', 'Test_3@email.ru', 'Test_password_3', 30000.0, 'ACTIVE', 'ROLE_USER', '2018-01-01T10:10:10'),
       ('TestName_4', 'Test_4@email.ru', 'Test_password_4', 40000.0, 'ACTIVE', 'ROLE_USER', '2018-01-01T10:10:10');

INSERT INTO PUBLIC.COMPANIES (name, description, owner_id, logo_url, company_status)
VALUES ('TestCompanyName_1', 'TestCompanyDescription_1', 1, 'TestCompanyUrl_1', 'ACTIVE'),
       ('TestCompanyName_2', 'TestCompanyDescription_2', 1, 'TestCompanyUrl_2', 'ACTIVE'),
       ('TestCompanyName_3', 'TestCompanyDescription_3', 2, 'TestCompanyUrl_3', 'ACTIVE');

INSERT INTO PUBLIC.DISCOUNTS (description, discount_value, start_date_time, finish_date_time)
VALUES ('TestDiscountDescription_1', 0.1, '2022-01-01T10:10:10', '2024-01-01T10:10:10'),
       ('TestDiscountDescription_1', 0.2, '2022-01-01T10:10:10', '2024-01-01T10:10:10'),
       ('TestDiscountDescription_1', 0.3, '2024-01-01T10:10:10', '2025-01-01T10:10:10');

INSERT INTO PUBLIC.PRODUCTS (name, description, company_id, price, quantity, discount_id, key_words, characteristics,
                             product_status)
VALUES ('TestProductName_1', 'TestProductDescription_1', 1, 1000.0, 10, 1, 'TestKeyWords_1', 'TestCharacteristics_1',
        'ACTIVE'),
       ('TestProductName_2', 'TestProductDescription_2', 1, 2000.0, 20, 2, 'TestKeyWords_2', 'TestCharacteristics_2',
        'ACTIVE'),
       ('TestProductName_3', 'TestProductDescription_3', 1, 3000.0, 30, 3, 'TestKeyWords_3', 'TestCharacteristics_3',
        'ACTIVE'),
       ('TestProductName_4', 'TestProductDescription_4', 1, 4000.0, 40, null, 'TestKeyWords_4',
        'TestCharacteristics_4', 'ACTIVE');

INSERT INTO PUBLIC.PURCHASES (type_of_purchase, company_id, company_id_copy, company_name, seller_id, buyer_id,
                              product_id, product_id_copy, product_name, quantity, price_for_unit,
                              total_sum_without_discount, total_sum_with_discount, shop_commission_sum, discount_sum,
                              seller_income_sum, purchase_date_time, is_rejected, reject_for_purchase_id, rejection_id)
VALUES ('PURCHASE', 1, 1, 'TestCompanyName_1', 2, 3, 1, 1, 'TestProductName_1', 2, 2000.00, 2000.00, 1800.00, 90.00,
        200.00, 1710.00,
        '2022-01-01T10:10:10', false,
        null, null),
       ('PURCHASE', 1, 1, 'TestCompanyName_1', 2, 3, 1, 1, 'TestProductName_1', 1, 1000.00, 1000.00, 900.00, 45.00,
        100.00, 855.00,
        '2022-01-01T10:10:10', false, null,
        3),
       ('REJECT', 1, 1, 'TestCompanyName_1', 2, 3, 1, 1, 'TestProductName_1', 1, 1000.00, 1000.00, 900.00, 45.00,
        100.00, 855.00,
        '2022-01-01T10:10:10', true, 2, null);

INSERT INTO COMMENTS (comment_text, user_id, product_id, created_on)
VALUES ('Test comment text_1', 3, 1, '2022-01-01T10:10:10');

INSERT INTO PUBLIC.RATES (rate, user_id, product_id, created_on)
VALUES (4, 3, 1, '2022-01-01T10:10:10');

INSERT INTO PUBLIC.NOTIFICATIONS (text, user_id, product_id, created_on)
VALUES ('Notification text_1', 3, 1, '2022-01-01T10:10:10');
