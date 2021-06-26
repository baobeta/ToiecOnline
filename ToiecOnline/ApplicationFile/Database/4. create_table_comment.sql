use toieconline;
create table comment(
    commentid bigint not null primary key auto_increment,
    context text null,
    userid bigint null ,
    listenguidelineid bigint null,
    createddate timestamp null

);

alter table comment add constraint fk_user_comment foreign key (userid) references user(userid);
alter table comment add constraint fk_listenguideline_comment foreign key (listenguidelineid) references listenguideline(listenguidelineid);


