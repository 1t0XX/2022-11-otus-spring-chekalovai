INSERT INTO acl_sid (principal, sid) VALUES
(0, 'ROLE_CHILD'),
(0, 'ROLE_ADULT');

INSERT INTO acl_class (class) VALUES
('ru.otus.model.Book');

INSERT INTO acl_object_identity (object_id_class, object_id_identity, parent_object, owner_sid, entries_inheriting) VALUES

(1,1, NULL,(select id from acl_sid where sid = 'ROLE_ADULT'), 0),
(1,2, NULL,(select id from acl_sid where sid = 'ROLE_ADULT'), 0),
(1,3, NULL,(select id from acl_sid where sid = 'ROLE_ADULT'), 0),
(1,4, NULL,(select id from acl_sid where sid = 'ROLE_ADULT'), 0),
(1,5, NULL,(select id from acl_sid where sid = 'ROLE_ADULT'), 0)
;

INSERT INTO acl_entry (acl_object_identity, ace_order, sid, mask,granting, audit_success, audit_failure) VALUES
(1, 1, (select id from acl_sid where sid = 'ROLE_CHILD'), 1, 0, 1, 1),
(2, 1, (select id from acl_sid where sid = 'ROLE_CHILD'), 1, 1, 1, 1),
(3, 1, (select id from acl_sid where sid = 'ROLE_CHILD'), 1, 1, 1, 1),
(4, 1, (select id from acl_sid where sid = 'ROLE_CHILD'), 1, 1, 1, 1),
(5, 1, (select id from acl_sid where sid = 'ROLE_CHILD'), 1, 0, 1, 1),
(1, 2, (select id from acl_sid where sid = 'ROLE_ADULT'), 1, 1, 1, 1),
(2, 2, (select id from acl_sid where sid = 'ROLE_ADULT'), 1, 1, 1, 1),
(3, 2, (select id from acl_sid where sid = 'ROLE_ADULT'), 1, 1, 1, 1),
(4, 2, (select id from acl_sid where sid = 'ROLE_ADULT'), 1, 1, 1, 1),
(5, 2, (select id from acl_sid where sid = 'ROLE_ADULT'), 1, 1, 1, 1)
;