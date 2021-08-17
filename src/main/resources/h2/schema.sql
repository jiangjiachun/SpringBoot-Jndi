drop table if exists t_user;

create table t_user (
	id int auto_increment primary key,
	name varchar(250) not null,
	create_datetime datetime not null 
);