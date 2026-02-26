package com.mechafactory.mecha.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.*;
import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/api/cards")
public class CardController {
    private final List<Map<String, Object>> cards = new ArrayList<>();

    @PostConstruct
    public void loadCardsFromJson() {
        try {
            String path = "src/main/resources/cards/cards.json";
            String json = new String(Files.readAllBytes(Paths.get(path)));
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> loaded = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
            cards.clear();
            cards.addAll(loaded);
        } catch (Exception e) {
            System.err.println("Error cargando cards.json: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return cards;
    }

    // Endpoint para servir imágenes de cartas
    @GetMapping("/{filename}/image")
    public ResponseEntity<byte[]> getCardImage(@PathVariable String filename) {
        // Validación básica para evitar path traversal
        if (filename == null || filename.contains("..") || filename.contains("/") || filename.contains("\\")) {
            return ResponseEntity.status(400).body(null);
        }
        String basePath = "src/main/resources/cards/";
        String filePath = basePath + filename;
        try {
            if (!Files.exists(Paths.get(filePath))) {
                return ResponseEntity.status(404).body(null);
            }
            byte[] imageBytes = Files.readAllBytes(Paths.get(filePath));
            String contentType;
            if (filename.endsWith(".png")) {
                contentType = "image/png";
            } else if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
                contentType = "image/jpeg";
            } else if (filename.endsWith(".webp")) {
                contentType = "image/webp";
            } else {
                contentType = "application/octet-stream";
            }
            return ResponseEntity.ok()
                .header("Content-Type", contentType)
                .body(imageBytes);
        } catch (Exception e) {
            System.err.println("Error sirviendo imagen de carta: " + e.getMessage());
            return ResponseEntity.status(500).body(null);
        }
    }

    // Endpoint para carta aleatoria (excepto reversa)
    @GetMapping("/random")
    public ResponseEntity<Map<String, Object>> getRandomCard() {
        List<Map<String, Object>> filtered = new ArrayList<>();
        for (Map<String, Object> card : cards) {
            Object imageObj = card.get("image");
            if (imageObj != null && !imageObj.toString().equalsIgnoreCase("reversa.png")) {
                filtered.add(card);
            }
        }
        if (filtered.isEmpty()) {
            return ResponseEntity.status(404).body(null);
        }
        Map<String, Object> randomCard = filtered.get(new Random().nextInt(filtered.size()));
        // Añade campo imageUrl para el frontend
        String backendBaseUrl = System.getenv("VITE_BACKEND_URL");
        if (backendBaseUrl == null || backendBaseUrl.isEmpty()) {
            backendBaseUrl = "http://localhost:8080";
        }
        String imageFilename = randomCard.get("image") != null ? randomCard.get("image").toString() : "";
        randomCard.put("imageUrl", backendBaseUrl + "/api/cards/" + imageFilename + "/image");
        return ResponseEntity.ok(randomCard);
    }
}
