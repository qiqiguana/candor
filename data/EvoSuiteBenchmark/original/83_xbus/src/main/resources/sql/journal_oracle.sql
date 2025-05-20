CREATE TABLE journal
( 
  jo_id                  NUMBER(12,0),
  jo_type                VARCHAR2(1),
  jo_system              VARCHAR2(50),
  jo_function            VARCHAR2(50),
  jo_message_id          VARCHAR2(50),
  jo_request_message     CLOB,
  jo_request_timestamp   VARCHAR2(23),
  jo_response_message    CLOB,
  jo_response_timestamp  VARCHAR2(23),
  jo_returncode          VARCHAR2(10),
  jo_errorcode           NUMBER(6,0),
  jo_errormessage        VARCHAR2(255)
)
/

ALTER TABLE journal ADD CONSTRAINT journal_pk PRIMARY KEY (jo_id)
/

CREATE SEQUENCE journal_seq
/

CREATE OR REPLACE TRIGGER journal_bir
BEFORE INSERT ON journal
FOR EACH ROW
BEGIN
  SELECT journal_seq.NEXTVAL
  INTO   :NEW.jo_id
  FROM   DUAL;
END;
/

CREATE INDEX journal_idx1 ON journal (jo_request_timestamp);

CREATE INDEX journal_idx2 ON journal (jo_system);

CREATE INDEX journal_idx3 ON journal (jo_returncode);

CREATE INDEX journal_idx4 ON journal (jo_message_id);



