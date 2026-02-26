<template>
  <div class="arena-view">
    <!-- Indicador de turnos -->
    <div class="turn-indicator">
      <span v-if="gameStore.battle.currentTurn === 'player'" class="turn-label turn-label--player">🎮 TU TURNO</span>
      <span v-else class="turn-label turn-label--ai">🤖 TURNO DE LA IA</span>
    </div>
    <!-- HUD de combate (puedes personalizar o mantener el tuyo) -->
    <div class="battle-hud-compact">
      <!-- ...barras de vida, nombres, etc... -->
    </div>
    <!-- Arena de combate visual -->
    <div class="arena-combat">
      <canvas ref="combatCanvas" class="combat-canvas"></canvas>
    </div>
    <!-- Créditos pegados abajo -->
    <footer class="footer">
      <!-- ...créditos... -->
    </footer>
    <!-- Modal de victoria/derrota si lo tienes -->
    <div v-if="partidaTerminada" class="game-over-modal-overlay">
      <div class="game-over-modal">
        <!-- ...modal contenido... -->
      <template>
        <div class="arena-view">
          <!-- Indicador de turnos -->
          <div class="turn-indicator">
            <span v-if="gameStore.battle.currentTurn === 'player'" class="turn-label turn-label--player">🎮 TU TURNO</span>
            <span v-else class="turn-label turn-label--ai">🤖 TURNO DE LA IA</span>
          </div>

          <!-- Modal de victoria/derrota -->
          <div v-if="endScreenState.shown" class="game-over-modal-overlay">
            <div class="game-over-modal">
              <div class="result-icon">
                {{ endScreenState.victory ? '🏆' : '💀' }}
              </div>
              <h2 class="result-title">
                {{ endScreenState.victory ? '¡VICTORIA!' : 'DERROTA' }}
              </h2>
              <p class="result-message">
                {{ endScreenState.victory ? '¡Has derrotado a la IA y ganado el combate!' : 'La IA te ha vencido. Intenta de nuevo.' }}
              </p>
              <div class="result-actions">
                <button class="btn btn--primary" @click="retryBattle">Reintentar</button>
                <button class="btn btn--secondary" @click="goHome">Salir</button>
              </div>
            </div>
          </div>

          <!-- Arena de combate visual -->
          <div class="arena-combat">
            <canvas ref="combatCanvas" class="combat-canvas"></canvas>
          </div>

          <!-- HUD de combate -->
          <div class="battle-hud-compact">
            <div class="hud-player">
              <div class="hud-name">{{ gameStore.robot?.name || 'Jugador' }}</div>
              <div class="hud-health">
                <div class="health-bar">
                  <div class="health-fill" :style="{ width: playerHealthPercent + '%', background: getHealthColor(playerHealthPercent) }"></div>
                </div>
                <span class="health-text">{{ gameStore.battle.playerHealth }}/{{ gameStore.battle.playerMaxHealth }}</span>
              </div>
            </div>
            <div class="hud-vs">VS</div>
            <div class="hud-ai">
              <div class="hud-name">IA</div>
              <div class="hud-health">
                <div class="health-bar">
                  <div class="health-fill" :style="{ width: aiHealthPercent + '%', background: getHealthColor(aiHealthPercent) }"></div>
                </div>
                <span class="health-text">{{ gameStore.battle.aiHealth }}/{{ gameStore.battle.aiMaxHealth }}</span>
              </div>
            </div>
          </div>
        </div>
      </template>

      <script setup lang="ts">
      import { ref, onMounted, onUnmounted, computed, reactive, watch } from 'vue';
      import { useRouter } from 'vue-router';
      import { useGameStore } from '../stores/gameStore';
      import { useCardStore } from '../stores/cardStore';

      const gameStore = useGameStore();
      const cardStore = useCardStore();
      const router = useRouter();

      const endScreenState = reactive({
        shown: false,
        victory: false,
        defeat: false,
      });

      const playerHealthPercent = computed(() => gameStore.playerHealthPercent);
      const aiHealthPercent = computed(() => gameStore.aiHealthPercent);

      function getHealthColor(percent: number): string {
        if (percent > 60) return '#2ecc71';
        if (percent > 30) return '#f1c40f';
        return '#e74c3c';
      }

      function retryBattle() {
        endScreenState.shown = false;
        endScreenState.victory = false;
        endScreenState.defeat = false;
        gameStore.startBattle();
      }
      function goHome() {
        endScreenState.shown = false;
        router.push('/');
      }

      // --- Canvas y animación de robots ---
      const combatCanvas = ref<HTMLCanvasElement | null>(null);
      let animationId: number | null = null;
      type PunchSide = 'player' | 'ai' | null;
      let state = {
        player: { x: 120, y: 220, idle: 0, knockback: 0, healAura: 0 },
        ai: { x: 380, y: 220, idle: 0, knockback: 0, healAura: 0 },
        punch: { active: false, side: null as PunchSide, progress: 0, hitEffect: 0 },
      };

      function drawRobots(ctx: CanvasRenderingContext2D, time: number) {
        state.player.idle = Math.sin(time / 600) * 8;
        state.ai.idle = Math.sin(time / 600 + Math.PI) * 8;
        let playerX = state.player.x + state.player.knockback;
        let aiX = state.ai.x + state.ai.knockback;
        ctx.save();
        ctx.translate(playerX, state.player.y + state.player.idle);
        drawRobot(ctx, 'red', state.punch.active && state.punch.side === 'player' ? state.punch.progress : 0);
        if (state.player.healAura > 0) drawHealAura(ctx, state.player.healAura);
        ctx.restore();
        ctx.save();
        ctx.translate(aiX, state.ai.y + state.ai.idle);
        drawRobot(ctx, 'blue', state.punch.active && state.punch.side === 'ai' ? state.punch.progress : 0);
        if (state.ai.healAura > 0) drawHealAura(ctx, state.ai.healAura);
        ctx.restore();
        if (state.punch.hitEffect > 0) {
          drawHitEffect(ctx, state.punch.side === 'player' ? aiX : playerX, state.player.y, state.punch.hitEffect);
        }
      }
      function drawRobot(ctx: CanvasRenderingContext2D, gloveColor: string, punchProgress: number) {
        ctx.fillStyle = '#444';
        ctx.beginPath();
        ctx.arc(0, 0, 40, 0, Math.PI * 2);
        ctx.fill();
        ctx.fillStyle = '#222';
        ctx.beginPath();
        ctx.arc(0, -50, 22, 0, Math.PI * 2);
        ctx.fill();
        ctx.strokeStyle = '#888';
        ctx.lineWidth = 8;
        ctx.beginPath();
        ctx.moveTo(-30, -10);
        ctx.lineTo(-60 + punchProgress, -10);
        ctx.moveTo(30, -10);
        ctx.lineTo(60 - punchProgress, -10);
        ctx.stroke();
        ctx.fillStyle = gloveColor;
        ctx.beginPath();
        ctx.arc(-60 + punchProgress, -10, 14, 0, Math.PI * 2);
        ctx.arc(60 - punchProgress, -10, 14, 0, Math.PI * 2);
        ctx.fill();
        ctx.strokeStyle = '#666';
        ctx.lineWidth = 7;
        ctx.beginPath();
        ctx.moveTo(-15, 40);
        ctx.lineTo(-15, 80);
        ctx.moveTo(15, 40);
        ctx.lineTo(15, 80);
        ctx.stroke();
      }
      function drawHitEffect(ctx: CanvasRenderingContext2D, x: number, y: number, progress: number) {
        ctx.save();
        ctx.globalAlpha = 0.5 * (1 - progress / 1);
        ctx.beginPath();
        ctx.arc(x, y, 40 + progress * 30, 0, Math.PI * 2);
        ctx.strokeStyle = '#fff';
        ctx.lineWidth = 8 - progress * 6;
        ctx.stroke();
        ctx.restore();
      }
      function drawHealAura(ctx: CanvasRenderingContext2D, progress: number) {
        ctx.save();
        ctx.globalAlpha = 0.3 + 0.2 * Math.sin(progress * Math.PI * 2);
        ctx.beginPath();
        ctx.arc(0, 0, 55 + 10 * Math.sin(progress * Math.PI * 2), 0, Math.PI * 2);
        ctx.strokeStyle = '#00ff88';
        ctx.lineWidth = 8;
        ctx.stroke();
        ctx.restore();
      }
      function animate(time: number) {
        const canvas = combatCanvas.value;
        if (!canvas) return;
        const ctx = canvas.getContext('2d');
        if (!ctx) return;
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        drawRobots(ctx, time);
        animationId = requestAnimationFrame(animate);
      }
      function resizeCanvas() {
        const canvas = combatCanvas.value;
        if (!canvas) return;
        const parent = canvas.parentElement;
        if (!parent) return;
        canvas.width = parent.offsetWidth;
        canvas.height = Math.max(320, parent.offsetHeight);
      }

      onMounted(() => {
        cardStore.loadDeck();
        gameStore.loadCards();
        gameStore.startBattle();
        resizeCanvas();
        window.addEventListener('resize', resizeCanvas);
        animationId = requestAnimationFrame(animate);
      });
      onUnmounted(() => {
        window.removeEventListener('resize', resizeCanvas);
        if (animationId !== null) {
          cancelAnimationFrame(animationId);
        }
      });

      watch(
        () => gameStore.battle.battleLog.length,
        () => {
          if (gameStore.battle.isFinished && !endScreenState.shown) {
            endScreenState.shown = true;
            endScreenState.victory = gameStore.battle.winner === 'player';
            endScreenState.defeat = gameStore.battle.winner === 'ai';
          }
        }
      );
      </script>

      <style scoped>
      ...existing code...
      </style>
