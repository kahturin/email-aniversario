package br.com.enviodeemails.pages;

import org.openqa.selenium.By;

public class EmailPage {
    public final By botaoEntrar = By.linkText("Entrar");
    public final By campoEmail = By.xpath("//input[@placeholder='Email, telefone ou Skype']");
    public final By escolherConta = By.xpath("//div[text()='Escolha uma conta']");
    public final By contaNTT = By.xpath("(//div[contains(@data-test-id, 'nttdata.com')])[1]");
    public final By caixaCorreioCompartilhada = By.xpath("//span[contains(text(),'qsbradesco')]");
    public final By inboxCaixaCompartilhada = By.xpath("//span[contains(text(),'Inbox')]");
    public final By corpoMensagem = By.xpath("//div[@aria-label='Corpo da mensagem, pressione Alt+F10 para sair']");
    public final By botaoEnviar = By.xpath("//button[@aria-label='Enviar']");
    public final By botaoNovoEmail = By.xpath("//span[contains(text(),'Novo email')]");
    public final By campoDestinatario = By.xpath("//div[@role='textbox']");
    public final By campoAssunto = By.xpath("//input[@placeholder='Adicionar um assunto']");

}
