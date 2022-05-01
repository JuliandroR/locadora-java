package models;

import java.io.Serializable;


public class Veiculo implements Serializable{

    private String placa;
    private String nome;
    private String restricao;
    private int filial_localizacao;
    private double valor_locacao;
    private boolean isLocado;

    public Veiculo() {
    }

    public Veiculo(String placa, String nome, String restricao, int filial_localizacao, double valor_locacao) {
        this.placa = placa;
        this.nome = nome;
        this.restricao = restricao;
        this.filial_localizacao = filial_localizacao;
        this.valor_locacao = valor_locacao;
        this.isLocado = false;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRestricao() {
        return restricao;
    }

    public void setRestricao(String restricao) {
        this.restricao = restricao;
    }

    public int getFilial_localizacao() {
        return filial_localizacao;
    }

    public void setFilial_localizacao(int filial_localizacao) {
        this.filial_localizacao = filial_localizacao;
    }

    public double getValor_locacao() {
        return valor_locacao;
    }

    public void setValor_locacao(double valor_locacao) {
        this.valor_locacao = valor_locacao;
    }

    public boolean isIsLocado() {
        return isLocado;
    }

    public void setIsLocado(boolean isLocado) {
        this.isLocado = isLocado;
    }

    
}
