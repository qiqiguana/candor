CREATE TABLE journal (
	jo_id                    INT(12) NOT NULL  AUTO_INCREMENT PRIMARY KEY, 
	jo_type                  CHAR(1),
	jo_system                VARCHAR(50),
	jo_function              VARCHAR(50),
	jo_message_id            VARCHAR(50),
	jo_request_message       TEXT,
	jo_request_timestamp     CHAR(23),
	jo_response_message      TEXT,
	jo_response_timestamp    CHAR(23),
	jo_returncode            VARCHAR(10),
	jo_errorcode             INT(6),
	jo_errormessage          varchar(255)
);

CREATE INDEX idx1 ON journal (jo_request_timestamp);

CREATE INDEX idx2 ON journal (jo_system);

CREATE INDEX idx3 ON journal (jo_returncode);

CREATE INDEX idx4 ON journal (jo_message_id);



