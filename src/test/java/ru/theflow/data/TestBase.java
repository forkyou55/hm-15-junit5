package ru.theflow.data;

import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;

public class TestBase {
    @AfterEach
    void closeBrowser() {
        Selenide.closeWebDriver();
    }
}
