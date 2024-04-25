package hotel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.*;

import hotel.quarto.controller.QuartoController;
import hotel.quarto.model.Quarto;

public class cadastroBasicoQuartoTest {

    @Test
    public static void testCadastrarQuarto() throws IOException {
        String inputData = "101\nTV, Wi-Fi\n2\n150.0\ntrue\n20-04-2024\n"; 
        InputStream in = new ByteArrayInputStream(inputData.getBytes());
        Scanner scanner = new Scanner(in);
        QuartoController.inserirQuarto(scanner);
        Quarto quarto = QuartoController.listarQuartos().get(0);
        assertNotNull(quarto);
        assertEquals(101, quarto.getNumero());
        assertEquals("TV, Wi-Fi", quarto.getComodidades());
        assertEquals(2, quarto.getCapacidade());
        assertEquals(150.0f, quarto.getTarifa(), 0.001);
        assertTrue(quarto.getDisponibilidade());
        assertEquals("20-04-2024", QuartoController.sdf.format(quarto.getData()));
    }

    @Test
    public static void testExcluirQuarto() throws IOException {
        Quarto quarto = new Quarto(101, "TV, Wi-Fi", 2, 150.0f, true, null);
        QuartoController.criarQuarto(quarto);
        QuartoController.excluirQuarto(quarto.getNumero());
        assertEquals(0, QuartoController.listarQuartos().size());
    }

}
