package main.java.br.com.casadocodigo.servico;

import java.util.Calendar;

public class RelogioDoSistema implements Relogio {
    @Override
    public Calendar hoje() {
        return Calendar.getInstance();
    }
}
