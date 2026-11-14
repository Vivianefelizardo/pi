import java.util.Scanner;

public class SistemaEstoqueTributavel {
    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);

        // Capacidade aumentada para suportar a lista completa de produtos
        int capacidade = 30;
        String[] skus = new String[capacidade];
        String[] categorias = new String[capacidade];
        String[] especificacoes = new String[capacidade];
        double[] precos = new double[capacidade];
        int[] estoques = new int[capacidade];
        int[] estoquesMinimos = new int[capacidade];

        // Limites de segurança para evitar valores absurdos (e estouro de soma)
        double precoMaximo = 1000000.0;
        int quantidadeMaxima = 100000;

        // Populando a lista completa de produtos fornecida
        skus[0] = "HDMI-2.1-001"; categorias[0] = "HDMI"; especificacoes[0] = "HDMI 2.1 - 2m"; precos[0] = 100.0; estoques[0] = 50; estoquesMinimos[0] = 10;
        skus[1] = "HDMI-1.4-011"; categorias[1] = "HDMI"; especificacoes[1] = "HDMI 1.4 - 1.5m"; precos[1] = 30.0; estoques[1] = 40; estoquesMinimos[1] = 10;
        skus[2] = "HDMI-MINI-013"; categorias[2] = "HDMI"; especificacoes[2] = "HDMI Mini - 1m"; precos[2] = 45.0; estoques[2] = 6; estoquesMinimos[2] = 10;
        skus[3] = "USB-A-3.0-002"; categorias[3] = "USB"; especificacoes[3] = "USB 3.0 - 1m"; precos[3] = 35.0; estoques[3] = 40; estoquesMinimos[3] = 10;
        skus[4] = "USB-EXT-014"; categorias[4] = "USB"; especificacoes[4] = "USB Extensor - 2m"; precos[4] = 20.0; estoques[4] = 50; estoquesMinimos[4] = 10;
        skus[5] = "USB-HUB-016"; categorias[5] = "USB"; especificacoes[5] = "USB Hub 4 Portas"; precos[5] = 38.0; estoques[5] = 7; estoquesMinimos[5] = 10;
        skus[6] = "RED-CAT6-003"; categorias[6] = "Rede"; especificacoes[6] = "Cat6 - 5m"; precos[6] = 25.0; estoques[6] = 60; estoquesMinimos[6] = 10;
        skus[7] = "RED-CAT5E-017"; categorias[7] = "Rede"; especificacoes[7] = "Cat5e - 3m"; precos[7] = 12.0; estoques[7] = 80; estoquesMinimos[7] = 10;
        skus[8] = "SATA-3.0-004"; categorias[8] = "Sata"; especificacoes[8] = "SATA III - 50cm"; precos[8] = 35.0; estoques[8] = 8; estoquesMinimos[8] = 10;
        skus[9] = "SATA-ESATA-022"; categorias[9] = "Sata"; especificacoes[9] = "Cabo eSATA"; precos[9] = 42.0; estoques[9] = 5; estoquesMinimos[9] = 10;
        skus[10] = "USBC-100W-005"; categorias[10] = "USB-C"; especificacoes[10] = "Cabo USB-C 100W"; precos[10] = 45.0; estoques[10] = 45; estoquesMinimos[10] = 10;
        skus[11] = "FIB-LC-006"; categorias[11] = "Fibra Óptica"; especificacoes[11] = "Fibra LC-LC"; precos[11] = 75.0; estoques[11] = 25; estoquesMinimos[11] = 10;
        skus[12] = "COX-RG6-007"; categorias[12] = "Coaxial"; especificacoes[12] = "Cabo Coaxial RG6"; precos[12] = 18.0; estoques[12] = 70; estoquesMinimos[12] = 10;
        skus[13] = "RCA-AUDIO-008"; categorias[13] = "RCA"; especificacoes[13] = "Cabo RCA Áudio"; precos[13] = 22.0; estoques[13] = 30; estoquesMinimos[13] = 10;
        skus[14] = "RCA-3AV-032"; categorias[14] = "RCA"; especificacoes[14] = "Cabo 3x3 RCA Vídeo/Áudio"; precos[14] = 18.0; estoques[14] = 55; estoquesMinimos[14] = 10;
        skus[15] = "XLR-MIC-009"; categorias[15] = "XLR"; especificacoes[15] = "Cabo Microfone XLR"; precos[15] = 65.0; estoques[15] = 5; estoquesMinimos[15] = 10;
        skus[16] = "XLR-BAL-036"; categorias[16] = "XLR"; especificacoes[16] = "Cabo XLR Balanceado"; precos[16] = 115.0; estoques[16] = 11; estoquesMinimos[16] = 10;
        skus[17] = "MICUSB-V8-010"; categorias[17] = "Micro-USB"; especificacoes[17] = "Cabo Micro-USB V8"; precos[17] = 15.0; estoques[17] = 65; estoquesMinimos[17] = 10;

        int totalProdutos = 18;

        // Estrutura para controle de vendas do Vendedor
        int maxVendas = 50;
        String[] historicoDescricao = new String[maxVendas];
        double[] historicoValores = new double[maxVendas];
        int totalVendasRealizadas = 0;
        double totalVendidoGeral = 0;

        boolean logado = false;
        String perfilAtual = "";

        // ================= TELA DE LOGIN =================
        while (!logado) {
            System.out.println("\n==================================");
            System.out.println("   LOGIN - SISTEMA DE ESTOQUE     ");
            System.out.println("==================================");
            System.out.println("Opções de acesso:");
            System.out.println(" - ADMIN / senha: 123");
            System.out.println(" - VENDEDOR / senha: 456");
            System.out.println("----------------------------------");

            // nextLine() lê a linha inteira: linhas vazias ou com espaços não travam o login
            System.out.print("Digite o usuário: ");
            String usuario = entrada.nextLine().trim().toUpperCase();

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

        System.out.println("\nLogin efetuado com sucesso! Bem-vindo, " + perfilAtual + ".");

        int opcao = 0;

        // ================= MENU DO ADMINISTRADOR =================
        if (perfilAtual.equals("ADMINISTRADOR")) {
            do {
                System.out.println("\n--- MENU ADMINISTRADOR ---");
                System.out.println("1. Visão Geral do Estoque");
                System.out.println("2. Estoque Detalhado e Tributação");
                System.out.println("3. Cadastrar Novo Produto");
                System.out.println("4. Sair / Deslogar");
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
                        int produtosBaixos = 0;
                        int produtosEsgotados = 0;

                        for (int i = 0; i < totalProdutos; i++) {
                            totalUnidades += estoques[i];
                            if (estoques[i] == 0) {
                                produtosEsgotados++;
                            } else if (estoques[i] <= estoquesMinimos[i]) {
                                produtosBaixos++;
                            }
                        }

                        System.out.println("Total de tipos de produtos: " + totalProdutos);
                        System.out.println("Total de unidades físicas: " + totalUnidades);
                        System.out.println("Produtos com estoque baixo (<= mínimo): " + produtosBaixos);
                        System.out.println("Produtos esgotados (0 unidades): " + produtosEsgotados);

                        System.out.println("\nResumo por produto:");
                        for (int i = 0; i < totalProdutos; i++) {
                            String situacao = "OK";
                            if (estoques[i] == 0) situacao = "ESGOTADO";
                            else if (estoques[i] <= estoquesMinimos[i]) situacao = "REPOSIÇÃO";

                            System.out.println("- " + especificacoes[i] + " | Unidades: " + estoques[i] + " | Status: " + situacao);
                        }
                        break;

                    case 2: // Estoque Detalhado e Tributação
                        System.out.println("\n--- ESTOQUE DETALHADO E TRIBUTÁVEL ---");
                        double totalGeralEstoque = 0;

                        for (int i = 0; i < totalProdutos; i++) {
                            double precoBase = precos[i];
                            double imposto = precoBase * 0.2725; // Base proporcional de imposto informada na tabela
                            double precoTotalUnidade = precoBase + imposto;
                            double valorTotalItem = precoTotalUnidade * estoques[i];
                            totalGeralEstoque += valorTotalItem;

                            String status = (estoques[i] > estoquesMinimos[i]) ? "OK" : "REPOSIÇÃO";
                            if (estoques[i] == 0) status = "ESGOTADO";

                            System.out.printf("Código: %s | Tipo: %s | Qtd: %d | Preço Base: R$ %.2f | Imposto: R$ %.2f | Preço Total (un): R$ %.2f | Total Geral: R$ %.2f | Status: %s%n",
                                skus[i], categorias[i], estoques[i], precoBase, imposto, precoTotalUnidade, valorTotalItem, status);
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

                            // ---- Preço: número maior que zero ----
                            double novoPreco = 0;
                            boolean precoValido = false;
                            while (!precoValido) {
                                System.out.print("Digite o preço base: ");
                                if (entrada.hasNextDouble()) {
                                    novoPreco = entrada.nextDouble();
                                    if (novoPreco > 0 && novoPreco <= precoMaximo) {
                                        precoValido = true;
                                    } else {
                                        System.out.println("[ERRO] O preço deve ser maior que zero e até R$ " + precoMaximo + ".");
                                    }
                                } else {
                                    System.out.println("[ERRO] Digite um valor numérico (ex.: 49,90 ou 49.90, conforme seu sistema).");
                                }
                                entrada.nextLine(); // descarta o restante da linha (válida ou não)
                            }

                            // ---- Quantidade inicial: inteiro >= 0 ----
                            int novoEstoque = 0;
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
                            int novoMinimo = 0;
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

                            // Só grava no vetor depois que TUDO foi validado
                            skus[totalProdutos] = novoSku;
                            categorias[totalProdutos] = novaCategoria;
                            especificacoes[totalProdutos] = novaEspecificacao;
                            precos[totalProdutos] = novoPreco;
                            estoques[totalProdutos] = novoEstoque;
                            estoquesMinimos[totalProdutos] = novoMinimo;

                            totalProdutos++;
                            System.out.println("\nProduto cadastrado com sucesso e integrado ao estoque geral!");
                        }
                        break;

                    case 4:
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida! Escolha entre 1 e 4.");
                }
            } while (opcao != 4);
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
                            System.out.printf("[%d] %s - %s | Preço: R$ %.2f | Estoque: %d%n",
                                i, skus[i], especificacoes[i], precos[i], estoques[i]);
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

                        System.out.print("Digite a quantidade desejada (disponível: " + estoques[prodIndex] + "): ");
                        if (!entrada.hasNextInt()) {
                            entrada.nextLine();
                            System.out.println("\n[ERRO] Quantidade inválida. Digite um número inteiro.");
                            break;
                        }
                        int qtdVenda = entrada.nextInt();
                        entrada.nextLine();

                        if (qtdVenda > 0 && qtdVenda <= estoques[prodIndex]) {
                            double valorVenda = qtdVenda * precos[prodIndex];

                            // Atualiza o estoque do produto
                            estoques[prodIndex] -= qtdVenda;

                            // Registra a venda no histórico (o espaço já foi garantido acima)
                            historicoDescricao[totalVendasRealizadas] = qtdVenda + "x " + especificacoes[prodIndex];
                            historicoValores[totalVendasRealizadas] = valorVenda;
                            totalVendasRealizadas++;
                            totalVendidoGeral += valorVenda;
                            System.out.printf("\nVenda realizada com sucesso! Valor total: R$ %.2f%n", valorVenda);
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
                        System.out.println("Encerrando o sistema...");
                        break;

                    default:
                        System.out.println("Opção inválida! Escolha entre 1 e 4.");
                }
            } while (opcao != 4);
        }

        entrada.close();
    }
}