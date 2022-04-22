CREATE DATABASE  IF NOT EXISTS HRSystem
    CHARACTER SET utf8 COLLATE utf8_unicode_ci;
    
use HRSystem;
   
CREATE TABLE IF NOT EXISTS position (
	position_id INT(11) auto_increment NOT NULL,
    position_name varchar(255) NOT NULL,
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`position_id`)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS document (
	document_id INT(11) auto_increment NOT NULL,
    document_name varchar(255) NOT NULL,
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`document_id`)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS system_role (
	system_role_id INT(11) auto_increment NOT NULL,
    role_name varchar(50) NOT NULL UNIQUE,
    role_description varchar(255),
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    apply_scope varchar(20) NOT NULL DEFAULT "API",
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`system_role_id`)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS system_account (
	system_account_id INT(11) auto_increment NOT NULL,
    system_email varchar(50) UNIQUE NOT NULL,
    system_password varchar(255) NOT NULL,
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`system_account_id`)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS system_account_role (
	system_account_id int(11) NOT NULL,
    system_role_id int(11) NOT NULL,
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    CONSTRAINT fk_system_account_id FOREIGN KEY (system_account_id)  
		REFERENCES system_account(system_account_id),
	CONSTRAINT fk_system_role_id FOREIGN KEY (system_role_id)  
		REFERENCES system_role(system_role_id)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS personal_info (
	personal_info_id INT(11) auto_increment NOT NULL,
    personal_name varchar(50) NOT NULL,
    personal_birthday date NOT NULL,
    personal_address Varchar(255) NOT NULL,
    personal_phone_number varchar(15) NOT NULL,
    personal_sex varchar(10) NOT NULL,
    personal_id_card varchar(50) NOT NULL UNIQUE,
    personal_email varchar(255) NOT NULL,
    personal_image varchar(255),
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`personal_info_id`)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS room (
	room_id INT(11) auto_increment NOT NULL,
    room_name varchar(50) NOT NULL,
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`room_id`)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS request_type (
	request_type_id INT(11) auto_increment NOT NULL,
    request_type_name varchar(255) NOT NULL,
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`request_type_id`)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS reason (
	reason_id INT(11) auto_increment NOT NULL,
    reason_name varchar(255) NOT NULL,
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`reason_id`)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS request (
	request_id INT(11) auto_increment NOT NULL,
    request_type_id int(11) NOT NULL,
    reason_id int(11) NOT NULL,
    primary key (`request_id`),
    CONSTRAINT fk_request_type_id FOREIGN KEY (request_type_id)  
		REFERENCES request_type(request_type_id),
	CONSTRAINT fk_reason_id FOREIGN KEY (reason_id)  
		REFERENCES reason(reason_id)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS employee (
	employee_id INT(11) auto_increment NOT NULL,
    employee_profile_id varchar(10) NOT NULL,
    room_id int(11) NOT NULL,
    personal_info_id int(11) NOT NULL,
    system_account_id int(11),
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    employee_start_date date NOT NULL,
    employee_end_date date,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`employee_id`),
    CONSTRAINT `fk_room_id` FOREIGN KEY (room_id)  
		REFERENCES room(room_id),
	CONSTRAINT `fk_personal_info_id` FOREIGN KEY (personal_info_id)  
		REFERENCES personal_info(personal_info_id),
	CONSTRAINT `fk_employee_system_account_id` FOREIGN KEY (system_account_id)  
		REFERENCES system_account(system_account_id)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS employee_position (
	employee_id int(11) NOT NULL,
    position_id int (11) NOT NULL,
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    start_date DATE NOT NULL,
    end_date DATE,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    CONSTRAINT `fk_position_employee_id` FOREIGN KEY (employee_id)  
		REFERENCES employee(employee_id),
    CONSTRAINT `fk_position_id` FOREIGN KEY (position_id)  
		REFERENCES `position`(position_id)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS employee_document (
	employee_id int(11) NOT NULL,
    document_id int(11) NOT NULL,
    deleted_flag tinyint(1) NOT NULL DEFAULT 1,
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    CONSTRAINT `fk_document_employee_id` FOREIGN KEY (employee_id)  
		REFERENCES employee(employee_id),
    CONSTRAINT `fk_document_id` FOREIGN KEY (document_id)  
		REFERENCES `document`(document_id)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS approver_action (
	approver_action_id INT(11) auto_increment NOT NULL,
    approver_id int(11) NOT NULL,
    action_type varchar(10) NOT NULL DEFAULT 'WAITING',
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`approver_action_id`),
    CONSTRAINT `fk_approver_id` FOREIGN KEY (approver_id)  
		REFERENCES employee(employee_id)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS supervisor_action (
	supervisor_action_id INT(11) auto_increment NOT NULL,
    supervisor_id int(11) NOT NULL,
    action_type varchar(10) NOT NULL DEFAULT 'WAITING',
    created_at datetime NOT NULL default current_timestamp(),
    updated_at datetime NOT NULL default current_timestamp(),
    primary key (`supervisor_action_id`),
    CONSTRAINT `fk_supervisor_id` FOREIGN KEY (supervisor_id)  
		REFERENCES employee(employee_id)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS `comment` (
	supervisor_action_id INT(11) NOT NULL,
    approver_action_id int(11) NOT NULL,
    comment_detail varchar(255),
    created_at datetime NOT NULL default current_timestamp(),
    CONSTRAINT `fk_comment_supervisor` FOREIGN KEY (supervisor_action_id)  
		REFERENCES supervisor_action(supervisor_action_id),
    CONSTRAINT `fk_comment_approver` FOREIGN KEY (approver_action_id)  
		REFERENCES approver_action(approver_action_id)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS request_employee (
	employee_id int(11) NOT NULL,
    request_id int(11) NOT NULL,
    supervisor_action_id int(11) NOT NULL,
    approver_action_id int(11) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    partial_date varchar(10) NOT NULL DEFAULT 'ALL DAY',
    request_description varchar(255),
    expected_approve_date date NOT NULL,
    duration int(11) NOT NULL,
    request_status varchar(10) NOT NULL DEFAULT 'WAITING',
    created_at datetime NOT NULL default current_timestamp(),
    CONSTRAINT `fk_request_employee_employee_id` FOREIGN KEY (employee_id)  
		REFERENCES employee(employee_id),
    CONSTRAINT `fk_request_employee_request_id` FOREIGN KEY (request_id)  
		REFERENCES request(request_id),
    CONSTRAINT `fk_request_employee_supervisor_action_id` FOREIGN KEY (supervisor_action_id)  
		REFERENCES supervisor_action(supervisor_action_id),
    CONSTRAINT `fk_request_employee_approver_action_id` FOREIGN KEY (approver_action_id)  
		REFERENCES approver_action(approver_action_id)
) engine = InnoDB default CHARACTER SET utf8 COLLATE utf8_unicode_ci;

CREATE TABLE IF NOT EXISTS refresh_token (
	refresh_token_id int(11) NOT NULL auto_increment,
    system_account_id int (11) NOT NULL,
    refresh_token_name varchar(255) NOT NULL,
    expiry_date varchar(255) NOT NULL,
    primary key (`refresh_token_id`),
    CONSTRAINT `fk_fresh_token_account` FOREIGN KEY (system_account_id)  
		REFERENCES system_account(system_account_id)
)
