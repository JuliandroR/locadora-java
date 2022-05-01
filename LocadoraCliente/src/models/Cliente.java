package models;

import java.io.Serializable;

public class Cliente implements Serializable {
    private int id;
    private String nome;
    private String cpf;
    private String categoria;
    private boolean podeLocar;

    public Cliente() {
    }

    public Cliente(int id, String cpf, String nome, String categoria) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.categoria = categoria;
        this.podeLocar = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isPodeLocar() {
        return podeLocar;
    }

    public void setPodeLocar(boolean podeLocar) {
        this.podeLocar = podeLocar;
    }
    
}

