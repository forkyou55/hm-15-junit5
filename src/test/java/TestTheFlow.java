import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import ru.theflow.data.TestBase;

import java.util.List;
import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selenide.$;

public class TestTheFlow  extends TestBase {
    @ValueSource(strings = {"oxxxymiron", "atl", "horus"})
    @ParameterizedTest(name = "Поиск статьи про {0} на The-Flow")
    void theflowSearchCommonTest(String testArtists) {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://the-flow.ru/");
        $("#keyword").setValue(testArtists);
        $("#searchBtn").click();
        $$(By.xpath("//div[contains(@class, 'search_article_item')]"))
                .shouldHave(CollectionCondition.size(48))
                .first()
                .shouldHave(text(testArtists));
    }

    @CsvSource(value = {
            "oxxxymiron |  Говорят, начни себя — я убил в себе империю",
            "atl | Не могу найти в себе никаких моральных сил играть концерты",
            "horus | Horus выпустит новый альбом в июне",
    }, delimiter = '|')
    @ParameterizedTest(name = "Поиск статьи про {0} с текстом {1} на The-Flow")
    void theFlowSearchArtistTest(String testArtists, String expectedResult) {
        SelenideLogger.addListener("allure", new AllureSelenide());
        open("https://the-flow.ru/");
        $("#keyword").setValue(testArtists);
        $("#searchBtn").click();
        $(".content__left_column")
                .shouldHave(Condition.text(expectedResult));

    }


    static Stream<Arguments> coronaUsaButtonsText() {
        return Stream.of(
                Arguments.of("https://www.coronausa.com", List.of("Our Cervezas")),
                Arguments.of("https://www.coronausa.com/es", List.of("Nuestras Cervezas"))
        );
    }
    @MethodSource()
    @ParameterizedTest(name = "Проверка отображения названия кнопок на Coronausa для локали {0}")
    void coronaUsaButtonsText(String url,List<String> resultbuttonsTexts) {
        open(url);
        $("#bmonth").setValue("10");
        $("#bday").setValue("31");
        $("#byear").setValue("1994").pressEnter();
        $$("#header").shouldHave(CollectionCondition.textsInAnyOrder(resultbuttonsTexts));
    }

}

