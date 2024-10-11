package com.conversordemoedas.principal.servicos;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpServico {

    private String apiKey = "405c8b271d25ee705e779bf4";
    private String buscar;

    public void setBuscar(String buscar) {
        this.buscar = buscar;
    }

    public String pegaRates() throws IOException, InterruptedException {
        String endereco = "https://v6.exchangerate-api.com/v6/" + apiKey + "/latest/" + buscar;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endereco))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
