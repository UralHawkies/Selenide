import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardDeliveryTest {

    @BeforeEach
    void setUp() {
        Configuration.holdBrowserOpen=true;
        open("http://localhost:9999");
    }

    @Test
    void shouldSendCorrectForm() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Екатеринбург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Иванов Олег");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79128893988");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        $x("//*[@data-test-id='notification']").shouldHave(Condition.text("Успешно! Встреча успешно забронирована на " + verificationDate), Duration.ofSeconds(15));
    }

    @Test
    void shouldChooseInvalidCity() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Березники");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Иванов Олег");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79128893988");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"city\"]").getText();
        assertEquals("Доставка в выбранный город недоступна", text);
    }

    @Test
    void shouldNotChooseCity() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Иванов Олег");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79128893988");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"city\"]").getText();
        assertEquals("Поле обязательно для заполнения", text);
    }

    @Test
    void shouldNotChooseDate() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Екатеринбург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().format(DateTimeFormatter.ofPattern(""));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Иванов Олег");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79128893988");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[contains(text(),'Неверно введена дата')]").getText();
        assertEquals("Неверно введена дата", text);
    }

    @Test
    void shouldChooseNotValidName() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Екатеринбург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Ivanov Oleg");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79128893988");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"name\"]").getText();
        assertEquals("Фамилия и имя\n" + "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text);
    }

    @Test
    void shouldNotChooseName() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Екатеринбург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+79128893988");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"name\"]").getText();
        assertEquals("Фамилия и имя\n" + "Поле обязательно для заполнения", text);
    }

    @Test
    void shouldChooseNotValidPhone() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Екатеринбург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Иванов Олег");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+791288Олег");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldNotChoosePhone() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Екатеринбург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Иванов Олег");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Поле обязательно для заполнения", text);
    }

    @Test
    void shouldChoosePhoneLessThan11Numbers() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Екатеринбург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Иванов Олег");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+7912889398");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldChoosePhoneMoreThan11Numbers() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Екатеринбург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"name\"]//self::input").setValue("Иванов Олег");
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("+791288939888");
        $x("//*[@data-test-id=\"agreement\"]").click();
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"phone\"]").getText();
        assertEquals("Мобильный телефон\n" + "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text);
    }

    @Test
    void shouldNotPushCheckBox() {
        $x("//*[@data-test-id=\"city\"]//self::input").setValue("Екатеринбург");
        $x("//*[@data-test-id=\"date\"]//self::input").doubleClick().sendKeys(Keys.DELETE);
        String verificationDate = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        $x("//*[@data-test-id=\"date\"]//self::input").setValue(verificationDate);
        $x("//*[@data-test-id=\"phone\"]//self::input").setValue("Иванов Олег");
        $x("//input[@name=\"phone\"]").setValue("+79128893988");
        $x("//*[@class=\"button__text\"]").click();
        String text = $x("//*[@data-test-id=\"agreement\"]").getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных", text);
    }
}
