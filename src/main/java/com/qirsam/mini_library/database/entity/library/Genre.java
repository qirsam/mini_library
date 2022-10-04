package com.qirsam.mini_library.database.entity.library;

public enum Genre {
    BIOGRAPHYAUTOBIOGRAPHY("Автобиография"),
    ANTHOLOGY("Антология"),
    FABLE("Басня"),
    DRAMA("Драма"),
    PERIODICALS("Журналы"),
    HISTORICALFICTION("Историческая фантастика"),
    CLASSIC("Классика"),
    COMICANDGRAPHICNOVEL("Комикс и графическая новелла"),
    CRIMEANDDETECTIVE("Криминал и детектив"),
    LEGEND("Легенды"),
    MAGICALREALISM("Магический реализм"),
    MEMOIR("Мемуары"),
    MYSTERY("Мистика"),
    MYTHOLOGY("Мифология"),
    SCIFI("Научная фантастика"),
    ACTIONANDADVENTURE("Приключение"),
    POETRY("Поэзия"),
    PROGRAMMING("Программирование"),
    SHORTSTORY("Рассказ"),
    REALISTICFICTION("Реалистическая фантастика"),
    ROMANCE("Романс"),
    SATIRE("Сатира"),
    SELFHELPBOOK("Селф-хелп"),
    FAIRYTALE("Сказка"),
    REFERENCEBOOKS("Справочная литература"),
    NARRATIVENONFICTION("Творческая научная литература"),
    SUSPENSETHRILLER("Триллер"),
    HORROR("Ужасы"),
    FANFICTION("Фанфик"),
    FANTASY("Фэнтези"),
    ESSAY("Эссе"),
    HUMOR("Юмор");

    private final String value;

    Genre(String text) {
        value = text;
    }

    public String getValue() {
        return value;
    }

}
