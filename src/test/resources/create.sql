CREATE TABLE samples (
	id BIGINT PRIMARY KEY AUTO_INCREMENT,
	str VARCHAR(64) NOT NULL
);

CREATE TABLE users (
	username VARCHAR(64) PRIMARY KEY,
	password VARCHAR(64) NOT NULL
);

CREATE TABLE tasks (
	task_id VARCHAR(128) PRIMARY KEY,
	task_name VARCHAR(256) NOT NULL,
	deadline BIGINT(20)
);
