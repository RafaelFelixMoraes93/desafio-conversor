package com.conversordemoedas.principal.modelos;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CurrencyConverter {

    public JsonNode convertToJson(String jsonResponse) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode moedas = objectMapper.createObjectNode();

        JsonNode jsonNode = objectMapper.readTree(jsonResponse);
        JsonNode rates = jsonNode.get("conversion_rates");
        moedas.put("USD", rates.get("USD").asDouble());
        moedas.put("EUR", rates.get("EUR").asDouble());
        moedas.put("BRL", rates.get("BRL").asDouble());
        moedas.put("CNY", rates.get("CNY").asDouble());
        moedas.put("EGP", rates.get("EGP").asDouble());
        moedas.put("AMD", rates.get("AMD").asDouble());
        return moedas;
    }

    public double converterMoeda(JsonNode rates, String moedaDestino, double quantidade) {
        if (rates.has(moedaDestino)) {
            double taxa = rates.get(moedaDestino).asDouble();
            return quantidade * taxa;
        } else {
            throw new IllegalArgumentException("Moeda não suportada: " + moedaDestino);
        }
    }
}