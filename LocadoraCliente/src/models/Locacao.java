package models;

import java.io.Serializable;

public class Locacao implements Serializable {
    private int id;
    private Long hora_locacao;
    private Long hora_devolucao;
    private String valor;
    private int id_cliente;
    private String placa_veiculo;
    private int id_locadora_retirada;
    private int id_locadora_dovlucao;

    public Locacao() {
    }

    public Locacao(int id, Long hora_locacao, Long hora_devolucao, String valor, int id_cliente, String placa_veiculo, int id_locadora_retirada, int id_locadora_dovlucao) {
        this.id = id;
        this.hora_locacao = hora_locacao;
        this.hora_devolucao = hora_devolucao;
        this.valor = valor;
        this.id_cliente = id_cliente;
        this.placa_veiculo = placa_veiculo;
        this.id_locadora_retirada = id_locadora_retirada;
        this.id_locadora_dovlucao = id_locadora_dovlucao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getHora_locacao() {
        return hora_locacao;
    }

    public void setHora_locacao(Long hora_locacao) {
        this.hora_locacao = hora_locacao;
    }

    public Long getHora_devolucao() {
        return hora_devolucao;
    }

    public void setHora_devolucao(Long hora_devolucao) {
        this.hora_devolucao = hora_devolucao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getPlaca_veiculo() {
        return placa_veiculo;
    }

    public void setPlaca_veiculo(String placa_veiculo) {
        this.placa_veiculo = placa_veiculo;
    }

    public int getId_locadora_retirada() {
        return id_locadora_retirada;
    }

    public void setId_locadora_retirada(int id_locadora_retirada) {
        this.id_locadora_retirada = id_locadora_retirada;
    }

    public int getId_locadora_dovlucao() {
        return id_locadora_dovlucao;
    }

    public void setId_locadora_dovlucao(int id_locadora_dovlucao) {
        this.id_locadora_dovlucao = id_locadora_dovlucao;
    }
    
    
}
