CREATE TABLE  acl_sid (
  id SERIAL  primary key,
  principal SMALLINT NOT NULL,
  sid varchar(100) NOT NULL,
  UNIQUE(sid,principal)
);

CREATE TABLE acl_class (
  id SERIAL primary key,
  class varchar(255) NOT NULL,
  UNIQUE(class)
);

CREATE TABLE acl_entry (
  id SERIAL primary key,
  acl_object_identity INT NOT NULL,
  ace_order int NOT NULL,
  sid INT NOT NULL,
  mask int NOT NULL,
  granting INT NOT NULL,
  audit_success SMALLINT  NOT NULL,
  audit_failure SMALLINT  NOT NULL,
  UNIQUE(acl_object_identity,ace_order)
);

CREATE TABLE acl_object_identity (
  id SERIAL primary key,
  object_id_class INT NOT NULL,
  object_id_identity INT NOT NULL,
  parent_object INT DEFAULT NULL,
  owner_sid INT DEFAULT NULL,
  entries_inheriting SMALLINT  NOT NULL,
  UNIQUE(object_id_class,object_id_identity)
);

ALTER TABLE acl_entry
ADD FOREIGN KEY (acl_object_identity) REFERENCES acl_object_identity(id);

ALTER TABLE acl_entry
ADD FOREIGN KEY (sid) REFERENCES acl_sid(id);

--
-- Constraints for table acl_object_identity
--
ALTER TABLE acl_object_identity
ADD FOREIGN KEY (parent_object) REFERENCES acl_object_identity (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (object_id_class) REFERENCES acl_class (id);

ALTER TABLE acl_object_identity
ADD FOREIGN KEY (owner_sid) REFERENCES acl_sid (id);
