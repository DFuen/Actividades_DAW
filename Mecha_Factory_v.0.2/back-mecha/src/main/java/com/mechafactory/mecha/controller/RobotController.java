package com.mechafactory.mecha.controller;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/robots")
public class RobotController {
    private final List<Map<String, Object>> robots = new ArrayList<>();

    @GetMapping
    public List<Map<String, Object>> getAll() {
        return robots;
    }

    @PostMapping
    public Map<String, Object> create(@RequestBody Map<String, Object> robot) {
        robot.put("id", UUID.randomUUID().toString());
        robots.add(robot);
        return robot;
    }

    @GetMapping("/{id}")
    public Map<String, Object> getById(@PathVariable String id) {
        return robots.stream().filter(r -> id.equals(r.get("id"))).findFirst().orElse(null);
    }
}
