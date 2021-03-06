import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulPlanAndReplanMeeting() {

        val validUser = DataGenerator.Registration.generateValidUser("ru");
        val daysToAddForFirstMeeting = 4;
        val firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        val daysToAddForSecondMeeting = 7;
        val secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL +"A",Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(withText("Запланировать")).click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id= 'success-notification'] .notification__content").shouldHave(text(firstMeetingDate)).shouldBe(Condition.visible, Duration.ofSeconds(20));
        open("http://localhost:9999");
        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL +"A",Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(secondMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $$("button").find(exactText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .button__text").click();
        $("[data-test-id= 'success-notification'] .notification__content").shouldHave(text(secondMeetingDate)).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    void shouldAlertIfEmptyPlaceTest() {
        val validUser = DataGenerator.Registration.generateValidUser("ru");
        val meetingDate = DataGenerator.generateDate(5);

        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL +"A",Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(meetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfWrongPlaceEnteredTest() {
        val validUser = DataGenerator.Registration.generateValidUser("ru");
        val invalidUser = DataGenerator.Registration.generateInvalidUser("en");
        val meetingDate = DataGenerator.generateDate(5);

        $("[data-test-id='city'] input").setValue(invalidUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL +"A",Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(meetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfEmptyNameTest() {
        val validUser = DataGenerator.Registration.generateValidUser("ru");
        val meetingDate = DataGenerator.generateDate(5);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL +"A",Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(meetingDate);
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfWrongNameEnteredTest() {
        val validUser = DataGenerator.Registration.generateValidUser("ru");
        val invalidUser = DataGenerator.Registration.generateInvalidUser("en");
        val meetingDate = DataGenerator.generateDate(5);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL +"A",Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(meetingDate);
        $("[data-test-id='name'] input").setValue(invalidUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfEmptyPhoneTest() {
        val validUser = DataGenerator.Registration.generateValidUser("ru");
        val meetingDate = DataGenerator.generateDate(5);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL +"A",Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(meetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfWrongPhoneEnteredTest() {
        val validUser = DataGenerator.Registration.generateValidUser("ru");
        val invalidUser = DataGenerator.Registration.generateInvalidUser("en");
        val meetingDate = DataGenerator.generateDate(5);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL +"A",Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(meetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(invalidUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfNoAgreement() {
        val validUser = DataGenerator.Registration.generateValidUser("ru");
        val meetingDate = DataGenerator.generateDate(5);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='date'] input").sendKeys(Keys.CONTROL +"A",Keys.DELETE);
        $("[data-test-id='date'] input").sendKeys(meetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".button").click();
        $("label.input_invalid[data-test-id='agreement']").shouldBe(Condition.visible);;
    }
}
