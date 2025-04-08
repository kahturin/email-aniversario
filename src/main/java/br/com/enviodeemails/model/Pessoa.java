package br.com.enviodeemails.model;

public class Pessoa {
    private String nome;
    private String email;

    public Pessoa(String nome, String email){
        this.nome = nome;
        this.email = email;
    }

    public String getNome(){
        return nome;
    }

    public String getEmail(){
        return email;
    }
<<<<<<< HEAD

    public String getCaminhoImagem() {
        return "anexos/" + nome.toLowerCase().replace(" ", "_") + ".png";
    }
=======
>>>>>>> c74d7b312cd6d2fa3b2839710ca30bb1bf60ca98
}