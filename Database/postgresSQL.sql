CREATE SCHEMA IF NOT EXISTS hrsystem;

set schema 'hrsystem';

CREATE TABLE  IF NOT exists position (
	position_id serial PRIMARY KEY,
	position_name varchar(255) NOT NULL,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    created_at timestamp NOT null DEFAULT CURRENT_TIMESTAMP,
    updated_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS document (
	document_id serial PRIMARY KEY,
    document_name varchar(255) NOT NULL,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp
);

CREATE TABLE IF NOT EXISTS system_role (
	system_role_id serial PRIMARY KEY,
    role_name varchar(50) NOT NULL UNIQUE,
    role_description varchar(255),
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    apply_scope varchar(20) NOT NULL DEFAULT 'API',
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp
);

CREATE TABLE IF NOT EXISTS system_account (
	system_account_id serial PRIMARY KEY,
    system_email varchar(50) UNIQUE NOT NULL,
    system_password varchar(255) NOT NULL,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp
);

CREATE TABLE IF NOT EXISTS system_account_role (
	system_account_role_id serial PRIMARY KEY,
	system_account_id integer NOT NULL,
    system_role_id integer NOT NULL,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT fk_system_account_id FOREIGN KEY (system_account_id)  
		REFERENCES system_account(system_account_id),
	CONSTRAINT fk_system_role_id FOREIGN KEY (system_role_id)  
		REFERENCES system_role(system_role_id)
);

CREATE TABLE IF NOT EXISTS personal_info (
	personal_info_id serial PRIMARY KEY,
    personal_name varchar(50) NOT NULL,
    personal_birthday date NOT NULL,
    personal_address Varchar(255) NOT NULL,
    personal_phone_number varchar(15) NOT NULL,
    personal_sex varchar(10) NOT NULL,
    personal_id_card varchar(50) NOT NULL UNIQUE,
    personal_email varchar(255) NOT NULL,
    personal_image varchar(255),
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp
);

CREATE TABLE IF NOT EXISTS room (
	room_id serial PRIMARY KEY,
    room_name varchar(50) NOT NULL,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp
);

CREATE TABLE IF NOT EXISTS request_type (
	request_type_id serial PRIMARY KEY,
    request_type_name varchar(255) NOT NULL,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp
);

CREATE TABLE IF NOT EXISTS reason (
	reason_id serial PRIMARY KEY,
    reason_name varchar(255) NOT NULL,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp
);

CREATE TABLE IF NOT EXISTS request (
	request_id serial PRIMARY KEY,
    request_type_id integer NOT NULL,
    reason_id integer NOT NULL,
    CONSTRAINT fk_request_type_id FOREIGN KEY (request_type_id)  
		REFERENCES request_type(request_type_id),
	CONSTRAINT fk_reason_id FOREIGN KEY (reason_id)  
		REFERENCES reason(reason_id)
);

CREATE TABLE IF NOT EXISTS employee (
	employee_id serial PRIMARY KEY,
    employee_profile_id varchar(10) NOT NULL UNIQUE,
    room_id integer NOT NULL,
    personal_info_id integer NOT NULL UNIQUE,
    system_account_id integer UNIQUE,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    employee_start_date date NOT NULL,
    employee_end_date date,
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT fk_room_id FOREIGN KEY (room_id)  
		REFERENCES room(room_id),
	CONSTRAINT fk_personal_info_id FOREIGN KEY (personal_info_id)  
		REFERENCES personal_info(personal_info_id),
	CONSTRAINT fk_employee_system_account_id FOREIGN KEY (system_account_id)  
		REFERENCES system_account(system_account_id)
);

CREATE TABLE IF NOT EXISTS employee_position (
	employee_position_id serial PRIMARY KEY,
	employee_id integer NOT NULL,
    position_id integer NOT NULL,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    start_date DATE NOT NULL,
    end_date DATE,
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT fk_position_employee_id FOREIGN KEY (employee_id)  
		REFERENCES employee(employee_id),
    CONSTRAINT fk_position_id FOREIGN KEY (position_id)  
		REFERENCES position(position_id)
);

CREATE TABLE IF NOT EXISTS employee_document (
	employee_document_id serial PRIMARY KEY,
	employee_id integer NOT NULL,
    document_id integer NOT NULL,
    deleted_flag boolean NOT NULL DEFAULT FALSE,
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT fk_document_employee_id FOREIGN KEY (employee_id)  
		REFERENCES employee(employee_id),
    CONSTRAINT fk_document_id FOREIGN KEY (document_id)  
		REFERENCES document(document_id)
);

CREATE TABLE IF NOT EXISTS approver_action (
	approver_action_id serial PRIMARY KEY,
    approver_id integer NOT NULL,
    action_type varchar(10) NOT NULL DEFAULT 'WAITING',
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT fk_approver_id FOREIGN KEY (approver_id)  
		REFERENCES employee(employee_id)
);

CREATE TABLE IF NOT EXISTS supervisor_action (
	supervisor_action_id serial PRIMARY KEY,
    supervisor_id integer NOT NULL,
    action_type varchar(10) NOT NULL DEFAULT 'WAITING',
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT fk_supervisor_id FOREIGN KEY (supervisor_id)  
		REFERENCES employee(employee_id)
);

CREATE TABLE IF NOT EXISTS requester_action (
	requester_action_id serial PRIMARY KEY,
    requester_id integer NOT NULL,
    action_type varchar(10) NOT NULL DEFAULT 'WAITING',
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT fk_requester_id FOREIGN KEY (requester_id)  
		REFERENCES employee(employee_id)
);

CREATE TABLE IF NOT EXISTS comment (
	comment_id serial PRIMARY KEY,
	supervisor_action_id integer,
    approver_action_id integer,
    requester_action_id integer,
    comment_detail varchar(255),
    created_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT fk_comment_supervisor FOREIGN KEY (supervisor_action_id)  
		REFERENCES supervisor_action(supervisor_action_id),
    CONSTRAINT fk_comment_approver FOREIGN KEY (approver_action_id)  
		REFERENCES approver_action(approver_action_id),
	CONSTRAINT fk_comment_requester FOREIGN KEY (requester_action_id)  
		REFERENCES requester_action(requester_action_id)
);

CREATE TABLE IF NOT EXISTS request_employee (
	employee_id integer NOT NULL,
    request_id integer NOT NULL,
    supervisor_action_id integer NOT NULL,
    approver_action_id integer NOT NULL,
    requester_action_id integer NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    partial_date varchar(10) NOT NULL DEFAULT 'ALL DAY',
    request_description varchar(255),
    expected_approve_date timestamptz NOT NULL,
    duration DECIMAL (5,2) NOT NULL,
    request_status varchar(10) NOT NULL DEFAULT 'WAITING',
    created_at timestamp NOT NULL default current_timestamp,
    updated_at timestamp NOT NULL default current_timestamp,
    CONSTRAINT fk_request_employee_employee_id FOREIGN KEY (employee_id)  
		REFERENCES employee(employee_id),
    CONSTRAINT fk_request_employee_request_id FOREIGN KEY (request_id)  
		REFERENCES request(request_id),
    CONSTRAINT fk_request_employee_supervisor_action_id FOREIGN KEY (supervisor_action_id)  
		REFERENCES supervisor_action(supervisor_action_id),
    CONSTRAINT fk_request_employee_approver_action_id FOREIGN KEY (approver_action_id)  
		REFERENCES approver_action(approver_action_id),
    CONSTRAINT fk_request_employee_requester_action_id FOREIGN KEY (requester_action_id)  
		REFERENCES requester_action(requester_action_id)
);

CREATE TABLE IF NOT EXISTS refresh_token (
	refresh_token_id serial PRIMARY KEY,
    system_account_id integer NOT NULL,
    refresh_token_name varchar(255) NOT NULL,
    expiry_date varchar(255) NOT NULL,
    CONSTRAINT fk_fresh_token_account FOREIGN KEY (system_account_id)  
		REFERENCES system_account(system_account_id)
);

CREATE TABLE IF NOT EXISTS history_action (
	history_action_id serial PRIMARY KEY,
    employee_id integer NOT NULL,
    action_type varchar(50) NOT NULL default 'N/A',
    computer_ip varchar(255) NOT NULL default 'N/A',
    target_table varchar(255) NOT NULL default 'N/A',
    target_row_id integer NOT NULL default 0,
    target_column varchar(255) NOT NULL default 'N/A',
    target_value varchar(255) NOT NULL default 'N/A',
    created_at timestamp NOT NULL default current_timestamp, 
    CONSTRAINT fk_history_action_employee FOREIGN KEY (employee_id)  
		REFERENCES employee(employee_id)
);

CREATE TABLE IF NOT EXISTS notification (
	notification_id serial PRIMARY KEY,
    request_id integer NOT NULL,
    sender_id integer NOT NULL,
    receiver_id integer NOT NULL,
    created_at timestamp NOT NULL default current_timestamp, 
    read_flag boolean NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_notification_sender_id FOREIGN KEY (sender_id)  
		REFERENCES employee(employee_id),
    CONSTRAINT fk_notification_receiver_id FOREIGN KEY (receiver_id)  
		REFERENCES employee(employee_id)
);