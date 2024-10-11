package com.conversordemoedas.principal.modelos;

import com.conversordemoedas.principal.interfaces.Menu;
import com.conversordemoedas.principal.servicos.HttpServico;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner entrada = new Scanner(System.in);
        HttpServico httpServico = new HttpServico();
        CurrencyConverter currencyConverter = new CurrencyConverter();
        Menu menu = new Menu();
        String moedaBase;
        String moedaDestino;

        while (true) {
            System.out.println("Qual será a moeda base para conversão?");
            menu.menuVerificacao();
            moedaBase = entrada.nextLine().toUpperCase();
            httpServico.setBuscar(moedaBase);

            switch (moedaBase) {
                case "USD", "EUR", "BRL", "CNY", "EGP", "AMD":
                    break;
                case "SAIR":
                    System.out.println("Saindo...");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
            }

            String jsonResponse = httpServico.pegaRates();
            JsonNode moeda = currencyConverter.convertToJson(jsonResponse);

            System.out.println("Quantos " + moedaBase + " você tem?");
            double quantidade = entrada.nextDouble();
            entrada.nextLine();

            while (true) {
                System.out.println("Para qual moeda deseja converter?");
                menu.menuVerificacao();
                moedaDestino = entrada.nextLine().toUpperCase();

                switch (moedaDestino) {
                    case "USD", "EUR", "BRL", "CNY", "EGP", "AMD":
                        try {
                            double resultado = currencyConverter.converterMoeda(moeda, moedaDestino, quantidade);
                            System.out.printf("Resultado da conversão: %.2f %s%n", resultado, moedaDestino);
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                        }
                        break;
                    case "SAIR":
                        System.out.println("Saindo...");
                        System.exit(0);
                    default:
                        System.out.println("Valor inválido. Tente novamente.");
                        continue;
                }

                System.out.println("Deseja fazer outra conversão? (s/n)");
                String resposta = entrada.nextLine().trim().toLowerCase();
                if (!resposta.equals("s")) {
                    System.out.println("Saindo...");
                    System.exit(0);
                }
                break;
            }
        }
    }
}