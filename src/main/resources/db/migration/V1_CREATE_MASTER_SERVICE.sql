CREATE TABLE master_services (
      id BIGSERIAL primary key,
      duration INTEGER,
      price NUMERIC(10, 2),
      is_active  BOOLEAN NOT NULL DEFAULT TRUE,
      default_service_id BIGINT,
      master_id BIGINT,
      CONSTRAINT fk_default_services_master_services FOREIGN KEY (default_service_id) REFERENCES default_services(id),
      CONSTRAINT fk_default_services_masters FOREIGN KEY (master_id) REFERENCES masters(id),
      CONSTRAINT uq_default_service_master UNIQUE (default_service_id, master_id),
      CONSTRAINT ck_price_bigger_zero CHECK (price>0),
      CONSTRAINT ck_price_bigger_zero CHECK (duration>0)
);