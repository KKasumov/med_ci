create table if not exists telegram_code
(
    email           varchar(255) not null
    constraint pk_telegram_code
    primary key,
    code            varchar(255) not null,
    chat_id         bigint       not null,
    expiration_date timestamp    not null
    );

alter table telegram_code
    owner to postgres;