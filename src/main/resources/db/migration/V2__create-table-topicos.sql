create table topicos(
                        id bigint not null auto_increment,
                        titulo varchar(100) not null unique,
                        mensaje varchar(300) not null unique,
                        fecha datetime not null,
                        status varchar(100) not null,
                        autor_id bigint not null,
                        nombre_curso varchar(14) not null,
                        primary key(id),
                        constraint fk_topicos_autor_id foreign key(autor_id) references autores(id)
);