// No exportar en <script setup>


onMounted(() => {
  cardStore.loadDeck();
  gameStore.loadCards();
  gameStore.startBattle();
});

/* Duplicate import and computed removed to fix duplicate identifier error */

// function goToGarage() {
//   endScreenState.value.shown = false;
//   router.push('/garage');
// }

function goHome() {
  endScreenState.shown = false;
  router.push('/');
}

watch(
  () => gameStore.battle.battleLog.length,
  (len) => {
    const log = gameStore.battle.battleLog[len - 1];
    if (!log) return;
    // Bloquea combate si terminó
    if (gameStore.battle.isFinished && !endScreenState.shown) {
      endScreenState.shown = true;
      endScreenState.victory = gameStore.battle.winner === 'player';
      endScreenState.defeat = gameStore.battle.winner === 'ai';
    }
    // Log de acciones
    const isPlayer = log.actorType === 'player';
    const isAI = log.actorType === 'ai';
    if (isPlayer) {
      if (log.action && log.action.includes('cura')) {
        console.log(`[ARENA] El jugador se cura: +${log.damage} HP | Vida tras acción: ${gameStore.battle.playerHealth}`);
        console.log(`[ARENA] El jugador ataca: -${log.damage} HP a IA | Vida IA tras acción: ${gameStore.battle.aiHealth}`);
      }
    } else if (isAI) {
      if (log.action && log.action.includes('cura')) {
        console.log(`[ARENA] La IA se cura: +${log.damage} HP | Vida IA tras acción: ${gameStore.battle.aiHealth}`);
      } else {
        console.log(`[ARENA] La IA ataca: -${log.damage} HP al jugador | Vida jugador tras acción: ${gameStore.battle.playerHealth}`);
      }
    }
  }
);

// Helpers para stats de IA y jugador
const aiStats = computed(() => {
  // Suma los stats de las partes del robot enemigo
  const enemy = gameStore.enemyRobot;
  if (!enemy) return {};
  const parts = [enemy.head, enemy.body, enemy.limbs, enemy.weapon].filter(Boolean);
  return parts.reduce((acc, part: any) => {
    if (part && part.stats) {
      for (const [key, value] of Object.entries(part.stats)) {
        acc[key] = (acc[key] || 0) + (typeof value === 'number' ? value : 0);
      }
    }
    return acc;
  }, {} as Record<string, number>);
});

const playerStats = computed(() => {
  // Suma los stats de las partes del robot del jugador
  const robot = gameStore.robot;
  if (!robot) return {};
  const parts = [robot.head, robot.body, robot.limbs, robot.weapon].filter(Boolean);
  return parts.reduce((acc, part: any) => {
    if (part && part.stats) {
      for (const [key, value] of Object.entries(part.stats)) {
        acc[key] = (acc[key] || 0) + (typeof value === 'number' ? value : 0);
      }
    }
    return acc;
  }, {} as Record<string, number>);
});

const aiStatMultiplier = computed(() => {
  // Puedes ajustar el multiplicador según dificultad, ejemplo:
  if (aiDifficulty.value === 'hard') return 1.2;
  if (aiDifficulty.value === 'easy') return 0.85;
  return 1;
});
// If you have a global difficulty in the store, use that instead:
const aiDifficulty = computed(() => 'normal');
// Or, if you want to hardcode it for now:
//
// const aiDifficulty = computed(() => 'normal');

// Lógica avanzada de IA
function aiDecideAction() {
  if (gameStore.battle.isFinished) return;
  const aiStatsVal = aiStats.value;
  const playerStatsVal = playerStats.value;
  const aiHealthPct = gameStore.battle.aiHealth / gameStore.battle.aiMaxHealth;
  const playerHealthPct = gameStore.battle.playerHealth / gameStore.battle.playerMaxHealth;
  // Escalado de stats
  const aiAttack = Math.floor((aiStatsVal.attack ?? 0) * aiStatMultiplier.value);
  const aiDefense = Math.floor((aiStatsVal.defense ?? 0) * aiStatMultiplier.value);
  // Estimar daño
  let estimatedDamage = aiAttack - Math.floor((playerStatsVal.defense ?? 0) * 0.45);
  if (Math.random() < ((aiStatsVal.critChance ?? 0) + (aiDifficulty.value === 'hard' ? 0.08 : 0))) {
    estimatedDamage *= aiStatsVal.critMultiplier || 1.5;
  }
  estimatedDamage *= (1 - (playerStatsVal.damageReduction || 0));
  estimatedDamage -= (playerStatsVal.resistance || 0);
  estimatedDamage = Math.max(1, Math.floor(estimatedDamage));
  // Estimar daño recibido
  let estimatedPlayerDamage = (playerStatsVal.attack ?? 0) - Math.floor((aiDefense ?? 0) * 0.45);
  estimatedPlayerDamage *= (1 - (aiStatsVal.damageReduction || 0));
  estimatedPlayerDamage -= (aiStatsVal.resistance || 0);
  estimatedPlayerDamage = Math.max(1, Math.floor(estimatedPlayerDamage));
  // Decisión
  let action = 'attack';
  // Si IA puede eliminar al jugador
  if (gameStore.battle.playerHealth - estimatedDamage <= 0) {
    action = 'attack';
  } else if (aiHealthPct < 0.35 && aiCooldowns.value.heal === 0 && gameStore.battle.aiHealth < gameStore.battle.aiMaxHealth - 10) {
    // Si IA baja de 35% y puede curarse
    action = 'heal';
  } else if (playerHealthPct < 0.25 && Math.random() < 0.7) {
    // Si jugador bajo de vida, IA ataca agresivo
    action = 'attack';
  } else if (aiCooldowns.value.special === 0 && Math.random() < 0.25) {
    // Uso inteligente de habilidad especial
    action = 'special';
  } else if (
    typeof aiStatsVal.health === 'number' &&
    estimatedPlayerDamage > aiStatsVal.health * 0.4 &&
    aiCooldowns.value.heal === 0 &&
    aiHealthPct < 0.5
  ) {
    // Si IA puede recibir mucho daño, prioriza curarse
    action = 'heal';
  } else if (aiHealthPct > 0.9 && aiCooldowns.value.heal === 0) {
    // No curarse si casi a vida máxima
    action = 'attack';
  }
  // Gestión de cooldowns
  if (action === 'heal') aiCooldowns.value.heal = 2;
  if (action === 'special') aiCooldowns.value.special = 3;
  // Ejecutar acción
  gameStore.aiTurn();
}

// Mueve la carta especial a la posición final y desbloquea la arena
function moveSpecialCard(): void {
  specialCardState.moved = true;
  specialCardState.blocked = false;
}

// Reducir cooldowns cada turno
watch(
  () => gameStore.battle.currentTurn,
  () => {
    if (gameStore.battle.currentTurn === 'ai' && !gameStore.battle.isFinished) {
      aiDecideAction();
      if (aiCooldowns.value.heal > 0) aiCooldowns.value.heal--;
      if (aiCooldowns.value.special > 0) aiCooldowns.value.special--;
    }
  }
);
</script>

