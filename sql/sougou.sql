drop table if exists category;
create table category(
       id int not null AUTO_INCREMENT,
       classID char(7) not null,
       des varchar(16) not null,
       primary key(id),
       unique(classID)
)DEFAULT CHARSET='utf8';

drop table if exists doc;
create table doc(
       id int not null auto_increment,
       classID char(7) not null,
       content text not null,
       primary key(id)
)DEFAULT CHARSET='utf8';
