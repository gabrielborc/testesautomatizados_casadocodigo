package br.com.casadocodigo.leilao;

import br.com.casadocodigo.dao.LeilaoDao;
import br.com.casadocodigo.utils.Carteiro;

import java.util.Calendar;
import java.util.List;

public class EncerradorDeLeilao {

    private int total = 0;
    private final LeilaoDao dao;
    private final Carteiro carteiro;

    public EncerradorDeLeilao(LeilaoDao dao, Carteiro carteiro) {
        this.dao = dao;
        this.carteiro = carteiro;
    }

    public void encerra() {
        List<Leilao> todosLeiloesCorrentes = dao.correntes();

        for (Leilao leilao : todosLeiloesCorrentes) {
            if (comecouSemanaPassada(leilao)) {
                try {
                    leilao.encerra();
                    total++;
                    dao.atualiza(leilao);
                    carteiro.envia(leilao);
                } catch (Exception e) {}
            }
        }
    }

    private boolean comecouSemanaPassada(Leilao leilao) {
        return diasEntre(leilao.getData(), Calendar.getInstance()) >= 7;
    }

    private int diasEntre(Calendar inicio, Calendar fim) {
        Calendar data = (Calendar) inicio.clone();
        int diasNoIntervalo = 0;

        while (data.before(fim)) {
            data.add(Calendar.DAY_OF_MONTH, 1);
            diasNoIntervalo++;
        }
        return diasNoIntervalo;
    }

    public int getTotalEncerrados() {
        return total;
    }

}
