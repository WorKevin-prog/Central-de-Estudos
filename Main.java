
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static final Scanner entrada = new Scanner(System.in);
    private static final ArrayList<Disciplina> disciplinas =
        ArquivoDisciplinas.carregar();

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ");

            switch (opcao) {
                case 1:
                    cadastrarDisciplina();
                    break;
                case 2:
                    listarDisciplinas();
                    break;
                case 3:
                    consultarSituacao();
                    break;
                case 4:
                    exibirResumo();
                    break;
                case 5:
                    editarDisciplina();
                    break;
                case 6:
                    pesquisarDisciplina();
                    break;  
                case 0:
                    System.out.println("Até a próxima!");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }

            System.out.println();

        } while (opcao != 0);

        entrada.close();
    }

    private static void exibirMenu() {
        System.out.println("==============================");
        System.out.println("      CENTRAL DE ESTUDOS");
        System.out.println("==============================");
        System.out.println("1. Cadastrar disciplina");
        System.out.println("2. Consultar disciplinas");
        System.out.println("3. Verificar aprovação");
        System.out.println("4. Ver resumo geral");
        System.out.println("5. Editar disciplina");
        System.out.println("6. Pesquisar disciplina");
        System.out.println("0. Sair");
        System.out.println("==============================");
    }

    private static void cadastrarDisciplina() {
        System.out.print("Nome da disciplina: ");
        String nome = entrada.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("O nome não pode ficar vazio.");
            return;
        }

        for (Disciplina disciplina : disciplinas) {
            if (disciplina.getNome().equalsIgnoreCase(nome)) {
                System.out.println("Essa disciplina já existe.");
                return;
            }
        }

        double nota1 = lerNota("Primeira nota: ");
        double nota2 = lerNota("Segunda nota: ");
        double nota3 = lerNota("Terceira nota: ");

        Disciplina nova = new Disciplina(nome, nota1, nota2, nota3);
        disciplinas.add(nova);
        ArquivoDisciplinas.salvar(disciplinas);

        System.out.println("Disciplina cadastrada com sucesso!");
        System.out.printf("Média: %.2f%n", nova.calcularMedia());
        System.out.println("Situação: " + nova.verificarSituacao());
    }

    private static void listarDisciplinas() {
        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }

        System.out.println("--- Disciplinas cadastradas ---");

        for (int i = 0; i < disciplinas.size(); i++) {
            Disciplina disciplina = disciplinas.get(i);

            System.out.printf(
                    "%d. %s | Média: %.2f | %s%n",
                    i + 1,
                    disciplina.getNome(),
                    disciplina.calcularMedia(),
                    disciplina.verificarSituacao()
            );
        }
    }

    private static void consultarSituacao() {
        if (disciplinas.isEmpty()) {
            System.out.println("Cadastre uma disciplina primeiro.");
            return;
        }

        listarDisciplinas();
        int numero = lerInteiro("Número da disciplina: ");

        if (numero < 1 || numero > disciplinas.size()) {
            System.out.println("Número de disciplina inválido.");
            return;
        }

        Disciplina disciplina = disciplinas.get(numero - 1);

        System.out.println("Disciplina: " + disciplina.getNome());
        System.out.printf("Média: %.2f%n", disciplina.calcularMedia());
        System.out.println("Situação: " + disciplina.verificarSituacao());
    }

    private static void exibirResumo() {
        if (disciplinas.isEmpty()) {
            System.out.println("Ainda não há dados para o resumo.");
            return;
        }

        double somaMedias = 0;
        int aprovadas = 0;

        for (Disciplina disciplina : disciplinas) {
            double media = disciplina.calcularMedia();
            somaMedias += media;

            if (media >= 7.0) {
                aprovadas++;
            }
        }

        double mediaGeral = somaMedias / disciplinas.size();

        System.out.println("--- Resumo geral ---");
        System.out.println("Total de disciplinas: " + disciplinas.size());
        System.out.println("Disciplinas aprovadas: " + aprovadas);
        System.out.println(
                "Disciplinas não aprovadas: "
                + (disciplinas.size() - aprovadas)
        );
        System.out.printf("Média geral: %.2f%n", mediaGeral);
    }

    private static double lerNota(String mensagem) {
        while (true) {
            System.out.print(mensagem);
            String texto = entrada.nextLine().trim().replace(',', '.');

            try {
                double nota = Double.parseDouble(texto);

                if (Double.isFinite(nota) && nota >= 0 && nota <= 10) {
                    return nota;
                }

                System.out.println("Digite uma nota entre 0 e 10.");

            } catch (NumberFormatException erro) {
                System.out.println("Digite um número válido.");
            }
        }
    }

    
private static int lerInteiro(String mensagem) {
    while (true) {
        System.out.print(mensagem);
        String texto = entrada.nextLine().trim();

        try {
            return Integer.parseInt(texto);
        } catch (NumberFormatException erro) {
            System.out.println("Digite um número inteiro válido.");
        }
    }
}


    // Método para editar uma disciplina
    private static void editarDisciplina() {

        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }

        listarDisciplinas();

        int indice = lerInteiro(
            "Digite o número da disciplina que deseja editar: "
        );

        if (indice < 1 || indice > disciplinas.size()) {
            System.out.println("Disciplina inválida.");
            return;
        }

        Disciplina disciplina = disciplinas.get(indice - 1);

        System.out.println("Editando: " + disciplina.getNome());

        double nota1 = lerNota("Nova primeira nota: ");
        double nota2 = lerNota("Nova segunda nota: ");
        double nota3 = lerNota("Nova terceira nota: ");

        disciplina.setNota1(nota1);
        disciplina.setNota2(nota2);
        disciplina.setNota3(nota3);
        ArquivoDisciplinas.salvar(disciplinas);

        System.out.println("Disciplina atualizada com sucesso!");
        System.out.printf("Nova média: %.2f%n", disciplina.calcularMedia());
        System.out.println("Nova situação: " + disciplina.verificarSituacao());
    }

    // Método para pesquisar uma disciplina
    private static void pesquisarDisciplina() {

        if (disciplinas.isEmpty()) {
            System.out.println("Nenhuma disciplina cadastrada.");
            return;
        }

        System.out.print("Digite o nome da disciplina: ");
        String nomeBusca = entrada.nextLine().trim();

        boolean encontrada = false;

        for (Disciplina disciplina : disciplinas) {

            if (disciplina.getNome().equalsIgnoreCase(nomeBusca)) {

                System.out.println("Disciplina encontrada!");
                System.out.println("Nome: " + disciplina.getNome());

                System.out.printf(
                    "Média: %.2f%n",
                    disciplina.calcularMedia()
                );

                System.out.println(
                    "Situação: " + disciplina.verificarSituacao()
                );

                encontrada = true;
                break;
            }
        }

        if (!encontrada) {
            System.out.println("Disciplina não encontrada.");
        }
    }

}