CREATE CACHED TABLE journal (
	jo_id                  INT NOT NULL  IDENTITY, 
	jo_type                CHAR(1),
	jo_system              VARCHAR(50),
	jo_function            VARCHAR(50),
	jo_message_id          VARCHAR(50),
	jo_request_message     VARCHAR(99999),
	jo_request_timestamp   CHAR(23),
	jo_response_message    VARCHAR(99999),
	jo_response_timestamp  CHAR(23),
	jo_returncode          VARCHAR(10),
	jo_errorcode           INT,
	jo_errormessage        VARCHAR(255)
);

CREATE INDEX idx1 ON journal (jo_request_timestamp);

CREATE INDEX idx2 ON journal (jo_system);

CREATE INDEX idx3 ON journal (jo_returncode);

CREATE INDEX idx4 ON journal (jo_message_id);



