package tasks.functional;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class TasksTest {

    public WebDriver acessarAplicacao() throws MalformedURLException {
        //WebDriver driver = new ChromeDriver();

        DesiredCapabilities capabilities = new DesiredCapabilities();
        ChromeOptions chromeOptions = new ChromeOptions();
        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
        chromeOptions.merge(capabilities);
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.252:4444/wd/hub"), capabilities);

        driver.navigate().to("http://192.168.0.252:8001/tasks"); // passar o ip ao inves do localhost quando usar o grid
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    @Test
    public void deveSalvarTaskComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();

        // Cicar no botao de adicionar tarefa
        driver.findElement(By.id("addTodo")).click();

        // Escrever a descricao da tarefa
        driver.findElement(By.id("task")).sendKeys("Tarefa 1");

        // Escrever a data
        driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

        // Clicar no botao de salvar
        driver.findElement(By.id("saveButton")).click();

        // Validar mensagem de sucesso
        assertEquals("Success!", driver.findElement(By.id("message")).getText());

        // Fechar o browser
        driver.quit();
    }

    @Test
    public void naoDeveSalvarTaskComDataPassada() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            // Cicar no botao de adicionar tarefa
            driver.findElement(By.id("addTodo")).click();

            // Escrever a descricao da tarefa
            driver.findElement(By.id("task")).sendKeys("Erro");

            // Escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2020");

            // Clicar no botao de salvar
            driver.findElement(By.id("saveButton")).click();

            // Validar mensagem de sucesso
            assertEquals("Due date must not be in past", driver.findElement(By.id("message")).getText());
        } finally {
            // Fechar o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTaskSemDescricao() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            // Cicar no botao de adicionar tarefa
            driver.findElement(By.id("addTodo")).click();

            // Escrever a data
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");

            // Clicar no botao de salvar
            driver.findElement(By.id("saveButton")).click();

            // Validar mensagem de sucesso
            assertEquals("Fill the task description", driver.findElement(By.id("message")).getText());
        } finally {
            // Fechar o browser
            driver.quit();
        }
    }

    @Test
    public void naoDeveSalvarTaskSemData() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            // Clicar no botao de adicionar tarefa
            driver.findElement(By.id("addTodo")).click();

            // Escrever a descricao da tarefa
            driver.findElement(By.id("task")).sendKeys("Erro");

            // Clicar no botao de salvar
            driver.findElement(By.id("saveButton")).click();

            // Validar mensagem de sucesso
            assertEquals("Fill the due date", driver.findElement(By.id("message")).getText());
        } finally {
            // Fechar o browser
            driver.quit();
        }
    }

    @Test
    public void deveRemoverTaskComSucesso() throws MalformedURLException {
        WebDriver driver = acessarAplicacao();
        try {
            // Inserindo uma tarefa para poder remover
            driver.findElement(By.id("addTodo")).click();
            driver.findElement(By.id("task")).sendKeys("Tarefa 1");
            driver.findElement(By.id("dueDate")).sendKeys("10/10/2030");
            driver.findElement(By.id("saveButton")).click();
            assertEquals("Success!", driver.findElement(By.id("message")).getText());

            // Clicar no botao de remover tarefa
            driver.findElement(By.xpath("//a[@class='btn btn-outline-danger btn-sm']")).click();

            // Validar mensagem de sucesso
            assertEquals("Success!", driver.findElement(By.id("message")).getText());
        } finally {
            // Fechar o browser
            driver.quit();
        }
    }
}
