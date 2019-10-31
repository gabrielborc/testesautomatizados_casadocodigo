package br.com.casadocodigo.dao;

import br.com.casadocodigo.pagamento.Pagamento;

public interface PagamentoDao {
    void salva(Pagamento pagamento);
}
