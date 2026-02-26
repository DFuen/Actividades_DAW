package com.mechafactory.mecha.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import javax.annotation.PostConstruct;
import java.nio.file.Files;
import java.nio.file.Paths;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;

@RestController
@RequestMapping("/api/parts")
public class PartController {
    private final List<Map<String, Object>> parts = new ArrayList<>();

    @PostConstruct
    public void loadPartsFromJson() {
        try {
            String path = "src/main/resources/parts.json";
            String json = new String(Files.readAllBytes(Paths.get(path)));
            ObjectMapper mapper = new ObjectMapper();
            List<Map<String, Object>> loaded = mapper.readValue(json, new TypeReference<List<Map<String, Object>>>() {});
            parts.clear();
            parts.addAll(loaded);
        } catch (Exception e) {
            System.err.println("Error cargando parts.json: " + e.getMessage());
        }
    }

    @GetMapping
    public Map<String, List<Map<String, Object>>> getAll() {
        Map<String, List<Map<String, Object>>> grouped = new HashMap<>();
        grouped.put("head", new ArrayList<>());
        grouped.put("limbs", new ArrayList<>());
        grouped.put("body", new ArrayList<>());
        grouped.put("weapon", new ArrayList<>());
        for (Map<String, Object> part : parts) {
            String category = (String) part.get("category");
            if (grouped.containsKey(category)) {
                grouped.get(category).add(part);
            }
        }
        return grouped;
    }

    @PostMapping
    public Map<String, Object> create(@RequestBody Map<String, Object> part) {
        part.put("id", UUID.randomUUID().toString());
        parts.add(part);
        return part;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable String id) {
        return parts.stream().filter(p -> id.equals(p.get("id"))).findFirst().orElse(null);
    }
}
