package com.api.cliente.services.v1;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class DeletarClienteServiceTest {

    // Classe Cliente para simular um cliente
    class Cliente {
        private String nome;
        private int status;

        public Cliente(String nome, int status) {
            this.nome = nome;
            this.status = status;
        }

        public int getStatus() {
            return status;
        }
    }

    // Método para testar cliente ativo
    @Test
    public void testClienteAtivo() {
        Cliente cliente = new Cliente("ClienteAtivo", 1);


        boolean clienteAtivo = (cliente.getStatus() == 1);

        assertEquals(true, clienteAtivo);
    }

    // Método para testar cliente inativo
    @Test
    public void testClienteInativo() {
        Cliente cliente = new Cliente("ClienteInativo", 0);


        boolean clienteInativo = (cliente.getStatus() == 0);

        assertEquals(true, clienteInativo);
    }
}