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
}