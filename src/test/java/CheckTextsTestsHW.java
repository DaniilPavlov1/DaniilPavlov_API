import beans.YandexSpellerAnswer;
import core.YandexSpellerApi;
import dataProviders.DataProviders;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static core.YandexSpellerConstants.*;
import static core.YandexSpellerConstants.Options.DEFAULT;
import static utils.ResponseChecker.*;

public class CheckTextsTestsHW {

    @Test(description = "Check correction of wrong words for all supported languages",
            dataProvider = "wrongWordDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkWrongWordsCorrection(String[] texts, Language lang, List[] expectedSuggestions) {
        SoftAssert soft = new SoftAssert();

        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersArray(
                        YandexSpellerApi.with().texts(texts).language(lang).callApi());

        checkResponseStrictly(texts, expectedSuggestions, soft, answers);
    }

    @Test(description = "Check multicorrection of wrong words for all supported languages",
            dataProvider = "multiLangWrongWordDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkWrongWordsMultiCorrection(String[] texts, Language lang, List[] expectedSuggestions) {
        SoftAssert soft = new SoftAssert();

        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersArray(
                        YandexSpellerApi.with().texts(texts).language(lang).callApi());

        checkResponseSoftly(texts, expectedSuggestions, soft, answers);
    }

    //There is bug - answers when DEFAULT option is activated haven't corrections for words with wrong case
    @Test(description = "Check correction of wrong capitalization with DEFAULT option and with IGNORE_CASE option for all supported languages",
            dataProvider = "capitalizationDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkCapitalization(String[] texts, Language lang, Options options, List[] expectedSuggestions) {
        SoftAssert soft = new SoftAssert();

        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersArray(
                        YandexSpellerApi.with().texts(texts).language(lang).options(options).callApi());

        if (options == DEFAULT) {
            checkResponseStrictly(texts, expectedSuggestions, soft, answers);
        } else {
            checkResponseIsEmpty(texts, soft, answers);
        }
    }

    //There is bug - answers when DEFAULT option is activated for EN lang haven't corrections for wrong words with digits
    @Test(description = "Check that service work correct with digits with DEFAULT option and with IGNORE_DIGITS option for all supported languages",
            dataProvider = "alphaNumericStringsDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkWordsWithDigits(String[] texts, Language lang, Options options) {
        SoftAssert soft = new SoftAssert();

        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersArray(
                        YandexSpellerApi.with().texts(texts).language(lang).options(options).callApi());

        if (options == DEFAULT) {
            checkResponseIsNotEmpty(texts, soft, answers);
        } else {
            checkResponseIsEmpty(texts, soft, answers);
        }
    }

    //There is bug - answers when DEFAULT options is activated for all lang haven't corrections for URLs
    @Test(description = "Check that service work correct with URLs DEFAULT option and with IGNORE_URLS option for all supported languages",
            dataProvider = "URLDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkWordsWithURLs(String[] texts, Language lang, Options options) {
        SoftAssert soft = new SoftAssert();

        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersArray(
                        YandexSpellerApi.with().texts(texts).language(lang).options(options).callApi());

        if (options == DEFAULT) {
            checkResponseIsNotEmpty(texts, soft, answers);
        } else {
            checkResponseIsEmpty(texts, soft, answers);
        }
    }

    //There is bug - answers when DEFAULT option is activated for all lang haven't corrections for Filenames
    @Test(description = "Check that service work correct with filenames with DEFAULT option and with IGNORE_URLS option for all supported languages",
            dataProvider = "FilenamesDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkFileNames(String[] texts, Language lang, Options options) {
        SoftAssert soft = new SoftAssert();

        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersArray(
                        YandexSpellerApi.with().texts(texts).language(lang).options(options).callApi());

        if (options == DEFAULT) {
            checkResponseIsNotEmpty(texts, soft, answers);
        } else {
            checkResponseIsEmpty(texts, soft, answers);
        }
    }

    //There is bug - answers when FIND_REPEAT_WORDS option is activated for all lang haven't corrections
    @Test(description = "Check that service work correct with repeated words with DEFAULT option and with FIND_REPEAT_WORDS option for all supported languages",
            dataProvider = "repeatWordsDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkRepeatWords(String[] texts, Language lang, Options options) {
        SoftAssert soft = new SoftAssert();

        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersArray(
                        YandexSpellerApi.with().texts(texts).language(lang).options(options).callApi());

        if (options == DEFAULT) {
            checkResponseIsNotEmpty(texts, soft, answers);
        } else {
            checkResponseIsEmpty(texts, soft, answers);
        }
    }

    //There is bug - server returns "200" status code for unsupported French lang
    @Test(description = "Check that service return error code for the request with incorrect language value",
            dataProvider = "unsupportedLanguagesDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkUnsupportedLanguageFailed(String unsupportedLang) {
        RestAssured
                .given()
                .queryParams(PARAM_TEXT, "This is test")
                .param(PARAM_LANG, unsupportedLang)
                .log().all()
                .when()
                .get(YANDEX_SPELLER_API_URI_TEXTS)
                .then()
                .assertThat()
                .statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(Matchers.equalTo("SpellerService: Invalid parameter 'lang'"));
    }

    //There is bug - for UK and RU thare are suggestions for english text
    @Test(description = "Check that service doesn't check words in one lang in case if option's lang is another one",
            dataProvider = "wrongLanguageWordsDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkWrongLanguageWords(String[] texts, Language lang) {
        SoftAssert soft = new SoftAssert();

        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersArray(
                        YandexSpellerApi.with().texts(texts).language(lang).callApi());

        checkResponseIsEmpty(texts, soft, answers);
    }

    @Test(description = "Check there is no correction for correct words",
            dataProvider = "correctWordsDataProvider", dataProviderClass = DataProviders.class,
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkNoSuggestionForCorrectWords(String[] texts, Language lang) {
        SoftAssert soft = new SoftAssert();

        List<List<YandexSpellerAnswer>> answers =
                YandexSpellerApi.getYandexSpellerAnswersArray(
                        YandexSpellerApi.with().texts(texts).language(lang).callApi());

        checkResponseIsEmpty(texts, soft, answers);
    }

    @Test(description = "Check all methods for request and corresponding server responses",
            dataProviderClass = DataProviders.class, dataProvider = "methodsDataProvider",
            retryAnalyzer = utils.RetryAnalyzer.class)
    public void checkRequestWithDifferentMethods(Method requestMethod, int expectedCode, String expectedStatusLine) {
        YandexSpellerApi.with()
                .texts("This", "is", "test")
                .requestWithMethod(requestMethod)
                .callApi()
                .then()
                .assertThat()
                .statusCode(expectedCode)
                .and()
                .statusLine(expectedStatusLine);
    }
}
