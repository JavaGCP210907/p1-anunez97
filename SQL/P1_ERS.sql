-------------------- ERS USER ROLES (DONE) -------------------
CREATE TABLE ers_user_roles (
	ers_user_role_id serial PRIMARY KEY,
	user_role varchar(10)
);

INSERT INTO ers_user_roles (user_role)
VALUES ('Manager'),
	   ('Employee');

SELECT * FROM ers_user_roles;

DROP TABLE ers_user_roles;

------------------ ERS_USERS ------------------------
CREATE TABLE ers_users (
	ers_user_id serial  PRIMARY KEY,
	ers_username varchar(50) UNIQUE,
	ers_password varchar(50),
	user_first_name varchar(100),
	user_last_name varchar(100),
	user_email varchar(150) UNIQUE,
	user_role_id_fk int REFERENCES ers_user_roles (ers_user_role_id)
);

INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role_id_fk)
VALUES ('braybeil', 'pass1', 'Erin', 'Morigs', 'wow@email.com', 1),
       ('eddie_murph', 'pass2', 'Tyler', 'Eastbrook', 'ani@email.com', 1),
       ('audilla', 'pass3', 'Audie', 'Villa', 'rush@email.com', 2),
       ('m_lowree', 'pass4', 'Maikeru', 'Jayson', 'bb4l@email.com', 2),
       ('stevey', 'pass5', 'John', 'Uni', 'flo_man@email.com', 2),
       ('echupe', 'pass6', 'Andies', 'Nunies', 'g_king@email.com', 1);

SELECT * FROM ers_users;

DROP TABLE ers_users;

------------------ ERS_REIMBURSEMENT TYPES (DONE) -------------
CREATE TABLE ers_reimbursement_types (
	reimb_type_id serial PRIMARY KEY,
	reimb_type varchar(10)
);

INSERT INTO ers_reimbursement_types (reimb_type)
VALUES ('LODGING'),
       ('TRAVEL'),
       ('FOOD'),
       ('OTHER');
      
SELECT * FROM ers_reimbursement_types;

DROP TABLE ers_reimbursement_types;

------------------- ERS_REIMBURSEMENT_STATUSES (DONE) ----------------
CREATE TABLE ers_reimbursement_statuses (
	reimb_status_id serial PRIMARY KEY,
	reimb_status varchar(10)
);

INSERT INTO ers_reimbursement_statuses (reimb_status)
VALUES ('PENDING'),
       ('APPROVED'),
       ('DENIED');

SELECT * FROM ers_reimbursement_statuses;

DROP TABLE ers_reimbursement_statuses;

------------------- ERS_REIMBURSEMENTS -------------------------------
CREATE TABLE ers_reimbursements (
	reimb_id serial PRIMARY KEY,
	reimb_amount int,
	reimb_submitted date DEFAULT "----------",
	reimb_resolved date,
	reimb_description varchar(250),
	reimb_receipt varchar(250),
	reimb_author_fk int REFERENCES ers_users (ers_user_id),
	reimb_resolver_fk int REFERENCES ers_users (ers_user_id),
	reimb_status_id_fk int REFERENCES ers_reimbursement_statuses (reimb_status_id),
	reimb_type_id_fk int REFERENCES ers_reimbursement_types (reimb_type_id)
);

-- show all reimbs
SELECT reimb.reimb_id, reimb.reimb_amount, reimb.reimb_submitted, reimb.reimb_resolved, 
reimb.reimb_description, reimb.reimb_author_fk, reimb.reimb_resolver_fk, status.reimb_status, "types".reimb_type 
FROM ers_reimbursements AS reimb
LEFT JOIN ers_reimbursement_statuses AS status
ON reimb.reimb_status_id_fk = status.reimb_status_id 
LEFT JOIN ers_reimbursement_types AS "types"
ON reimb.reimb_type_id_fk = "types".reimb_type_id
ORDER BY reimb_id;

SELECT reimb.reimb_id, reimb.reimb_amount, reimb.reimb_submitted, reimb.reimb_resolved, 
reimb.reimb_description, reimb.reimb_author_fk, reimb.reimb_resolver_fk, status.reimb_status, "types".reimb_type 
FROM ers_reimbursements AS reimb
LEFT JOIN ers_reimbursement_statuses AS status
ON reimb.reimb_status_id_fk = status.reimb_status_id 
LEFT JOIN ers_reimbursement_types AS "types"
ON reimb.reimb_type_id_fk = "types".reimb_type_id
WHERE reimb.reimb_status_id_fk = 1
AND reimb.reimb_author_fk = 1
ORDER BY reimb_id;

SELECT reimb.reimb_id, reimb.reimb_amount, reimb.reimb_submitted, reimb.reimb_resolved, 
reimb.reimb_description, reimb.reimb_author_fk, reimb.reimb_resolver_fk, status.reimb_status, "types".reimb_type 
FROM ers_reimbursements AS reimb
LEFT JOIN ers_reimbursement_statuses AS status
ON reimb.reimb_status_id_fk = status.reimb_status_id 
LEFT JOIN ers_reimbursement_types AS "types"
ON reimb.reimb_type_id_fk = "types".reimb_type_id
WHERE reimb.reimb_author_fk = 3
ORDER BY reimb_id;

ALTER TABLE ers_reimbursements ALTER COLUMN reimb_amount TYPE NUMERIC(9, 2);

SELECT * FROM ers_reimbursements WHERE reimb_author_fk = 3;

DROP TABLE ers_reimbursements;

