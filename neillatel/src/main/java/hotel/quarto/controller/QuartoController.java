package hotel.quarto.controller;

import hotel.quarto.model.Quarto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Date;

public class QuartoController {
    private static final String FILE_PATH = "src/main/java/hotel/data/quartos.txt";
    private static final String DELIMITER = ",";
    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");


    public static void inserirQuarto(Scanner scanner) throws IOException {
        System.out.print("Digite o número do quarto: ");
        int numero = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite as comodidades do quarto: ");
        String comodidades = scanner.nextLine();

        System.out.print("Digite a capacidade do quarto: ");
        int capacidade = Integer.parseInt(scanner.nextLine());

        System.out.print("Digite a tarifa do quarto: ");
        float tarifa = Float.parseFloat(scanner.nextLine());

        System.out.print("Disponível (true/false): ");
        boolean disponibilidade = Boolean.parseBoolean(scanner.nextLine());


        System.out.print("Data de início (dd-MM-yyyy): ");
        Date dataQuarto = null;

        try {
            dataQuarto = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.out.println("Data inválida." + e.getMessage());
            return;
        }
        Quarto novoQuarto = new Quarto(numero, comodidades, capacidade, tarifa, disponibilidade, dataQuarto);
        criarQuarto(novoQuarto);
        System.out.println("");
        System.out.println("--------------------------------\n");
        System.out.println("Quarto adicionado com sucesso.\n");
        System.out.println("----------------------------------");
    }

    
    public static void criarQuarto(Quarto quarto) throws IOException {
        List<Quarto> quartos = listarQuartos();
        for (Quarto q : quartos) {
            if (q.getNumero() == quarto.getNumero()) {
                throw new IOException("Quarto já existe.");
            }
        }
        String quartoData = formatQuartoData(quarto);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(quartoData + "\n");
        }
    }

    private static String formatQuartoData(Quarto quarto) {
        return String.join(DELIMITER,
                Integer.toString(quarto.getNumero()),
                quarto.getComodidades(),
                Integer.toString(quarto.getCapacidade()),
                Float.toString(quarto.getTarifa()),
                Boolean.toString(quarto.getDisponibilidade()),
                sdf.format(quarto.getData())
        );
    }

    public static List<Quarto> listarQuartos() throws IOException {
        List<Quarto> quartos = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        String[] dados = line.split(DELIMITER);
                        int numero = Integer.parseInt(dados[0]);
                        String comodidades = dados[1];
                        int capacidade = Integer.parseInt(dados[2]);
                        float tarifa = Float.parseFloat(dados[3]);
                        boolean disponivel = Boolean.parseBoolean(dados[4]);
                        Date dataQuarto = sdf.parse(dados[5]);
                        quartos.add(new Quarto(numero, comodidades, capacidade, tarifa, disponivel, dataQuarto));
                    } catch (ParseException e) {
                        System.err.println("Failed to parse date for quarto: " + line);
                    }
                }
            }
        }
        return quartos;
    }
    

    public static void excluirQuarto(int numero) throws IOException {
        List<Quarto> quartos = listarQuartos();
        boolean found = false;
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Quarto quarto : quartos) {
                if (quarto.getNumero() == numero) {
                    found = true;
                    System.out.println("");
                    System.out.println("--------------------------------\n");
                    System.out.println("Quarto excluído com sucesso.\n");
                    System.out.println("----------------------------------");
                } else {
                    out.println(formatQuartoData(quarto));
                }
            }
        }
        if (!found) {
            throw new IOException("Quarto não encontrado.");
        }
    }

    public static boolean verificarDisponibilidade(int numero) throws IOException {
        List<Quarto> quartos = listarQuartos(); // Utiliza o método existente para obter todos os quartos
        for (Quarto quarto : quartos) {
            if (quarto.getNumero() == numero) {
                return quarto.getDisponibilidade();
            }
        }
        return false;
    }

    public static void editarQuarto(Scanner scanner) throws IOException {
        System.out.println("");
        System.out.print("Digite o número do quarto que deseja editar: ");
        int numero = Integer.parseInt(scanner.nextLine());
        List<Quarto> quartos = listarQuartos();
        boolean found = false;
    
        for (Quarto quarto : quartos) {
            if (quarto.getNumero() == numero) {
                found = true;
                System.out.print("Digite as novas comodidades (anterior: " + quarto.getComodidades() + "): ");
                quarto.setComodidades(scanner.nextLine());
    
                System.out.print("Digite a nova capacidade (anterior: " + quarto.getCapacidade() + "): ");
                quarto.setCapacidade(Integer.parseInt(scanner.nextLine()));
    
                System.out.print("Digite a nova tarifa (anterior: " + quarto.getTarifa() + "): ");
                quarto.setTarifa(Float.parseFloat(scanner.nextLine()));
    
                System.out.print("Digite a nova disponibilidade (Sim/não) (anterior: " + (quarto.getDisponibilidade() ? "Sim" : "não") + "): ");
                boolean novaDisponibilidade = scanner.nextLine().trim().equalsIgnoreCase("sim");
                quarto.setDisponibilidade(novaDisponibilidade);
    
                // Reescrevendo no arquivo
                try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
                    for (Quarto qt : quartos) {
                        out.println(formatQuartoData(qt));
                    }
                }
                System.out.println("------------------------------\n");
                System.out.println("Quarto editado com sucesso.\n");
                System.out.println("------------------------------\n");
                break;
            }
        }
        if (!found) {
            throw new IOException("Quarto não encontrado.");
        }
    }
    
    public static List<Integer> verificarDisponibilidadePorData(Scanner scanner) throws IOException {
        List<Integer> quartosDisponiveis = new ArrayList<>();
        List<Quarto> quartos = listarQuartos();
        System.out.print("Inclua a data para a verificação(dd-MM-yyyy): ");

        Date dataRequerida = null;
    
        try {
            dataRequerida = sdf.parse(scanner.nextLine());
        } catch (ParseException e) {
            System.err.println("Formato de data inválido. Use o formato dd-MM-yyyy.");
            return quartosDisponiveis; // Retorna lista vazia se a data não puder ser parseada.
        }
    
        for (Quarto quarto : quartos) {
            if (quarto.getData() != null && quarto.getData().equals(dataRequerida)) {
                quartosDisponiveis.add(quarto.getNumero());
            }
            
        }
        System.out.println("Diponivel(eis):");
        System.out.println(quartosDisponiveis);
        return quartosDisponiveis;
    }


    public static String visualizarQuarto(int numero) throws IOException {
        List<Quarto> quartos = listarQuartos();
        if (quartos.isEmpty()) {
            return "Nenhum quarto carregado do arquivo."; // Caso lista esteja vazia
        }
        for (Quarto quarto : quartos) {
            if (quarto.getNumero() == numero) {
                System.out.println("");
                System.out.println("Quarto encontrado!\n" + quarto.toString());
                return "Quarto encontrado: " + quarto.toString();
            }
        }
        return "Quarto não encontrado.";
    }
    
}
