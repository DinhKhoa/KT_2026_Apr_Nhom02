package common;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TestListener implements ITestListener, IMethodInterceptor {
    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        System.out.println(">>> Intercepting methods. Count: " + methods.size());

        methods.sort((m1, m2) -> m1.getMethod().getMethodName().compareTo(m2.getMethod().getMethodName()));

        System.out.println(">>> Global Test Order (Alphabetical):");
        methods.forEach(m -> System.out.println("  - " + m.getMethod().getMethodName()));

        return new java.util.ArrayList<>(methods);
    }

    @Override
    public void onStart(ITestContext context) {
        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportPath = System.getProperty("user.dir") + "/test-output/ExtentReport_" + timestamp + ".html";

        ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
        spark.config().setTheme(Theme.STANDARD);
        spark.config().setDocumentTitle("Railway Test Report");
        spark.config().setReportName("Functional Test Execution");

        String neobrutalistCss = ":root { --bg: #f5f2f0; --card-bg: #fdfaf3; --text: #000000; --border: #000000; --shadow: #000000; --accent: #FFD93D; --pass: #2ECC71; --fail: #FF4757; }"
                +
                ".app, .vcontainer, .main-content, .test-wrapper, .test-content, .test-content-detail, .test-list-wrapper, .container-fluid { background-color: var(--bg) !important; color: var(--text) !important; }"
                +
                ".card, .test-item, th, td, .side-nav, .header { background-color: var(--card-bg) !important; color: var(--text) !important; border-color: var(--border) !important; }"
                +
                ".side-nav { border-right: 4px solid var(--border) !important; }" +
                ".side-nav .nav-link i, .side-nav .nav-link .title, .side-nav .nav-link, .header .vheader .nav-item .nav-link, .header .vheader .nav-item .search-input { color: var(--text) !important; font-weight: 800 !important; }"
                +
                ".header { border-bottom: 4px solid var(--border) !important; }" +
                ".card { border: 4px solid var(--border) !important; border-radius: 0px !important; box-shadow: 8px 8px 0px var(--shadow) !important; margin-bottom: 30px !important; transition: all 0.2s ease !important; }"
                +
                ".card:hover { transform: translate(-4px, -4px) !important; box-shadow: 12px 12px 0px var(--shadow) !important; }"
                +
                ".badge-success { background-color: var(--pass) !important; color: #000 !important; border: 2px solid #000 !important; font-weight: 800 !important; }"
                +
                ".badge-danger { background-color: var(--fail) !important; color: #000 !important; border: 2px solid #000 !important; font-weight: 800 !important; }"
                +
                ".test-wrapper .test-list .test-item { border: 3px solid var(--border) !important; margin-bottom: 15px !important; box-shadow: 5px 5px 0px var(--shadow) !important; transition: 0.2s !important; }"
                +
                ".test-wrapper .test-list .test-item:hover { transform: translate(-2px, -2px); box-shadow: 7px 7px 0px var(--shadow) !important; }"
                +
                ".test-list-tools { background-color: var(--card-bg) !important; border-bottom: 2px solid var(--border) !important; }"
                +
                ".test-list-tools * { color: var(--text) !important; font-weight: 500 !important; }"
                +
                ".detail-body img { border: 4px solid var(--border) !important; box-shadow: 6px 6px 0px var(--shadow) !important; margin-top: 15px !important; border-radius: 0px !important; transition: 0.2s !important; cursor: pointer !important; }"
                +
                ".detail-body img:hover { transform: scale(1.02); }"
                +
                ".test-content-detail .card-title { border-bottom: 3px solid var(--border) !important; color: var(--text) !important; }"
                +
                "th, td, .table, .table * { border: 2px solid var(--border) !important; color: var(--text) !important; }";

        spark.config().setCss(neobrutalistCss);

        extent = new ExtentReports();
        extent.attachReporter(spark);
        extent.setSystemInfo("Platform", "Windows");
        extent.setSystemInfo("Author", "Nhom02");
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
        System.out.println("Starting test: " + result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test Passed");
        System.out.println("Test Passed: " + result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        System.out.println(">>> Processing " + testName + " as FAILURE (Red)");
        test.get().log(Status.FAIL, "Test Failed: " + result.getThrowable());

        WebDriver driver = Constant.WEBDRIVER;
        if (driver != null) {
            try {
                String screenshotPath = captureScreenshot(result.getName());
                test.get().addScreenCaptureFromPath(screenshotPath);

                Reporter.log("<br><img src='" + screenshotPath + "' height='400' width='600'/><br>");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test Skipped");
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    private String captureScreenshot(String testName) throws IOException {
        WebDriver driver = Constant.WEBDRIVER;
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        File outDir = new File(System.getProperty("user.dir") + "/test-output/screenshots");
        if (!outDir.exists())
            outDir.mkdirs();

        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = outDir.getAbsolutePath() + "/" + fileName;

        File destFile = new File(filePath);
        Files.copy(scrFile.toPath(), destFile.toPath());

        return "screenshots/" + fileName;
    }
}