<template>
        <template>
          <div class="arena-view">
            <!-- Indicador de turnos -->
            <div class="turn-indicator">
              <span v-if="gameStore.battle.currentTurn === 'player'" class="turn-label turn-label--player">🎮 TU TURNO</span>
              <span v-else class="turn-label turn-label--ai">🤖 TURNO DE LA IA</span>
            </div>
            <!-- HUD de combate (puedes personalizar o mantener el tuyo) -->
            <div class="battle-hud-compact">
              <!-- ...barras de vida, nombres, etc... -->
            </div>
            <!-- Arena de combate visual -->
            <div class="arena-combat">
              <canvas ref="combatCanvas" class="combat-canvas"></canvas>
              <!-- ...otros elementos de la arena... -->
            </div>
            <!-- Créditos pegados abajo -->
            <footer class="footer">
              <!-- ...créditos... -->
            </footer>
            <!-- Modal de victoria/derrota si lo tienes -->
            <div v-if="partidaTerminada" class="game-over-modal-overlay">
              <div class="game-over-modal">
                <!-- ...modal contenido... -->
              </div>
            </div>
          </div>
        </template>
        import { ref, onMounted, onUnmounted, watch } from 'vue';
        // Si usas Pinia o tu store:
        // import { useGameStore } from '@/stores/gameStore';
        // const gameStore = useGameStore();

        const combatCanvas = ref(null);
        let animationId;

        // Estado visual de los robots y efectos
        let state = {
          player: { x: 120, y: 220, idle: 0, knockback: 0, healAura: 0 },
          ai: { x: 380, y: 220, idle: 0, knockback: 0, healAura: 0 },
          punch: { active: false, side: null, progress: 0, hitEffect: 0 },
        };

        // Dibuja ambos robots y efectos
        function drawRobots(ctx, time) {
          // Idle breathing
          state.player.idle = Math.sin(time / 600) * 8;
          state.ai.idle = Math.sin(time / 600 + Math.PI) * 8;

          // Knockback
          let playerX = state.player.x + state.player.knockback;
          let aiX = state.ai.x + state.ai.knockback;

          // Player Robot
          ctx.save();
          ctx.translate(playerX, state.player.y + state.player.idle);
          drawRobot(ctx, 'red', state.punch.active && state.punch.side === 'player' ? state.punch.progress : 0);
          if (state.player.healAura > 0) drawHealAura(ctx, state.player.healAura);
          ctx.restore();

          // AI Robot
          ctx.save();
          ctx.translate(aiX, state.ai.y + state.ai.idle);
          drawRobot(ctx, 'blue', state.punch.active && state.punch.side === 'ai' ? state.punch.progress : 0);
          if (state.ai.healAura > 0) drawHealAura(ctx, state.ai.healAura);
          ctx.restore();

          // Hit effect
          if (state.punch.hitEffect > 0) {
            drawHitEffect(ctx, state.punch.side === 'player' ? aiX : playerX, state.player.y, state.punch.hitEffect);
          }
        }

        // Dibuja un robot en guardia de boxeo
        function drawRobot(ctx, gloveColor, punchProgress) {
          // Cuerpo
          ctx.fillStyle = '#444';
          ctx.beginPath();
          ctx.arc(0, 0, 40, 0, Math.PI * 2);
          ctx.fill();
          // Cabeza
          ctx.fillStyle = '#222';
          ctx.beginPath();
          ctx.arc(0, -50, 22, 0, Math.PI * 2);
          ctx.fill();
          // Brazos
          ctx.strokeStyle = '#888';
          ctx.lineWidth = 8;
          ctx.beginPath();
          ctx.moveTo(-30, -10);
          ctx.lineTo(-60 + punchProgress, -10);
          ctx.moveTo(30, -10);
          ctx.lineTo(60 - punchProgress, -10);
          ctx.stroke();
          // Guantes
          ctx.fillStyle = gloveColor;
          ctx.beginPath();
          ctx.arc(-60 + punchProgress, -10, 14, 0, Math.PI * 2);
          ctx.arc(60 - punchProgress, -10, 14, 0, Math.PI * 2);
          ctx.fill();
          // Piernas
          ctx.strokeStyle = '#666';
          ctx.lineWidth = 7;
          ctx.beginPath();
          ctx.moveTo(-15, 40);
          ctx.lineTo(-15, 80);
          ctx.moveTo(15, 40);
          ctx.lineTo(15, 80);
          ctx.stroke();
        }

        // Efecto radial de impacto
        function drawHitEffect(ctx, x, y, progress) {
          ctx.save();
          ctx.globalAlpha = 0.5 * (1 - progress / 1);
          ctx.beginPath();
          ctx.arc(x, y, 40 + progress * 30, 0, Math.PI * 2);
          ctx.strokeStyle = '#fff';
          ctx.lineWidth = 8 - progress * 6;
          ctx.stroke();
          ctx.restore();
        }

        // Aura de curación animada
        function drawHealAura(ctx, progress) {
          ctx.save();
          ctx.globalAlpha = 0.3 + 0.2 * Math.sin(progress * Math.PI * 2);
          ctx.beginPath();
          ctx.arc(0, 0, 55 + 10 * Math.sin(progress * Math.PI * 2), 0, Math.PI * 2);
          ctx.strokeStyle = '#00ff88';
          ctx.lineWidth = 8;
          ctx.stroke();
          ctx.restore();
        }

        // Animación principal
        function animate(time) {
          const canvas = combatCanvas.value;
          if (!canvas) return;
          const ctx = canvas.getContext('2d');
          ctx.clearRect(0, 0, canvas.width, canvas.height);

          drawRobots(ctx, time);

          // Animar puñetazo
          if (state.punch.active) {
            state.punch.progress += 8;
            if (state.punch.progress >= 40 && state.punch.hitEffect === 0) {
              state.punch.hitEffect = 0.01;
              // Knockback
              if (state.punch.side === 'player') state.ai.knockback = 18;
              else state.player.knockback = -18;
              // Callback: sincroniza barra de vida aquí
              emitImpact();
            }
            if (state.punch.progress >= 60) {
              state.punch.active = false;
              state.punch.progress = 0;
              state.punch.hitEffect = 0;
              state.player.knockback = 0;
              state.ai.knockback = 0;
            }
          }
          // Animar hit effect
          if (state.punch.hitEffect > 0) {
            state.punch.hitEffect += 0.08;
            if (state.punch.hitEffect > 1) state.punch.hitEffect = 0;
          }
          // Animar aura de curación
          if (state.player.healAura > 0) {
            state.player.healAura += 0.03;
            if (state.player.healAura > 1.5) state.player.healAura = 0;
          }
          if (state.ai.healAura > 0) {
            state.ai.healAura += 0.03;
            if (state.ai.healAura > 1.5) state.ai.healAura = 0;
          }

          animationId = requestAnimationFrame(animate);
        }

        // Sincroniza barra de vida con Vue
        function emitImpact() {
          // Aquí puedes emitir un evento, llamar a una función, o actualizar el store
          // Ejemplo: gameStore.playerHit(), gameStore.aiHit()
        }

        // Llama esto para animar un puñetazo (side: 'player' o 'ai')
        function triggerPunch(side = 'player') {
          if (!state.punch.active) {
            state.punch.active = true;
            state.punch.side = side;
            state.punch.progress = 0;
            state.punch.hitEffect = 0;
          }
        }

        // Llama esto para animar curación (side: 'player' o 'ai')
        function triggerHeal(side = 'player') {
          if (side === 'player') state.player.healAura = 0.01;
          else state.ai.healAura = 0.01;
        }

        // Canvas responsivo
        function resizeCanvas() {
          const canvas = combatCanvas.value;
          if (!canvas) return;
          const parent = canvas.parentElement;
          canvas.width = parent.offsetWidth;
          canvas.height = Math.max(320, parent.offsetHeight);
        }

        onMounted(() => {
          resizeCanvas();
          window.addEventListener('resize', resizeCanvas);
          animationId = requestAnimationFrame(animate);
        });
        onUnmounted(() => {
          window.removeEventListener('resize', resizeCanvas);
          cancelAnimationFrame(animationId);
        });

        // Exporta las funciones para tus botones:
        export { triggerPunch, triggerHeal };
        <div class="arena-view">
          <!-- Indicador de turnos -->
          <div class="turn-indicator">
            <span v-if="gameStore.battle.currentTurn === 'player'" class="turn-label turn-label--player">🎮 TU TURNO</span>
            <span v-else class="turn-label turn-label--ai">🤖 TURNO DE LA IA</span>
          </div>
          <!-- HUD de combate -->
          <div class="battle-hud-compact">
            <!-- ...barras de vida y VS... -->
          </div>
          <!-- Arena de combate -->
          <div class="arena-combat">
            <canvas ref="combatCanvas" class="combat-canvas"></canvas>
            <!-- ...otros elementos de la arena... -->
          </div>
          <!-- Créditos pegados abajo -->
          <footer class="footer">
            <!-- ...créditos... -->
          </footer>
          <!-- Modal de victoria/derrota (si lo tienes) -->
          <div v-if="partidaTerminada" class="game-over-modal-overlay">
            <div class="game-over-modal">
              <!-- ...modal contenido... -->
            </div>
          </div>
        </div>
      import { ref, onMounted, onUnmounted, watch } from 'vue';
      const combatCanvas = ref(null);

      let animationId;
      let state = {
        player: { x: 120, y: 220, idle: 0, knockback: 0, healAura: 0 },
        ai: { x: 380, y: 220, idle: 0, knockback: 0, healAura: 0 },
        punch: { active: false, side: null, progress: 0, hitEffect: 0 },
      };

      function drawRobots(ctx, time) {
        // Idle breathing
        state.player.idle = Math.sin(time / 600) * 8;
        state.ai.idle = Math.sin(time / 600 + Math.PI) * 8;

        // Knockback
        let playerX = state.player.x + state.player.knockback;
        let aiX = state.ai.x + state.ai.knockback;

        // Draw Player Robot
        ctx.save();
        ctx.translate(playerX, state.player.y + state.player.idle);
        drawRobot(ctx, 'red', state.punch.active && state.punch.side === 'player' ? state.punch.progress : 0);
        if (state.player.healAura > 0) drawHealAura(ctx, state.player.healAura);
        ctx.restore();

        // Draw AI Robot
        ctx.save();
        ctx.translate(aiX, state.ai.y + state.ai.idle);
        drawRobot(ctx, 'blue', state.punch.active && state.punch.side === 'ai' ? state.punch.progress : 0);
        if (state.ai.healAura > 0) drawHealAura(ctx, state.ai.healAura);
        ctx.restore();

        // Hit effect
        if (state.punch.hitEffect > 0) {
          drawHitEffect(ctx, state.punch.side === 'player' ? aiX : playerX, state.player.y, state.punch.hitEffect);
        }
      }

      function drawRobot(ctx, gloveColor, punchProgress) {
        // Body
        ctx.fillStyle = '#444';
        ctx.beginPath();
        ctx.arc(0, 0, 40, 0, Math.PI * 2);
        ctx.fill();
        // Head
        ctx.fillStyle = '#222';
        ctx.beginPath();
        ctx.arc(0, -50, 22, 0, Math.PI * 2);
        ctx.fill();
        // Arms
        ctx.strokeStyle = '#888';
        ctx.lineWidth = 8;
        ctx.beginPath();
        ctx.moveTo(-30, -10);
        ctx.lineTo(-60 + punchProgress, -10);
        ctx.moveTo(30, -10);
        ctx.lineTo(60 - punchProgress, -10);
        ctx.stroke();
        // Gloves
        ctx.fillStyle = gloveColor;
        ctx.beginPath();
        ctx.arc(-60 + punchProgress, -10, 14, 0, Math.PI * 2);
        ctx.arc(60 - punchProgress, -10, 14, 0, Math.PI * 2);
        ctx.fill();
        // Legs
        ctx.strokeStyle = '#666';
        ctx.lineWidth = 7;
        ctx.beginPath();
        ctx.moveTo(-15, 40);
        ctx.lineTo(-15, 80);
        ctx.moveTo(15, 40);
        ctx.lineTo(15, 80);
        ctx.stroke();
      }

      function drawHitEffect(ctx, x, y, progress) {
        ctx.save();
        ctx.globalAlpha = 0.5 * (1 - progress / 1);
        ctx.beginPath();
        ctx.arc(x, y, 40 + progress * 30, 0, Math.PI * 2);
        ctx.strokeStyle = '#fff';
        ctx.lineWidth = 8 - progress * 6;
        ctx.stroke();
        ctx.restore();
      }

      function drawHealAura(ctx, progress) {
        ctx.save();
        ctx.globalAlpha = 0.3 + 0.2 * Math.sin(progress * Math.PI * 2);
        ctx.beginPath();
        ctx.arc(0, 0, 55 + 10 * Math.sin(progress * Math.PI * 2), 0, Math.PI * 2);
        ctx.strokeStyle = '#00ff88';
        ctx.lineWidth = 8;
        ctx.stroke();
        ctx.restore();
      }

      function animate(time) {
        const canvas = combatCanvas.value;
        if (!canvas) return;
        const ctx = canvas.getContext('2d');
        ctx.clearRect(0, 0, canvas.width, canvas.height);

        drawRobots(ctx, time);

        // Animate punch
        if (state.punch.active) {
          state.punch.progress += 8;
          if (state.punch.progress >= 40 && state.punch.hitEffect === 0) {
            state.punch.hitEffect = 0.01;
            // Knockback
            if (state.punch.side === 'player') state.ai.knockback = 18;
            else state.player.knockback = -18;
            // Callback: sincroniza barra de vida aquí
            emitImpact();
          }
          if (state.punch.progress >= 60) {
            state.punch.active = false;
            state.punch.progress = 0;
            state.punch.hitEffect = 0;
            state.player.knockback = 0;
            state.ai.knockback = 0;
          }
        }
        // Animate hit effect
        if (state.punch.hitEffect > 0) {
          state.punch.hitEffect += 0.08;
          if (state.punch.hitEffect > 1) state.punch.hitEffect = 0;
        }
        // Animate heal aura
        if (state.player.healAura > 0) {
          state.player.healAura += 0.03;
          if (state.player.healAura > 1.5) state.player.healAura = 0;
        }
        if (state.ai.healAura > 0) {
          state.ai.healAura += 0.03;
          if (state.ai.healAura > 1.5) state.ai.healAura = 0;
        }

        animationId = requestAnimationFrame(animate);
      }

      // Sincroniza barra de vida con Vue
      function emitImpact() {
        // Aquí puedes emitir un evento, llamar a una función, o actualizar el store
        // Ejemplo: gameStore.playerHit(), gameStore.aiHit()
      }

      function triggerPunch(side = 'player') {
        if (!state.punch.active) {
          state.punch.active = true;
          state.punch.side = side;
          state.punch.progress = 0;
          state.punch.hitEffect = 0;
        }
      }

      function triggerHeal(side = 'player') {
        if (side === 'player') state.player.healAura = 0.01;
        else state.ai.healAura = 0.01;
      }

      function resizeCanvas() {
        const canvas = combatCanvas.value;
        if (!canvas) return;
        const parent = canvas.parentElement;
        canvas.width = parent.offsetWidth;
        canvas.height = Math.max(320, parent.offsetHeight);
      }

      onMounted(() => {
        resizeCanvas();
        window.addEventListener('resize', resizeCanvas);
        animationId = requestAnimationFrame(animate);
      });
      onUnmounted(() => {
        window.removeEventListener('resize', resizeCanvas);
        cancelAnimationFrame(animationId);
      });

      // Ejemplo de integración: llama triggerPunch('player') o triggerHeal('player') desde Vue cuando el usuario ataca o cura.
      <!-- Modal de fin de juego (overlay) -->
      <div v-if="partidaTerminada" class="game-over-modal-overlay">
        <div class="game-over-modal">
          <div class="result-icon">
            {{ gameStore.battle.winner === 'player' ? '🏆' : '💀' }}
          </div>
          <h2 class="result-title">
            {{ gameStore.battle.winner === 'player' ? '¡VICTORIA!' : 'DERROTA' }}
          </h2>
          <p class="result-message">
            {{ gameStore.battle.winner === 'player' ? '¡Has derrotado a la IA y ganado el combate!' : 'La IA te ha vencido. Intenta de nuevo.' }}
          </p>
          <div class="result-stats">
            <div class="stat-row">
              <span>Vida restante:</span>
              <strong>{{ gameStore.battle.playerHealth }}</strong>
            </div>
            <div class="stat-row">
              <span>Daño total infligido:</span>
              <strong>{{ calculateTotalDamage }}</strong>
            </div>
            <div class="stat-row">
              <span>Turnos jugados:</span>
              <strong>{{ gameStore.battle.battleLog.length }}</strong>
            </div>
          </div>
          <div class="result-actions">
            <button class="btn btn--primary" @click="retryBattle">Reiniciar</button>
            <button class="btn btn--secondary" @click="goHome">Salir</button>
          </div>
        </div>
      </div>
    <!-- Modal de victoria/derrota -->
    <div v-if="isGameOver" class="gameover-modal-overlay">
      <div class="gameover-modal">
        <div class="result-icon">
          {{ gameStore.battle.winner === 'player' ? '🏆' : '💀' }}
        </div>
        <h2 class="result-title">
          {{ gameStore.battle.winner === 'player' ? '¡VICTORIA!' : 'DERROTA' }}
        </h2>
        <p class="result-message">
          {{ gameStore.battle.winner === 'player' ? '¡Has derrotado a la IA y ganado el combate!' : 'La IA te ha vencido. Intenta de nuevo.' }}
        </p>
        <div class="result-stats">
          <div class="stat-row">
            <span>Vida restante:</span>
            <strong>{{ gameStore.battle.playerHealth }}</strong>
          </div>
          <div class="stat-row">
            <span>Daño total infligido:</span>
            <strong>{{ calculateTotalDamage }}</strong>
          </div>
          <div class="stat-row">
            <span>Turnos jugados:</span>
            <strong>{{ gameStore.battle.battleLog.length }}</strong>
          </div>
        </div>
        <div class="result-actions">
          <button class="btn btn--primary" @click="retryBattle">Reintentar</button>
          <button class="btn btn--secondary" @click="goHome">Salir</button>
        </div>
      </div>
    </div>
  <div class="arena-view" :class="{ 'arena-view--shake': gameStore.cameraShake }">
    <div v-if="specialCardState.blocked" class="arena-block-overlay">
      <div class="arena-block-modal">
        <transition name="fade-in">
          <div v-if="specialCardState.choosing" class="arena-card-reverse-centered">
            <img
              :src="reversaImgUrl"
              alt="Carta Reversa"
              class="arena-card-img"
              @error="onImageError('reversa')"
              v-show="reversaImgLoaded"
              @load="reversaImgLoaded = true"
            />
            <div v-if="!reversaImgLoaded" class="arena-card-img-placeholder">
              <span style="font-size:2rem;">🚫</span>
              <div style="margin-top:0.5rem;">No se pudo cargar la carta reversa.</div>
              <div style="font-size:0.9rem;color:#e74c3c;margin-top:0.25rem;">Verifica conexión o ruta de imagen.</div>
            </div>
            <div class="arena-card-question">¿Quieres la carta?</div>
            <div class="arena-card-actions">
              <button class="arena-card-btn" @click="handleSpecialCardChoice('yes')">Sí</button>
              <button class="arena-card-btn" @click="handleSpecialCardChoice('no')">Paso</button>
            </div>
          </div>
        </transition>
        <transition name="card-flip">
          <div v-if="specialCardState.revealed && specialCardState.card && !specialCardState.moved" class="arena-card-real-block arena-card-hand-focus" @click="moveSpecialCard">
            <img
              :src="specialCardImgUrl"
              :alt="specialCardState.card.name"
              class="arena-card-img"
              @error="onImageError('special')"
              v-show="specialCardImgLoaded"
              @load="onSpecialCardLoaded"
            />
            <div v-if="!specialCardImgLoaded" class="arena-card-img-placeholder">Imagen no disponible</div>
            <div class="arena-card-real-title">{{ specialCardState.card.name }}</div>
            <div class="arena-card-real-desc">{{ specialCardState.card.description }}</div>
            <div class="arena-card-real-effects">
              <div v-for="(value, key) in Object.entries(specialCardState.card.effects).filter(([k]) => !['head','limbs','body','weapon'].includes(k))" :key="key">
                <strong>{{ key }}:</strong> {{ value }}
              </div>
            </div>
          </div>
        </transition>
        <!-- Eliminado: bloque duplicado de carta movida en overlay/modal -->
      </div>
    </div>
    <!-- Carta especial movida: solo debajo del botón Curarse -->
    <div class="arena-battle-flex-grid">
      <div class="arena-battle-col arena-battle-col--left">
        
        <!-- Selector y listado de partes reales -->
        <CharacterParts />
      </div>
      <!-- Pantalla final de victoria/derrota -->
      <div v-if="endScreenState.shown" class="battle-finished-modal">
        
      </div>
      <div class="arena-battle-col arena-battle-col--center"></div>
      <div class="arena-battle-col arena-battle-col--right">
        
      </div>
    </div>
    <div class="epic-bg">
      <div class="epic-glow epic-glow--1"></div>
      <div class="epic-glow epic-glow--2"></div>
      <div class="epic-glow epic-glow--3"></div>
    </div>
    <div class="battle-hud-compact">
      <div class="hud-player">
        <div class="hud-name">{{ gameStore.robot.name || 'Jugador' }}</div>
        <div class="hud-health">
          <div class="health-bar">
            <div class="health-fill" :style="{ width: playerHealthPercent + '%', background: getHealthColor(playerHealthPercent) }"></div>
          </div>
          <span class="health-text">{{ gameStore.battle.playerHealth }}/{{ gameStore.battle.playerMaxHealth }}</span>
        </div>
      </div>
      <div class="hud-vs">VS</div>
      <div class="hud-ai">
        <div class="hud-name">IA</div>
        <div class="hud-health">
          <div class="health-bar">
            <div class="health-fill" :style="{ width: aiHealthPercent + '%', background: getHealthColor(aiHealthPercent) }"></div>
          </div>
          <span class="health-text">{{ gameStore.battle.aiHealth }}/{{ gameStore.battle.aiMaxHealth }}</span>
        </div>
      </div>
    </div>
    <div class="arena-battle-flex-grid">
      <div class="arena-battle-col arena-battle-col--left">
        <div class="arena-robot-action-buttons-grid">
          <button
            class="action-btn action-btn--attack"
            :disabled="gameStore.battle.currentTurn !== 'player' || gameStore.battle.isFinished || endScreenState.shown || specialCardState.blocked"
            @click="onPlayerAttack"
          >
            <span class="action-btn__icon">⚔️</span>
            <span>ATACAR</span>
          </button>
          <button
            class="action-btn action-btn--heal"
            :disabled="gameStore.battle.currentTurn !== 'player' || gameStore.battle.isFinished || endScreenState.shown || specialCardState.blocked"
            @click="onPlayerHeal"
          >
            <span class="action-btn__icon">💊</span>
            <span>CURARSE</span>
          </button>
          import { triggerPunch, triggerHeal } from './ArenaView.vue';

          function onPlayerAttack() {
            triggerPunch('player');
            gameStore.playerAttack();
          }

          function onPlayerHeal() {
            triggerHeal('player');
            gameStore.playerHeal();
          }
          <!-- Carta especial justo debajo del botón Curarse -->
          <div class="special-card-fixed">
            <transition name="move-card">
              <div v-if="specialCardState.moved && specialCardState.card && !gameStore.battle.isFinished" class="arena-card-real-block">
                <div class="special-card-container">
                  <img
                    :src="specialCardImgUrl"
                    :alt="specialCardState.card.name"
                    class="arena-card-img"
                    @error="onImageError('special')"
                    @load="onSpecialCardLoaded"
                  />
                  <div v-if="!specialCardImgUrl || specialCardImgUrl.length === 0 || !specialCardImgLoaded" class="arena-card-img-placeholder">Imagen no disponible</div>
                  <div class="arena-card-real-title">{{ specialCardState.card.name }}</div>
                  <div class="arena-card-real-desc">{{ specialCardState.card.description }}</div>
                  <div class="arena-card-real-effects">
                    <div v-for="([k, v]) in Object.entries(specialCardState.card.effects).filter(([k]) => !['head','limbs','body','weapon'].includes(k))" :key="k">
                      <strong>{{ k }}:</strong> {{ v }}
                    </div>
                  </div>
                </div>
              </div>
            </transition>
          </div>
        </div>
        <!-- Selector y listado de partes reales -->
        <CharacterParts />
      </div>
      <!-- Pantalla final de victoria/derrota -->
      <div v-if="endScreenState.shown" class="battle-finished-modal">
        <div :class="['result-card', endScreenState.victory ? 'result-card--victory' : 'result-card--defeat']">
          <div class="result-icon">
            {{ endScreenState.victory ? '🏆' : '💀' }}
          </div>
          <h2 class="result-title">
            {{ endScreenState.victory ? '¡VICTORIA!' : 'DERROTA' }}
          </h2>
          <p class="result-message">
            {{ endScreenState.victory ? '¡Has derrotado a la IA y ganado el combate!' : 'La IA te ha vencido. Intenta de nuevo.' }}
          </p>
          <div class="result-stats">
            <div class="stat-row">
              <span>Vida restante:</span>
              <strong>{{ gameStore.battle.playerHealth }}</strong>
            </div>
            <div class="stat-row">
              <span>Daño total infligido:</span>
              <strong>{{ calculateTotalDamage }}</strong>
            </div>
            <div class="stat-row">
              <span>Turnos jugados:</span>
              <strong>{{ gameStore.battle.battleLog.length }}</strong>
            </div>
          </div>
          <div class="result-actions">
            <button class="btn btn--primary" @click="retryBattle">Reintentar</button>
            <button class="btn btn--secondary" @click="goHome">Salir</button>
          </div>
        </div>
      </div>
      <div class="arena-battle-col arena-battle-col--center">
          <div :class="['arena-robots', 'arena-robots--hud-style', { 'arena-blur': partidaTerminada }]">
            <!-- Modal de victoria/derrota -->
            <div v-if="partidaTerminada" class="game-over-modal-overlay">
              <div class="game-over-modal">
                <div class="result-icon">
                  {{ gameStore.battle.winner === 'player' ? '🏆' : '💀' }}
                </div>
                <h2 class="result-title">
                  {{ gameStore.battle.winner === 'player' ? '¡VICTORIA!' : 'DERROTA' }}
                </h2>
                <p class="result-message">
                  {{ gameStore.battle.winner === 'player' ? '¡Has derrotado a la IA y ganado el combate!' : 'La IA te ha vencido. Intenta de nuevo.' }}
                </p>
                <div class="result-stats">
                  <div class="stat-row">
                    <span>Vida restante:</span>
                    <strong>{{ gameStore.battle.playerHealth }}</strong>
                  </div>
                  <div class="stat-row">
                    <span>Daño total infligido:</span>
                    <strong>{{ calculateTotalDamage }}</strong>
                  </div>
                  <div class="stat-row">
                    <span>Turnos jugados:</span>
                    <strong>{{ gameStore.battle.battleLog.length }}</strong>
                  </div>
                </div>
                <div class="result-actions">
                  <button class="btn btn--primary" @click="retryBattle">Reiniciar</button>
                  <button class="btn btn--secondary" @click="goHome">Salir</button>
                </div>
              </div>
            </div>
          <div class="arena-robot arena-robot--player">
            <div class="arena-robot__visual">
              <RobotHead :part="gameStore.robot.head" :showLabel="false" />
              <RobotArms :part="gameStore.robot.limbs" :showLabel="false" />
              <RobotBody :part="gameStore.robot.body" :showLabel="false" />
              <RobotLegs :part="gameStore.robot.limbs" :showLabel="false" />
              <div v-if="gameStore.robot.weapon" class="arena-robot__weapon">
                <span class="arena-robot__weapon-icon">🔫</span>
              </div>
            </div>
          </div>
          <div class="arena-robot__vs">VS</div>
          <div class="arena-robot arena-robot--ai">
            <div class="arena-robot__visual">
              <RobotHead :part="gameStore.enemyRobot.head" :showLabel="false" />
              <RobotArms :part="gameStore.enemyRobot.limbs" :showLabel="false" />
              <RobotBody :part="gameStore.enemyRobot.body" :showLabel="false" />
              <RobotLegs :part="gameStore.enemyRobot.limbs" :showLabel="false" />
              <div v-if="gameStore.enemyRobot.weapon" class="arena-robot__weapon">
                <span class="arena-robot__weapon-icon">🔫</span>
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- Cierre de columna central -->
      <div class="arena-battle-col arena-battle-col--right">
        <div class="battle-log" style="margin-bottom:0;max-height:350px;overflow-y:auto;background:rgba(0,0,0,0.25);border-radius:8px;padding:0.5rem 1rem;">
          <div v-for="log in gameStore.battle.battleLog" :key="log.id" style="margin-bottom:0.25rem;">
            <span :style="{color: log.actorType === 'player' ? '#2ecc71' : '#e74c3c', fontWeight:'bold'}">
              {{ log.actorType === 'player' ? gameStore.robot.name : 'IA' }}
            </span>
            <span> — </span>
            <span>{{ log.action }}</span>
            <span v-if="log.damage > 0"> ({{ log.damage > 0 ? (log.actorType === 'player' ? '-' : '-') + log.damage : '' }} HP)</span>
          </div>
        </div>
      </div>
      <!-- Cierre de grid principal -->
    </div>
      
    <div class="turn-indicator">
      <span v-if="gameStore.battle.currentTurn === 'player'" class="turn-label turn-label--player">
        🎮 TU TURNO
      </span>
      <span v-else class="turn-label turn-label--ai">
        🤖 TURNO DE LA IA
      </span>
    </div>
    <!-- Lógica visual de carta especial -->
    <div v-if="specialCardState.shown && !specialCardState.revealed">
      <div class="arena-card-reverse-block">
        <img src="/cards/reversa.png" alt="Carta Reversa" style="width:120px;filter:drop-shadow(0 0 10px #fff);" />
      </div>
      <div v-if="specialCardState.modal" class="modal-overlay">
        <div class="modal">
          <div class="modal-title">¿Quieres la carta?</div>
          <div class="modal-actions">
            <button @click="handleSpecialCardChoice('yes')">Sí</button>
            <button @click="handleSpecialCardChoice('no')">Paso</button>
          </div>
        </div>
      </div>
      <div v-if="specialCardState.animating" class="card-flip-animation">
        <!-- Aquí puedes agregar animación CSS -->
        <img src="/cards/reversa.png" alt="Carta Reversa" style="width:120px;filter:drop-shadow(0 0 10px #fff);transform:rotateY(180deg);transition:transform 0.8s;" />
      </div>
    </div>
    <div v-if="gameStore.battle.error" class="battle-error" style="padding:2rem;text-align:center;color:#e74c3c;font-size:1.3rem;">
      <strong>{{ gameStore.battle.error }}</strong>
    </div>
    <div v-else-if="gameStore.battle.isFinished && ((gameStore.battle.winner === 'player' && gameStore.battle.aiHealth === 0) || (gameStore.battle.winner === 'ai' && gameStore.battle.playerHealth === 0))" class="battle-finished">
      <div :class="['result-card', gameStore.battle.winner === 'player' ? 'result-card--victory' : 'result-card--defeat']">
        <div class="result-icon">
          {{ gameStore.battle.winner === 'player' ? '🏆' : '💀' }}
        </div>
        <h2 class="result-title">
          {{ gameStore.battle.winner === 'player' ? '¡VICTORIA!' : 'DERROTA' }}
        </h2>
        <p class="result-message">
          {{ gameStore.battle.winner === 'player' ? '¡Has derrotado a la IA y ganado el combate!' : 'La IA te ha vencido. Intenta de nuevo.' }}
        </p>
        <div class="result-stats">
          <div class="stat-row">
            <span>Turnos jugados:</span>
            <strong>{{ gameStore.battle.battleLog.length }}</strong>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
