package dataProviders;

import org.testng.annotations.DataProvider;

import java.util.Arrays;
import java.util.List;

import static core.YandexSpellerConstants.Language.*;
import static core.YandexSpellerConstants.Options.*;
import static io.restassured.http.Method.*;
import static org.apache.http.HttpStatus.SC_METHOD_NOT_ALLOWED;
import static org.apache.http.HttpStatus.SC_OK;

public class DataProviders {


    @DataProvider()
    public Object[][] wrongWordDataProvider() {
        return new Object[][]{
                //Ukrainian case
                {new String[]{"хвалинку", "читири", "огірк"},
                        UK,
                        new List[]{Arrays.asList("хвилинку", "хвилинка", "хвелинку"),
                                Arrays.asList("чотири", "читыри", "четири"),
                                Arrays.asList("огірки", "огірок", "огирки", "огірка", "огіркі", "огірку", "огір")}},

                //Russian case
                {new String[]{"тварог", "агурец", "мшка"},
                        RU,
                        new List[]{Arrays.asList("творог", "творога", "сварог"),
                                Arrays.asList("огурец", "гуриец", "гаарец"),
                                Arrays.asList("мишка", "мышка", "мушка", "мешка")}},

                //English case
                {new String[]{"cucumbar", "ambrsia", "marbl"},
                        EN,
                        new List[]{Arrays.asList("cucumber", "cucumbers", "cucumba"),
                                Arrays.asList("ambrosia", "ambria", "ambrosial"),
                                Arrays.asList("marble", "marbel", "marbles", "marbled", "marblo", "maribel", "marbly")}}
        };
    }

