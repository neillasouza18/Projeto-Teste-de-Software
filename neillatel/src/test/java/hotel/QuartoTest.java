package hotel;

import static org.junit.Assert.assertFalse;

import java.io.IOException;

import org.junit.*;

import hotel.quarto.controller.QuartoController;

public class QuartoTest {

    @Test
    public static void testVerificarDisponibilidade() throws IOException {
        // Simula o número do quarto que se deseja verificar a disponibilidade
        int numeroQuarto = 101;
        // Caso o quarto com o número especificado não venha a existir no arquivo de quartos, então deve retornar false
        assertFalse(QuartoController.verificarDisponibilidade(numeroQuarto));
    }
}
