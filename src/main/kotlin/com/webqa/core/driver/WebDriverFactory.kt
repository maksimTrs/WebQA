package com.webqa.core.driver

import com.webqa.core.config.Configuration
import io.github.bonigarcia.wdm.WebDriverManager
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.firefox.FirefoxDriver

object WebDriverFactory {
    fun createDriver(): WebDriver {
        return when (Configuration.browser.lowercase()) {
            "chrome" -> {
                WebDriverManager.chromedriver().setup()
                ChromeDriver()
            }
            "firefox" -> {
                WebDriverManager.firefoxdriver().setup()
                FirefoxDriver()
            }
            else -> throw IllegalArgumentException("Unsupported browser: ${Configuration.browser}")
        }
    }
}
