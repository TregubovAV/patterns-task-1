package pages;

import com.codeborne.selenide.SelenideElement;
import utils.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import org.openqa.selenium.Keys;

public class OrderCardPage {

    private final SelenideElement cityInput = $("[data-test-id=city] input");
    private final SelenideElement dateInput = $("[data-test-id=date] input");
    private final SelenideElement nameInput = $("[data-test-id=name] input");
    private final SelenideElement phoneInput = $("[data-test-id=phone] input");
    private final SelenideElement checkbox = $("[data-test-id=agreement]");
    private final SelenideElement submitButton = $x("//button[.//span[contains(text(),'Запланировать')]]");
    private final SelenideElement successMessage = $("[data-test-id=success-notification]");
    private final SelenideElement replanMessage = $("[data-test-id=replan-notification]");
    private final SelenideElement replanButton = $x("//button[.//span[contains(text(),'Перепланировать')]]");
    private final SelenideElement closeSuccessModalButton = successMessage.$("button");

    public void fillForm(DataHelper.UserInfo user, String date) {
        cityInput.click();
        cityInput.sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        cityInput.setValue(user.getCity());

        dateInput.doubleClick().setValue(date);
        nameInput.setValue(user.getFullName());
        phoneInput.setValue(user.getPhone());
        checkbox.click();
    }

    public void changeDateOnly(String newDate) {
        closeSuccessModal();
        dateInput.doubleClick().sendKeys(Keys.chord(Keys.CONTROL, "a"), Keys.BACK_SPACE);
        dateInput.setValue(newDate);
    }

    public void updateDateOnly(String date) {
        changeDateOnly(date);
    }

    public void closeSuccessModal() {
        if (closeSuccessModalButton.isDisplayed()) {
            closeSuccessModalButton.click();
        }
    }

    public void submit() {
        submitButton.click();
    }

    public void verifySuccess(String expectedDate) {
        successMessage.shouldHave(text("Встреча успешно запланирована на " + expectedDate), Duration.ofSeconds(1));
    }

    public void verifyReplanPrompt() {
        replanMessage.shouldBe(visible, Duration.ofSeconds(1));
    }

    public void confirmReplan() {
        if (!replanButton.exists() || !replanButton.isDisplayed() || !replanButton.isEnabled()) {
            return;
        }
        replanButton.scrollTo();
        replanButton.click();
    }
}