package com.felix.conversormoedas.main;

import com.felix.conversormoedas.service.ExchangeRates;
import com.felix.conversormoedas.service.GetAPI;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {

        Gson gson = new Gson();
        GetAPI getAPI = new GetAPI();

        Scanner scanner = new Scanner(System.in);

        String inputUsuario = "1";

        while (!Objects.equals(inputUsuario, "0")) {
            System.out.println("Se quiser converter um valor, digite 1, se quiser sair, digite 0");
                inputUsuario = scanner.nextLine();

                if (Objects.equals(inputUsuario, "1")) {
                    System.out.println("Qual moeda a ser convertida? (ex: BRL, EUR, JPY): ");
                    String moedaOriginal = scanner.nextLine().toUpperCase();

                    System.out.println("Um segundo, conectando as informações!");

                    var api = getAPI.obterDados("https://v6.exchangerate-api.com/v6/6c52c047f9f72d1101b0e062/latest/" + moedaOriginal);
                    ExchangeRates exchangeRate = gson.fromJson(api, ExchangeRates.class);

                    System.out.println("Ótimo! Qual a moeda do valor original?");

                    String moedaDesejada = scanner.nextLine().toUpperCase();

                    System.out.println("Beleza! Qual o valor para converter de " + moedaDesejada + " para " + moedaOriginal + "?");

                    double valorConverter = scanner.nextDouble();

                    if (exchangeRate.getConversion_rates().containsKey(moedaDesejada)) {
                        double taxa = exchangeRate.getConversion_rates().get(moedaDesejada);

                        double valorFinal = valorConverter * taxa;

                        System.out.println("O valor convertido de " + moedaOriginal + " para " + moedaDesejada + " é de: " + valorFinal + ".");

                        //System.out.println("Quer converter outro número? Se sim, digite 1, se não 0");
                        //inputUsuario = scanner.nextLine();

                    } else {
                        System.out.println("Código de moeda não encontrado.");
                    }
                } else if (inputUsuario.equals("0")) {
                    System.out.println("Programa finalizado.");
                } else {
                   System.out.println("Você digitou: " + inputUsuario + ", você quis dizer 0 ou 1?");
                }


        }

    }
}