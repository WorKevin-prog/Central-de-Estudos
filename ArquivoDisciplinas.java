import java.io.*;
import java.util.ArrayList;

public class ArquivoDisciplinas {

    private static final String NOME_ARQUIVO = "disciplinas.csv";

    // Salva as disciplinas no arquivo CSV

    public static void salvar(ArrayList<Disciplina> disciplinas) {

        try (PrintWriter escritor = new PrintWriter(
                new FileWriter(NOME_ARQUIVO))) {

            for (Disciplina disciplina : disciplinas) {

                escritor.println(
                    disciplina.getNome() + "," +
                    disciplina.getNota1() + "," +
                    disciplina.getNota2() + "," +
                    disciplina.getNota3()
                );
            }

            System.out.println("Dados salvos com sucesso!");

        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }

    // Carrega as disciplinas do arquivo CSV

    public static ArrayList<Disciplina> carregar() {

        ArrayList<Disciplina> disciplinas = new ArrayList<>();

        File arquivo = new File(NOME_ARQUIVO);

        // Se o arquivo ainda não existir, retorna uma lista vazia

        if (!arquivo.exists()) {
            return disciplinas;
        }

        try (BufferedReader leitor = new BufferedReader(
                new FileReader(arquivo))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {

                String[] dados = linha.split(",");

                // Verifica se a linha contém os quatro campos

                if (dados.length != 4) {
                    continue;
                }

                try {

                    String nome = dados[0];

                    double nota1 = Double.parseDouble(dados[1]);
                    double nota2 = Double.parseDouble(dados[2]);
                    double nota3 = Double.parseDouble(dados[3]);

                    Disciplina disciplina = new Disciplina(
                        nome,
                        nota1,
                        nota2,
                        nota3
                    );

                    disciplinas.add(disciplina);

                } catch (NumberFormatException e) {
                    System.out.println("Uma linha inválida foi ignorada.");
                }
            }

        } catch (IOException e) {
            System.out.println("Erro ao carregar os dados: " + e.getMessage());
        }

        return disciplinas;
    }
}