/**数据结构初始化**/
insert into SYS_USER (id,userName, password) values (1,'wyf', 'wyf');
insert into SYS_USER (id,userName, password) values (2,'wisely', 'wisely');

insert into SYS_ROLE(id,name) values(1,'ROLE_ADMIN');
insert into SYS_ROLE(id,name) values(2,'ROLE_USER');

insert into SYS_USER_ROLES(SYS_USER_ID,ROLES_ID) values(1,1);
insert into SYS_USER_ROLES(SYS_USER_ID,ROLES_ID) values(2,2);