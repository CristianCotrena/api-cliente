package com.api.cliente.core.data;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class DateUtils {

    public DateUtils() {

    }

    public boolean dataIso(String data) {
        if (!Pattern.compile("^[0-9]{4}-[0-9]{2}-[0-9]{2}$").matcher(data).matches()) {
            return false;
        }

        String[] partes = data.split("-");
        int ano = Integer.parseInt(partes[0]);
        int mes = Integer.parseInt(partes[1]);
        int dia = Integer.parseInt(partes[2]);

        if (ano >= 1900 && ano <= LocalDate.now().getYear() && mes >= 1 && mes <=12) {
            if ((ano % 4 == 0 && ano % 100 != 0) || (ano % 400 == 0)) {
                if (mes == 2) {
                    if (dia >= 1 && dia <= 29) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12){
                    if (dia >= 1 && dia <= 31) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (dia >= 1 && dia <= 30) {
                        return true;
                    } else {
                        return false;
                    }
                }
            } else {
                if (mes == 1 || mes == 3 || mes == 5 || mes == 7 || mes == 8 || mes == 10 || mes == 12) {
                    if (dia >= 1 && dia <= 31) {
                        return true;
                    } else {
                        return false;
                    }
                } else if (mes == 4 || mes == 6 || mes == 9 || mes == 11) {
                    if (dia >= 1 && dia <= 30) {
                        return true;
                    } else {
                        return false;
                    }
                } else {
                    if (dia >= 1 && dia <= 28) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
    }

    public int idadeMinima(String data) {

        LocalDate dataDeNascimento = LocalDate.parse(data);
        LocalDate dataAtual = LocalDate.now();
        Period diferencaEntreDatas = Period.between(dataDeNascimento, dataAtual);
        return diferencaEntreDatas.getYears();
    }
}
