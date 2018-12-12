--db-version:0.0.1.1
insert into t_user (user_id, user_name, state, user_password) values ('admin', '管理员', 0, '21232F297A57A5A743894A0E4A801FC3');
insert into t_user (user_id, user_name, state, user_password) values ('yuanyc', '开发者', 0, '21232F297A57A5A743894A0E4A801FC3');
update t_version set db_version = '0.0.1.1', update_time = now() where id = 1;
commit;