    @DataProvider()
    public Object[][] multiLangWrongWordDataProvider() {
        return new Object[][]{
                //Multi lang case
                {new String[]{"дівчена", "дывушка", "giral"},
                        MULTI,
                        new List[]{Arrays.asList("дівчина"),
                                Arrays.asList("девушка"),
                                Arrays.asList("girl")}}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] capitalizationDataProvider() {
        return new Object[][]{
                //Ukrainian case
                {new String[]{"софiя", "київ"},
                        UK,
                        DEFAULT,
                        new List[]{Arrays.asList("Софiя"),
                                Arrays.asList("Київ")}},

                //Russian case
                {new String[]{"дмитрий", "москва"},
                        RU,
                        DEFAULT,
                        new List[]{Arrays.asList("Дмитрий"),
                                Arrays.asList("Москва")}},

                //English case
                {new String[]{"john", "california"},
                        EN,
                        DEFAULT,
                        new List[]{Arrays.asList("John"),
                                Arrays.asList("California")}},

                //Ukrainian case with ignore capitalization
                {new String[]{"софiя", "київ"},
                        UK,
                        IGNORE_CAPITALIZATION,
                        new List[]{}},

                //Russian case with ignore capitalization
                {new String[]{"дмитрий", "москва"},
                        RU,
                        IGNORE_CAPITALIZATION,
                        new List[]{}},

                //English case with ignore capitalization
                {new String[]{"john", "california"},
                        EN,
                        IGNORE_CAPITALIZATION,
                        new List[]{}}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] alphaNumericStringsDataProvider() {
        return new Object[][]{
                //Ukrainian case with DEFAULT option
                {new String[]{"1лицар", "ли2цар", "лицар3"}, UK, DEFAULT},

                //Russian case with DEFAULT option
                {new String[]{"1рыцарь", "рыц2арь", "рыцарь3"}, RU, DEFAULT},

                //English case with DEFAULT option
                {new String[]{"1knight", "kni2ght", "knight3"}, EN, DEFAULT},

                //Ukrainian case with IGNORE_DIGITS option
                {new String[]{"1лицар", "ли2цар", "лицар3"}, UK, IGNORE_DIGITS},

                //Russian case with IGNORE_DIGITS option
                {new String[]{"1рыцарь", "рыц2арь", "рыцарь3"}, RU, IGNORE_DIGITS},

                //English case with IGNORE_DIGITS option
                {new String[]{"1knight", "kni2ght", "knight3"}, EN, IGNORE_DIGITS}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] URLDataProvider() {
        return new Object[][]{
                //Ukrainian case with DEFAULT option
                {new String[]{"https://sinoptik.ua/", "https://olx.ua/", "https://tsn.ua/"},
                        UK,
                        DEFAULT},

                //Russian case with DEFAULT option
                {new String[]{"https://yandex.ru/", "https://mail.ru/", "https://rambler.ru/"},
                        RU,
                        DEFAULT},

                //English case with DEFAULT option
                {new String[]{"https://facebook.com/", "https://youtube.com/", "https://yahoo.com/"},
                        EN, DEFAULT},

                //Ukrainian case with IGNORE_URLS option
                {new String[]{"https://sinoptik.ua/", "https://olx.ua/", "https://tsn.ua/"},
                        UK,
                        IGNORE_URLS},

                //Russian case with IGNORE_URLS option
                {new String[]{"https://yandex.ru/", "https://mail.ru/", "https://rambler.ru/"},
                        RU,
                        IGNORE_URLS},

                //English case with IGNORE_URLS option
                {new String[]{"https://facebook.com/", "https://youtube.com/", "https://yahoo.com/"},
                        EN,
                        IGNORE_URLS}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] FilenamesDataProvider() {
        return new Object[][]{
                //Ukrainian case with DEFAULT option
                {new String[]{"README.txt", "file.exe", "кiт.png"},
                        UK,
                        DEFAULT},

                //Russian case with DEFAULT option
                {new String[]{"README.txt", "file.exe", "кот.png"},
                        RU,
                        DEFAULT},

                //English case with DEFAULT option
                {new String[]{"README.txt", "file.exe", "cat.png"},
                        EN, DEFAULT},

                //Ukrainian case with IGNORE_URLS option
                {new String[]{"README.txt", "file.exe", "кiт.png"},
                        UK,
                        IGNORE_URLS},

                //Russian case with IGNORE_URLS option
                {new String[]{"README.txt", "file.exe", "кот.png"},
                        RU,
                        IGNORE_URLS},

                //English case with IGNORE_URLS option
                {new String[]{"README.txt", "file.exe", "cat.png"},
                        EN,
                        IGNORE_URLS}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] repeatWordsDataProvider() {
        return new Object[][]{
                //Ukrainian case with DEFAULT option
                {new String[]{"кiт кiт"},
                        UK,
                        DEFAULT
                },

                //Russian case with DEFAULT option
                {new String[]{"кот кот"
                },
                        RU,
                        DEFAULT},

                //English case with DEFAULT option
                {new String[]{"cat cat"},
                        EN,
                        DEFAULT},
                //Ukrainian case with FIND_REPEAT_WORDS option
                {new String[]{"кiт кiт"},
                        UK,
                        FIND_REPEAT_WORDS
                },

                //Russian case with FIND_REPEAT_WORDS option
                {new String[]{"кот кот"
                },
                        RU,
                        FIND_REPEAT_WORDS
                },

                //English case with FIND_REPEAT_WORDS option
                {new String[]{"cat cat"},
                        EN,
                        FIND_REPEAT_WORDS
                }
        };
    }

    @DataProvider(parallel = true)
    public Object[][] unsupportedLanguagesDataProvider() {
        return new Object[][]{

                //French case in English
                {"fr"},

                //French case in Russian
                {"фр"},

                //Something case
                {"123"}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] wrongLanguageWordsDataProvider() {
        return new Object[][]{

                //Russian case
                {new String[]{"Этот текст не должен быть проверен на ошыбки"
                },
                        EN},

                //English case
                {new String[]{"This text should not be checked for errars"},
                        RU},

                //Ukrainian case
                {new String[]{"This text should not be checked for errars"},
                        UK}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] correctWordsDataProvider() {
        return new Object[][]{
                //Ukrainian case
                {new String[]{"добре", "ведмідь", "мать"}, UK},

                //Russian case
                {new String[]{"хорошо", "медведь", "матір"}, RU},

                //English case
                {new String[]{"good", "bear", "mother"}, EN}
        };
    }

    @DataProvider(parallel = true)
    public Object[][] methodsDataProvider() {
        return new Object[][]{
                {GET, SC_OK, "HTTP/1.1 200 OK"},
                {POST, SC_OK, "HTTP/1.1 200 OK"},
                {HEAD, SC_OK, "HTTP/1.1 200 OK"},
                {OPTIONS, SC_OK, "HTTP/1.1 200 OK"},
                {PUT, SC_METHOD_NOT_ALLOWED, "HTTP/1.1 405 Method not allowed"},
                {PATCH, SC_METHOD_NOT_ALLOWED, "HTTP/1.1 405 Method not allowed"},
                {DELETE, SC_METHOD_NOT_ALLOWED, "HTTP/1.1 405 Method not allowed"}
        };
    }
}
