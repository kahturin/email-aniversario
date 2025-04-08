package br.com.enviodeemails.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.*;

import java.time.Duration;

public class Utils {
    public static WebElement waitVisibility(WebDriver driver, By by) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void clicarElemento(WebDriver driver, By by) {
        waitVisibility(driver, by).click();
    }

    public static void preencherCampo(WebDriver driver, By by, String texto) {
        waitVisibility(driver, by).sendKeys(texto);
    }

    public static boolean verificarElementoExiste(WebDriver driver, By by) {
        return !driver.findElements(by).isEmpty();
    }

    public static void alternarParaUltimaAba(WebDriver driver){
        Set<String> abas = driver.getWindowHandles();
        List<String> listaAbas = new ArrayList<>(abas);
        driver.switchTo().window(listaAbas.get(listaAbas.size() - 1));
    }
}
