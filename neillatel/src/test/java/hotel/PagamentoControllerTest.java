package hotel;

import hotel.pagamento.controller.PagamentoController;

import org.junit.*;

import java.io.IOException;
import java.util.Scanner;

import static org.junit.Assert.DoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PagamentoControllerTest {

    @Test
    public static void testRegistrarPagamento() {
        PagamentoController pagamentoController = new PagamentoController();
        Scanner scanner = new Scanner("100\nPixn");
        assertDoesNotThrow(() -> pagamentoController.registrarPagamento(scanner));
    }

    @Test
    public static void testVisualizarPagamento() {
        PagamentoController pagamentoController = new PagamentoController();
        assertDoesNotThrow(() -> pagamentoController.visualizarPagamento(1));
        assertThrows(IOException.class, () -> pagamentoController.visualizarPagamento(-1));
    }
    
    @Test
    public static void testListarPagamentos() {
        
        inserirPagamentosFicticios();

        List<Pagamento> pagamentos = null;
        try {
            pagamentos = pagamentoController.listarPagamentos();
        } catch (IOException e) {
            fail("Erro ao listar pagamentos: " + e.getMessage());
        }

        assertNotNull(pagamentos);
        assertEquals(3, pagamentos.size());

        assertEquals(1, pagamentos.get(0).getId());
        assertEquals(100.0f, pagamentos.get(0).getValor(), 0.001);
        assertEquals("Pix", pagamentos.get(0).getMetodoPagamento());
    }

    @Test
    public static void testEditarPagamento() {
        PagamentoController pagamentoController = new PagamentoController();
        Scanner scanner = new Scanner("1\n100\nPix\n");
        assertThrows(IOException.class, () -> pagamentoController.editarPagamento(scanner));
    }

    @Test
    public static void testExcluirPagamento() {
        PagamentoController pagamentoController = new PagamentoController();
        assertThrows(IOException.class, () -> pagamentoController.excluirPagamento(-1));
    }
}

