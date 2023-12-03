CREATE TABLE categories
(
    id              BIGINT NOT NULL,
    created_at      datetime NULL,
    is_deleted      BIT(1) NOT NULL,
    last_updated_at datetime NULL,
    `description`   VARCHAR(255) NULL,
    name            VARCHAR(255) NULL,
    CONSTRAINT PK_CATEGORIES PRIMARY KEY (id)
);

CREATE TABLE categories_seq
(
    next_val BIGINT NULL
);

CREATE TABLE jt_instructors
(
    rating  VARCHAR(255) NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT PK_JT_INSTRUCTORS PRIMARY KEY (user_id)
);

CREATE TABLE jt_mentors
(
    company VARCHAR(255) NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT PK_JT_MENTORS PRIMARY KEY (user_id)
);

CREATE TABLE jt_ta
(
    grad_year VARCHAR(255) NULL,
    user_id   BIGINT NOT NULL,
    CONSTRAINT PK_JT_TA PRIMARY KEY (user_id)
);

CREATE TABLE jt_users
(
    id    BIGINT NOT NULL,
    email VARCHAR(255) NULL,
    name  VARCHAR(255) NULL,
    CONSTRAINT PK_JT_USERS PRIMARY KEY (id)
);

CREATE TABLE jt_users_seq
(
    next_val BIGINT NULL
);

CREATE TABLE mapped_instructors
(
    id     BIGINT NOT NULL,
    email  VARCHAR(255) NULL,
    name   VARCHAR(255) NULL,
    rating VARCHAR(255) NULL,
    CONSTRAINT PK_MAPPED_INSTRUCTORS PRIMARY KEY (id)
);

CREATE TABLE mapped_instructors_seq
(
    next_val BIGINT NULL
);

CREATE TABLE mapped_mentors
(
    id      BIGINT NOT NULL,
    email   VARCHAR(255) NULL,
    name    VARCHAR(255) NULL,
    company VARCHAR(255) NULL,
    CONSTRAINT PK_MAPPED_MENTORS PRIMARY KEY (id)
);

CREATE TABLE mapped_mentors_seq
(
    next_val BIGINT NULL
);

CREATE TABLE mapped_ta
(
    id        BIGINT NOT NULL,
    email     VARCHAR(255) NULL,
    name      VARCHAR(255) NULL,
    grad_year VARCHAR(255) NULL,
    CONSTRAINT PK_MAPPED_TA PRIMARY KEY (id)
);

CREATE TABLE mapped_ta_seq
(
    next_val BIGINT NULL
);

CREATE TABLE product
(
    id              BIGINT NOT NULL,
    created_at      datetime NULL,
    is_deleted      BIT(1) NOT NULL,
    last_updated_at datetime NULL,
    `description`   VARCHAR(255) NULL,
    image_url       VARCHAR(255) NULL,
    price DOUBLE NOT NULL,
    title           VARCHAR(255) NULL,
    category_id     BIGINT NULL,
    CONSTRAINT PK_PRODUCT PRIMARY KEY (id)
);

CREATE TABLE product_seq
(
    next_val BIGINT NULL
);

CREATE TABLE sc_users
(
    user_type INT    NOT NULL,
    id        BIGINT NOT NULL,
    email     VARCHAR(255) NULL,
    name      VARCHAR(255) NULL,
    rating    VARCHAR(255) NULL,
    company   VARCHAR(255) NULL,
    grad_year VARCHAR(255) NULL,
    CONSTRAINT PK_SC_USERS PRIMARY KEY (id)
);

CREATE TABLE sc_users_seq
(
    next_val BIGINT NULL
);

CREATE TABLE tpc_instructors
(
    id     BIGINT NOT NULL,
    email  VARCHAR(255) NULL,
    name   VARCHAR(255) NULL,
    rating VARCHAR(255) NULL,
    CONSTRAINT PK_TPC_INSTRUCTORS PRIMARY KEY (id)
);

CREATE TABLE tpc_mentors
(
    id      BIGINT NOT NULL,
    email   VARCHAR(255) NULL,
    name    VARCHAR(255) NULL,
    company VARCHAR(255) NULL,
    CONSTRAINT PK_TPC_MENTORS PRIMARY KEY (id)
);

CREATE TABLE tpc_ta
(
    id        BIGINT NOT NULL,
    email     VARCHAR(255) NULL,
    name      VARCHAR(255) NULL,
    grad_year VARCHAR(255) NULL,
    CONSTRAINT PK_TPC_TA PRIMARY KEY (id)
);

CREATE TABLE tpc_users
(
    id    BIGINT NOT NULL,
    email VARCHAR(255) NULL,
    name  VARCHAR(255) NULL,
    CONSTRAINT PK_TPC_USERS PRIMARY KEY (id)
);

CREATE TABLE tpc_users_seq
(
    next_val BIGINT NULL
);

CREATE INDEX FKowomku74u72o6h8q0khj7id8q ON product (category_id);

ALTER TABLE jt_instructors
    ADD CONSTRAINT FKfcvjoonghpm022veo6us1ifyp FOREIGN KEY (user_id) REFERENCES jt_users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE jt_mentors
    ADD CONSTRAINT FKjf8q53loch2j5whhm3b4xq2hg FOREIGN KEY (user_id) REFERENCES jt_users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE jt_ta
    ADD CONSTRAINT FKkll30sn4v47smcg8mlp6xqq94 FOREIGN KEY (user_id) REFERENCES jt_users (id) ON UPDATE RESTRICT ON DELETE RESTRICT;

ALTER TABLE product
    ADD CONSTRAINT FKowomku74u72o6h8q0khj7id8q FOREIGN KEY (category_id) REFERENCES categories (id) ON UPDATE RESTRICT ON DELETE RESTRICT;