package main.java.br.com.casadocodigo.dominio;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Leilao {

    private String nome;
    private List<Lance> lances = new ArrayList<>();
    private boolean encerrado;
    private Calendar data;

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

    public boolean isEncerrado() {
        return encerrado;
    }

    public Calendar getData() {
        return data;
    }

    public void setData(Calendar data) {
        this.data = data;
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

    public void encerra() {
        encerrado = true;
    }
}

