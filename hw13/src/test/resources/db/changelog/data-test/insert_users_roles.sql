insert into t_user_role(user_id,role_id) values ((select id from t_user where username = 'user'),(select id from t_role where name = 'ROLE_USER'));
insert into t_user_role(user_id,role_id) values ((select id from t_user where username = 'user'),(select id from t_role where name ='ROLE_CHILD'));
insert into t_user_role(user_id,role_id) values ((select id from t_user where username = 'postman'),(select id from t_role where name ='ROLE_USER'));
insert into t_user_role(user_id,role_id) values ((select id from t_user where username = 'postman'),(select id from t_role where name ='ROLE_CHILD'));
insert into t_user_role(user_id,role_id) values ((select id from t_user where username = 'admin'),(select id from t_role where name ='ROLE_ADULT'));
insert into t_user_role(user_id,role_id) values ((select id from t_user where username = 'admin'),(select id from t_role where name ='ROLE_MANAGER'));