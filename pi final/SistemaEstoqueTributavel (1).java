import java.util.NoSuchElementException;
import java.util.Scanner;

public class SistemaEstoqueTributavel {

    // Converte texto em número aceitando vírgula ou ponto (49,90 ou 49.90). Retorna -1 se inválido.
    static double lerNumero(String texto) {
        try {
            return Double.parseDouble(texto.trim().replace(",", "."));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    // Arredonda para 2 casas (evita sobras como 297.49999999 metros)
    static double arredondar(double valor) {
        return Math.round(valor * 100) / 100.0;
    }

    // Mostra a quantidade com a unidade certa: "50 un" ou "305,00 m"
    static String formatarQtd(double valor, boolean metro) {
        if (metro) {
            return String.format("%.2f m", valor);
        }
        return String.format("%.0f un", valor);
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // Capacidade aumentada para suportar a lista completa de produtos
        int capacidade = 30;
        String[] skus = new String[capacidade];
        String[] categorias = new String[capacidade];
        String[] especificacoes = new String[capacidade];
        double[] precos = new double[capacidade];          // por unidade ou por metro
        double[] estoques = new double[capacidade];        // unidades ou metros
        double[] estoquesMinimos = new double[capacidade]; // unidades ou metros
        boolean[] porMetro = new boolean[capacidade];      // true = rolo vendido por metro
        double[] comprimentos = new double[capacidade];    // metros de cada cabo pronto (0 = não informado)
        double[] vendidos = new double[capacidade];        // unidades ou metros já vendidos

        // Limites de segurança para evitar valores absurdos (e estouro de soma)
        double precoMaximo = 1000000.0;
        int quantidadeMaxima = 100000;

        // Alíquota total de impostos (ICMS 18% + PIS 1,65% + COFINS 7,6% = 27,25%)
        double aliquotaImposto = 0.2725;

        // ---- Cabos prontos (venda por UNIDADE) ----
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

        // ---- Rolos (venda por METRO): preço por metro, estoque e mínimo em metros ----
        skus[18] = "RED-CAT6-ROLO"; categorias[18] = "Rede"; especificacoes[18] = "Rolo Cat6 (caixa 305m)"; precos[18] = 3.50; estoques[18] = 305; estoquesMinimos[18] = 50; porMetro[18] = true;
        skus[19] = "RED-CAT5E-ROLO"; categorias[19] = "Rede"; especificacoes[19] = "Rolo Cat5e (caixa 305m)"; precos[19] = 2.20; estoques[19] = 305; estoquesMinimos[19] = 50; porMetro[19] = true;
        skus[20] = "COX-RG6-ROLO"; categorias[20] = "Coaxial"; especificacoes[20] = "Rolo Coaxial RG6 (100m)"; precos[20] = 2.80; estoques[20] = 100; estoquesMinimos[20] = 20; porMetro[20] = true;

        int totalProdutos = 21;

        // Estrutura para controle de vendas (compartilhada entre Vendedor e Administrador)
        int maxVendas = 50;
        String[] historicoDescricao = new String[maxVendas];
        double[] historicoValores = new double[maxVendas];
        double[] historicoImpostos = new double[maxVendas];
        int totalVendasRealizadas = 0;
        double totalVendidoGeral = 0;
        double totalImpostosGeral = 0;

        boolean sistemaAtivo = true;

        // O try/catch evita que o programa quebre se a entrada for encerrada (Ctrl+D / Ctrl+Z)
        try {
            // Volta para o login sempre que alguém desloga, mantendo os dados na memória
            while (sistemaAtivo) {
                boolean logado = false;
                String perfilAtual = "";

                // ================= TELA DE LOGIN =================
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
                    String usuario = entrada.nextLine().trim().toUpperCase();

                    if (usuario.equals("SAIR")) {
                        sistemaAtivo = false;
                    } else {
                        System.out.print("Digite a senha: ");
                        String senha = entrada.nextLine().trim();

                        if (usuario.equals("ADMIN") && senha.equals("123")) {
                            perfilAtual = "ADMINISTRADOR";
                            logado = true;
                        } else if (usuario.equals("VENDEDOR") && senha.equals("456")) {
                            perfilAtual = "VENDEDOR";
                            logado = true;
                        } else {
                            System.out.println("\n[ERRO] Usuário ou senha incorretos. Tente novamente.");
                        }
                    }
                }

                if (!sistemaAtivo) {
                    break;
                }

                System.out.println("\nLogin efetuado com sucesso! Bem-vindo, " + perfilAtual + ".");

                int opcao = 0;

                // ================= MENU DO ADMINISTRADOR =================
                if (perfilAtual.equals("ADMINISTRADOR")) {

                    // Alerta automático: ao entrar, o administrador já vê o que precisa repor
                    int qtdAlertas = 0;
                    for (int i = 0; i < totalProdutos; i++) {
                        if (estoques[i] <= estoquesMinimos[i]) {
                            if (qtdAlertas == 0) {
                                System.out.println("\n!!! ALERTA DE ESTOQUE: produtos que precisam de reposição !!!");
                            }
                            String situacao = (estoques[i] == 0) ? "ESGOTADO" : "BAIXO";
                            System.out.println("- " + skus[i] + " | " + especificacoes[i] + " | Restam: "
                                + formatarQtd(estoques[i], porMetro[i]) + " (mínimo: "
                                + formatarQtd(estoquesMinimos[i], porMetro[i]) + ") | " + situacao);
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
                        System.out.println("6. Sair / Deslogar");
                        System.out.print("Escolha uma opção: ");

                        // Leitura segura: se não for número, avisa e pede de novo (sem quebrar)
                        while (!entrada.hasNextInt()) {
                            entrada.nextLine(); // descarta a linha inválida
                            System.out.print("[ERRO] Digite apenas o número da opção: ");
                        }
                        opcao = entrada.nextInt();
                        entrada.nextLine(); // limpa o restante da linha

                        switch (opcao) {
                            case 1: // Visão Geral do Estoque
                                System.out.println("\n--- VISÃO GERAL DO ESTOQUE ---");
                                int totalUnidades = 0;
                                double totalMetrosRolo = 0;
                                int produtosBaixos = 0;
                                int produtosEsgotados = 0;

                                for (int i = 0; i < totalProdutos; i++) {
                                    if (porMetro[i]) {
                                        totalMetrosRolo += estoques[i];
                                    } else {
                                        totalUnidades += (int) estoques[i];
                                    }
                                    if (estoques[i] == 0) {
                                        produtosEsgotados++;
                                    } else if (estoques[i] <= estoquesMinimos[i]) {
                                        produtosBaixos++;
                                    }
                                }

                                System.out.println("Total de tipos de produtos: " + totalProdutos);
                                System.out.println("Total de unidades físicas (cabos prontos): " + totalUnidades);
                                System.out.printf("Total de metros em rolos: %.2f m%n", totalMetrosRolo);
                                System.out.println("Produtos com estoque baixo (<= mínimo): " + produtosBaixos);
                                System.out.println("Produtos esgotados: " + produtosEsgotados);

                                System.out.println("\nResumo por produto:");
                                for (int i = 0; i < totalProdutos; i++) {
                                    String situacao = "OK";
                                    if (estoques[i] == 0) situacao = "ESGOTADO";
                                    else if (estoques[i] <= estoquesMinimos[i]) situacao = "REPOSIÇÃO";

                                    System.out.println("- " + especificacoes[i] + " | Estoque: "
                                        + formatarQtd(estoques[i], porMetro[i]) + " | Status: " + situacao);
                                }
                                break;

                            case 2: // Estoque Detalhado e Tributação
                                System.out.println("\n--- ESTOQUE DETALHADO E TRIBUTÁVEL ---");
                                double totalGeralEstoque = 0;

                                for (int i = 0; i < totalProdutos; i++) {
                                    double precoBase = precos[i];
                                    double imposto = precoBase * aliquotaImposto;
                                    double precoTotalUnidade = precoBase + imposto;
                                    double valorTotalItem = precoTotalUnidade * estoques[i];
                                    totalGeralEstoque += valorTotalItem;

                                    String status = (estoques[i] > estoquesMinimos[i]) ? "OK" : "REPOSIÇÃO";
                                    if (estoques[i] == 0) status = "ESGOTADO";
                                    String medida = porMetro[i] ? "m" : "un";

                                    System.out.printf("Código: %s | Tipo: %s | Qtd: %s | Preço Base: R$ %.2f/%s | Imposto: R$ %.2f | Preço Total: R$ %.2f/%s | Total Geral: R$ %.2f | Status: %s%n",
                                        skus[i], categorias[i], formatarQtd(estoques[i], porMetro[i]), precoBase, medida,
                                        imposto, precoTotalUnidade, medida, valorTotalItem, status);
                                }
                                System.out.printf("\nTOTAL GERAL DO ESTOQUE: R$ %.2f%n", totalGeralEstoque);
                                break;

                            case 3: // Cadastrar Novo Produto
                                if (totalProdutos >= capacidade) {
                                    System.out.println("\n[ERRO] Capacidade máxima de cadastro atingida.");
                                } else {
                                    System.out.println("\n--- CADASTRAR NOVO PRODUTO ---");

                                    // ---- SKU: não pode ser vazio nem repetido ----
                                    String novoSku = "";
                                    boolean skuValido = false;
                                    while (!skuValido) {
                                        System.out.print("Digite o código (SKU): ");
                                        novoSku = entrada.nextLine().trim().toUpperCase();

                                        if (novoSku.equals("")) {
                                            System.out.println("[ERRO] O código não pode ficar vazio.");
                                        } else {
                                            boolean repetido = false;
                                            for (int i = 0; i < totalProdutos; i++) {
                                                if (skus[i].equals(novoSku)) {
                                                    repetido = true;
                                                }
                                            }
                                            if (repetido) {
                                                System.out.println("[ERRO] Já existe um produto com esse código.");
                                            } else {
                                                skuValido = true;
                                            }
                                        }
                                    }

                                    // ---- Categoria: não pode ser vazia ----
                                    String novaCategoria = "";
                                    while (novaCategoria.equals("")) {
                                        System.out.print("Digite o tipo/categoria: ");
                                        novaCategoria = entrada.nextLine().trim();
                                        if (novaCategoria.equals("")) {
                                            System.out.println("[ERRO] A categoria não pode ficar vazia.");
                                        }
                                    }

                                    // ---- Especificação: não pode ser vazia ----
                                    String novaEspecificacao = "";
                                    while (novaEspecificacao.equals("")) {
                                        System.out.print("Digite a especificação/nome: ");
                                        novaEspecificacao = entrada.nextLine().trim();
                                        if (novaEspecificacao.equals("")) {
                                            System.out.println("[ERRO] A especificação não pode ficar vazia.");
                                        }
                                    }

                                    // ---- Forma de venda: unidade ou metro ----
                                    int formaVenda = 0;
                                    while (formaVenda != 1 && formaVenda != 2) {
                                        System.out.print("Forma de venda (1 = por unidade | 2 = por metro/rolo): ");
                                        if (entrada.hasNextInt()) {
                                            formaVenda = entrada.nextInt();
                                            if (formaVenda != 1 && formaVenda != 2) {
                                                System.out.println("[ERRO] Digite 1 ou 2.");
                                            }
                                        } else {
                                            System.out.println("[ERRO] Digite 1 ou 2.");
                                        }
                                        entrada.nextLine();
                                    }
                                    boolean novoPorMetro = (formaVenda == 2);
                                    String medida = novoPorMetro ? "metro" : "unidade";

                                    // ---- Preço: aceita vírgula ou ponto como decimal ----
                                    double novoPreco = 0;
                                    boolean precoValido = false;
                                    while (!precoValido) {
                                        System.out.print("Digite o preço base por " + medida + " (ex.: 49,90): ");
                                        novoPreco = lerNumero(entrada.nextLine());
                                        if (novoPreco > 0 && novoPreco <= precoMaximo) {
                                            precoValido = true;
                                        } else {
                                            System.out.println("[ERRO] Digite um preço maior que zero e até R$ " + precoMaximo
                                                + ", sem separador de milhar (ex.: 1500,00).");
                                        }
                                    }

                                    double novoEstoque = 0;
                                    double novoMinimo = 0;
                                    double novoComprimento = 0;

                                    if (novoPorMetro) {
                                        // ---- Rolo: estoque e mínimo em metros (aceita decimal) ----
                                        boolean metrosValidos = false;
                                        while (!metrosValidos) {
                                            System.out.print("Digite a metragem inicial do rolo (m): ");
                                            novoEstoque = lerNumero(entrada.nextLine());
                                            if (novoEstoque >= 0 && novoEstoque <= quantidadeMaxima) {
                                                metrosValidos = true;
                                            } else {
                                                System.out.println("[ERRO] A metragem deve estar entre 0 e " + quantidadeMaxima + " m.");
                                            }
                                        }
                                        boolean minimoValido = false;
                                        while (!minimoValido) {
                                            System.out.print("Digite a metragem mínima para alerta (m): ");
                                            novoMinimo = lerNumero(entrada.nextLine());
                                            if (novoMinimo >= 0 && novoMinimo <= quantidadeMaxima) {
                                                minimoValido = true;
                                            } else {
                                                System.out.println("[ERRO] O mínimo deve estar entre 0 e " + quantidadeMaxima + " m.");
                                            }
                                        }
                                        novoEstoque = arredondar(novoEstoque);
                                        novoMinimo = arredondar(novoMinimo);
                                    } else {
                                        // ---- Quantidade inicial: inteiro >= 0 ----
                                        boolean estoqueValido = false;
                                        while (!estoqueValido) {
                                            System.out.print("Digite a quantidade inicial: ");
                                            if (entrada.hasNextInt()) {
                                                novoEstoque = entrada.nextInt();
                                                if (novoEstoque >= 0 && novoEstoque <= quantidadeMaxima) {
                                                    estoqueValido = true;
                                                } else {
                                                    System.out.println("[ERRO] A quantidade deve estar entre 0 e " + quantidadeMaxima + ".");
                                                }
                                            } else {
                                                System.out.println("[ERRO] Digite um número inteiro.");
                                            }
                                            entrada.nextLine();
                                        }

                                        // ---- Estoque mínimo: inteiro >= 0 ----
                                        boolean minimoValido = false;
                                        while (!minimoValido) {
                                            System.out.print("Digite o limite de estoque mínimo para alerta: ");
                                            if (entrada.hasNextInt()) {
                                                novoMinimo = entrada.nextInt();
                                                if (novoMinimo >= 0 && novoMinimo <= quantidadeMaxima) {
                                                    minimoValido = true;
                                                } else {
                                                    System.out.println("[ERRO] O mínimo deve estar entre 0 e " + quantidadeMaxima + ".");
                                                }
                                            } else {
                                                System.out.println("[ERRO] Digite um número inteiro.");
                                            }
                                            entrada.nextLine();
                                        }

                                        // ---- Comprimento de cada cabo (para o cálculo de metragem) ----
                                        boolean comprimentoValido = false;
                                        while (!comprimentoValido) {
                                            System.out.print("Digite o comprimento de cada cabo em metros (0 se não se aplica): ");
                                            novoComprimento = lerNumero(entrada.nextLine());
                                            if (novoComprimento >= 0 && novoComprimento <= 1000) {
                                                comprimentoValido = true;
                                            } else {
                                                System.out.println("[ERRO] O comprimento deve estar entre 0 e 1000 m.");
                                            }
                                        }
                                    }

                                    // Só grava no vetor depois que TUDO foi validado
                                    skus[totalProdutos] = novoSku;
                                    categorias[totalProdutos] = novaCategoria;
                                    especificacoes[totalProdutos] = novaEspecificacao;
                                    precos[totalProdutos] = novoPreco;
                                    estoques[totalProdutos] = novoEstoque;
                                    estoquesMinimos[totalProdutos] = novoMinimo;
                                    porMetro[totalProdutos] = novoPorMetro;
                                    comprimentos[totalProdutos] = novoComprimento;

                                    totalProdutos++;
                                    System.out.println("\nProduto cadastrado com sucesso e integrado ao estoque geral!");
                                }
                                break;

                            case 4: // Relatório de Vendas (feitas pelo vendedor)
                                System.out.println("\n--- RELATÓRIO DE VENDAS ---");
                                if (totalVendasRealizadas == 0) {
                                    System.out.println("Nenhuma venda realizada até o momento.");
                                } else {
                                    for (int i = 0; i < totalVendasRealizadas; i++) {
                                        System.out.printf("Venda #%d: %s - Valor: R$ %.2f (impostos: R$ %.2f)%n",
                                            (i + 1), historicoDescricao[i], historicoValores[i], historicoImpostos[i]);
                                    }
                                    System.out.println("Quantidade de vendas: " + totalVendasRealizadas);
                                    System.out.printf("Faturamento total: R$ %.2f%n", totalVendidoGeral);
                                    System.out.printf("Impostos recolhidos: R$ %.2f%n", totalImpostosGeral);
                                }
                                break;

                            case 5: // Metragem em Estoque
                                System.out.println("\n--- METRAGEM EM ESTOQUE ---");
                                double metrosRolos = 0;
                                double metrosCabos = 0;

                                System.out.println("Rolos (venda por metro):");
                                for (int i = 0; i < totalProdutos; i++) {
                                    if (porMetro[i]) {
                                        double inicial = estoques[i] + vendidos[i];
                                        double percentual = (inicial > 0) ? estoques[i] / inicial * 100 : 0;
                                        String status = (estoques[i] <= estoquesMinimos[i]) ? "REPOR" : "OK";
                                        System.out.printf("- %s | Sobram: %.2f m de %.2f m (%.1f%%) | Vendidos: %.2f m | Mínimo: %.2f m | %s%n",
                                            especificacoes[i], estoques[i], inicial, percentual, vendidos[i], estoquesMinimos[i], status);
                                        metrosRolos += estoques[i];
                                    }
                                }

                                System.out.println("\nCabos prontos (unidades x comprimento):");
                                for (int i = 0; i < totalProdutos; i++) {
                                    if (!porMetro[i]) {
                                        if (comprimentos[i] > 0) {
                                            double metros = estoques[i] * comprimentos[i];
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
                                System.out.println("Deslogando...");
                                break;

                            default:
                                System.out.println("Opção inválida! Escolha entre 1 e 6.");
                        }
                    } while (opcao != 6);
                }
                // ================= MENU DO VENDEDOR =================
                else if (perfilAtual.equals("VENDEDOR")) {
                    do {
                        System.out.println("\n--- MENU VENDEDOR ---");
                        System.out.println("1. Visão Geral (Vendas realizadas e total vendido)");
                        System.out.println("2. Realizar Nova Venda");
                        System.out.println("3. Verificar Todas as Vendas Realizadas");
                        System.out.println("4. Sair / Deslogar");
                        System.out.print("Escolha uma opção: ");

                        while (!entrada.hasNextInt()) {
                            entrada.nextLine();
                            System.out.print("[ERRO] Digite apenas o número da opção: ");
                        }
                        opcao = entrada.nextInt();
                        entrada.nextLine();

                        switch (opcao) {
                            case 1: // Visão Geral
                                System.out.println("\n--- VISÃO GERAL DE VENDAS ---");
                                System.out.println("Quantidade total de vendas realizadas: " + totalVendasRealizadas);
                                System.out.printf("Valor total vendido (Geral / Dia): R$ %.2f%n", totalVendidoGeral);
                                break;

                            case 2: // Realizar Nova Venda
                                // Verifica o limite do histórico ANTES de mexer no estoque
                                if (totalVendasRealizadas >= maxVendas) {
                                    System.out.println("\n[ERRO] Limite de histórico de vendas atingido. Nenhuma venda pode ser registrada.");
                                    break;
                                }

                                System.out.println("\n--- PRODUTOS DISPONÍVEIS ---");
                                for (int i = 0; i < totalProdutos; i++) {
                                    double precoComImposto = precos[i] * (1 + aliquotaImposto);
                                    String medida = porMetro[i] ? "m" : "un";
                                    System.out.printf("[%d] %s - %s | Preço (com impostos): R$ %.2f/%s | Estoque: %s%n",
                                        i, skus[i], especificacoes[i], precoComImposto, medida, formatarQtd(estoques[i], porMetro[i]));
                                }

                                System.out.print("\nDigite o código (índice) do produto que deseja vender: ");
                                if (!entrada.hasNextInt()) {
                                    entrada.nextLine();
                                    System.out.println("\n[ERRO] Produto inválido. Digite o número entre colchetes.");
                                    break;
                                }
                                int prodIndex = entrada.nextInt();
                                entrada.nextLine();

                                if (prodIndex < 0 || prodIndex >= totalProdutos) {
                                    System.out.println("\n[ERRO] Produto inválido.");
                                    break;
                                }

                                if (estoques[prodIndex] == 0) {
                                    System.out.println("\n[ERRO] Produto esgotado.");
                                    break;
                                }

                                // ---- Quantidade: metros (decimal) para rolos, unidades (inteiro) para cabos ----
                                double qtdVenda;
                                if (porMetro[prodIndex]) {
                                    System.out.printf("Digite a metragem desejada (disponível: %.2f m, ex.: 7,5): ", estoques[prodIndex]);
                                    qtdVenda = arredondar(lerNumero(entrada.nextLine()));
                                } else {
                                    System.out.print("Digite a quantidade desejada (disponível: " + formatarQtd(estoques[prodIndex], false) + "): ");
                                    if (!entrada.hasNextInt()) {
                                        entrada.nextLine();
                                        System.out.println("\n[ERRO] Quantidade inválida. Digite um número inteiro.");
                                        break;
                                    }
                                    qtdVenda = entrada.nextInt();
                                    entrada.nextLine();
                                }

                                if (qtdVenda > 0 && qtdVenda <= estoques[prodIndex]) {
                                    // Mesmo cálculo do relatório tributável do administrador
                                    double subtotal = qtdVenda * precos[prodIndex];
                                    double impostoVenda = subtotal * aliquotaImposto;
                                    double valorVenda = subtotal + impostoVenda;

                                    // Atualiza o estoque e o total vendido do produto
                                    estoques[prodIndex] = arredondar(estoques[prodIndex] - qtdVenda);
                                    vendidos[prodIndex] = arredondar(vendidos[prodIndex] + qtdVenda);

                                    // Registra a venda no histórico (o espaço já foi garantido acima)
                                    historicoDescricao[totalVendasRealizadas] = formatarQtd(qtdVenda, porMetro[prodIndex]) + " de " + especificacoes[prodIndex];
                                    historicoValores[totalVendasRealizadas] = valorVenda;
                                    historicoImpostos[totalVendasRealizadas] = impostoVenda;
                                    totalVendasRealizadas++;
                                    totalVendidoGeral += valorVenda;
                                    totalImpostosGeral += impostoVenda;

                                    System.out.println("\nVenda realizada com sucesso!");
                                    System.out.printf("Subtotal: R$ %.2f | Impostos (27,25%%): R$ %.2f | Total: R$ %.2f%n",
                                        subtotal, impostoVenda, valorVenda);

                                    if (porMetro[prodIndex]) {
                                        System.out.printf("Metragem que sobra no rolo: %.2f m%n", estoques[prodIndex]);
                                    }

                                    if (estoques[prodIndex] <= estoquesMinimos[prodIndex]) {
                                        System.out.println("ALERTA: estoque baixo para " + especificacoes[prodIndex]
                                            + " (restam " + formatarQtd(estoques[prodIndex], porMetro[prodIndex]) + "). Necessário reposição.");
                                    }
                                } else {
                                    System.out.println("\n[ERRO] Quantidade inválida ou estoque insuficiente.");
                                }
                                break;

                            case 3: // Verificar todas as vendas já realizadas
                                System.out.println("\n--- HISTÓRICO DE VENDAS REALIZADAS ---");
                                if (totalVendasRealizadas == 0) {
                                    System.out.println("Nenhuma venda realizada até o momento.");
                                } else {
                                    for (int i = 0; i < totalVendasRealizadas; i++) {
                                        System.out.printf("Venda #%d: %s - Valor: R$ %.2f%n",
                                            (i + 1), historicoDescricao[i], historicoValores[i]);
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
        } catch (NoSuchElementException e) {
            System.out.println("\n\nEntrada encerrada. Sistema finalizado.");
        }

        System.out.println("\nSistema encerrado.");
        entrada.close();
    }
}
