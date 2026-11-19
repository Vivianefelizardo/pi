import java.util.Scanner;

public class SistemaEstoqueTributavel {

    public static int lerInteiro(Scanner entrada, String mensagem) {
        int valor = 0;
        boolean lido = false;
        while (!lido) {
            System.out.print(mensagem);
            try {
                valor = entrada.nextInt();
                entrada.nextLine(); 
                lido = true;
            } catch (Exception e) {
                entrada.nextLine(); 
                System.out.println("[ERRO] Digite apenas números inteiros.");
            }
        }
        return valor;
    }

    public static double lerDecimal(Scanner entrada, String mensagem) {
        double valor = 0;
        boolean lido = false;
        while (!lido) {
            System.out.print(mensagem);
            try {
                valor = entrada.nextDouble();
                entrada.nextLine();
                lido = true;
            } catch (Exception e) {
                entrada.nextLine();
                System.out.println("[ERRO] Digite um número (ex.: 7,5).");
            }
        }
        return valor;
    }


    public static double arredondar(double valor) {
        return Math.round(valor * 100) / 100.0;
    }

    public static String formatoQtd(boolean porMetro) {
        if (porMetro) {
            return "%.2f m";
        }
        return "%.0f un";
    }

    public static double calcularSubtotal(double quantidade, double precoUnitario) {
        return arredondar(quantidade * precoUnitario);
    }

    public static double calcularImposto(double valor, double aliquota) {
        return arredondar(valor * aliquota);
    }

    public static double calcularTotal(double subtotal, double imposto) {
        return arredondar(subtotal + imposto);
    }

    public static String situacaoEstoque(double estoque, double minimo) {
        if (estoque <= 0) {
            return "ESGOTADO";
        } else if (estoque <= minimo) {
            return "REPOSIÇÃO";
        }
        return "OK";
    }

    public static boolean precisaRepor(double estoque, double minimo) {
        return estoque <= minimo;
    }

    public static boolean quantidadeValida(double quantidade, double estoque) {
        return quantidade > 0 && quantidade <= estoque;
    }

    public static boolean precoValido(double preco, double precoMaximo) {
        return preco > 0 && preco <= precoMaximo;
    }

    public static boolean skuExiste(String[] skus, int total, String sku) {
        for (int i = 0; i < total; i++) {
            if (skus[i].equals(sku)) {
                return true;
            }
        }
        return false;
    }

    public static double metragemDeCabos(double unidades, double comprimento) {
        return arredondar(unidades * comprimento);
    }

    public static double percentualRestante(double atual, double inicial) {
        if (inicial <= 0) {
            return 0;
        }
        return atual / inicial * 100;
    }

    public static String validarLogin(String usuario, String senha) {
        if ((usuario.equals("ADMIN") || usuario.equals("admin")) && senha.equals("123")) {
            return "ADMINISTRADOR";
        } else if ((usuario.equals("VENDEDOR") || usuario.equals("vendedor")) && senha.equals("456")) {
            return "VENDEDOR";
        }
        return "";
    }

    public static boolean ufValida(String uf) {
        String[] ufs = {"AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG",
            "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO"};
        for (int i = 0; i < ufs.length; i++) {
            if (ufs[i].equals(uf)) {
                return true;
            }
        }
        return false;
    }

    public static double aliquotaIcms(String uf) {
        if (uf.equals("SP")) {
            return 0.18;
        }
        return 0.12;
    }

    public static double aliquotaTotal(int regime, double icms) {
        switch (regime) {
            case 1:
                return 0.04;                       
            case 2:
                return icms + 0.0065 + 0.03;       
            default:
                return icms + 0.0165 + 0.076;      
        }
    }

    public static String nomeRegime(int regime) {
        switch (regime) {
            case 1:
                return "Simples Nacional";
            case 2:
                return "Lucro Presumido";
            default:
                return "Lucro Real";
        }
    }

    public static String descricaoTributos(int regime, String uf) {
        String icms;
        if (uf.equals("SP")) {
            icms = "ICMS 18%";
        } else {
            icms = "ICMS 12%";
        }
        switch (regime) {
            case 1:
                return "DAS 4%";
            case 2:
                return icms + " + PIS 0,65% + COFINS 3%";
            default:
                return icms + " + PIS 1,65% + COFINS 7,6%";
        }
    }

    public static String tipoDocumento(String uf, boolean balcao) {
        if (uf.equals("SP") && balcao) {
            return "NFC-e";
        }
        return "NF-e";
    }

    public static boolean ncmValido(int ncm) {
        return ncm >= 1 && ncm <= 99999999;
    }

    public static boolean cestValido(int cest) {
        return cest >= 1 && cest <= 9999999;
    }

    public static void imprimirNcmCest(int ncm, int cest) {
        System.out.printf("NCM: %04d.%02d.%02d | CEST: %02d.%03d.%02d",
            ncm / 10000, ncm / 100 % 100, ncm % 100,
            cest / 100000, cest / 100 % 1000, cest % 100);
    }

    public static boolean mesValido(int mes) {
        return mes >= 1 && mes <= 12;
    }

    public static boolean respostaValida(String resposta) {
        return resposta.equals("S") || resposta.equals("s") || resposta.equals("N") || resposta.equals("n");
    }

    public static boolean respostaSim(String resposta) {
        return resposta.equals("S") || resposta.equals("s");
    }

    public static double participacao(double valor, double total) {
        if (total <= 0) {
            return 0;
        }
        return valor / total * 100;
    }

