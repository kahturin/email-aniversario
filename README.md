Este projeto Java automatiza o envio de e-mails personalizados de aniversário utilizando o Outlook Web.
Ele utiliza Selenium WebDriver para interação com a interface e lê os aniversariantes de um arquivo Excel externo. 
A mensagem é carregada dinamicamente de um arquivo .txt e os e-mails são enviados com imagens inline, caso existam.

-----------------------------------------------------------------------------------------------------------------------------------------------------------
Funcionalidades:
-----------------------------------------------------------------------------------------------------------------------------------------------------------
Leitura de aniversariantes via planilha Excel externa;
Carregamento dinâmico de mensagens personalizadas;
Envio de e-mails com Selenium no Outlook Web;
Inclusão de imagem no corpo do e-mail, vinculada ao aniversariante;
Configurações externas via config.properties;

Este projeto foi desenvolvido com foco em gerar um arquivo .jar, permitindo que a automação seja executada fora do ambiente de desenvolvimento.
O build é feito via Maven, utilizando o maven-assembly-plugin para empacotar todas as dependências em um único arquivo .jar. 

-----------------------------------------------------------------------------------------------------------------------------------------------------------
⚠️ Atenção: Todos os arquivos configurados para serem editados livremente pelo usuário final e podem estar fora do .jar são:
-----------------------------------------------------------------------------------------------------------------------------------------------------------
config.properties = Este arquivo contém as configurações gerais da aplicação, como caminhos dos arquivos a seguir e do navegador (Google Chrome).

/massa/aniversariantes.xlsx = Planilha com os dados das pessoas que vão receber o e-mail de aniversário.

/massa/config.xlsx = Planilha que consta o e-mail do remetente.

/imagens/ = Pasta que contém as imagens a serem enviadas no corpo do e-mail, vinculadas aos aniversariantes.
⚠️ Atenção: Os nomes das imagens devem coincidir com o nome da planilha aniversariantes.xlsx

mensagem.txt (ou como definido em config.properties) = Arquivo de mensagem padrão que será enviada no corpo do e-mail.

