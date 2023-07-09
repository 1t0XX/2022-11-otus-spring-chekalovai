insert into t_book
(
    name
,author_id
,genre_id
)
values
    (
        'Лезвие бритвы'
    ,(select id from t_author a where a.name = 'Иван' and a.surname = 'Ефремов')
    ,(select id from t_genre g where g.name = 'Научная фантастика')
    );
insert into t_book
(
    name
,author_id
,genre_id
)
values
    (
        'Собачье сердце'
    ,(select id from t_author a where a.name = 'Михаил' and a.surname = 'Булгаков')
    ,(select id from t_genre g where g.name = 'Повесть')
    );
insert into t_book
(
    name
,author_id
,genre_id
)
values
    (
        'Белый клык'
    ,(select id from t_author a where a.name = 'Джек' and a.surname = 'Лондон')
    ,(select id from t_genre g where g.name = 'Повесть')
    );
insert into t_book
(
    name
,author_id
,genre_id
)
values
    (
        'Морфий'
    ,(select id from t_author a where a.name = 'Михаил' and a.surname = 'Булгаков')
    ,(select id from t_genre g where g.name = 'Рассказ')
    );