package br.com.casadocodigo.leilao;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Leilao {

    private String nome;
    private List<Lance> lances = new ArrayList<>();

    public Leilao(String nome) {
        this.nome = nome;
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

    public void propoe(Lance lance) {
        if (lances.isEmpty() || podeDarLance(lance.getUsuario())) {
            lances.add(lance);
        }
    }

    private Lance ultimoLanceDado() {
        return lances.get(lances.size()-1);
    }

    private int qtdDelancesDo(Usuario usuario) {
        int total = 0;
        for (Lance l : lances) {
            if (l.getUsuario().equals(usuario)) total++;
        }
        return total;
    }

    private boolean podeDarLance(Usuario usuario) {
        return !ultimoLanceDado().getUsuario().equals(usuario)
                && qtdDelancesDo(usuario) < 5;
    }

}

