package hotel;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.*;

import hotel.usuario.controller.usuarioController;
import hotel.usuario.model.usuario;

public class cadastroBasicoTest {
    @Test
    public static void testCadastrarCliente() throws IOException{
        usuario Usuario = new usuario(" teste_"," teste_"," teste_"," teste_"," teste_"," teste_", false);
        usuarioController.criarUsuario(Usuario);
        String target = usuarioController.visualizarUsuario(" teste_");
        assertEquals("usuário encontrado:  teste_",target);
    }
}