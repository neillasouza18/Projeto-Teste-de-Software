package hotel.reserva.controller;
import hotel.reserva.model.Reserva;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import hotel.pagamento.controller.PagamentoController;
import hotel.quarto.model.Quarto;

public class ReservaController {
    private static final String FILE_PATH = "src/main/java/hotel/data/reservas.txt";
    private static final String DELIMITER = ",";
    Quarto quarto = new Quarto(0, DELIMITER, 0, 0, false, null);
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    static PagamentoController pagamento = new PagamentoController();
    // Criar uma nova reserva
    public static void realizarReserva(Scanner scanner) throws IOException {
        System.out.println("Digite as informações da reserva:");

        System.out.print("Data de início (dd-MM-yyyy): ");
        Date dataInicio = null;

        try {
            dataInicio = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida.");
            return;
        }
        System.out.print("Data de término (dd-MM-yyyy): ");
        Date dataTermino = null;
        try {
            dataTermino = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida.");
            return;
        }

        Date dataCheckin = dataInicio;
        Date dataCheckout = dataTermino;

        boolean pagamentoRealizado = pagamento.registrarPagamento(scanner);
        if(pagamentoRealizado){
            Reserva novaReserva = new Reserva(gerarNovoId(), dataInicio, dataTermino,  dataCheckin, dataCheckout, true);
            criarReserva(novaReserva);
            System.out.println("--------------------------------");
            System.out.println("Reserva realizada com sucesso.");
            System.out.println("--------------------------------");
        }else{
            System.out.println("Falha ao realizar pagamento");
        }
     
       
    }


    // criação de uma reserva e salvamento em arquivo
    public static void criarReserva(Reserva reserva) throws IOException {
        List<Reserva> reservas = listarReservas();
        reservas.add(reserva);
        salvarReservas(reservas);
    }

    // Leitura de todas as reservas do arquivo
    public static List<Reserva> listarReservas() throws IOException {
        List<Reserva> reservas = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    Reserva reserva = parseReservaData(line.split(DELIMITER));
                    reservas.add(reserva);
                }
            }
        }
        return reservas;
    }

    // vizualização de uma reserva específica
    public static void visualizarReserva(int idReserva) throws IOException {
        List<Reserva> reservas = listarReservas();
        boolean encontrado = false;
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                System.out.println("Reserva encontrada:");
                System.out.println("ID: " + reserva.getId());
                System.out.println("Data de início: " + sdf.format(reserva.getDataInicio()));
                System.out.println("Data de término: " + sdf.format(reserva.getDataTermino()));
                System.out.println("Data de Checkin: " + sdf.format(reserva.getDataCheckin()));
                System.out.println("Data de Checkout: " + sdf.format(reserva.getDataCheckout()));

                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new IOException("Reserva não encontrada.");
        }
    }

    public static Date getDataCheckin(int idReserva) throws IOException {
        List<Reserva> reservas = listarReservas();
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                return reserva.getDataCheckin();
            }
        }
        return null;
    }
    
    public static Date getDataCheckout(int idReserva) throws IOException {
        List<Reserva> reservas = listarReservas();
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                return reserva.getDataCheckout();
            }
        }
        return null;
    }

    // Cancelamento de uma reserva
    public static void cancelarReserva(int idReserva) throws IOException {
        List<Reserva> reservas = listarReservas();
        reservas.removeIf(reserva -> reserva.getId() == idReserva);
        salvarReservas(reservas);
        System.out.println("Reserva cancelada com sucesso.");
    }

    // atualização de uma reserva
    public static void editarReserva(int idReserva, Scanner scanner) throws IOException {
        List<Reserva> reservas = listarReservas();
        boolean encontrado = false;
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                encontrado = true;
                System.out.print("Digite a nova data de inicio. (anterior: " + reserva.getDataInicio() + "): ");

                try {
                    reserva.setDataInicio(sdf.parse(scanner.nextLine()));
                } catch (ParseException e) {
                    System.out.println("Data inválida.");
                    continue;
                }

                System.out.print("Digite a nova data de termino. (anterior: " + reserva.getDataTermino() + "): ");
                try {
                    reserva.setDataTermino(sdf.parse(scanner.nextLine()));
                } catch (ParseException e) {
                    System.out.println("Data inválida.");
                    continue;
                }

                System.out.print("Digite a nova data de checkin. (anterior: " + reserva.getDataCheckin() + "): ");
                try {
                    reserva.setDataCheckin(sdf.parse(scanner.nextLine()));
                } catch (ParseException e) {
                    System.out.println("Data inválida.");
                    continue;
                }

                System.out.print("Digite a nova data de checkout. (anterior: " + reserva.getDataCheckout() + "): ");
                try {
                    reserva.setDataCheckout(sdf.parse(scanner.nextLine()));
                } catch (ParseException e) {
                    System.out.println("Data inválida.");
                    continue;
                }
                break;
            }
        }
        if (!encontrado) {
            throw new IOException("Reserva não encontrada.");
        }
        salvarReservas(reservas);
        System.out.println("Reserva atualizada com sucesso.");
    }


    // exclusão de uma reserva
    public static void excluirReserva(int idReserva) throws IOException {
        List<Reserva> reservas = listarReservas();
        reservas.removeIf(reserva -> reserva.getId() == idReserva);
        salvarReservas(reservas);
        System.out.println("Reserva excluída com sucesso.");
    }


    public static boolean verificaReserva(int idReserva) throws IOException {
        List<Reserva> reservas = listarReservas();
        for (Reserva reserva : reservas) {
            if (reserva.getId() == idReserva) {
                return true;  
            }
        }
        return false;  // Reserva não encontrada
    }
    // Auxiliar para salvar todas as reservas em arquivo
    private static void salvarReservas(List<Reserva> reservas) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Reserva reserva : reservas) {
                out.println(formatReservaData(reserva));
            }
        }
    }

    // formatação dos dados da reserva para salvar no arquivo
    private static String formatReservaData(Reserva reserva) {
        return String.join(DELIMITER,
                Integer.toString(reserva.getId()),
                sdf.format(reserva.getDataInicio()),
                sdf.format(reserva.getDataTermino()),
                sdf.format(reserva.getDataCheckin()),
                sdf.format(reserva.getDataCheckout()),
                Boolean.toString(reserva.getValidaReserva()));

    }

    //conversão de dados do arquivo em objetos Reserva
    private static Reserva parseReservaData(String[] dados) {
        try {
            int id = Integer.parseInt(dados[0]);
            Date dataInicio = sdf.parse(dados[1]);
            Date dataTermino = sdf.parse(dados[2]);
            Date dataCheckin = sdf.parse(dados[3]);
            Date dataCheckout = sdf.parse(dados[4]);
            return new Reserva(id, dataInicio, dataTermino,  dataCheckin, dataCheckout, false);
        } catch (ParseException e) {
            System.out.println("Erro ao analisar a data: " + e.getMessage());
            return null;
        }
    }

    public static int gerarNovoId() throws IOException {
        List<Reserva> pagamentos = listarReservas();
        if (pagamentos.isEmpty()) {
            return 1;
        } else {
            int maiorId = pagamentos.stream().mapToInt(Reserva::getId).max().getAsInt();
            return maiorId + 1;
        }
    }
}