    public static String classeABC(double acumuladoAntes) {
        if (acumuladoAntes < 80) {
            return "A";
        } else if (acumuladoAntes < 95) {
            return "B";
        }
        return "C";
    }
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        int capacidade = 30;
        String[] skus = new String[capacidade];
        String[] categorias = new String[capacidade];
        String[] especificacoes = new String[capacidade];
        double[] precos = new double[capacidade];         
        double[] estoques = new double[capacidade];        
        double[] estoquesMinimos = new double[capacidade];
        boolean[] porMetro = new boolean[capacidade];      
        double[] comprimentos = new double[capacidade];    
        double[] vendidos = new double[capacidade];
        int[] ncms = new int[capacidade];                 
        int[] cests = new int[capacidade];                 

        double precoMaximo = 1000000.0;
        int quantidadeMaxima = 100000;
        int regime = 3; 

        skus[0] = "HDMI-2.1-001"; categorias[0] = "HDMI"; especificacoes[0] = "HDMI 2.1 - 2m"; precos[0] = 100.0; estoques[0] = 50; estoquesMinimos[0] = 10; comprimentos[0] = 2.0;
        skus[1] = "HDMI-1.4-011"; categorias[1] = "HDMI"; especificacoes[1] = "HDMI 1.4 - 1.5m"; precos[1] = 30.0; estoques[1] = 40; estoquesMinimos[1] = 10; comprimentos[1] = 1.5;
        skus[2] = "HDMI-MINI-013"; categorias[2] = "HDMI"; especificacoes[2] = "HDMI Mini - 1m"; precos[2] = 45.0; estoques[2] = 6; estoquesMinimos[2] = 10; comprimentos[2] = 1.0;
        skus[3] = "USB-A-3.0-002"; categorias[3] = "USB"; especificacoes[3] = "USB 3.0 - 1m"; precos[3] = 35.0; estoques[3] = 40; estoquesMinimos[3] = 10; comprimentos[3] = 1.0;
        skus[4] = "USB-EXT-014"; categorias[4] = "USB"; especificacoes[4] = "USB Extensor - 2m"; precos[4] = 20.0; estoques[4] = 50; estoquesMinimos[4] = 10; comprimentos[4] = 2.0;
        skus[5] = "USB-HUB-016"; categorias[5] = "USB"; especificacoes[5] = "USB Hub 4 Portas"; precos[5] = 38.0; estoques[5] = 7; estoquesMinimos[5] = 10;
        skus[6] = "RED-CAT6-003"; categorias[6] = "Rede"; especificacoes[6] = "Cat6 - 5m"; precos[6] = 25.0; estoques[6] = 60; estoquesMinimos[6] = 10; comprimentos[6] = 5.0;
        skus[7] = "RED-CAT5E-017"; categorias[7] = "Rede"; especificacoes[7] = "Cat5e - 3m"; precos[7] = 12.0; estoques[7] = 80; estoquesMinimos[7] = 10; comprimentos[7] = 3.0;
        skus[8] = "SATA-3.0-004"; categorias[8] = "SATA"; especificacoes[8] = "SATA III - 50cm"; precos[8] = 35.0; estoques[8] = 8; estoquesMinimos[8] = 10; comprimentos[8] = 0.5;
        skus[9] = "SATA-ESATA-022"; categorias[9] = "SATA"; especificacoes[9] = "Cabo eSATA"; precos[9] = 42.0; estoques[9] = 5; estoquesMinimos[9] = 10;
        skus[10] = "USBC-100W-005"; categorias[10] = "USB-C"; especificacoes[10] = "Cabo USB-C 100W"; precos[10] = 45.0; estoques[10] = 45; estoquesMinimos[10] = 10;
        skus[11] = "FIB-LC-006"; categorias[11] = "Fibra Óptica"; especificacoes[11] = "Fibra LC-LC"; precos[11] = 75.0; estoques[11] = 25; estoquesMinimos[11] = 10;
        skus[12] = "COX-RG6-007"; categorias[12] = "Coaxial"; especificacoes[12] = "Cabo Coaxial RG6"; precos[12] = 18.0; estoques[12] = 70; estoquesMinimos[12] = 10;
        skus[13] = "RCA-AUDIO-008"; categorias[13] = "RCA"; especificacoes[13] = "Cabo RCA Áudio"; precos[13] = 22.0; estoques[13] = 30; estoquesMinimos[13] = 10;
        skus[14] = "RCA-3AV-032"; categorias[14] = "RCA"; especificacoes[14] = "Cabo 3x3 RCA Vídeo/Áudio"; precos[14] = 18.0; estoques[14] = 55; estoquesMinimos[14] = 10;
        skus[15] = "XLR-MIC-009"; categorias[15] = "XLR"; especificacoes[15] = "Cabo Microfone XLR"; precos[15] = 65.0; estoques[15] = 5; estoquesMinimos[15] = 10;
        skus[16] = "XLR-BAL-036"; categorias[16] = "XLR"; especificacoes[16] = "Cabo XLR Balanceado"; precos[16] = 115.0; estoques[16] = 11; estoquesMinimos[16] = 10;
        skus[17] = "MICUSB-V8-010"; categorias[17] = "Micro-USB"; especificacoes[17] = "Cabo Micro-USB V8"; precos[17] = 15.0; estoques[17] = 65; estoquesMinimos[17] = 10;
        skus[18] = "RED-CAT6-ROLO"; categorias[18] = "Rede"; especificacoes[18] = "Rolo Cat6 (caixa 305m)"; precos[18] = 3.50; estoques[18] = 305; estoquesMinimos[18] = 50; porMetro[18] = true;
        skus[19] = "RED-CAT5E-ROLO"; categorias[19] = "Rede"; especificacoes[19] = "Rolo Cat5e (caixa 305m)"; precos[19] = 2.20; estoques[19] = 305; estoquesMinimos[19] = 50; porMetro[19] = true;
        skus[20] = "COX-RG6-ROLO"; categorias[20] = "Coaxial"; especificacoes[20] = "Rolo Coaxial RG6 (100m)"; precos[20] = 2.80; estoques[20] = 100; estoquesMinimos[20] = 20; porMetro[20] = true;

