package br.com.casadocodigo.leilao;

import java.util.ArrayList;
import java.util.List;

public class Leilao {

    private String nome;
    private List<Lance> lances = new ArrayList<>();

    public Leilao(String nome) {
        this.nome = nome;
    }

    public void propoe(Lance lance) {
        lances.add(lance);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Lance> getLances() {
        return lances;
    }

    public void setLances(List<Lance> lances) {
        this.lances = lances;
    }

}

