package hotel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Scanner;

import org.junit.Test;

import hotel.reserva.controller.ReservaController;

public class ReservaTest {

    @Test
    public static void testCriarReserva() throws IOException {
        String input = "01-04-2024\n10-04-2024\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        ReservaController controller = new ReservaController();
        controller.realizarReserva(scanner);
        assertTrue(controller.verificaReserva(1));
    }

    @Test
    public static void testVisualizarReserva() throws IOException {
        ReservaController controller = new ReservaController();
        assertEquals("Reserva não encontrada.", controller.visualizarReserva(1));
    }

    @Test
    public static void testCheckin() throws IOException {
        ReservaController controller = new ReservaController();
        // Assume que existe uma reserva com ID 1
        Date dataCheckin = new Date();
        controller.checkin(1, dataCheckin);

        // Verifica se a data de check-in foi atualizada corretamente
        assertEquals(dataCheckin, controller.getDataCheckin(1));
    }

    @Test
    public static void testCheckout() throws IOException {
        ReservaController controller = new ReservaController();
        // Assume que existe uma reserva com ID 1
        Date dataCheckout = new Date();
        controller.checkout(1, dataCheckout);

        // Verifica se a data de checkout foi atualizada corretamente
        assertEquals(dataCheckout, controller.getDataCheckout(1));
    }

    @Test
    public static void testCancelarReserva() throws IOException {
        ReservaController controller = new ReservaController();
        // Assume que existe uma reserva com ID 1
        controller.cancelarReserva(1);

        // Verifica se a reserva foi cancelada
        assertFalse(controller.verificaReserva(1));
    }

}