        int totalProdutos = 21;

        for (int i = 0; i < totalProdutos; i++) {
            ncms[i] = 85444200;   
            cests[i] = 1200100;   
        }
        ncms[5] = 84718000;   
        ncms[11] = 85447010;  
        ncms[12] = 85442000;
        ncms[18] = 85444900;  
        ncms[19] = 85444900;  
        ncms[20] = 85442000;  

        int maxVendas = 50;
        double[] historicoQtd = new double[maxVendas];
        double[] historicoValores = new double[maxVendas];
        double[] historicoImpostos = new double[maxVendas];
        int[] historicoProduto = new int[maxVendas];
        int[] historicoMes = new int[maxVendas];
        String[] historicoUf = new String[maxVendas];
        String[] historicoDocumento = new String[maxVendas];
        int totalVendasRealizadas = 0;
        double totalVendidoGeral = 0;
        double totalImpostosGeral = 0;
        int numeroNfce = 0;
        int numeroNfe = 0;

        boolean sistemaAtivo = true;

        try {
            while (sistemaAtivo) {
                boolean logado = false;
                String perfilAtual = "";

                while (!logado && sistemaAtivo) {
                    System.out.println("\n==================================");
                    System.out.println("   LOGIN - SISTEMA DE ESTOQUE     ");
                    System.out.println("==================================");
                    System.out.println("Opções de acesso:");
                    System.out.println(" - ADMIN / senha: 123");
                    System.out.println(" - VENDEDOR / senha: 456");
                    System.out.println(" - Digite SAIR para encerrar o sistema");
                    System.out.println("----------------------------------");

                    System.out.print("Digite o usuário: ");
                    String usuario = entrada.nextLine();

                    if (usuario.equals("SAIR") || usuario.equals("sair")) {
                        sistemaAtivo = false;
                    } else {
                        System.out.print("Digite a senha: ");
                        String senha = entrada.nextLine();

                        perfilAtual = validarLogin(usuario, senha);
                        if (!perfilAtual.equals("")) {
                            logado = true;
                        } else {
                            System.out.println("\n[ERRO] Usuário ou senha incorretos. Tente novamente.");
                        }
                    }
                }

                if (logado) {
                    System.out.println("\nLogin efetuado com sucesso! Bem-vindo, " + perfilAtual + ".");
                    int opcao = 0;

                    if (perfilAtual.equals("ADMINISTRADOR")) {

                        int qtdAlertas = 0;
                        for (int i = 0; i < totalProdutos; i++) {
                            if (precisaRepor(estoques[i], estoquesMinimos[i])) {
                                if (qtdAlertas == 0) {
                                    System.out.println("\n!!! ALERTA DE ESTOQUE: produtos que precisam de reposição !!!");
                                }
                                String situacao;
                                if (situacaoEstoque(estoques[i], estoquesMinimos[i]).equals("ESGOTADO")) {
                                    situacao = "ESGOTADO";
                                } else {
                                    situacao = "BAIXO";
                                }
                                System.out.printf("- %s | %s | Restam: " + formatoQtd(porMetro[i]) + " (mínimo: "
                                    + formatoQtd(porMetro[i]) + ") | %s%n",
                                    skus[i], especificacoes[i], estoques[i], estoquesMinimos[i], situacao);
                                qtdAlertas++;
                            }
                        }
                        if (qtdAlertas == 0) {
                            System.out.println("\nNenhum produto abaixo do estoque mínimo.");
                        } else {
                            System.out.println("Total de produtos para repor: " + qtdAlertas);
                        }

                        do {
                            System.out.println("\n--- MENU ADMINISTRADOR ---");
                            System.out.println("1. Visão Geral do Estoque");
                            System.out.println("2. Estoque Detalhado e Tributação");
                            System.out.println("3. Cadastrar Novo Produto");
                            System.out.println("4. Relatório de Vendas");
                            System.out.println("5. Metragem em Estoque");
                            System.out.println("6. Fiscal e Relatórios (regime, mês, categoria, curva ABC)");
                            System.out.println("7. Sair / Deslogar");
                            opcao = lerInteiro(entrada, "Escolha uma opção: ");

                            switch (opcao) {
                                case 1: 
                                    System.out.println("\n--- VISÃO GERAL DO ESTOQUE ---");
                                    double totalUnidades = 0;
                                    double totalMetrosRolo = 0;
                                    int produtosBaixos = 0;
                                    int produtosEsgotados = 0;

                                    for (int i = 0; i < totalProdutos; i++) {
                                        if (porMetro[i]) {
                                            totalMetrosRolo += estoques[i];
                                        } else {
                                            totalUnidades += estoques[i];
                                        }
                                        String situacaoItem = situacaoEstoque(estoques[i], estoquesMinimos[i]);
                                        if (situacaoItem.equals("ESGOTADO")) {
                                            produtosEsgotados++;
                                        } else if (situacaoItem.equals("REPOSIÇÃO")) {
                                            produtosBaixos++;
                                        }
                                    }

                                    System.out.println("Total de tipos de produtos: " + totalProdutos);
                                    System.out.printf("Total de unidades físicas (cabos prontos): %.0f%n", totalUnidades);
                                    System.out.printf("Total de metros em rolos: %.2f m%n", totalMetrosRolo);
                                    System.out.println("Produtos com estoque baixo (<= mínimo): " + produtosBaixos);
                                    System.out.println("Produtos esgotados: " + produtosEsgotados);

                                    System.out.println("\nResumo por produto:");
                                    for (int i = 0; i < totalProdutos; i++) {
                                        System.out.printf("- %s | Estoque: " + formatoQtd(porMetro[i]) + " | Status: %s%n",
                                            especificacoes[i], estoques[i], situacaoEstoque(estoques[i], estoquesMinimos[i]));
                                    }
                                    break;

                                case 2: 
                                    System.out.println("\n--- ESTOQUE DETALHADO E TRIBUTÁVEL ---");
                                    double aliquotaInterna = aliquotaTotal(regime, aliquotaIcms("SP"));
                                    System.out.printf("Regime: %s | Impostos de venda interna (SP): %s = %.2f%%%n",
                                        nomeRegime(regime), descricaoTributos(regime, "SP"), aliquotaInterna * 100);
                                    double totalGeralEstoque = 0;

                                    for (int i = 0; i < totalProdutos; i++) {
                                        double precoBase = precos[i];
                                        double imposto = calcularImposto(precoBase, aliquotaInterna);
                                        double precoTotalUnidade = calcularTotal(precoBase, imposto);
                                        double valorTotalItem = calcularSubtotal(estoques[i], precoTotalUnidade);
                                        totalGeralEstoque = arredondar(totalGeralEstoque + valorTotalItem);

                                        String medida;
                                        if (porMetro[i]) {
                                            medida = "m";
                                        } else {
                                            medida = "un";
                                        }

                                        System.out.printf("Código: %s | Tipo: %s | Qtd: " + formatoQtd(porMetro[i])
                                            + " | Preço Base: R$ %.2f/%s | Imposto: R$ %.2f | Preço Total: R$ %.2f/%s | Total Geral: R$ %.2f | Status: %s | ",
                                            skus[i], categorias[i], estoques[i], precoBase, medida, imposto, precoTotalUnidade,
                                            medida, valorTotalItem, situacaoEstoque(estoques[i], estoquesMinimos[i]));
                                        imprimirNcmCest(ncms[i], cests[i]);
                                        System.out.println();
                                    }
                                    System.out.printf("\nTOTAL GERAL DO ESTOQUE: R$ %.2f%n", totalGeralEstoque);
                                    break;

                                case 3: 
                                    if (totalProdutos >= capacidade) {
                                        System.out.println("\n[ERRO] Capacidade máxima de cadastro atingida.");
                                    } else {
                                        System.out.println("\n--- CADASTRAR NOVO PRODUTO ---");
                                        
                                        String novoSku = "";
                                        boolean skuValido = false;
                                        while (!skuValido) {
                                            System.out.print("Digite o código (SKU): ");
                                            novoSku = entrada.nextLine();
                                            if (novoSku.equals("")) {
                                                System.out.println("[ERRO] O código não pode ficar vazio.");
                                            } else if (skuExiste(skus, totalProdutos, novoSku)) {
                                                System.out.println("[ERRO] Já existe um produto com esse código.");
                                            } else {
                                                skuValido = true;
                                            }
                                        }

                                        String novaCategoria = "";
                                        while (novaCategoria.equals("")) {
                                            System.out.print("Digite o tipo/categoria: ");
                                            novaCategoria = entrada.nextLine();
                                            if (novaCategoria.equals("")) {
                                                System.out.println("[ERRO] A categoria não pode ficar vazia.");
                                            }
                                        }

                                        String novaEspecificacao = "";
                                        while (novaEspecificacao.equals("")) {
                                            System.out.print("Digite a especificação/nome: ");
                                            novaEspecificacao = entrada.nextLine();
                                            if (novaEspecificacao.equals("")) {
                                                System.out.println("[ERRO] A especificação não pode ficar vazia.");
                                            }
                                        }

                                        int novoNcm = 0;
                                        while (!ncmValido(novoNcm)) {
                                            novoNcm = lerInteiro(entrada, "Digite o NCM, só os 8 números (ex.: 85444200): ");
                                            if (!ncmValido(novoNcm)) {
                                                System.out.println("[ERRO] O NCM deve ter até 8 dígitos e ser maior que zero.");
                                            }
                                        }
                                        int novoCest = 0;
                                        while (!cestValido(novoCest)) {
                                            novoCest = lerInteiro(entrada, "Digite o CEST, só os 7 números (ex.: 1200100): ");
                                            if (!cestValido(novoCest)) {
                                                System.out.println("[ERRO] O CEST deve ter até 7 dígitos e ser maior que zero.");
                                            }
                                        }

                                        int formaVenda = 0;
                                        while (formaVenda != 1 && formaVenda != 2) {
                                            formaVenda = lerInteiro(entrada, "Forma de venda (1 = por unidade | 2 = por metro/rolo): ");
                                            if (formaVenda != 1 && formaVenda != 2) {
                                                System.out.println("[ERRO] Digite 1 ou 2.");
                                            }
                                        }
                                        boolean novoPorMetro = formaVenda == 2;
                                        String medidaPreco;
                                        if (novoPorMetro) {
                                            medidaPreco = "metro";
                                        } else {
                                            medidaPreco = "unidade";
                                        }

                                        double novoPreco = 0;
                                        while (!precoValido(novoPreco, precoMaximo)) {
                                            novoPreco = lerDecimal(entrada, "Digite o preço base por " + medidaPreco + " (ex.: 49,90): ");
                                            if (!precoValido(novoPreco, precoMaximo)) {
                                                System.out.println("[ERRO] O preço deve ser maior que zero e até R$ 1.000.000,00.");
                                            }
                                        }

                                        double novoEstoque = -1;
                                        double novoMinimo = -1;
                                        double novoComprimento = -1;

                                        if (novoPorMetro) {
                                            while (novoEstoque < 0 || novoEstoque > quantidadeMaxima) {
                                                novoEstoque = lerDecimal(entrada, "Digite a metragem inicial do rolo (m): ");
                                                if (novoEstoque < 0 || novoEstoque > quantidadeMaxima) {
                                                    System.out.println("[ERRO] A metragem deve estar entre 0 e " + quantidadeMaxima + " m.");
                                                }
                                            }
                                            while (novoMinimo < 0 || novoMinimo > quantidadeMaxima) {
                                                novoMinimo = lerDecimal(entrada, "Digite a metragem mínima para alerta (m): ");
                                                if (novoMinimo < 0 || novoMinimo > quantidadeMaxima) {
                                                    System.out.println("[ERRO] O mínimo deve estar entre 0 e " + quantidadeMaxima + " m.");
                                                }
                                            }
                                            novoEstoque = arredondar(novoEstoque);
                                            novoMinimo = arredondar(novoMinimo);
                                            novoComprimento = 0;
                                        } else {
                                            while (novoEstoque < 0 || novoEstoque > quantidadeMaxima) {
                                                novoEstoque = lerInteiro(entrada, "Digite a quantidade inicial: ");
                                                if (novoEstoque < 0 || novoEstoque > quantidadeMaxima) {
                                                    System.out.println("[ERRO] A quantidade deve estar entre 0 e " + quantidadeMaxima + ".");
                                                }
                                            }
                                            while (novoMinimo < 0 || novoMinimo > quantidadeMaxima) {
                                                novoMinimo = lerInteiro(entrada, "Digite o limite de estoque mínimo para alerta: ");
                                                if (novoMinimo < 0 || novoMinimo > quantidadeMaxima) {
                                                    System.out.println("[ERRO] O mínimo deve estar entre 0 e " + quantidadeMaxima + ".");
                                                }
                                            }
                                            while (novoComprimento < 0 || novoComprimento > 1000) {
                                                novoComprimento = lerDecimal(entrada, "Digite o comprimento de cada cabo em metros (0 se não se aplica): ");
                                                if (novoComprimento < 0 || novoComprimento > 1000) {
                                                    System.out.println("[ERRO] O comprimento deve estar entre 0 e 1000 m.");
                                                }
                                            }
                                        }

                                        skus[totalProdutos] = novoSku;
                                        categorias[totalProdutos] = novaCategoria;
                                        especificacoes[totalProdutos] = novaEspecificacao;
                                        precos[totalProdutos] = novoPreco;
                                        estoques[totalProdutos] = novoEstoque;
                                        estoquesMinimos[totalProdutos] = novoMinimo;
                                        porMetro[totalProdutos] = novoPorMetro;
                                        comprimentos[totalProdutos] = novoComprimento;
                                        ncms[totalProdutos] = novoNcm;
                                        cests[totalProdutos] = novoCest;
                                        totalProdutos++;
                                        System.out.println("\nProduto cadastrado com sucesso e integrado ao estoque geral!");
                                    }
                                    break;

                                case 4: 
                                    System.out.println("\n--- RELATÓRIO DE VENDAS ---");
                                    if (totalVendasRealizadas == 0) {
                                        System.out.println("Nenhuma venda realizada até o momento.");
                                    } else {
                                        for (int i = 0; i < totalVendasRealizadas; i++) {
                                            int p = historicoProduto[i];
                                            System.out.printf("Venda #%d: %s | %s | mês %d | " + formatoQtd(porMetro[p])
                                                + " de %s - Valor: R$ %.2f (impostos: R$ %.2f)%n",
                                                i + 1, historicoDocumento[i], historicoUf[i], historicoMes[i], historicoQtd[i],
                                                especificacoes[p], historicoValores[i], historicoImpostos[i]);
                                        }
                                        System.out.println("Quantidade de vendas: " + totalVendasRealizadas);
                                        System.out.printf("Faturamento total: R$ %.2f%n", totalVendidoGeral);
                                        System.out.printf("Impostos recolhidos: R$ %.2f%n", totalImpostosGeral);
                                    }
                                    break;

                                case 5: 
                                    System.out.println("\n--- METRAGEM EM ESTOQUE ---");
                                    double metrosRolos = 0;
                                    double metrosCabos = 0;

                                    System.out.println("Rolos (venda por metro):");
                                    for (int i = 0; i < totalProdutos; i++) {
                                        if (porMetro[i]) {
                                            double inicial = arredondar(estoques[i] + vendidos[i]);
                                            String status;
                                            if (precisaRepor(estoques[i], estoquesMinimos[i])) {
                                                status = "REPOR";
                                            } else {
                                                status = "OK";
                                            }
                                            System.out.printf("- %s | Sobram: %.2f m de %.2f m (%.1f%%) | Vendidos: %.2f m | Mínimo: %.2f m | %s%n",
                                                especificacoes[i], estoques[i], inicial, percentualRestante(estoques[i], inicial),
                                                vendidos[i], estoquesMinimos[i], status);
                                            metrosRolos += estoques[i];
                                        }
                                    }

                                    System.out.println("\nCabos prontos (unidades x comprimento):");
                                    for (int i = 0; i < totalProdutos; i++) {
                                        if (!porMetro[i]) {
                                            if (comprimentos[i] > 0) {
                                                double metros = metragemDeCabos(estoques[i], comprimentos[i]);
                                                System.out.printf("- %s | %.0f un x %.2f m = %.2f m%n",
                                                    especificacoes[i], estoques[i], comprimentos[i], metros);
                                                metrosCabos += metros;
                                            } else {
                                                System.out.printf("- %s | %.0f un | comprimento não informado%n",
                                                    especificacoes[i], estoques[i]);
                                            }
                                        }
                                    }

                                    System.out.printf("%nTotal em rolos: %.2f m%n", metrosRolos);
                                    System.out.printf("Total em cabos prontos: %.2f m%n", metrosCabos);
                                    System.out.printf("METRAGEM TOTAL EM ESTOQUE: %.2f m%n", metrosRolos + metrosCabos);
                                    break;

                                case 6: 
                                    int opcaoFiscal;
                                    do {
                                        System.out.println("\n--- FISCAL E RELATÓRIOS ---");
                                        System.out.println("Regime atual: " + nomeRegime(regime));
                                        System.out.println("1. Definir regime tributário");
                                        System.out.println("2. Vendas por mês");
                                        System.out.println("3. Vendas por categoria");
                                        System.out.println("4. Curva ABC");
                                        System.out.println("0. Voltar");
                                        opcaoFiscal = lerInteiro(entrada, "Escolha uma opção: ");

                                        switch (opcaoFiscal) {
                                            case 1: 
                                                System.out.println("\n1 = Simples Nacional | 2 = Lucro Presumido | 3 = Lucro Real");
                                                int novoRegime = 0;
                                                while (novoRegime < 1 || novoRegime > 3) {
                                                    novoRegime = lerInteiro(entrada, "Escolha o regime: ");
                                                    if (novoRegime < 1 || novoRegime > 3) {
                                                        System.out.println("[ERRO] Digite 1, 2 ou 3.");
                                                    }
                                                }
                                                regime = novoRegime;
                                                System.out.println("Regime definido: " + nomeRegime(regime)
                                                    + " | Venda interna: " + descricaoTributos(regime, "SP")
                                                    + " | Interestadual: " + descricaoTributos(regime, "RJ"));
                                                break;

                                            case 2: 
                                                int mesFiltro = 0;
                                                while (!mesValido(mesFiltro)) {
                                                    mesFiltro = lerInteiro(entrada, "Digite o mês (1 a 12): ");
                                                    if (!mesValido(mesFiltro)) {
                                                        System.out.println("[ERRO] O mês deve estar entre 1 e 12.");
                                                    }
                                                }
                                                System.out.println("\n--- VENDAS DO MÊS " + mesFiltro + " ---");
                                                double totalMes = 0;
                                                double impostosMes = 0;
                                                int vendasMes = 0;
                                                for (int i = 0; i < totalVendasRealizadas; i++) {
                                                    if (historicoMes[i] == mesFiltro) {
                                                        int p = historicoProduto[i];
                                                        System.out.printf("- %s | %s | " + formatoQtd(porMetro[p]) + " de %s - R$ %.2f%n",
                                                            historicoDocumento[i], historicoUf[i], historicoQtd[i], especificacoes[p], historicoValores[i]);
                                                        totalMes = arredondar(totalMes + historicoValores[i]);
                                                        impostosMes = arredondar(impostosMes + historicoImpostos[i]);
                                                        vendasMes++;
                                                    }
                                                }
                                                System.out.printf("Vendas no mês %d: %d | Faturamento: R$ %.2f | Impostos: R$ %.2f%n",
                                                    mesFiltro, vendasMes, totalMes, impostosMes);
                                                break;

                                            case 3: 
                                                System.out.println("\n--- VENDAS POR CATEGORIA ---");
                                                if (totalVendasRealizadas == 0) {
                                                    System.out.println("Nenhuma venda realizada até o momento.");
                                                } else {
                                                    double maiorCategoria = -1;
                                                    String nomeMaior = "";
                                                    for (int c = 0; c < totalProdutos; c++) {                                
                                                        boolean jaMostrada = false;
                                                        for (int j = 0; j < c; j++) {
                                                            if (categorias[j].equals(categorias[c])) {
                                                                jaMostrada = true;
                                                            }
                                                        }
                                                        if (!jaMostrada) {
                                                            double totalCategoria = 0;
                                                            int vendasCategoria = 0;
                                                            for (int i = 0; i < totalVendasRealizadas; i++) {
                                                                if (categorias[historicoProduto[i]].equals(categorias[c])) {
                                                                    totalCategoria = arredondar(totalCategoria + historicoValores[i]);
                                                                    vendasCategoria++;
                                                                }
                                                            }
                                                            System.out.printf("- %s: %d venda(s) | R$ %.2f (%.1f%%)%n", categorias[c],
                                                                vendasCategoria, totalCategoria, participacao(totalCategoria, totalVendidoGeral));
                                                            if (totalCategoria > maiorCategoria) {
                                                                maiorCategoria = totalCategoria;
                                                                nomeMaior = categorias[c];
                                                            }
                                                        }
                                                    }
                                                    System.out.println("Categoria com maior faturamento: " + nomeMaior);
                                                    double totalHdmi = 0;
                                                    double totalUsb = 0;
                                                    for (int i = 0; i < totalVendasRealizadas; i++) {
                                                        String cat = categorias[historicoProduto[i]];
                                                        if (cat.equals("HDMI")) {
                                                            totalHdmi = arredondar(totalHdmi + historicoValores[i]);
                                                        } else if (cat.equals("USB")) {
                                                            totalUsb = arredondar(totalUsb + historicoValores[i]);
                                                        }
                                                    }
                                                    if (totalHdmi > totalUsb) {
                                                        System.out.printf("Comparativo: HDMI (R$ %.2f) vendeu mais que USB (R$ %.2f).%n", totalHdmi, totalUsb);
                                                    } else if (totalUsb > totalHdmi) {
                                                        System.out.printf("Comparativo: USB (R$ %.2f) vendeu mais que HDMI (R$ %.2f).%n", totalUsb, totalHdmi);
                                                    } else {
                                                        System.out.printf("Comparativo: HDMI e USB empatados (R$ %.2f).%n", totalHdmi);
                                                    }
                                                }
                                                break;

                                            case 4:
                                                System.out.println("\n--- CURVA ABC (por faturamento) ---");
                                                if (totalVendasRealizadas == 0) {
                                                    System.out.println("Nenhuma venda realizada até o momento.");
                                                } else {
                                                
                                                    double[] faturadoProduto = new double[totalProdutos];
                                                    for (int i = 0; i < totalVendasRealizadas; i++) {
                                                        int p = historicoProduto[i];
                                                        faturadoProduto[p] = arredondar(faturadoProduto[p] + historicoValores[i]);
                                                    }
   
                                                    int[] ordem = new int[totalProdutos];
                                                    for (int i = 0; i < totalProdutos; i++) {
                                                        ordem[i] = i;
                                                    }
                                                    for (int i = 0; i < totalProdutos - 1; i++) {
                                                        for (int j = 0; j < totalProdutos - 1 - i; j++) {
                                                            if (faturadoProduto[ordem[j]] < faturadoProduto[ordem[j + 1]]) {
                                                                int troca = ordem[j];
                                                                ordem[j] = ordem[j + 1];
                                                                ordem[j + 1] = troca;
                                                            }
                                                        }
                                                    }
       
                                                    double acumulado = 0;
                                                    int qtdA = 0;
                                                    int qtdB = 0;
                                                    int qtdC = 0;
                                                    for (int i = 0; i < totalProdutos; i++) {
                                                        int p = ordem[i];
                                                        String classe = classeABC(acumulado);
                                                        double parte = participacao(faturadoProduto[p], totalVendidoGeral);
                                                        acumulado += parte;
                                                        if (classe.equals("A")) {
                                                            qtdA++;
                                                        } else if (classe.equals("B")) {
                                                            qtdB++;
                                                        } else {
                                                            qtdC++;
                                                        }
                                                        if (faturadoProduto[p] > 0) {
                                                            System.out.printf("%s | %s | R$ %.2f | %.1f%% | acumulado %.1f%% | Classe %s%n",
                                                                classe, skus[p], faturadoProduto[p], parte, acumulado, classe);
                                                        }
                                                    }
                                                    System.out.println("Produtos sem venda entram na classe C.");
                                                    System.out.printf("Resumo: %d produto(s) A | %d B | %d C%n", qtdA, qtdB, qtdC);
                                                }
                                                break;

                                            case 0:
                                                break;

                                            default:
                                                System.out.println("Opção inválida! Escolha entre 0 e 4.");
                                        }
                                    } while (opcaoFiscal != 0);
                                    break;

                                case 7:
                                    System.out.println("Deslogando...");
                                    break;

                                default:
                                    System.out.println("Opção inválida! Escolha entre 1 e 7.");
                            }
                        } while (opcao != 7);
                    }
   
                    else if (perfilAtual.equals("VENDEDOR")) {
                        do {
                            System.out.println("\n--- MENU VENDEDOR ---");
                            System.out.println("1. Visão Geral (Vendas realizadas e total vendido)");
                            System.out.println("2. Realizar Nova Venda");
                            System.out.println("3. Verificar Todas as Vendas Realizadas");
                            System.out.println("4. Sair / Deslogar");
                            opcao = lerInteiro(entrada, "Escolha uma opção: ");

                            switch (opcao) {
                                case 1:
                                    System.out.println("\n--- VISÃO GERAL DE VENDAS ---");
                                    System.out.println("Quantidade total de vendas realizadas: " + totalVendasRealizadas);
                                    System.out.printf("Valor total vendido (Geral / Dia): R$ %.2f%n", totalVendidoGeral);
                                    break;

                                case 2: 
                                    if (totalVendasRealizadas >= maxVendas) {
                                        System.out.println("\n[ERRO] Limite de histórico de vendas atingido. Nenhuma venda pode ser registrada.");
                                    } else {
                                        System.out.println("\n--- PRODUTOS DISPONÍVEIS ---");
                                        double aliquotaLista = aliquotaTotal(regime, aliquotaIcms("SP"));
                                        System.out.println("(preços com impostos de venda em SP, regime " + nomeRegime(regime) + ")");
                                        for (int i = 0; i < totalProdutos; i++) {
                                            double precoComImposto = calcularTotal(precos[i], calcularImposto(precos[i], aliquotaLista));
                                            String medida;
                                            if (porMetro[i]) {
                                                medida = "m";
                                            } else {
                                                medida = "un";
                                            }
                                            System.out.printf("[%d] %s - %s | Preço (com impostos): R$ %.2f/%s | Estoque: " + formatoQtd(porMetro[i]) + "%n",
                                                i, skus[i], especificacoes[i], precoComImposto, medida, estoques[i]);
                                        }

                                        int prodIndex = lerInteiro(entrada, "\nDigite o código (índice) do produto que deseja vender: ");

                                        if (prodIndex < 0 || prodIndex >= totalProdutos) {
                                            System.out.println("\n[ERRO] Produto inválido.");
                                        } else if (estoques[prodIndex] == 0) {
                                            System.out.println("\n[ERRO] Produto esgotado.");
                                        } else {
                                            
                                            double qtdVenda;
                                            if (porMetro[prodIndex]) {
                                                qtdVenda = arredondar(lerDecimal(entrada, "Digite a metragem desejada (ex.: 7,5): "));
                                            } else {
                                                qtdVenda = lerInteiro(entrada, "Digite a quantidade desejada: ");
                                            }

                                            if (!quantidadeValida(qtdVenda, estoques[prodIndex])) {
                                                System.out.println("\n[ERRO] Quantidade inválida ou estoque insuficiente.");
                                            } else {
                                              
                                                String ufDestino = "";
                                                while (!ufValida(ufDestino)) {
                                                    System.out.print("UF de destino, em maiúsculas (ex.: SP, RJ, MG): ");
                                                    ufDestino = entrada.nextLine();
                                                    if (!ufValida(ufDestino)) {
                                                        System.out.println("[ERRO] UF inválida. Digite a sigla do estado com 2 letras maiúsculas.");
                                                    }
                                                }

                                                boolean balcao = false;
                                                if (ufDestino.equals("SP")) {
                                                    String resposta = "";
                                                    while (!respostaValida(resposta)) {
                                                        System.out.print("Venda de balcão para consumidor final? (S/N): ");
                                                        resposta = entrada.nextLine();
                                                        if (!respostaValida(resposta)) {
                                                            System.out.println("[ERRO] Responda S ou N.");
                                                        }
                                                    }
                                                    balcao = respostaSim(resposta);
                                                }

                                                int mesVenda = 0;
                                                while (!mesValido(mesVenda)) {
                                                    mesVenda = lerInteiro(entrada, "Mês da venda (1 a 12): ");
                                                    if (!mesValido(mesVenda)) {
                                                        System.out.println("[ERRO] O mês deve estar entre 1 e 12.");
                                                    }
                                                }

                                                double aliquota = aliquotaTotal(regime, aliquotaIcms(ufDestino));
                                                double subtotal = calcularSubtotal(qtdVenda, precos[prodIndex]);
                                                double impostoVenda = calcularImposto(subtotal, aliquota);
                                                double valorVenda = calcularTotal(subtotal, impostoVenda);

                                                String tipo = tipoDocumento(ufDestino, balcao);
                                                int numero;
                                                if (tipo.equals("NFC-e")) {
                                                    numeroNfce++;
                                                    numero = numeroNfce;
                                                } else {
                                                    numeroNfe++;
                                                    numero = numeroNfe;
                                                }
                                                String documento = tipo + " nº " + numero;

                                                estoques[prodIndex] = arredondar(estoques[prodIndex] - qtdVenda);
                                                vendidos[prodIndex] = arredondar(vendidos[prodIndex] + qtdVenda);

                                                historicoQtd[totalVendasRealizadas] = qtdVenda;
                                                historicoValores[totalVendasRealizadas] = valorVenda;
                                                historicoImpostos[totalVendasRealizadas] = impostoVenda;
                                                historicoProduto[totalVendasRealizadas] = prodIndex;
                                                historicoMes[totalVendasRealizadas] = mesVenda;
                                                historicoUf[totalVendasRealizadas] = ufDestino;
                                                historicoDocumento[totalVendasRealizadas] = documento;
                                                totalVendasRealizadas++;
                                                totalVendidoGeral = arredondar(totalVendidoGeral + valorVenda);
                                                totalImpostosGeral = arredondar(totalImpostosGeral + impostoVenda);

                                                String operacao;
                                                if (ufDestino.equals("SP")) {
                                                    operacao = "(interna)";
                                                } else {
                                                    operacao = "(interestadual)";
                                                }
                                                System.out.println("\nVenda realizada com sucesso!");
                                                System.out.println("========== " + documento + " ==========");
                                                System.out.printf("Produto: " + formatoQtd(porMetro[prodIndex]) + " de %s | ",
                                                    qtdVenda, especificacoes[prodIndex]);
                                                imprimirNcmCest(ncms[prodIndex], cests[prodIndex]);
                                                System.out.println();
                                                System.out.println("Destino: " + ufDestino + " " + operacao + " | Mês: " + mesVenda
                                                    + " | Regime: " + nomeRegime(regime));
                                                System.out.println("Tributos: " + descricaoTributos(regime, ufDestino));
                                                System.out.printf("Subtotal: R$ %.2f | Impostos (%.2f%%): R$ %.2f | Total: R$ %.2f%n",
                                                    subtotal, aliquota * 100, impostoVenda, valorVenda);

                                                if (porMetro[prodIndex]) {
                                                    System.out.printf("Metragem que sobra no rolo: %.2f m%n", estoques[prodIndex]);
                                                }
                                                if (precisaRepor(estoques[prodIndex], estoquesMinimos[prodIndex])) {
                                                    System.out.printf("ALERTA: estoque baixo para %s (restam " + formatoQtd(porMetro[prodIndex])
                                                        + "). Necessário reposição.%n", especificacoes[prodIndex], estoques[prodIndex]);
                                                }
                                            }
                                        }
                                    }
                                    break;

                                case 3:
                                    System.out.println("\n--- HISTÓRICO DE VENDAS REALIZADAS ---");
                                    if (totalVendasRealizadas == 0) {
                                        System.out.println("Nenhuma venda realizada até o momento.");
                                    } else {
                                        for (int i = 0; i < totalVendasRealizadas; i++) {
                                            int p = historicoProduto[i];
                                            System.out.printf("Venda #%d: %s | " + formatoQtd(porMetro[p]) + " de %s - Valor: R$ %.2f%n",
                                                i + 1, historicoDocumento[i], historicoQtd[i], especificacoes[p], historicoValores[i]);
                                        }
                                        System.out.printf("\nTotal Acumulado Vendido: R$ %.2f%n", totalVendidoGeral);
                                    }
                                    break;

                                case 4:
                                    System.out.println("Deslogando...");
                                    break;

                                default:
                                    System.out.println("Opção inválida! Escolha entre 1 e 4.");
                            }
                        } while (opcao != 4);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("\n\nEntrada encerrada. Sistema finalizado.");
        }

        System.out.println("\nSistema encerrado.");
        entrada.close();
    }
}
