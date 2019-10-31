package br.com.casadocodigo.pagamento;

import br.com.casadocodigo.avaliador.Avaliador;
import br.com.casadocodigo.dao.LeilaoDao;
import br.com.casadocodigo.dao.PagamentoDao;
import br.com.casadocodigo.leilao.Leilao;
import br.com.casadocodigo.utils.Relogio;
import br.com.casadocodigo.utils.RelogioDoSistema;

import java.util.Calendar;
import java.util.List;

public class GeradorDePagamento {

    private final PagamentoDao pagamentos;
    private final LeilaoDao leiloes;
    private final Avaliador avaliador;
    private final Relogio relogio;

    public GeradorDePagamento(LeilaoDao leiloes,
                              PagamentoDao pagamentos,
                              Avaliador avaliador,
                              Relogio relogio) {
        this.leiloes = leiloes;
        this.pagamentos = pagamentos;
        this.avaliador = avaliador;
        this.relogio = relogio;
    }

    public GeradorDePagamento(LeilaoDao leiloes,
                              PagamentoDao pagamentos,
                              Avaliador avaliador) {
        this.leiloes = leiloes;
        this.pagamentos = pagamentos;
        this.avaliador = avaliador;
        this.relogio = new RelogioDoSistema();
    }

    public void gera() {
        List<Leilao> leiloesEncerrados = leiloes.encerrados();
        for (Leilao leilao : leiloesEncerrados) {
            avaliador.avalia(leilao);

            Pagamento novoPagamento = new Pagamento(avaliador.getMaiorLance(),
                    primeiroDiaUtil());
            pagamentos.salva(novoPagamento);
        }
    }

    private Calendar primeiroDiaUtil() {
        Calendar data = relogio.hoje();
        int diaDaSemana = data.get(Calendar.DAY_OF_WEEK);

        if (diaDaSemana == Calendar.SATURDAY) {
            data.add(Calendar.DAY_OF_MONTH, 2);
        } else if (diaDaSemana == Calendar.SUNDAY) {
            data.add(Calendar.DAY_OF_MONTH, 1);
        }

        return data;
    }


}
