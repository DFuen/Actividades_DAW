package com.mechafactory.mecha.service;

import java.util.Map;

public class BattleService {
    public Map<String, Object> resolveBattle(Map<String, Object> player, Map<String, Object> enemy) {
        // Lógica simple: comparar stats y determinar ganador
        int playerAttack = getStat(player, "attack");
        int playerHealth = getStat(player, "health");
        int enemyAttack = getStat(enemy, "attack");
        int enemyHealth = getStat(enemy, "health");

        int playerFinalHealth = playerHealth - enemyAttack;
        int enemyFinalHealth = enemyHealth - playerAttack;

        String winner;
        if (playerFinalHealth > enemyFinalHealth) {
            winner = "player";
        } else if (enemyFinalHealth > playerFinalHealth) {
            winner = "enemy";
        } else {
            winner = "draw";
        }

        return Map.of(
            "playerFinalHealth", playerFinalHealth,
            "enemyFinalHealth", enemyFinalHealth,
            "winner", winner
        );
    }

    private int getStat(Map<String, Object> robot, String stat) {
        Map<String, Object> stats = (Map<String, Object>) robot.get("stats");
        return stats != null && stats.get(stat) != null ? (int) stats.get(stat) : 0;
    }
}
