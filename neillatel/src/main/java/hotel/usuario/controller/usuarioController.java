package hotel.usuario.controller;
import hotel.usuario.model.usuario;

import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class usuarioController {
    private static final String FILE_PATH = "src/main/java/hotel/data/usuarios.txt";
    private static final String DELIMITER = ",";


    public static void inserirUsuario(Scanner scanner) throws IOException {

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o endereço: ");
        String endereco = scanner.nextLine();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        System.out.print("Digite o telefone: ");
        String telefone = scanner.nextLine();

        System.out.print("Digite o nome de usuário: ");
        String usuario = scanner.nextLine();

        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();


        usuario novoUsuario = new usuario(nome, endereco, email, telefone, usuario, senha, false);
        criarUsuario(novoUsuario);
        System.out.println("");
        System.out.println("--------------------------------\n");
        System.out.println("usuário adicionado com sucesso.\n");
        System.out.println("----------------------------------");
    }


    public static void criarUsuario(usuario Usuario) throws IOException {
        List<usuario> usuarios = listarUsuarios();
        for (usuario user : usuarios) {
            if (user.getUsuario().equals(Usuario.getUsuario())) {
                throw new IOException("usuário ja existe.");
            }
        }
        String userData = formatUserData(Usuario);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(userData + "\n");
        }
    }

    private static String formatUserData(usuario usuario) {
        return String.join(DELIMITER, usuario.getNome(), usuario.getEndereco(), usuario.getEmail(),
                usuario.getTelefone(), usuario.getUsuario(), usuario.getSenha(), String.valueOf(usuario.getAdminUsuario()));
    }

    public static List<usuario> listarUsuarios() throws IOException {
        List<usuario> usuarios = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] dados = line.split(DELIMITER);
                    if (dados.length==6)
                        usuarios.add(new usuario(dados[0], dados[1], dados[2], dados[3], dados[4], dados[5], false));
                }
            }
            System.out.println(usuarios);
        }
        return usuarios;
    }


    public static void editarUsuario(Scanner scanner) throws IOException {
        System.out.println("");
        System.out.print("Digite o nome de usuário que deseja editar: ");
        String username = scanner.nextLine();
        List<usuario> usuarios = listarUsuarios();
        boolean found = false;
        
        for (usuario user : usuarios) {
            if (user.getUsuario().equals(username)) {
                found = true;
                System.out.print("Digite o novo nome (anterior: " + user.getNome() + "): ");
                user.setNome(scanner.nextLine());

                System.out.print("Digite o novo endereço (anterior: " + user.getEndereco() + "): ");
                user.setEndereco(scanner.nextLine());

                System.out.print("Digite o novo email (anterior: " + user.getEmail() + "): ");
                user.setEmail(scanner.nextLine());

                System.out.print("Digite o novo telefone (anterior: " + user.getTelefone() + "): ");
                user.setTelefone(scanner.nextLine());

                System.out.print("Digite o novo usuário (anterior: " + user.getUsuario() + "): ");
                user.setUsuario(scanner.nextLine());

                System.out.print("Digite a nova senha: ");
                user.setSenha(scanner.nextLine());

               
        
                String newUserdata = formatUserData(user);
                try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
                    for (usuario usr : usuarios) {
                        if (usr.getUsuario().equals(username)) {
                            out.println(newUserdata);
                        } else {
                            out.println(formatUserData(usr));
                        }
                    }
                }
                System.out.println("------------------------------\n");
                System.out.println("usuário editado com sucesso.\n");
                System.out.println("------------------------------\n");     
                }
            }
            if (!found) {
                throw new IOException("usuário não encontrado.");
            }
        }
        

    public static String visualizarUsuario(String username) throws IOException {
        List<usuario> usuarios = listarUsuarios();
        if (usuarios.isEmpty()) {
            return "Nenhum usuário carregado do arquivo."; 
        }
        for (usuario Usuario : usuarios) {
            if (Usuario.getUsuario().equals(username)) {
                System.out.println("");
                System.out.println("usuário encontrado!\n" + Usuario.toString());
                return "usuário encontrado: " + Usuario.toString();
            }
        }
        return "usuário não encontrado."; 
    }


    public static void excluirUsuario(String username) throws IOException {
        List<usuario> usuarios = listarUsuarios();
        boolean found = false;
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (usuario Usuario : usuarios) {
                if (Usuario.getUsuario().equals(username)) {
                    found = true;
                    System.out.println("");
                    System.out.println("--------------------------------\n");
                    System.out.println("usuário excluido com sucesso.\n");
                    System.out.println("----------------------------------");
                } else {
                    out.println(formatUserData(Usuario));
                }
            }
        }
        if (!found) {
            throw new IOException("usuário nao encontrado.");
        }
    }


    public static boolean autenticarAdmin(Scanner scanner) throws IOException {
        System.out.print("Digite o nome de usuário: ");
        String username = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();
    
        String adminFilePath = "src/main/java/hotel/data/admin.txt";
    
        try (BufferedReader br = new BufferedReader(new FileReader(adminFilePath))) {
            String fileUsername = br.readLine();
            String filePassword = br.readLine();
            
            if (username.equals(fileUsername) && senha.equals(filePassword)) {
                return true;
            }
        } catch (IOException e) {
            throw new IOException("não foi possível acessar o arquivo de administrador.", e);
        }
        return false;
    }

    public static boolean autenticarUser(Scanner scanner) throws IOException {
        System.out.print("Digite o nome de usuário: ");
        String username = scanner.nextLine();
        System.out.print("Digite a senha: ");
        String senha = scanner.nextLine();
    
        String usersFilePath = "src/main/java/hotel/data/usuarios.txt";
    

        try (BufferedReader br = new BufferedReader(new FileReader(usersFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");  
                if (userData.length >= 6) {  // Verifica se a linha tem dados suficientes
                    String fileUsername = userData[4].trim();  
                    String filePassword = userData[5].trim();  
                    if (username.equals(fileUsername) && senha.equals(filePassword)) {
                        return true;
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Não foi possível acessar o arquivo de usuários.", e);
        }
        return false;
    }

}