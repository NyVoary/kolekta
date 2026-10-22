<script setup lang="ts">
withDefaults(defineProps<{ size?: number }>(), { size: 32 })
</script>

<template>
  <span class="inline-flex items-center gap-2" aria-label="Kolekta" role="img">
    <svg
      :width="size"
      :height="size"
      viewBox="0 0 28 28"
      xmlns="http://www.w3.org/2000/svg"
      aria-hidden="true"
      focusable="false"
    >
      <path d="M5 23C5 11 12 5 24 4C24 16 18 23 5 23Z" fill="#2F7D46" />
      <path
        d="M5 23C10 17 15 12 20 9"
        fill="none"
        stroke="#F3EEE1"
        stroke-width="1.6"
        stroke-linecap="round"
      />
    </svg>
    <span class="font-heading text-2xl font-extrabold text-foret" aria-hidden="true">Kolekta</span>
  </span>
</template>