/* Blur y bloqueo de interacción en la arena */
.arena-blur {
  filter: blur(8px);
  pointer-events: none;
}
/* Modal overlay de victoria/derrota */
.game-over-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0,0,0,0.5);
  z-index: 9999;
  display: flex;
  justify-content: center;
  align-items: center;
}
.game-over-modal {
  background: linear-gradient(135deg, #2c3e50 0%, #f39c12 100%);
  border: 2px solid #f39c12;
  border-radius: 1rem;
  padding: 2rem 3rem;
  box-shadow: 0 4px 16px rgba(241,196,15,0.15);
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 350px;
  animation: fade-in-modal 0.4s;
}
@keyframes fade-in-modal {
  from { opacity: 0; transform: scale(0.95);}
  to { opacity: 1; transform: scale(1);}
}
/* Modal overlay de fin de juego */
.game-over-modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background-color: rgba(0, 0, 0, 0.7);
  z-index: 9999;
  display: flex;
  justify-content: center;
  align-items: center;
  backdrop-filter: blur(8px);
}
.game-over-modal {
  background: linear-gradient(135deg, #2c3e50 0%, #f39c12 100%);
  border: 2px solid #f39c12;
  border-radius: 1rem;
  padding: 2rem 3rem;
  box-shadow: 0 4px 16px rgba(241,196,15,0.15);
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 350px;
  animation: fade-in-modal 0.4s;
}
@keyframes fade-in-modal {
  from { opacity: 0; transform: scale(0.95);}
  to { opacity: 1; transform: scale(1);}
}
/* Modal de victoria/derrota */
.gameover-modal-overlay {
  position: fixed;
  top: 0; left: 0; right: 0; bottom: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(44, 62, 80, 0.85);
  z-index: 9999;
  display: flex;
  align-items: center;
  justify-content: center;
  backdrop-filter: blur(5px);
}
.gameover-modal {
  background: linear-gradient(135deg, #2c3e50 0%, #f39c12 100%);
  border: 2px solid #f39c12;
  border-radius: 1rem;
  padding: 2rem 3rem;
  box-shadow: 0 4px 16px rgba(241,196,15,0.15);
  display: flex;
  flex-direction: column;
  align-items: center;
  min-width: 350px;
  animation: fade-in-modal 0.4s;
}
@keyframes fade-in-modal {
  from { opacity: 0; transform: scale(0.95);}
  to { opacity: 1; transform: scale(1);}
}
.card-flip-enter-active, .card-flip-leave-active {
  transition: transform 0.8s;
}
.card-flip-enter-from {
  transform: rotateY(180deg);
  opacity: 0;
}
.card-flip-enter-to {
  transform: rotateY(0deg);
  opacity: 1;
}
.card-flip-leave-from {
  transform: rotateY(0deg);
  opacity: 1;
}
.card-flip-leave-to {
  transform: rotateY(180deg);
  opacity: 0;
}
.arena-block-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 100vh;
  background: rgba(44, 62, 80, 0.85);
  z-index: 99999;
  display: flex;
  align-items: center;
  justify-content: center;
  transition: background 0.3s;
}
.arena-block-modal {
  background: linear-gradient(135deg, #2c3e50 0%, #f39c12 100%);
  border: 2px solid #f39c12;
  border-radius: 1rem;
  padding: 2rem 3rem;
  box-shadow: 0 4px 16px rgba(241,196,15,0.15);
  display: flex;
  flex-direction: column;
  align-items: center;
  animation: fade-in-modal 0.4s;
}
.arena-card-reverse-centered {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  animation: fade-in-card 0.5s;
}
.arena-card-img-placeholder {
  width: 120px;
  height: 180px;
  background: #7f8c8d;
  color: #fff;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 12px;
  font-size: 1rem;
  margin-bottom: 1rem;
}
@keyframes fade-in-modal {
  from { opacity: 0; transform: scale(0.95); }
  to { opacity: 1; transform: scale(1); }
}
@keyframes fade-in-card {
  from { opacity: 0; transform: translateY(40px); }
  to { opacity: 1; transform: translateY(0); }
}
.fade-in-enter-active, .fade-in-leave-active {
  transition: opacity 0.4s;
}
.fade-in-enter-from, .fade-in-leave-to {
  opacity: 0;
}
.fade-in-enter-to, .fade-in-leave-from {
  opacity: 1;
}
.arena-card-real-block {
  display: flex;
  flex-direction: row;
  align-items: center;
  margin: 1.5rem 0 0.5rem 0;
  background: linear-gradient(135deg, #2c3e50 0%, #f39c12 100%);
  border: 2px solid #f39c12;
  border-radius: 1rem;
  padding: 1.2rem 2rem;
  box-shadow: 0 4px 16px rgba(241,196,15,0.15);
  transition: transform 0.6s cubic-bezier(0.68,-0.55,0.27,1.55), opacity 0.6s;
  z-index: 9999;
}
.special-card-fixed {
  position: relative;
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: center;
  min-height: 220px;
}
.special-card-container {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  width: 100%;
  height: 100%;
  margin-left: 0;
  margin-top: 0;
}
.arena-card-img {
  width: 100%;
  height: 180px;
  object-fit: cover;
  background: #222;
  border-radius: 12px;
}
.arena-card-real-title {
  font-size: 1.3rem;
  font-weight: bold;
  color: #f39c12;
  margin-top: 0.5rem;
}
.arena-card-real-desc {
  font-size: 1rem;
  color: #fff;
  margin-top: 0.3rem;
  text-align: center;
}
.arena-view {
  /* Aplica el mismo estilo visual del HUD de vida al bloque de robots */
  .arena-robots--hud-style {
    background: linear-gradient(135deg, #1a252f 0%, #2c3e50 100%);
    border: 2px solid #3498db;
    border-radius: 0.75rem;
    padding: 1rem;
    margin: 0;
    box-sizing: border-box;
    width: 100%;
    max-width: 100%;
  }
  min-height: 100vh;
  background: linear-gradient(135deg, #0f1419 0%, #1a252f 100%);
  color: #ecf0f1;
  padding: 0 1rem;
  position: relative;
  overflow: hidden;
}

/* Fondo épico con brillos */
.epic-bg {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: none;
  z-index: 0;
}

.epic-glow {
  position: absolute;
  border-radius: 50%;
  filter: blur(80px);
  opacity: 0.15;
}

.epic-glow--1 {
  width: 600px;
  height: 600px;
  background: radial-gradient(circle, #e74c3c, transparent);
  top: -200px;
  left: -200px;
  animation: glow-move-1 8s ease-in-out infinite;
}

.epic-glow--2 {
  width: 500px;
  height: 500px;
  background: radial-gradient(circle, #3498db, transparent);
  bottom: -150px;
  right: -150px;
  animation: glow-move-2 10s ease-in-out infinite;
}

.epic-glow--3 {
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, #f39c12, transparent);
  top: 50%;
  right: 10%;
  animation: glow-move-3 12s ease-in-out infinite;
}

@keyframes glow-move-1 {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(100px, 100px); }
}

@keyframes glow-move-2 {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(-100px, -100px); }
}

@keyframes glow-move-3 {
  0%, 100% { transform: translate(0, 0); }
  50% { transform: translate(50px, -50px); }
}

.arena-view__container {
  position: relative;
  z-index: 1;
}

.epic-title {
  animation: title-glow 2s ease-in-out infinite;
  text-shadow: 0 0 20px rgba(231, 76, 60, 0.8),
               0 0 40px rgba(231, 76, 60, 0.4),
               0 0 60px rgba(241, 196, 15, 0.3);
  letter-spacing: 3px;
}

@keyframes title-glow {
  0%, 100% { 
    text-shadow: 0 0 20px rgba(231, 76, 60, 0.8),
                 0 0 40px rgba(231, 76, 60, 0.4),
                 0 0 60px rgba(241, 196, 15, 0.3);
  }
  50% { 
    text-shadow: 0 0 30px rgba(231, 76, 60, 1),
                 0 0 60px rgba(231, 76, 60, 0.6),
                 0 0 90px rgba(241, 196, 15, 0.5);
  }
}

.arena-view__container {
  max-width: 1200px;
  margin: 0 auto;
}

.arena-view__header {
  text-align: center;
  margin-bottom: 2rem;
}

.arena-view__header h1 {
  margin: 0;
  font-size: 2rem;
  color: #e74c3c;
  text-transform: uppercase;
}

/* HUD Compacto */
.battle-hud-compact {
  display: grid;
  grid-template-columns: 1fr auto 1fr;
  gap: 2rem;
  align-items: center;
  padding: 1rem;
  background: linear-gradient(135deg, #1a252f 0%, #2c3e50 100%);
  border: 2px solid #3498db;
  border-radius: 0.75rem;
  margin-bottom: 1rem;
}

.hud-player, .hud-ai {
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
}

.hud-name {
  font-weight: 700;
  font-size: 0.95rem;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.hud-player .hud-name {
  color: #2ecc71;
}

.hud-ai .hud-name {
  color: #e74c3c;
}

.hud-health {
  display: flex;
  flex-direction: column;
  gap: 0.25rem;
}

.health-bar {
  width: 100%;
  height: 20px;
  background: #000;
  border: 2px solid #7f8c8d;
  border-radius: 0.25rem;
  overflow: hidden;
}

.health-fill {
  height: 100%;
  background: linear-gradient(90deg, #2ecc71, #f1c40f);
  transition: width 0.3s ease;
}

.health-text {
  font-size: 0.8rem;
  font-weight: 700;
  color: #f1c40f;
}

.hud-vs {
  font-size: 1.5rem;
  font-weight: 900;
  color: #e74c3c;
  text-align: center;
}

.arena-view__content {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.battle-active {
  display: flex;
  flex-direction: column;
  gap: 1rem;
}

.arena-view__actions {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
  gap: 1rem;
}

/* Robots at edges: player left, AI right, with padding to avoid overlap */
.arena-robots {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center; /* Centrado vertical */
  width: 100%;
  gap: 18%;
  padding-left: 2.5rem;
  padding-right: 2.5rem;
  box-sizing: border-box;
}

/* Separador visual entre los robots */

.arena-robots--hud-style {
  background: linear-gradient(135deg, #1a252f 0%, #2c3e50 100%);
  border: 2px solid #3498db;
  border-radius: 0.75rem;
  padding: 1rem;
  margin: 0;
  box-sizing: border-box;
  width: 100%;
  max-width: 100%;
}

.arena-robot {
  display: flex;
  flex-direction: column;
  gap: 1rem;
  flex: 1 1 0;
}

.arena-robot__visual {
  position: relative;
  width: 260px;
  max-width: 100%;
  height: 450px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  margin: 0 65px;
  padding: 0.5rem 0;
  box-sizing: border-box;
}

.arena-robot--player .arena-robot__name {
  color: #2ecc71;
}

.arena-robot--ai .arena-robot__name {
  color: #e74c3c;
}

.arena-robot--ai .arena-robot__visual {
  filter: hue-rotate(330deg) saturate(1.2) brightness(0.95);
}

.arena-robot__weapon {
  position: absolute;
  right: -70px;
  top: 120px;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 6px;
}

.arena-robot__weapon-icon {
  font-size: 2.5rem;
  transform: scaleX(-1);
  filter: drop-shadow(0 0 10px rgba(241, 196, 15, 0.5));
}

.arena-robot__weapon-name {
  font-size: 0.7rem;
  font-weight: 700;
  color: #f1c40f;
  background: rgba(0, 0, 0, 0.7);
  padding: 3px 6px;
  border-radius: 3px;
  white-space: nowrap;
}

.arena-robot__vs {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 100%;
  font-size: 2rem;
  font-weight: 800;
  color: #f39c12;
  text-transform: uppercase;
}

.action-btn {
  padding: 1rem 1.5rem;
  font-size: 1rem;
  font-weight: 700;
  border: none;
  border-radius: 0.5rem;
  cursor: pointer;
  transition: all 0.3s ease;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.75rem;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.action-btn:hover:not(:disabled) {
  transform: translateY(-4px);
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.5);
}

.action-btn:active:not(:disabled) {
  transform: translateY(-2px);
}

.action-btn--attack {
  background: linear-gradient(135deg, #e74c3c, #c0392b);
  color: white;
  box-shadow: 0 0 20px rgba(231, 76, 60, 0.5);
}

.action-btn--attack:hover:not(:disabled) {
  background: linear-gradient(135deg, #e74c3c, #a93226);
  box-shadow: 0 0 40px rgba(231, 76, 60, 0.8),
              0 0 20px rgba(231, 76, 60, 0.6) inset;
}

.action-btn--heal {
  background: linear-gradient(135deg, #2ecc71, #27ae60);
  color: white;
  box-shadow: 0 0 20px rgba(46, 204, 113, 0.5);
}

.action-btn--heal:hover:not(:disabled) {
  background: linear-gradient(135deg, #2ecc71, #1e8449);
  box-shadow: 0 0 40px rgba(46, 204, 113, 0.8),
              0 0 20px rgba(46, 204, 113, 0.6) inset;
}

.action-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.action-btn__icon {
  font-size: 1.25rem;
}

.turn-indicator {
  text-align: center;
  padding: 1rem;
  background-color: #34495e;
  border-radius: 0.5rem;
  border: 2px solid #3498db;
}

.turn-label {
  font-size: 1.2rem;
  font-weight: 700;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.turn-label--player {
  color: #2ecc71;
}

.turn-label--ai {
  color: #e74c3c;
}

.battle-finished {
  display: flex;
  flex-direction: column;
  gap: 2rem;
}

.result-card {
  background: linear-gradient(135deg, #2c3e50 0%, #34495e 100%);
  border: 3px solid #3498db;
  border-radius: 1rem;
  padding: 2rem;
  text-align: center;
}

.result-card--victory {
  border-color: #2ecc71;
  background: linear-gradient(135deg, #1e4620 0%, #2c3e50 100%);
}

.result-card--defeat {
  border-color: #e74c3c;
  background: linear-gradient(135deg, #461e1e 0%, #2c3e50 100%);
}

.result-icon {
  font-size: 4rem;
  margin-bottom: 1rem;
}

.result-title {
  margin: 0 0 0.5rem 0;
  font-size: 2rem;
  font-weight: 900;
  text-transform: uppercase;
}

.result-card--victory .result-title {
  color: #2ecc71;
}

.result-card--defeat .result-title {
  color: #e74c3c;
}

.result-message {
  margin: 0 0 1.5rem 0;
  font-size: 1.1rem;
  color: #bdc3c7;
}

.result-stats {
  display: flex;
  flex-direction: column;
  gap: 0.75rem;
  padding-top: 1.5rem;
  border-top: 2px solid #7f8c8d;
}

.stat-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 0.95rem;
}

.stat-row span {
  color: #bdc3c7;
}

.stat-row strong {
  color: #f1c40f;
  font-size: 1.1rem;
}

.result-actions {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
  gap: 1rem;
}

.btn {
  padding: 0.75rem 1.5rem;
  font-size: 0.95rem;
  font-weight: 600;
  border: none;
  border-radius: 0.375rem;
  cursor: pointer;
  transition: all 0.2s ease;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.btn:hover:not(:disabled) {
  transform: translateY(-2px);
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.3);
}

.btn--primary {
  background-color: #3498db;
  color: white;
}

.btn--primary:hover:not(:disabled) {
  background-color: #2980b9;
}

.btn--secondary {
  background-color: #95a5a6;
  color: white;
}

.btn--secondary:hover:not(:disabled) {
  background-color: #7f8c8d;
}

/* Efectos Épicos */
.arena-view--shake {
  animation: camera-shake 0.3s ease-in-out;
}

@keyframes camera-shake {
  0%, 100% { transform: translate(0, 0) rotate(0deg); }
  10% { transform: translate(-5px, -5px) rotate(-1deg); }
  20% { transform: translate(5px, 5px) rotate(1deg); }
  30% { transform: translate(-5px, 5px) rotate(-1deg); }
  40% { transform: translate(5px, -5px) rotate(1deg); }
  50% { transform: translate(-5px, -5px) rotate(-1deg); }
  60% { transform: translate(5px, 5px) rotate(1deg); }
  70% { transform: translate(-5px, 5px) rotate(-1deg); }
  80% { transform: translate(5px, -5px) rotate(1deg); }
  90% { transform: translate(-5px, -5px) rotate(-1deg); }
}

.combo-counter {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1000;
  text-align: center;
  pointer-events: none;
  animation: combo-pulse 0.5s ease-in-out infinite;
}

.combo-shine {
  position: absolute;
  top: -50px;
  left: 50%;
  transform: translateX(-50%);
  width: 400px;
  height: 400px;
  background: radial-gradient(circle, rgba(255, 215, 0, 0.6), transparent 70%);
  border-radius: 50%;
  animation: combo-shine-pulse 1s ease-in-out infinite;
}

@keyframes combo-shine-pulse {
  0%, 100% { 
    transform: translateX(-50%) scale(1);
    opacity: 0.3;
  }
  50% { 
    transform: translateX(-50%) scale(1.2);
    opacity: 0.6;
  }
}

.combo-count {
  font-size: 6rem;
  font-weight: 900;
  background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  filter: drop-shadow(0 0 30px rgba(240, 147, 251, 1))
          drop-shadow(0 0 60px rgba(245, 87, 108, 0.8));
}

.combo-label {
  font-size: 2.5rem;
  font-weight: 900;
  background: linear-gradient(135deg, #00d4ff, #0099ff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  text-shadow: 0 0 20px rgba(0, 212, 255, 0.8);
  letter-spacing: 5px;
  filter: drop-shadow(0 0 15px rgba(0, 212, 255, 0.6));
}

.combo-multiplier {
  font-size: 3.5rem;
  font-weight: 900;
  color: #ffd700;
  text-shadow: 0 0 30px rgba(255, 215, 0, 1),
               0 0 60px rgba(255, 215, 0, 0.6);
  filter: drop-shadow(0 0 20px rgba(255, 215, 0, 0.8));
}

.combo-pop-enter-active {
  animation: combo-pop-in 0.4s cubic-bezier(0.68, -0.55, 0.265, 1.55);
}

.combo-pop-leave-active {
  animation: combo-pop-out 0.3s ease-in;
}

@keyframes combo-pop-in {
  from {
    transform: translate(-50%, -50%) scale(0);
    opacity: 0;
  }
  to {
    transform: translate(-50%, -50%) scale(1);
    opacity: 1;
  }
}

@keyframes combo-pop-out {
  from {
    transform: translate(-50%, -50%) scale(1);
    opacity: 1;
  }
  to {
    transform: translate(-50%, -50%) scale(1.5);
    opacity: 0;
  }
}

@keyframes combo-pulse {
  0%, 100% {
    transform: translate(-50%, -50%) scale(1);
  }
  50% {
    transform: translate(-50%, -50%) scale(1.1);
  }
}

.particle-container {
  position: fixed;
  inset: 0;
  pointer-events: none;
  z-index: 999;
}

.particle {
  position: absolute;
  font-size: 3rem;
  animation: particle-explode 1s ease-out forwards;
}

.particle--critical {
  font-size: 4rem;
}

@keyframes particle-explode {
  0% {
    transform: scale(0) rotate(0deg);
    opacity: 1;
  }
  100% {
    transform: scale(2) rotate(720deg) translateY(-200px);
    opacity: 0;
  }
}



.arena-battle-flex-grid {
  display: grid;
  grid-template-columns: 15% 70% 15%;
  align-items: flex-start;
  width: 100vw;
  max-width: 100vw;
  margin-left: 0;
  margin-right: 0;
  box-sizing: border-box;
  gap: 0;
}
.arena-battle-col {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-start;
  min-height: 420px;
}
.arena-battle-col--left {
  align-items: flex-end;
  justify-content: flex-start;
}
.arena-battle-col--center {
  align-items: center;
  justify-content: center;
}
.arena-battle-col--right {
  align-items: flex-start;
  justify-content: flex-start;
}
.arena-robot-action-buttons-grid {
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
  align-items: flex-end;
  width: 100%;
  margin-top: 2rem;
}

.arena-battle-flex {
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  gap: 2.5rem;
}

.arena-robot-action-buttons-outside {
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
  align-items: flex-start;
  margin-bottom: 1.5rem;
}

/* Botones de acción al lateral izquierdo del robot en la arena */
.arena-robot-action-layout {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: flex-start;
  gap: 2rem;
}
.arena-robot-action-buttons {
  display: flex;
  flex-direction: column;
  gap: 1.2rem;
  align-items: flex-end;
}
.action-btn {
  background: linear-gradient(135deg, #34495e 0%, #2ecc71 100%);
  color: #fff;
  border: none;
  border-radius: 0.5rem;
  padding: 0.9rem 1.5rem;
  font-size: 1.1rem;
  font-weight: bold;
  cursor: pointer;
  box-shadow: 0 2px 8px rgba(46, 204, 113, 0.15);
  transition: background 0.2s, transform 0.2s;
}
.action-btn.action-btn--attack {
  background: linear-gradient(135deg, #e74c3c 0%, #c0392b 100%);
}
.action-btn.action-btn--heal {
  background: linear-gradient(135deg, #27ae60 0%, #16a085 100%);
}
.action-btn:hover {
  transform: scale(1.07);
  filter: brightness(1.1);
}
</style>
