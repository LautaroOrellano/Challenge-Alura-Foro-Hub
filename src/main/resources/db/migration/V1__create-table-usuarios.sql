create table autores(
                        id bigint not null auto_increment,
                        login varchar(100) not null,
                        clave varchar(300) not null,
                        primary key(id)
);

insert into autores(id, login, clave) values(1, 'LautaroOrellano@gmail.com', '$2a$10$KnyXU/5vK0ZaHA3JdaRpUu6BhRr.39qeS2WksUBPr9TizYLGZHbg2');