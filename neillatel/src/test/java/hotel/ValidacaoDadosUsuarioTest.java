package hotel;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.junit.*;

import hotel.usuario.controller.usuarioController;

public class ValidacaoDadosUsuarioTest {

    @Test
    public static void testAutenticarAdmin() throws IOException {
        String input = "admin\nadmin123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        assertTrue(usuarioController.autenticarAdmin(scanner));
    }

    @Test
    public static void testAutenticarUser() throws IOException {
        String input = "usuario\nsenha123\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        assertTrue(usuarioController.autenticarUser(scanner));
    }

    @Test
    public static void testFalhaAutenticarAdmin() throws IOException {
        String input = "admin\nsenhaerrada\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        assertFalse(usuarioController.autenticarAdmin(scanner));
    }

    @Test
    public static void testFalhaAutenticarUser() throws IOException {
        String input = "usuario\nsenhaerrada\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        Scanner scanner = new Scanner(System.in);
        assertFalse(usuarioController.autenticarUser(scanner));
    }
}

