INSERT INTO author (id, firstname, lastname, birth_date, description)
VALUES (1, 'Джон Рональд Руэл', 'Толкин', '1892-01-03', 'Джон Рональд Руэл Толкин (англ. John Ronald Reuel Tolkien; 3 января 1892 — 2 сентября 1973) — английский писатель, лингвист, филолог, наиболее известен как автор" Хоббита", трилогии" Властелин колец".
Толкин был оксфордским профессором англосаксонского языка (1925—1945), английского языка и литературы (1945—1959). Ещё в детстве Джон со своими товарищами придумали несколько языков, чтобы общаться между собой.'),
       (2, 'Алексей', 'Пехов', '1978-03-30',
        'Известный российский писатель, работает в основном в жанре фэнтези. Наиболее известен цикл «Хроники Сиалы», получивший награду «Серебряный кадуцей» на международном фестивале Звёздный мост в 2003 году, за первую книгу трилогии, «Крадущийся в тени». '),
       (3, 'Виталий', 'Зыков', '1979-10-05',
        'Виталий Зыков – знаменитый писатель-фантаст. Виталий Валерьевич родился в городе Липецке 5 октября 1979 года. С раннего детства мальчик проявлял тягу к сочинительству и пытался писать рассказы. Он сменил две школы: один раз по инициативе родителей, а второй – из-за врожденной любознательности и «ослиного упрямства». Родной считает свою третью школу-лицей № 44. Окончив Липецкий государственный технический университет, поступил в аспирантуру. Во время учебы поспорил с одногруппником, что сможет написать книгу не хуже, чем Ник Перумов.');

SELECT SETVAL('author_id_seq', (SELECT MAX(id) FROM author));

INSERT INTO book (id, title, author_id, genre, description)
VALUES (1, 'Властелин колец', (SELECT id FROM author WHERE lastname = 'Толкин'), 'FANTASY',
        'Роман-эпопея английского писателя Дж. Р. Р. Толкина, одно из самых известных произведений жанра фэнтези. «Властелин колец» был написан как единая книга, но из-за объёма при первом издании его разделили на три части - «Братство Кольца», «Две крепости» и «Возвращение короля». В виде трилогии он публикуется и по сей день, хотя часто в едином томе. Роман считается первым произведением жанра эпическое фэнтези, а также его классикой.'),
       (2, 'Хоббит, или Туда и обратно', (SELECT id FROM author WHERE lastname = 'Толкин'), 'FANTASY',
        'Повесть английского писателя Джона Р. Р. Толкина. Впервые опубликована в 1937 году издательством George Allen & Unwin, став со временем классикой детской литературы. В основе сюжета - путешествие хоббита Бильбо Бэггинса, волшебника Гэндальфа и тринадцати гномов во главе с Торином Дубощитом. Их путь лежит к Одинокой Горе, где находятся гномьи сокровища, охраняемые драконом Смаугом.'),
       (3, 'Страж', (SELECT id FROM author WHERE lastname = 'Пехов'), 'FANTASY',
        'Жизнь Людвига ванн Нормайенна нельзя назвать спокойной из-за уникального дара, которым обладают единицы. Он может видеть то, что неподвластно обычным людям. Его не любят и боятся, но только приходит беда – зовут на помощь. Лишь стражам под силу справиться с темными силами, которые время от времени приходят из другого мира.'),
       (4, 'Крадущийся в тени', (SELECT id FROM author WHERE lastname = 'Пехов'), 'FANTASY',
        '«И вся королевская конница и вся королевская рать» и даже Орден Магов никак не могут справиться со злыми демонами. Да еще и наемные убийцы вкупе с воровской гильдией терроризируют. А вот Гаррету – честному и благородному вору – справиться придется, ведь иначе ему грозит смерть на плахе. Тут уж научишься выкручиваться из самых сложных ситуаций!'),
       (5, 'Безымянный раб', (SELECT id FROM author WHERE lastname = 'Зыков'), 'FANTASY',
        'Стар мир Торна, очень стар! Под безжалостным ветром времени исчезали цивилизации, низвергались в бездну великие расы… Новые народы магией и мечом утвердили свой порядок. Установилось Равновесие. В этот период на Торн не по своей воле попадают несколько землян. И заколебалась чаша весов, зашевелились последователи забытых культов, встрепенулись недовольные властью, зазвучали слова древних пророчеств, а спецслужбы затеяли новую игру… Над всем этим стоят кукловоды, безразличные к судьбе горстки людей, изгнанных из своего мира, и теперь лишь от самих землян зависит, как сложится здесь жизнь. Так один из них выбирает дорогу мага, а второго ждет путь раба, несмотря ни на что ведущий к свободе!'),
       (6, 'Наемник Его Величества', (SELECT id FROM author WHERE lastname = 'Зыков'), 'FANTASY',
        'Ветры перемен продолжают набирать силу над многострадальным Торном. Легендарные артефакты всплывают из небытия, правители становятся игрушками в руках тайных обществ, а сильные мира сего в очередной раз оказываются на пороге новой Великой войны… Последней войны в этом мире! И вновь звенят клинки, сотрясают земли Торна битвы чародеев, а в ночи беззвучно скользят тени наемных убийц. Борьба за жизнь, свободу и счастье продолжается!');

SELECT SETVAL('book_id_seq', (SELECT MAX(id) FROM book));

INSERT INTO users (id, username, password, birth_date, firstname, lastname, role)
VALUES (1, 'masha@gmail.com', '{noop}123', '1997-01-01', 'masha', 'andreeva', 'USER'),
       (2, 'petya@gmail.com', '{noop}123', '1997-01-01', 'petya', 'bunin', 'USER'),
       (3, 'alya@gmail.com', '{noop}123', '1997-01-01', 'alina', 'lazchenko', 'MODERATOR'),
       (4, 'qirsam@gmail.com', '{noop}123', '1997-01-01', 'sergey', 'lazchenko', 'ADMIN'),
       (5, 'test@gmail.com', '{noop}test', '2000-01-01', 'test', 'test', 'ADMIN');

SELECT SETVAL('users_id_seq', (SELECT MAX(id) FROM users));

INSERT INTO users_book (id, user_id, book_id, status)
VALUES (1, (SELECT id FROM users WHERE username = 'qirsam@gmail.com'),
        (SELECT id FROM book WHERE title = 'Наемник Его Величества'), 'COMPLETED'),
       (2, (SELECT id FROM users WHERE username = 'qirsam@gmail.com'), (SELECT id FROM book WHERE title = 'Страж'),
        'COMPLETED'),
       (3, (SELECT id FROM users WHERE username = 'alya@gmail.com'),
        (SELECT id FROM book WHERE title = 'Крадущийся в тени'), 'READING'),
       (4, (SELECT id FROM users WHERE username = 'test@gmail.com'),
        (SELECT id FROM book WHERE title = 'Крадущийся в тени'), 'READING'),
       (5, (SELECT id FROM users WHERE username = 'test@gmail.com'),
        (SELECT id FROM book WHERE title = 'Властелин колец'), 'READING'),
       (6, (SELECT id FROM users WHERE username = 'alya@gmail.com'),
        (SELECT id FROM book WHERE title = 'Хоббит, или Туда и обратно'), 'COMPLETED');

SELECT SETVAL('users_book_id_seq', (SELECT MAX(id) FROM users_book));
