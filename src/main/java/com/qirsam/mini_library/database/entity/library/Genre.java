package com.qirsam.mini_library.database.entity.library;

public enum Genre {
    ACTIONANDADVENTURE("Приключение"),
    ANTHOLOGY("Антология"),
    CLASSIC("Классика"),
    COMICANDGRAPHICNOVEL("Комикс и графическая новелла"),
    CRIMEANDDETECTIVE("Криминал и детектив"),
    DRAMA("Драма"),
    FABLE("Басня"),
    FAIRYTALE("Сказка"),
    FANFICTION("Фанфик"),
    FANTASY("Фэнтези"),
    HISTORICALFICTION("Историческая фантастика"),
    HORROR("Ужасы"),
    HUMOR("Юмор"),
    LEGEND("Легенды"),
    MAGICALREALISM("Магический реализм"),
    MYSTERY("Мистика"),
    MYTHOLOGY("Мифология"),
    REALISTICFICTION("Реалистическая фантастика"),
    ROMANCE("Романс"),
    SATIRE("Сатира"),
    SCIFI("Научная фантастика"),
    SHORTSTORY("Рассказ"),
    SUSPENSETHRILLER("Триллер"),
    BIOGRAPHYAUTOBIOGRAPHY("Автобиография"),
    ESSAY("Эссе"),
    MEMOIR("Мемуары"),
    NARRATIVENONFICTION("Творческая научная литература"),
    PERIODICALS("Журналы"),
    REFERENCEBOOKS("Справочная литература"),
    SELFHELPBOOK("Селф-хелп"),
    PROGRAMMING("Программирование"),
    POETRY("Поэзия");

    private final String value;

    Genre(String text) {
        value = text;
    }

    public String getValue() {
        return value;
    }

}
