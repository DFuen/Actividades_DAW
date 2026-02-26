<template>
  <button
    :class="[
      'btn',
      `btn--${variant}`,
      {
        'btn--disabled': disabled,
        'btn--loading': loading
      }
    ]"
    :disabled="disabled || loading"
  >
    <span v-if="!loading" class="btn__text">
      <slot></slot>
    </span>
    <span v-else class="btn__spinner">⟳</span>
  </button>
</template>

<script setup lang="ts">
interface Props {
  variant?: 'primary' | 'secondary' | 'danger' | 'success'
  disabled?: boolean
  loading?: boolean
}

withDefaults(defineProps<Props>(), {
  variant: 'primary',
  disabled: false,
  loading: false
})
</script>

<style scoped>
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

.btn:active:not(:disabled) {
  transform: translateY(0);
}

/* Variantes */
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

.btn--danger {
  background-color: #e74c3c;
  color: white;
}

.btn--danger:hover:not(:disabled) {
  background-color: #c0392b;
}

.btn--success {
  background-color: #2ecc71;
  color: white;
}

.btn--success:hover:not(:disabled) {
  background-color: #27ae60;
}

.btn--disabled,
.btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.btn__spinner {
  display: inline-block;
  animation: spin 0.6s linear infinite;
}

@keyframes spin {
  from {
    transform: rotate(0deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
