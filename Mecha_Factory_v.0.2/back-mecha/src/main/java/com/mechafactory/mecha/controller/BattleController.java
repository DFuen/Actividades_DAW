package com.mechafactory.mecha.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import com.mechafactory.mecha.service.BattleService;

@RestController
@RequestMapping("/api/battles")
public class BattleController {
    private final List<Map<String, Object>> battles = new ArrayList<>();
    private final BattleService battleService = new BattleService();

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return battles;
    }

    @PostMapping
    public Map<String, Object> create(@RequestBody Map<String, Object> battle) {
        battle.put("id", UUID.randomUUID().toString());
        battles.add(battle);
        return battle;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable String id) {
        return battles.stream().filter(b -> id.equals(b.get("id"))).findFirst().orElse(null);
    }

    @PostMapping("/resolve")
    public Map<String, Object> resolveBattle(@RequestBody Map<String, Object> payload) {
        Map<String, Object> player = (Map<String, Object>) payload.get("player");
        Map<String, Object> enemy = (Map<String, Object>) payload.get("enemy");
        return battleService.resolveBattle(player, enemy);
    }
}
