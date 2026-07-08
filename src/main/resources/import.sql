-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

INSERT INTO users (name, email, password, role, is_valid)
VALUES ('Admin', 'admin@test.ch', '$2a$10$njO.rB.Vyog0scXGvzzD3e7egPvA/v/I0T2wEtv.0HLEl7THfbPUa', 'admin', true);