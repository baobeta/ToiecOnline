use toieconline;

create table listenguideline(
    listenguidelineid bigint not null primary key auto_increment,
    title varchar(512) null,
    image nvarchar(255) null,
    context text null,
    createddate timestamp null,
    modifieddate timestamp null


)