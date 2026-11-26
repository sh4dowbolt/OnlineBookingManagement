CREATE TABLE master_services (
      id BIGSERIAL primary key,
      duration INTEGER,
      price NUMERIC(10, 2),
      is_active  BOOLEAN NOT NULL DEFAULT TRUE,
      default_service_id BIGINT,
      master_id BIGINT,
      CONSTRAINT fk_default_services_master_services
          FOREIGN KEY (default_service_id) REFERENCES default_service(id),
      CONSTRAINT fk_default_services_masters
          FOREIGN KEY (master_id) REFERENCES masters(id)
);