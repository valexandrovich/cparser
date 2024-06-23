<script setup>
import SideMenuComponent from "@/components/SideMenuComponent.vue";
</script>

<template>
  <div class="flex min-h-screen">
    <side-menu-component/>
    <main class="flex-1 p-4">
      <router-view></router-view>
    </main>
  </div>

</template>

<style scoped>

</style>
