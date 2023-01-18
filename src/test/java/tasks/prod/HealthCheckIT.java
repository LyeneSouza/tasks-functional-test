package tasks.prod;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.Assert.*;

public class HealthCheckIT {

    @Test
    public void healthCheck() throws MalformedURLException {
        // Acessar a home em producao
        DesiredCapabilities capabilities = new DesiredCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        chromeOptions.merge(capabilities);
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.252:4444/wd/hub"), capabilities);

        try {
            driver.navigate().to("http://192.168.0.252:9999/tasks");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Checar se o build esta presente na tela
            String version = driver.findElement(By.id("version")).getText();
            assertTrue(version.startsWith("build"));
        } finally {
            // Fechar o browser
            driver.quit();
        }
    }
}
