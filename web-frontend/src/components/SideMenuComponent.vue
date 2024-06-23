<script setup>

import {routes} from "@/router/routes.js";
</script>

<template>
  <aside class="w-64 bg-blue-700 text-white">
    <div class="p-4 pr-0">
      <div class=" text-center flex flex-col">
        <span class="font-black text-4xl">CP</span>
        <span class="text-xs text-gray-200">company parser</span>
      </div>
      <div class="flex flex-col mt-4 gap-1">
        <router-link v-for="route in routes" :key="route.name" :to="route.path" class="px-4 py-1 rounded-l-xl hover:bg-blue-500" >
          <font-awesome-icon :icon="route.icon" class="mr-2" />
          {{route.label}}
        </router-link>
      </div>
    </div>
  </aside>

  <!--    <aside class="w-64 bg-gray-800 text-white fixed inset-y-0">-->
  <!--      <div class="p-4">-->
  <!--        <h1 class="text-xl font-bold">AFS</h1>-->
  <!--        <nav class="mt-4">-->
  <!--          <ul>-->
  <!--            <li class="mt-2">-->
  <!--              <a href="#" class="block py-2 px-4 rounded hover:bg-gray-700">Home</a>-->
  <!--            </li>-->
  <!--            <li class="mt-2">-->
  <!--              <a href="#" class="block py-2 px-4 rounded hover:bg-gray-700">About</a>-->
  <!--            </li>-->
  <!--            <li class="mt-2">-->
  <!--              <a href="/progress" class="block py-2 px-4 rounded hover:bg-gray-700">Прогрес</a>-->
  <!--            </li>-->
  <!--            <li class="mt-2">-->
  <!--              <a href="#" class="block py-2 px-4 rounded hover:bg-gray-700">Contact</a>-->
  <!--            </li>-->
  <!--          </ul>-->
  <!--        </nav>-->
  <!--      </div>-->
  <!--    </aside>-->

</template>

<style scoped>
.router-link-active {
  @apply bg-gradient-to-r from-gray-100 to-white text-blue-700 shadow-xl;
}


.router-link-active:hover {
  @apply bg-white text-blue-700 !important;
}
</style>