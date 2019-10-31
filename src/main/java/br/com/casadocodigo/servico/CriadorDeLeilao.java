package main.java.br.com.casadocodigo.servico;

import main.java.br.com.casadocodigo.dominio.Lance;
import main.java.br.com.casadocodigo.dominio.Leilao;
import main.java.br.com.casadocodigo.dominio.Usuario;

import java.util.Calendar;

public class CriadorDeLeilao {

    private Leilao leilao;

    public CriadorDeLeilao() { }

    public CriadorDeLeilao para(String descricao) {
        this.leilao = new Leilao(descricao);
        return this;
    }

    public CriadorDeLeilao lance(Usuario usuario, double valor) {
        leilao.propoe(new Lance(usuario, valor));
        return this;
    }

    public CriadorDeLeilao naData(Calendar antiga) {
        leilao.setData(antiga);
        return this;
    }

    public Leilao constroi() {
        return leilao;
    }

}
