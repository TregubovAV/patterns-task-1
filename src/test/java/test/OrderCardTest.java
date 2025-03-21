package test;

import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeOptions;
import pages.OrderCardPage;
import utils.DataHelper;

import static com.codeborne.selenide.Selenide.*;

public class OrderCardTest {

    @BeforeAll
        static void setup() {
        // Указываем путь к браузеру Chrome
        System.setProperty("webdriver.chrome.driver", "/opt/homebrew/bin/chromedriver");
        System.setProperty("selenide.browser", "chrome");
        System.setProperty("chrome.binary", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");

        Configuration.browser = "chrome";
        Configuration.browserSize = "1920x1080";
        Configuration.baseUrl = "http://localhost:9999";

        ChromeOptions options = new ChromeOptions();
        options.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        Configuration.browserCapabilities = options;
    }

    @Test
    void shouldReplanMeetingDate() {
        OrderCardPage orderCardPage = open("/", OrderCardPage.class);

        // Генерируем пользователя и первую дату
        var userInfo = DataHelper.generateUser();
        var firstDate = DataHelper.generateValidDate(3);
        var secondDate = DataHelper.generateValidDate(7); // дата отличается

        // 1. Заполняем и отправляем первую заявку
        orderCardPage.fillForm(userInfo, firstDate);
        orderCardPage.submit();
        orderCardPage.verifySuccess(firstDate);

        // 2. Меняем только дату и отправляем повторно
        orderCardPage.updateDateOnly(secondDate);
        orderCardPage.submit();

        // 3. Дожидаемся окна перепланирования
        orderCardPage.verifyReplanPrompt();
        orderCardPage.confirmReplan();
        orderCardPage.verifySuccess(secondDate);
    }
}