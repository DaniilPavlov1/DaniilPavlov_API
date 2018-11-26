package utils;

import beans.YandexSpellerAnswer;
import org.testng.asserts.SoftAssert;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class ResponseChecker {

    public static void checkResponseStrictly(String[] texts, List[] expectedSuggestions, SoftAssert soft, List<List<YandexSpellerAnswer>> answers) {
        //Assert that there are correct number of suggestions received in response
        assertThat(answers.size(), equalTo(texts.length));

        //Assert that suggestion are expected
        for (int i = 0; i < texts.length; i++) {
            //Check that current response array item is not empty
            if (!answers.get(i).isEmpty()) {
                soft.assertEquals(answers.get(i).get(0).s, expectedSuggestions[i], "Proposed suggestion is not expected:");
            } else {
                soft.assertFalse(answers.get(i).isEmpty(), "Received response is empty:");
            }
        }

        soft.assertAll();
    }

    public static void checkResponseSoftly(String[] texts, List[] expectedSuggestions, SoftAssert soft, List<List<YandexSpellerAnswer>> answers) {
        //Assert that there are correct number of suggestions received in response
        assertThat(answers.size(), equalTo(texts.length));

        //Assert that suggestion are expected
        for (int i = 0; i < texts.length; i++) {
            //Check that current response array item is not empty
            if (!answers.get(i).isEmpty()) {
                for (Object word : expectedSuggestions[i]) {
                    soft.assertTrue(answers.get(i).get(0).s.contains(word.toString()), "Received response doesn't contains such word");
                }
            } else {
                soft.assertFalse(answers.get(i).isEmpty(), "Received response is empty:");
            }
        }

        soft.assertAll();
    }

    public static void checkResponseIsEmpty(String[] texts, SoftAssert soft, List<List<YandexSpellerAnswer>> answers) {
        //Assert that there are correct number of answers received in response
        assertThat(answers.size(), equalTo(texts.length));

        //Assert that suggestion are expected
        for (int i = 0; i < texts.length; i++) {
            //Check that current response array item is empty
            soft.assertTrue(answers.get(i).isEmpty(), "Received response is empty for correct words:");
        }
        soft.assertAll();
    }

    public static void checkResponseIsNotEmpty(String[] texts, SoftAssert soft, List<List<YandexSpellerAnswer>> answers) {
        //Assert that there are correct number of answers received in response
        assertThat(answers.size(), equalTo(texts.length));

        //Assert that suggestion are expected
        for (int i = 0; i < texts.length; i++) {
            //Check that current response array item is not empty
            soft.assertFalse(answers.get(i).isEmpty(), "Received response is empty for correct words:");
        }
        soft.assertAll();
    }
}
