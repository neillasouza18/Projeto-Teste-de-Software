package hotel.pagamento.controller;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import hotel.pagamento.model.Pagamento;

public class PagamentoController {
    public static final String FILE_PATH = "src/main/java/hotel/data/pagamentos.txt";
    public static final String DELIMITER = ",";

    public static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    // Inserir dados do pagamento via scanner
    public boolean registrarPagamento(Scanner scanner) throws IOException {
        Date data = new Date();


        System.out.print("Digite o valor do pagamento: ");
        float valor = Float.parseFloat(scanner.nextLine());

        System.out.print("Digite o método de pagamento: ");
        String metodoPagamento = scanner.nextLine();

        Pagamento novoPagamento = new Pagamento(gerarNovoId(), data, valor, metodoPagamento);
        criarPagamento(novoPagamento);
        System.out.println("\n--------------------------------\n");
        System.out.println("Pagamento registrado com sucesso.\n");
        System.out.println("----------------------------------");
        return true;
    }

    // vizualização de pagamento por ID
    public static void visualizarPagamento(int id) throws IOException {
    List<Pagamento> pagamentos = listarPagamentos();
    boolean encontrado = false;

    for (Pagamento pagamento : pagamentos) {
        if (pagamento.getId() == id) {
            encontrado = true;
            System.out.println("Pagamento encontrado:");
            System.out.println("ID: " + pagamento.getId());
            System.out.println("Data: " + sdf.format(pagamento.getData()));
            System.out.println("Valor: " + pagamento.getValor());
            System.out.println("método de Pagamento: " + pagamento.getMetodoPagamento());
            System.out.println("------------------------------\n");
            break;
        }
    }

    if (!encontrado) {
        throw new IOException("Pagamento não encontrado.");
    }
}


    // criação de um pagamento e salvamento em arquivo
    public static void criarPagamento(Pagamento pagamento) throws IOException {
        List<Pagamento> pagamentos = listarPagamentos();
        for (Pagamento p : pagamentos) {
            if (p.getId() == pagamento.getId()) {
                throw new IOException("Pagamento já existe.");
            }
        }
        String pagamentoData = formatPagamentoData(pagamento);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(pagamentoData + "\n");
        }
    }

    // formatação dos dados do pagamento para salvar no arquivo
    public static String formatPagamentoData(Pagamento pagamento) {
        return String.join(DELIMITER,
                Integer.toString(pagamento.getId()),
                sdf.format(pagamento.getData()),
                Float.toString(pagamento.getValor()),
                pagamento.getMetodoPagamento());
    }

    // Leitura de todos os pagamentos do arquivo
    public static List<Pagamento> listarPagamentos() throws IOException {
        List<Pagamento> pagamentos = new ArrayList<>();
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] dados = line.split(DELIMITER);
                    try {
                        Date data = sdf.parse(dados[1]);
                        pagamentos.add(new Pagamento(
                                Integer.parseInt(dados[0]),
                                data,
                                Float.parseFloat(dados[2]),
                                dados[3]));
                    } catch (ParseException e) {
                        // Trate a exceção ou ignore a linha com data inválida
                        System.out.println("Erro ao analisar a data: " + e.getMessage());
                    }
                }
            }
        }
        return pagamentos;
    }

    public static void editarPagamento(Scanner scanner) throws IOException {
        System.out.println("");
        System.out.print("Digite o ID do pagamento que deseja editar: ");
        int id = Integer.parseInt(scanner.nextLine());
        List<Pagamento> pagamentos = listarPagamentos();
        boolean encontrado = false;

        for (Pagamento pagamento : pagamentos) {
            if (pagamento.getId() == id) {
                encontrado = true;
                float novoValor = 0;
                boolean valorValido = false;
                while (!valorValido) {
                    System.out.print("Digite o novo valor do pagamento (anterior: " + pagamento.getValor() + "): ");
                    String valorInput = scanner.nextLine().trim();
                    try {
                        novoValor = Float.parseFloat(valorInput);
                        valorValido = true;
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido. Por favor, digite um número válido.");
                    }
                }

                System.out.print("Digite o novo método de pagamento (anterior: " + pagamento.getMetodoPagamento() + "): ");
                String novoMetodoPagamento = scanner.nextLine();

                pagamento.setValor(novoValor);
                pagamento.setMetodoPagamento(novoMetodoPagamento);

                // Reescrevendo no arquivo
                try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
                    for (Pagamento p : pagamentos) {
                        out.println(formatPagamentoData(p));
                    }
                }

                System.out.println("------------------------------\n");
                System.out.println("Pagamento editado com sucesso.\n");
                System.out.println("------------------------------\n");
                break;
            }
        }

        if (!encontrado) {
            throw new IOException("Pagamento não encontrado.");
        }
    }
    
    
    // exclusão de pagamento por ID
    public static void excluirPagamento(int id) throws IOException {
        List<Pagamento> pagamentos = listarPagamentos();
        boolean encontrado = false;
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_PATH))) {
            for (Pagamento pagamento : pagamentos) {
                if (pagamento.getId() == id) {
                    encontrado = true;
                    System.out.println("Pagamento excluído com sucesso.\n");
                } else {
                    out.println(formatPagamentoData(pagamento));
                }
            }
        }
        if (!encontrado) {
            throw new IOException("Pagamento não encontrado.");
        }
    }

    // Gera um novo ID para o pagamento
    public static int gerarNovoId() throws IOException {
        List<Pagamento> pagamentos = listarPagamentos();
        if (pagamentos.isEmpty()) {
            return 1;
        } else {
            int maiorId = pagamentos.stream().mapToInt(Pagamento::getId).max().getAsInt();
            return maiorId + 1;
        }
    }
}