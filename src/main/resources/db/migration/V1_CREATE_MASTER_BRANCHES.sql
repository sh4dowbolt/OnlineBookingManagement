CREATE TABLE master_branches (
     id BIGSERIAL primary key,
     duration INTEGER,
     is_active  BOOLEAN NOT NULL DEFAULT TRUE,
     is_primary  BOOLEAN NOT NULL DEFAULT FALSE,
     valid_date_from DATE,
     valid_date_to DATE,
     create_date DATE,
     update_date DATE,
     master_id BIGINT,
     branch_id BIGINT,
     CONSTRAINT fk_master_branches_masters FOREIGN KEY (master_id) REFERENCES masters(id),
     CONSTRAINT fk_master_branches_branches FOREIGN KEY (branch_id) REFERENCES branches(id),
     CONSTRAINT ck_valid_from_to CHECK (valid_date_from=<valid_date_to)
    );

CREATE UNIQUE INDEX uq_master_has_only_primary_per_branch on master_branches(master_id)
WHERE is_primary=true