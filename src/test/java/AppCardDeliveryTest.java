import com.codeborne.selenide.Condition;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class AppCardDeliveryTest {

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSuccessfulPlanAndReplanMeeting() {
        val validUser = DataGenerator.Registration.generateUser("ru");
        val daysToAddForFirstMeeting = 4;
        val firstMeetingDate = DataGenerator.generateDate(daysToAddForFirstMeeting);
        val daysToAddForSecondMeeting = 7;
        val secondMeetingDate = DataGenerator.generateDate(daysToAddForSecondMeeting);

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='data'] input").setValue(firstMeetingDate);
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(20));
        $("[data-test-id='data'] input").setValue(secondMeetingDate);
        $(withText("Необходимо подтверждение")).shouldBe(Condition.visible, Duration.ofSeconds(20));
        $(withText("Перепланировать")).click();
        $(withText("Успешно")).shouldBe(Condition.visible, Duration.ofSeconds(20));
    }

    @Test
    void shouldAlertIfEmptyPlaceTest() {
        val validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfWrongPlaceEnteredTest() {
        val validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id='city'] input").setValue("Вена");
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Доставка в выбранный город недоступна")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfEmptyNameTest() {
        val validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfWrongNameEnteredTest() {
        val validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='name'] input").setValue("Vera Semenova");
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы")).shouldBe(Condition.visible);
    }

    @Test
    void shouldAlertIfEmptyPhoneTest() {
        val validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $(".checkbox").click();
        $(".button").click();
        $(withText("Поле обязательно для заполнения")).shouldBe(Condition.visible);
    }

//    @Test
//    void shouldAlertIfWrongPhoneEnteredTest() {
//        val validUser = DataGenerator.Registration.generateUser("ru");
//
//        $("[data-test-id='city'] input").setValue(validUser.getCity());
//        $("[data-test-id='name'] input").setValue(validUser.getName());
//        $("[data-test-id='phone'] input").setValue("89219999999");
//        $(".checkbox").click();
//        $(".button").click();
//        $(withText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.")).shouldBe(Condition.visible);
//    }

    @Test
    void shouldAlertIfNoAgreement() {
        val validUser = DataGenerator.Registration.generateUser("ru");

        $("[data-test-id='city'] input").setValue(validUser.getCity());
        $("[data-test-id='name'] input").setValue(validUser.getName());
        $("[data-test-id='phone'] input").setValue(validUser.getPhone());
        $(".button").click();
        $("label.input_invalid[data-test-id='agreement']");
    }

}
