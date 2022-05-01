
package models;

import java.io.Serializable;

public class Filial implements Serializable {
    private int id;
    private String nome;
    private String login;
    private String senha;

    public Filial() {
    }

    public Filial(int id, String nome, String login, String senha) {
        this.id = id;
        this.login = login;
        this.nome = nome;
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    
}

