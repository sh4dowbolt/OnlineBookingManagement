CREATE TABLE default_services (
     id BIGSERIAL primary key,
     name VARCHAR(255),
     description VARCHAR(255),
     category VARCHAR(255),
     duration INTEGER,
     price NUMERIC(10, 2),
     is_active  BOOLEAN NOT NULL DEFAULT TRUE,
     branch_id BIGINT,
    CONSTRAINT fk_default_services_masters
       FOREIGN KEY (branch_id) REFERENCES masters(id)
);