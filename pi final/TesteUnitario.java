// Testes unitários das funções auxiliares do SistemaEstoqueTributavel
// Compilar junto: javac SistemaEstoqueTributavel.java TesteUnitario.java && java TesteUnitario
import java.util.Locale;

public class TesteUnitario {
    static int ok = 0, falhas = 0;

    static void confere(String nome, Object obtido, Object esperado) {
        boolean igual = (obtido == null) ? esperado == null : obtido.equals(esperado);
        if (igual) {
            ok++;
            System.out.println("OK      | " + nome);
        } else {
            falhas++;
            System.out.println("FALHOU  | " + nome + " -> obtido: " + obtido + " | esperado: " + esperado);
        }
    }

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // saída previsível (ponto decimal)

        System.out.println("=== lerNumero ===");
        confere("inteiro \"10\"", SistemaEstoqueTributavel.lerNumero("10"), 10.0);
        confere("vírgula \"49,90\"", SistemaEstoqueTributavel.lerNumero("49,90"), 49.9);
        confere("ponto \"49.90\"", SistemaEstoqueTributavel.lerNumero("49.90"), 49.9);
        confere("espaços \"  7,5  \"", SistemaEstoqueTributavel.lerNumero("  7,5  "), 7.5);
        confere("negativo \"-3\"", SistemaEstoqueTributavel.lerNumero("-3"), -3.0);
        confere("texto \"abc\" -> -1", SistemaEstoqueTributavel.lerNumero("abc"), -1.0);
        confere("vazio \"\" -> -1", SistemaEstoqueTributavel.lerNumero(""), -1.0);
        confere("milhar \"1.000,50\" -> -1", SistemaEstoqueTributavel.lerNumero("1.000,50"), -1.0);
        confere("duas vírgulas \"1,2,3\" -> -1", SistemaEstoqueTributavel.lerNumero("1,2,3"), -1.0);
        confere("NaN é número, mas não > 0", SistemaEstoqueTributavel.lerNumero("NaN") > 0, false);
        confere("científico \"1e3\"", SistemaEstoqueTributavel.lerNumero("1e3"), 1000.0);

        System.out.println("\n=== arredondar ===");
        confere("2 casas 297.4999999", SistemaEstoqueTributavel.arredondar(297.4999999), 297.5);
        confere("0.1+0.2 vira 0.3", SistemaEstoqueTributavel.arredondar(0.1 + 0.2), 0.3);
        confere("0.004 vira 0.0", SistemaEstoqueTributavel.arredondar(0.004), 0.0);
        confere("0.005 vira 0.01", SistemaEstoqueTributavel.arredondar(0.005), 0.01);
        confere("inteiro 305", SistemaEstoqueTributavel.arredondar(305), 305.0);
        confere("negativo -2.345", SistemaEstoqueTributavel.arredondar(-2.345), -2.34);

        System.out.println("\n=== formatarQtd ===");
        confere("unidade 50", SistemaEstoqueTributavel.formatarQtd(50, false), "50 un");
        confere("unidade 0", SistemaEstoqueTributavel.formatarQtd(0, false), "0 un");
        confere("metro 305", SistemaEstoqueTributavel.formatarQtd(305, true), "305.00 m");
        confere("metro 7.5", SistemaEstoqueTributavel.formatarQtd(7.5, true), "7.50 m");
        Locale.setDefault(new Locale("pt", "BR"));
        confere("metro 7.5 em pt-BR", SistemaEstoqueTributavel.formatarQtd(7.5, true), "7,50 m");

        System.out.println("\nResultado: " + ok + " OK, " + falhas + " com problema");
    }
}
