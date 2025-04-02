package org.example;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.NoSuchElementException;

public class OutlookAutomation {
    public static void main(String[] args) {
        String caminhoPlanilha = "caminho/para/aniversariantes.xlsx";
        String emailOutlook = "kalvelim@emeal.nttdata.com";


        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver-win64\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("user-data-dir=C:\\Users\\Karina\\AppData\\Local\\Google\\Chrome\\User Data");
        options.addArguments("--profile-directory=Default");
        options.addArguments("--remote-allow-origins=*");
        System.out.println("passou aqui 1");

        options.setBinary("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe");
        System.out.println("passou aqui 2");
        System.setProperty("webdriver.chrome.verboseLogging", "true");
        System.out.println("passou aqui 3");
        WebDriver driver = new ChromeDriver(options);
        System.out.println("passou aqui 4");
        driver.get("https://outlook.live.com/");
        System.out.println("passou aqui 5");
        driver.quit();

        try {

            List<Map<String, String>> aniversariantes = verificarAniversariantes(caminhoPlanilha);

            if (aniversariantes.isEmpty()) {
                System.out.println("Nenhum aniversariante hoje.");
                driver.quit();
                return;
            }


            driver.get("https://outlook.live.com/");
            Thread.sleep(5000);


            if (driver.findElements(By.linkText("Entrar")).size() > 0) {
                driver.findElement(By.linkText("Entrar")).click();
                Thread.sleep(3000);
                driver.findElement(By.name("loginfmt")).sendKeys(emailOutlook, Keys.ENTER);
                Thread.sleep(3000);
                driver.findElement(By.name("passwd")).sendKeys("sua-senha", Keys.ENTER);
                Thread.sleep(5000);


                try {
                    driver.findElement(By.id("idSIButton9")).click();
                    Thread.sleep(3000);
                } catch (NoSuchElementException e) {

                }
            }


            for (Map<String, String> pessoa : aniversariantes) {
                String nome = pessoa.get("Nome");
                String email = pessoa.get("Email");

                // Clicar em "Novo e-mail"
                driver.findElement(By.xpath("//button[contains(text(), 'Novo e-mail')]")).click();
                Thread.sleep(3000);

                // Digitar o destinatário
                driver.findElement(By.xpath("//input[@role='combobox']")).sendKeys(email, Keys.ENTER);
                Thread.sleep(2000);

                // Digitar o assunto
                driver.findElement(By.xpath("//input[@placeholder='Adicionar um assunto']")).sendKeys("Feliz Aniversário, " + nome + "!");
                Thread.sleep(2000);

                // Digitar o corpo do e-mail
                String mensagem = "Olá, " + nome + "!\n\nParabéns pelo seu aniversário! Que seu dia seja incrível! 🎉";
                driver.findElement(By.xpath("//div[@role='textbox']")).sendKeys(mensagem);
                Thread.sleep(2000);

                // Clicar em "Enviar"
                driver.findElement(By.xpath("//button[contains(text(), 'Enviar')]")).click();
                Thread.sleep(3000);

                System.out.println("E-mail enviado para " + nome + " (" + email + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fechar o navegador
            driver.quit();
        }
    }

    public static List<Map<String, String>> verificarAniversariantes(String caminhoArquivo) {
        List<Map<String, String>> aniversariantes = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM");

        String hoje = sdf.format(new Date());

        try (FileInputStream file = new FileInputStream(new File(caminhoArquivo));
             Workbook workbook = new XSSFWorkbook(file)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (Row row : sheet) {
                if (row.getRowNum() == 0) continue;

                String nome = row.getCell(0).getStringCellValue();
                String email = row.getCell(1).getStringCellValue();
                String dataNascimento = new SimpleDateFormat("dd/MM")
                        .format(row.getCell(2).getDateCellValue());

                if (hoje.equals(dataNascimento)) {
                    Map<String, String> pessoa = new HashMap<>();
                    pessoa.put("Nome", nome);
                    pessoa.put("Email", email);
                    aniversariantes.add(pessoa);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return aniversariantes;
    }
}
