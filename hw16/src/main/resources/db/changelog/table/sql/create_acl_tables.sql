CREATE TABLE  acl_sid (
  id long auto_increment primary key,
  principal tinyint NOT NULL,
  sid varchar(100) NOT NULL,
  UNIQUE(sid,principal)
);

CREATE TABLE acl_class (
  id long auto_increment primary key,
  class varchar(255) NOT NULL,
  UNIQUE(class)
);

CREATE TABLE acl_entry (
  id long auto_increment primary key,
  acl_object_identity long NOT NULL,
  ace_order int NOT NULL,
  sid long NOT NULL,
  mask int NOT NULL,
  granting tinyint NOT NULL,
  audit_success tinyint NOT NULL,
  audit_failure tinyint NOT NULL,
  UNIQUE(acl_object_identity,ace_order)
);

CREATE TABLE acl_object_identity (
  id long auto_increment primary key,
  object_id_class long NOT NULL,
  object_id_identity long NOT NULL,
  parent_object long DEFAULT NULL,
  owner_sid long DEFAULT NULL,
  entries_inheriting tinyint NOT NULL,
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
