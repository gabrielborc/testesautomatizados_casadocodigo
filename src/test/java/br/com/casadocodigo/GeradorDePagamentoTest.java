package test.java.br.com.casadocodigo;

import main.java.br.com.casadocodigo.dominio.Pagamento;
import main.java.br.com.casadocodigo.servico.Avaliador;
import main.java.br.com.casadocodigo.dao.LeilaoDao;
import main.java.br.com.casadocodigo.dao.PagamentoDao;
import main.java.br.com.casadocodigo.servico.CriadorDeLeilao;
import main.java.br.com.casadocodigo.dominio.Leilao;
import main.java.br.com.casadocodigo.servico.GeradorDePagamento;
import main.java.br.com.casadocodigo.dominio.Usuario;
import main.java.br.com.casadocodigo.servico.Relogio;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Calendar;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class GeradorDePagamentoTest {

    @Test
    public void deveGerarPagamentoParaUmLeilaoEncerrado() {
        LeilaoDao leiloes = mock(LeilaoDao.class);
        PagamentoDao pagamentos = mock(PagamentoDao.class);
        Avaliador avaliador = mock(Avaliador.class);

        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation")
                .lance(new Usuario("José da Silva"), 2000.0)
                .lance(new Usuario("Maria Pereira"), 2500.0)
                .constroi();

        when(leiloes.encerrados())
                .thenReturn(Arrays.asList(leilao));
        when(avaliador.getMaiorLance())
                .thenReturn(2500.0);

        GeradorDePagamento gerador =
            new GeradorDePagamento(leiloes, pagamentos, avaliador);

        gerador.gera();

        ArgumentCaptor<Pagamento> argumento =
                ArgumentCaptor.forClass(Pagamento.class);

        verify(pagamentos).salva(argumento.capture());

        Pagamento pagamentoGerado = argumento.getValue();
        assertEquals(2500.0, pagamentoGerado.getValor(), 0.00001);
    }

    @Test
    public void deveEmpurrarParaOProximoDiaUtil() {
        LeilaoDao leiloes = mock(LeilaoDao.class);
        PagamentoDao pagamentos = mock(PagamentoDao.class);
        Relogio relogio = mock(Relogio.class);

        Calendar sabado = Calendar.getInstance();
        sabado.set(2012, Calendar.APRIL, 7);

        when(relogio.hoje()).thenReturn(sabado);

        Leilao leilao = new CriadorDeLeilao()
                .para("Playstation")
                .lance(new Usuario("José da Silva"), 2000.0)
                .lance(new Usuario("Maria Pereira"), 2500.0)
                .constroi();

        when(leiloes.encerrados())
                .thenReturn(Arrays.asList(leilao));

        GeradorDePagamento gerador =
                new GeradorDePagamento(leiloes, pagamentos, new Avaliador(), relogio);
        gerador.gera();

        ArgumentCaptor<Pagamento> argumento =
                ArgumentCaptor.forClass(Pagamento.class);
        verify(pagamentos).salva(argumento.capture());
        Pagamento pagamentoGerado = argumento.getValue();

        assertEquals(Calendar.MONDAY,
                pagamentoGerado.getData().get(Calendar.DAY_OF_WEEK));
        assertEquals(9,
                pagamentoGerado.getData().get(Calendar.DAY_OF_MONTH));
    }

}
