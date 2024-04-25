package hotel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.*;

import hotel.quarto.controller.QuartoController;

public class ValidacaoDadosQuartoTest {

    @Test
    public static void testCriarQuarto() throws IOException {
        String input = "101\nTV, Ar condicionado\n2\n150.0\ntrue\n01-04-2024\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        QuartoController.inserirQuarto(scanner);
        assertTrue(QuartoController.listarQuartos().stream().anyMatch(quarto -> quarto.getNumero() == 101));
    }

    @Test
    public static void testFalhaCriarQuartoNumeroExistente() throws IOException {
        String input = "101\nTV, Ar condicionado\n2\n150.0\ntrue\n01-04-2024\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        QuartoController.inserirQuarto(scanner);
        assertFalse(QuartoController.listarQuartos().stream().anyMatch(quarto -> quarto.getNumero() == 101));
    }

    @Test
    public static void testFalhaCriarQuartoDataInvalida() throws IOException {
        String input = "102\nTV, Ar condicionado\n2\n150.0\ntrue\ndata_invalida\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        QuartoController.inserirQuarto(scanner);
        assertFalse(QuartoController.listarQuartos().stream().anyMatch(quarto -> quarto.getNumero() == 102));
    }

}
