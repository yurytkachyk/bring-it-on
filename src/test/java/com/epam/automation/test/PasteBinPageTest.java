package com.epam.automation.test;

import com.epam.automation.page.PasteBinPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PasteBinPageTest {

    public static final String CODE_TEXT = "git config --global user.name  \"New Sheriff in Town\"\n" +
            "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
            "git push origin master --force";
    public static final String PASTE_NAME = "how to gain dominance among developers";
    public static final String BASH_NAME = "Bash";
    public static final String REGEX = "\n";

    private WebDriver driver;
    private PasteBinPage page;

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        page = new PasteBinPage(driver);
    }

    @Test
    public void testCreateNewPaste_shouldHaveEqualTitle_whenCreated() {
        final String currentTitle = page.openPage()
                .writeCode(CODE_TEXT)
                .showSyntaxHighlightingList()
                .selectSyntaxHighlighting()
                .showPasteExpirationList()
                .selectPasteExpiration()
                .enterPasteName(PASTE_NAME)
                .createNewPaste()
                .checkTitleMatch(PASTE_NAME);

        assertTrue(currentTitle.contains(PASTE_NAME));
    }

    @Test
    public void testCreateNewPaste_shouldHaveBashSyntax_whenCreated() {
        final String bash = page.openPage()
                .writeCode(CODE_TEXT)
                .showSyntaxHighlightingList()
                .selectSyntaxHighlighting()
                .showPasteExpirationList()
                .selectPasteExpiration()
                .enterPasteName(PASTE_NAME)
                .createNewPaste()
                .checkBushPresence();

        assertEquals(bash, BASH_NAME);
    }

    @Test
    public void testCreateNewPaste_shouldHaveEqualCode_whenCreated() {
        final List<String> codeLines = page.openPage()
                .writeCode(CODE_TEXT)
                .showSyntaxHighlightingList()
                .selectSyntaxHighlighting()
                .showPasteExpirationList()
                .selectPasteExpiration()
                .enterPasteName(PASTE_NAME)
                .createNewPaste()
                .checkCodeMatch();

        assertEquals(codeLines, Arrays.stream(CODE_TEXT.split(REGEX)).collect(Collectors.toList()));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}