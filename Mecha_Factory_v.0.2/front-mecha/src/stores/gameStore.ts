
import { defineStore } from 'pinia';
import { reactive, ref, computed } from 'vue';
import type { Robot, Part, BattleState, Achievement, PlayerStats } from '../types';
import { calculateRobotStats } from '../utils/calculations';
import { robotService } from '../services/robotService';
import { fetchParts } from '../services/partService';

export const useGameStore = defineStore('game', () => {
			// Parts system stubs
			const partsLoading = ref(false);
			const partsError = ref(null);
			const partsData = ref<{ head: Part[]; body: Part[]; limbs: Part[]; weapon: Part[] }>({ head: [], body: [], limbs: [], weapon: [] });

			async function loadParts() {
				partsLoading.value = true;
				partsError.value = null;
				try {
					const data = await fetchParts();
					partsData.value = data;
				} catch (e: any) {
					partsError.value = e.message || 'Error al cargar partes';
				} finally {
					partsLoading.value = false;
				}
			}

			function getPartsByCategory(category: 'head' | 'body' | 'limbs' | 'weapon'): Part[] {
				if (!partsData.value[category]) return [];
				return partsData.value[category];
			}
			function setPart(category: 'head' | 'body' | 'limbs' | 'weapon', part: Part | null) {
				// Stub: set part logic
				robot[category] = part;
			}
			function resetGame() {
				// Stub: reset game logic
			}
		// Card system stub
		const cardState = reactive({
			showReverse: false,
			canReveal: false,
			revealed: false,
			realCard: null as any
		});

		function loadCards() {
			// Stub: load cards logic
			cardState.showReverse = true;
			cardState.canReveal = true;
			cardState.revealed = false;
			cardState.realCard = { image: 'default.png', name: 'Default Card', description: 'Demo card.' };
		}

		function revealRealCard() {
			cardState.revealed = true;
		}

		function playerAttack() {
			// Validación: solo puede actuar si es su turno, no está terminado, no está derrotado
			if (battle.isFinished || battle.currentTurn !== 'player' || battle.playerHealth <= 0) return;
			// Cálculo de daño avanzado
			const attacker = calculateRobotStats(robot);
			const defender = calculateRobotStats(enemyRobot);
			// Buffs/debuffs
			let attackMod = 0;
			if ((robot as any).buff) attackMod += Number((robot as any).buff) || 0;
			if ((robot as any).debuff) attackMod -= Number((robot as any).debuff) || 0;
			let baseAttack = attacker.attack + attackMod;
			// Crítico
			const isCritical = Math.random() < (attacker.critChance ?? 0);
			let evade = Math.random() < (defender.evasion ?? 0);
			let resist = defender.resistance ?? 0;
			let reductionPct = defender.damageReduction ?? 0;
			let lifesteal = attacker.lifesteal ?? 0;
			let reduction = (defender.defense ?? 0) * 0.45;
			let finalDamage = baseAttack - reduction;
			if (isCritical) finalDamage *= attacker.critMultiplier || 1.5;
			finalDamage *= (1 - reductionPct);
			finalDamage -= resist;
			finalDamage = Math.max(1, Math.floor(finalDamage));
			if (evade) finalDamage = 0;
			// Aplicar daño
			battle.aiHealth = Math.max(0, battle.aiHealth - finalDamage);
			// Robo de vida
			if (lifesteal && finalDamage > 0) {
				battle.playerHealth = Math.min(battle.playerHealth + Math.floor(finalDamage * lifesteal), battle.playerMaxHealth);
			}
			// Log
			battle.battleLog.push({
				id: 'log_' + Date.now(),
				timestamp: Date.now(),
				action: isCritical ? 'ataque crítico' : 'ataque',
				damage: finalDamage,
				actorType: 'player'
			});
			// Victoria
			if (battle.aiHealth <= 0) {
				battle.isFinished = true;
				battle.winner = 'player';
				return;
			}
			// Cambiar turno
			battle.currentTurn = 'ai';
			setTimeout(() => aiTurn(), 800);
		}

		function playerHeal() {
			// Validación: solo puede curar si es su turno, no está terminado, no está derrotado
			if (battle.isFinished || battle.currentTurn !== 'player' || battle.playerHealth <= 0) return;
			// Cálculo de curación
			let heal = Math.floor(robot.totalStats.attack * 0.5 + 10);
			if ((robot as any).buff) heal += Number((robot as any).buff) || 0;
			if ((robot as any).debuff) heal -= Number((robot as any).debuff) || 0;
			const prevHealth = battle.playerHealth;
			battle.playerHealth = Math.min(battle.playerHealth + heal, battle.playerMaxHealth);
			const realHeal = battle.playerHealth - prevHealth;
			// Log
			battle.battleLog.push({
				id: 'log_' + Date.now(),
				timestamp: Date.now(),
				action: 'cura',
				damage: realHeal,
				actorType: 'player'
			});
			// Cambiar turno
			battle.currentTurn = 'ai';
			setTimeout(() => aiTurn(), 800);
		}
	// State
	const robot = reactive<Robot>({
		id: 'robot_' + Date.now(),
		name: 'Mecha Prototype',
		head: null,
		body: null,
		limbs: null,
		weapon: null,
		totalStats: { health: 0, attack: 0, speed: 0 },
		budget: 1000,
		spentBudget: 0
	});
	const battle = reactive<BattleState & { error?: string }>({
		playerHealth: 0,
		playerMaxHealth: 0,
		aiHealth: 0,
		aiMaxHealth: 0,
		isFinished: false,
		winner: null,
		battleLog: [],
		currentTurn: 'player',
		error: undefined
	});
	const aiProgression = reactive({
		defeats: 0,
		lastResult: null as null | 'win' | 'lose'
	});
	const aiEquipment = reactive({
		head: null as Part | null,
		body: null as Part | null,
		limbs: null as Part | null,
		weapon: null as Part | null
	});
	const playerStats = reactive<PlayerStats>({
		totalBattles: 0,
		wins: 0,
		losses: 0,
		totalDamageDealt: 0,
		totalDamageTaken: 0,
		criticalHits: 0,
		perfectVictories: 0,
		longestCombo: 0,
		highestDamage: 0,
		robotsBuilt: 0,
		creditsSpent: 0
	});
	const achievements = reactive<Achievement[]>([]);
	const recentAchievement = ref<Achievement | null>(null);
	const cameraShake = ref(false);
	const particleEffects = ref<any[]>([]);
	const enemyRobot = reactive<Robot>({
		id: 'enemy_' + Date.now(),
		name: 'Enemy Mecha',
		head: null,
		body: null,
		limbs: null,
		weapon: null,
		totalStats: { health: 0, attack: 0, speed: 0 },
		budget: 1000,
		spentBudget: 0
	});
	const combo = ref(0);

	// Computed properties
	const remainingBudget = computed(() => robot.budget - robot.spentBudget);
	const isRobotComplete = computed(() => Boolean(robot.head && robot.body && robot.limbs && robot.weapon));
	const playerHealthPercent = computed(() => {
		if (battle.playerMaxHealth === 0) return 0;
		return (battle.playerHealth / battle.playerMaxHealth) * 100;
	});
	const aiHealthPercent = computed(() => {
		if (battle.aiMaxHealth === 0) return 0;
		return (battle.aiHealth / battle.aiMaxHealth) * 100;
	});
	const winRate = computed(() => {
		if (playerStats.totalBattles === 0) return 0;
		return Number(((playerStats.wins / playerStats.totalBattles) * 100).toFixed(1));
	});

	// Actions (add your functions here)
	function updateSpentBudget() {
		robot.spentBudget = [robot.head, robot.body, robot.limbs, robot.weapon]
			.filter((p): p is Part => !!p)
			.reduce((sum: number, p: Part) => sum + (p.cost || 0), 0);
		updateRobotStats();
	}
	function updateRobotStats() {
		robot.totalStats = calculateRobotStats(robot);
	}
	function setRobotName(name: string) {
		robot.name = name;
	}
	function saveRobot() {
		robotService.saveRobot(robot);
	}
	function loadSavedRobot() {
		const saved = robotService.loadRobot();
		if (saved) {
			Object.assign(robot, saved);
			updateRobotStats();
		}
	}
	function aiTurn() {
		if (battle.isFinished || battle.aiHealth <= 0) return;
		// Decisión IA: curar si salud < 30%, atacar si jugador < 30, sino aleatorio
		const healthPct = battle.aiHealth / battle.aiMaxHealth;
		let action: 'attack' | 'heal' = 'attack';
		if (healthPct < 0.3) action = 'heal';
		else if (battle.playerHealth < 30) action = 'attack';
		else action = Math.random() > 0.4 ? 'attack' : 'heal';
		if (action === 'attack') {
			// Cálculo de daño avanzado IA
			const attacker = calculateRobotStats(enemyRobot);
			const defender = calculateRobotStats(robot);
			let attackMod = 0;
			if ((enemyRobot as any).buff) attackMod += Number((enemyRobot as any).buff) || 0;
			if ((enemyRobot as any).debuff) attackMod -= Number((enemyRobot as any).debuff) || 0;
			let baseAttack = attacker.attack + attackMod;
			const isCritical = Math.random() < (attacker.critChance ?? 0);
			let evade = Math.random() < (defender.evasion ?? 0);
			let resist = defender.resistance ?? 0;
			let reductionPct = defender.damageReduction ?? 0;
			let lifesteal = attacker.lifesteal ?? 0;
			let reduction = (defender.defense ?? 0) * 0.45;
			let finalDamage = baseAttack - reduction;
			if (isCritical) finalDamage *= attacker.critMultiplier || 1.5;
			finalDamage *= (1 - reductionPct);
			finalDamage -= resist;
			finalDamage = Math.max(1, Math.floor(finalDamage));
			if (evade) finalDamage = 0;
			battle.playerHealth = Math.max(0, battle.playerHealth - finalDamage);
			// Robo de vida IA
			if (lifesteal && finalDamage > 0) {
				battle.aiHealth = Math.min(battle.aiHealth + Math.floor(finalDamage * lifesteal), battle.aiMaxHealth);
			}
			battle.battleLog.push({
				id: 'log_' + Date.now(),
				timestamp: Date.now(),
				action: isCritical ? 'ataque crítico' : 'ataque',
				damage: finalDamage,
				actorType: 'ai'
			});
			// Derrota
			if (battle.playerHealth <= 0) {
				battle.isFinished = true;
				battle.winner = 'ai';
				return;
			}
		} else {
			// Curación IA avanzada
			const attacker = calculateRobotStats(enemyRobot);
			let heal = Math.floor(attacker.attack * 0.5 + 10);
			if ((enemyRobot as any).buff) heal += Number((enemyRobot as any).buff) || 0;
			if ((enemyRobot as any).debuff) heal -= Number((enemyRobot as any).debuff) || 0;
			const prevHealth = battle.aiHealth;
			battle.aiHealth = Math.min(battle.aiHealth + heal, battle.aiMaxHealth);
			const realHeal = battle.aiHealth - prevHealth;
			battle.battleLog.push({
				id: 'log_' + Date.now(),
				timestamp: Date.now(),
				action: 'cura',
				damage: realHeal,
				actorType: 'ai'
			});
		}
		// Cambiar turno
		battle.currentTurn = 'player';
	}

	function startBattle() {
		if (!(robot.head && robot.body && robot.limbs && robot.weapon)) {
			battle.isFinished = true;
			battle.winner = null;
			battle.battleLog = [];
			battle.currentTurn = 'player';
			battle.error = 'Debes equipar todas las partes del robot antes de luchar.';
			return;
		}
		battle.isFinished = false;
		battle.winner = null;
		battle.battleLog = [];
		battle.currentTurn = 'player';
		battle.playerMaxHealth = robot.totalStats.health;
		battle.playerHealth = robot.totalStats.health;
		battle.aiMaxHealth = Math.floor(robot.totalStats.health * 1.2);
		battle.aiHealth = battle.aiMaxHealth;
		// Turno de la IA se ejecuta cuando corresponda
	}

	// Return block
	return {
		partsLoading,
		partsError,
		loadParts,
		getPartsByCategory,
		setPart,
		resetGame,
		robot,
		battle,
		aiProgression,
		aiEquipment,
		playerStats,
		achievements,
		recentAchievement,
		cameraShake,
		particleEffects,
		enemyRobot,
		combo,
		remainingBudget,
		isRobotComplete,
		playerHealthPercent,
		aiHealthPercent,
		winRate,
		updateSpentBudget,
		updateRobotStats,
		setRobotName,
		saveRobot,
		loadSavedRobot,
		startBattle,
		// Card system
		cardState,
		loadCards,
		revealRealCard,
		playerAttack,
		playerHeal,
		aiTurn
		// Add other actions here
	};
});

