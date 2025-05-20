ALTER TABLE journal CHANGE number              jo_id                    INT(12),
                    CHANGE type                jo_type                  CHAR(1),
                    CHANGE system              jo_system                VARCHAR(50),
                    CHANGE function            jo_function              VARCHAR(50),
                    CHANGE message_id          jo_message_id            VARCHAR(50),
                    CHANGE request_message     jo_request_message       TEXT,
                    CHANGE request_timestamp   jo_request_timestamp     CHAR(23),
                    CHANGE response_message    jo_response_message      TEXT,
                    CHANGE response_timestamp  jo_response_timestamp    CHAR(23),
                    CHANGE returncode          jo_returncode            VARCHAR(10),
                    CHANGE errorcode           jo_errorcode             INT(6),
                    CHANGE errormessage        jo_errormessage          varchar(255);


