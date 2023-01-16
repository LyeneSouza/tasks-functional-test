package tasks.functional;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class TasksTest {

    public WebDriver acessarAplicacao() {
        WebDriver driver = new ChromeDriver();
        driver.navigate().to("http://localhost:8001/tasks");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

    @Test
    public void deveSalvarTaskComSucesso() {
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
    public void naoDeveSalvarTaskComDataPassada() {
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
    public void naoDeveSalvarTaskSemDescricao() {
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
    public void naoDeveSalvarTaskSemData() {
        WebDriver driver = acessarAplicacao();
        try {
            // Cicar no botao de adicionar tarefa
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
}
