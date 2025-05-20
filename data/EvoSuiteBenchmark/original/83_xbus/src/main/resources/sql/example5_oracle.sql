CREATE TABLE example5 (
	ex5_id            number(12,0)       PRIMARY KEY,
	ex5_text          varchar(999)
)
/

CREATE SEQUENCE example5_seq
/

CREATE OR REPLACE TRIGGER example5_bir
BEFORE INSERT ON example5
FOR EACH ROW
BEGIN
  SELECT example5_seq.NEXTVAL
  INTO   :NEW.ex5_id
  FROM   DUAL;
END;
/



