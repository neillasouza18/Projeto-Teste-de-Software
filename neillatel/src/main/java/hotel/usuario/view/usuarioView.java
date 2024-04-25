package hotel.usuario.view;
import java.util.Scanner;
import java.io.*;
import hotel.usuario.controller.usuarioController;
import hotel.quarto.controller.QuartoController;
import hotel.quarto.view.QuartoView;
import hotel.reserva.view.ReservaView;
import hotel.pagamento.view.PagamentoView;
import hotel.reserva.controller.ReservaController;

public class usuarioView {
    private static Scanner scanner;

    static ReservaController reserva = new ReservaController();
    static QuartoController quarto = new QuartoController();

   
    public static void login() {
        int opcao;
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            System.out.println("[1] - Acessar como ADM");
            System.out.println("[2] - Acessar como usuário");
            System.out.println("[0] - Sair");
            System.out.println("");
            System.out.print("opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();  
    
            switch (opcao) {
                case 1:
                    try {
                        if(usuarioController.autenticarAdmin(scanner)){
                            menuPrincipal();
                        }
                        else{
                            System.out.println("");
                            System.out.println("\tNome de usuário ou senha estao incorretos.");
                            System.out.println("");

                        }
                        
                    } catch (Exception e) {
                        System.out.println("Erro ao autenticar: " + e.getMessage());
                    }
                    break;
                case 2:
                    System.out.println("você já tem uma conta? (sim/nao)");
                    String resposta = scanner.nextLine();
                    if (resposta.equalsIgnoreCase("sim")) {
                       try {
                            usuarioController.autenticarUser(scanner);
                            menuUsuario();
                       } catch (Exception e) {
                        System.out.println("Erro ao cadastrar usuário: "+ e.getMessage());
                       }
                    } else if (resposta.equalsIgnoreCase("nao")) {
                        try {
                            usuarioController.inserirUsuario(scanner);
                        } catch (IOException e) {
                            System.out.println("Erro ao criar usuário: " + e.getMessage());
                        }
                    } else {
                        System.out.println("Resposta inválida, por favor digite 'sim' ou 'nao'.");
                    }
                    break;
                case 0:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("opção inválida. Tente novamente.");
                    break;
            }
        }
    }
    

    public static void menuPrincipal() {
        int opcao;
        while (true) {
            System.out.println("\n\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            System.out.println("[1] - Gerenciar usuários");
            System.out.println("[2] - Gerenciar quartos");
            System.out.println("[3] - Gerenciar reservas");
            System.out.println("[4] - Gerenciar pagamentos");
            System.out.println("[0] - Voltar");
            System.out.print("opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    gerenciarUsuario(scanner);
                    break;
                case 2:
                    QuartoView.quartoCadastro();
                    break;
                case 3:
                    ReservaView.menuReserva();
                    break;
                case 4:
                    PagamentoView.menuPagamento();;
                    break;
                case 0:
                System.out.println("Voltando...");
                    return;  
                default:
                    System.out.println("opção inválida. Tente novamente.");
                    break;
            }
        }
    }


    public static void gerenciarUsuario(Scanner scanner) {
        System.out.println("\nSelecione uma opção:");
        System.out.println("[1] - Remover");
        System.out.println("[2] - Visualizar");
        System.out.println("[3] - Listar");
        System.out.println("[4] - Editar");
        System.out.println("[0] - voltar");
        System.out.println("");
        System.out.print("Selecione a opção: ");
        int opcaoGerencial = scanner.nextInt();
        scanner.nextLine(); 
    
        switch (opcaoGerencial) {
      
          case 1:
            System.out.println("Digite o nome de usuário que deseja remover: ");
            String username = scanner.nextLine();
            try {
                usuarioController.excluirUsuario(username);
            } catch (IOException e) {
                System.out.println("Erro ao remover usuário: " + e.getMessage());
            }
            break;
          case 2:
            System.out.println("Digite o nome de usuário que deseja visualizar: ");
            String foundUser = scanner.nextLine();
            try {
                usuarioController.visualizarUsuario(foundUser);
            } catch (IOException e) {
                System.out.println("Erro ao visualizar usuário: " + e.getMessage());
            }
            break;
          case 3:
            System.out.println("");
            System.out.println("Listando todos os usuários...");
            try {
                usuarioController.listarUsuarios();
            } catch (IOException e) {
                System.out.println("Erro ao listar usuários: " + e.getMessage());
            }
            break;
          case 4:
            try {
                usuarioController.editarUsuario(scanner);
            } catch (IOException e) {
                System.out.println("Erro ao editar usuário: " + e.getMessage());
            }
            break;
        case 0:
          System.out.println("Voltando...");
            break;
          default:
            System.out.println("opção inválida. Tente novamente.");
            break;
        }
    }

    public static void menuUsuario() {
        int opcao;
        while (true) {
            System.out.println("\n\t----------------------");
            System.out.println("\t Selecione uma opção:");
            System.out.println("\t----------------------\n");
            System.out.println("[1] - Visualizar quartos");
            System.out.println("[2] - Realizar reserva");
            System.out.println("[3] - Visualizar pagamento");
            System.out.println("[0] - Voltar");
            System.out.println("");
            System.out.print("opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    QuartoView.menuQuarto();
                    break;
                case 2:
                System.out.println("Digite o id do quarto disponivel: ");
                int valor = Integer.parseInt(scanner.nextLine());
                    try {
                        if(QuartoController.verificarDisponibilidade(valor)){
                            ReservaView.menuUser();
                        }else{
                            System.out.println("O quarto se encontra indisponivel.");
                        }
                            
                    } catch (Exception e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;
                case 3:
                    System.out.println("");
                    System.out.println("Digite o id da reserva: ");
                    int check = Integer.parseInt(scanner.nextLine());
                    try {
                        if (ReservaController.verificaReserva(check)) {
                            PagamentoView.menuUser();
                        } else {
                            // Informa ao usuário que não há reserva ativa para acessar o menu de pagamento
                            System.out.println("");
                            System.out.println("você ainda não realizou uma reserva. Por favor, faça uma reserva antes de acessar o menu de pagamento.");
                            System.out.println("");
                        }
                        
                    } catch (Exception e) {
                        System.out.println("Voce ainda nao realizou uma reserva:" + e.getMessage());
                    }
                   
                    break;
                case 0:
                    System.out.println("Voltando...");
                    return; 
                default:
                    System.out.println("opção inválida. Tente novamente.");
                    break;
            }
        }
    }
}
