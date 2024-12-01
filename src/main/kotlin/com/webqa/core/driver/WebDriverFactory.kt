package com.webqa.core.driver

import com.webqa.core.config.Configuration
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

object WebDriverFactory {
    sealed class Browser {
        object Chrome : Browser()
        object Firefox : Browser()
    }

    fun createDriver(): WebDriver {
        return when (getBrowser()) {
            is Browser.Chrome -> {
                WebDriverManager.chromedriver().setup()
                ChromeDriver()
            }

            is Browser.Firefox -> {
                WebDriverManager.firefoxdriver().setup()
                FirefoxDriver()
            }
        }
    }

    private fun getBrowser(): Browser {
        return when (Configuration.browser.lowercase()) {
            "chrome" -> Browser.Chrome
            "firefox" -> Browser.Firefox
            else -> Browser.Chrome // Default to Chrome if unrecognized
        }
    }
}