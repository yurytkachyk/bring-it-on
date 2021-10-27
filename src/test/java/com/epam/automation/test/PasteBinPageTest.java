package com.epam.automation.test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.epam.automation.page.PasteBinPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class PasteBinPageTest {

    private static final String CODE_TEXT = "git config --global user.name  \"New Sheriff in Town\"\n" +
            "git reset $(git commit-tree HEAD^{tree} -m \"Legacy code\")\n" +
            "git push origin master --force";
    private static final String PASTE_NAME = "how to gain dominance among developers";
    private static final String BASH_NAME = "Bash";
    private static final String REGEX = "\n";
    private static final String TEST_PASSED_MESSAGE = "Test passed";
    private static final String TEST_FAILED_MESSAGE = "Test failed";
    private static final String TEST_SKIPPED_MESSAGE = "Test skipped";
    public static final String BROWSER_PAGE_TITLE_MATCHES_PASTE_NAME_TITLE_TEST_NAME = "Browser page title matches 'Paste Name / Title'";
    public static final String THE_SYNTAX_IS_SUSPENDED_FOR_BASH_TEST_NAME = "The syntax is suspended for bash";
    public static final String THE_CODE_MATCHES_THE_ORIGINAL_DATA_TEST_NAME = "The code matches the original data";

    private final ExtentHtmlReporter reporter = new ExtentHtmlReporter("./report/report.html");

    private WebDriver driver;
    private PasteBinPage page;
    private ExtentReports report;
    private ExtentTest test;

    @BeforeClass
    public void setReportUp() {
        report = new ExtentReports();
        report.attachReporter(reporter);
    }

    @BeforeMethod
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        page = new PasteBinPage(driver);
    }

    @Test
    public void testCreateNewPaste_shouldHaveEqualTitle_whenCreated() {
        test = report.createTest(BROWSER_PAGE_TITLE_MATCHES_PASTE_NAME_TITLE_TEST_NAME);

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
        test = report.createTest(THE_SYNTAX_IS_SUSPENDED_FOR_BASH_TEST_NAME);

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
        test = report.createTest(THE_CODE_MATCHES_THE_ORIGINAL_DATA_TEST_NAME);

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
    public void tearDown(ITestResult result) {
        if (result.getStatus() == ITestResult.SUCCESS) {
            test.log(Status.PASS, TEST_PASSED_MESSAGE);
        } else if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, TEST_FAILED_MESSAGE);
            test.fail(result.getThrowable());
        } else {
            test.log(Status.SKIP, TEST_SKIPPED_MESSAGE);
            test.skip(result.getThrowable());
        }

        driver.quit();
    }

    @AfterClass
    public void tearReportDown() {
        report.flush();
    }

}