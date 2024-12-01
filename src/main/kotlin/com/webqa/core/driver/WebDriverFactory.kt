import WebDriverFactory.Browser.CHROME
import WebDriverFactory.Browser.FIREFOX
import com.webqa.core.config.Configuration.browser
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions

object WebDriverFactory {
    enum class Browser { CHROME, FIREFOX }

    private val driverThreadLocal: ThreadLocal<WebDriver> = ThreadLocal()

    fun createDriver(
        browser: Browser = getBrowserFromConfig(),
        windowSize: Dimension = Dimension(1920, 1080)
    ): WebDriver {
        val driver = when (browser) {
            CHROME -> createChromeDriver(windowSize)
            FIREFOX -> createFirefoxDriver(windowSize)
        }
        driverThreadLocal.set(driver)
        return driver
    }

    fun getDriver(): WebDriver = driverThreadLocal.get()

    fun quitDriver() {
        driverThreadLocal.get().quit()
        driverThreadLocal.remove()
    }

    private fun createChromeDriver(windowSize: Dimension): WebDriver {
        WebDriverManager.chromedriver().setup()
        return ChromeDriver(ChromeOptions().apply {
            addArguments("--start-maximized")
            addArguments("--disable-extensions")
            addArguments("--incognito")
        }).apply {
            manage().window().size = windowSize
        }
    }

    private fun createFirefoxDriver(windowSize: Dimension): WebDriver {
        WebDriverManager.firefoxdriver().setup()
        return FirefoxDriver(FirefoxOptions()).apply {
            manage().window().size = windowSize
        }
    }

    private fun getBrowserFromConfig(): Browser =
        when (browser.lowercase()) {
            "chrome" -> CHROME
            "firefox" -> FIREFOX
            else -> CHROME // Default to Chrome if unrecognized
        }
}