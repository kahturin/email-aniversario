package br.com.enviodeemails.runner;

import br.com.enviodeemails.utils.ExcelReader;
import br.com.enviodeemails.model.Pessoa;
import br.com.enviodeemails.core.DriverFactory;
import br.com.enviodeemails.pages.EmailPage;
import br.com.enviodeemails.utils.Utils;
import org.openqa.selenium.*;
import java.util.*;

public class OutlookAutomation {
    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getDriver();
        EmailPage element = new EmailPage();

        String emailOutlook = ExcelReader.getEmailOutlook();
        if (emailOutlook == null || emailOutlook.isEmpty()) {
            System.out.println("E-mail não encontrado");
            DriverFactory.fecharDriver();
            return;
        }

        try {
            List<Pessoa> aniversariantes = ExcelReader.getAniversariantesHoje();
            if (aniversariantes.isEmpty()) {
                System.out.println("Nenhum aniversariante hoje.");
                driver.quit();
                return;
            }

            driver.get("https://outlook.live.com/");
            System.out.println("Acessando o outlook");

            if (Utils.verificarElementoExiste(driver, element.botaoEntrar)) {
                Utils.clicarElemento(driver, element.botaoEntrar);
                System.out.println("Botão entrar clicado");

                Utils.alternarParaUltimaAba(driver);

                System.out.println("Preencher e-mail");
                Utils.preencherCampo(driver, element.campoEmail, emailOutlook + Keys.ENTER);

                Thread.sleep(4000);
                if (Utils.verificarElementoExiste(driver, element.escolherConta)) {
                    System.out.println("Selecionar conta NTT");
                    Utils.clicarElemento(driver, element.contaNTT);
                }
            }

            if (Utils.verificarElementoExiste(driver, element.inboxCaixaCompartilhada)) {
                Utils.clicarElemento(driver, element.inboxCaixaCompartilhada);
            } else {
                Utils.clicarElemento(driver, element.caixaCorreioCompartilhada);
                Utils.clicarElemento(driver, element.inboxCaixaCompartilhada);
            }

            for (Pessoa pessoa : aniversariantes) {
                System.out.println("Novo e-mail");
                Utils.clicarElemento(driver, element.botaoNovoEmail);

                System.out.println("Digitar destinatário");
                Utils.preencherCampo(driver, element.campoDestinatario, pessoa.getEmail() + Keys.ENTER);

                System.out.println("Digitar assunto");
                Utils.preencherCampo(driver, element.campoAssunto, "Feliz Aniversário, " + pessoa.getNome() + "!");

                System.out.println("Digitar corpo do e-mail");
                String mensagem = "Olá, " + pessoa.getNome() + "!\n\nParabéns pelo seu aniversário! Que seu dia seja incrível!";
                Utils.preencherCampo(driver, element.corpoMensagem, mensagem);

                System.out.println("Enviar e-mail");
                Utils.clicarElemento(driver, element.botaoEnviar);

                System.out.println("E-mail enviado para " + pessoa.getNome() + " (" + pessoa.getEmail() + ")");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DriverFactory.fecharDriver();
        }
    }
}