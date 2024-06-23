<script setup>


import {onMounted, reactive} from "vue";
import axios from "axios";

const state = reactive({

  page: 0,
  pagesCount: 0,
  profilesCount: 0,
  size: 20,

  profiles: []
})


const fetchProfiles = (page) => {
  axios.get("/api/all?page=" + page + '&size=' + state.size)
      .then(res => {
        console.log(res)
        state.profiles = res.data.content
        state.page = res.data.number
        state.pagesCount = res.data.totalPages
        state.profilesCount = res.data.totalElements
      })
      .catch(err => {
        console.log(err)
      })
}

onMounted(() => {
  axios.get("/api/all?page=" + state.page + '&size=' + state.size)
      .then(res => {
        console.log(res)
        state.profiles = res.data.content
        state.pagesCount = res.data.totalPages
        state.profilesCount = res.data.totalElements
      })
      .catch(err => {
        console.log(err)
      })
})

</script>

<template>
  <div>
    <span class="font-black uppercase text-gray-400">
      <font-awesome-icon icon="magnifying-glass" class="mr-2"/>Search
    </span>


    <div class="flex flex-row p-4 gap-2">

      <input type="text" class="w-72 px-2 py-1 outline outline-1 rounded-2xl outline-gray-600" placeholder="Name">
      <input type="text" class="w-72 px-2 py-1 outline outline-1 rounded-2xl outline-gray-600" placeholder="IRS EIN">
      <button class="bg-blue-600 px-6 py-2 rounded-2xl text-white font-semibold hover:bg-blue-400">Search</button>

    </div>


    <div class="flex flex-row">

      Page: {{ state.page + 1 }} of {{ state.pagesCount }}
      Items: {{ state.profiles.length }} of {{ state.profilesCount }}

    </div>


    <div class="pagination flex flex-row gap-2">
      <button @click="fetchProfiles(0)" :disabled="state.page === 0" class="bg-blue-800 py-1 px-2 rounded-3xl text-white disabled:bg-blue-200">First</button>
      <button @click="fetchProfiles(state.page - 1)" :disabled="state.page === 0" class="bg-blue-800 py-1 px-2 rounded-3xl text-white disabled:bg-blue-200">Previous</button>
      <button @click="fetchProfiles(state.page + 1)" :disabled="state.page >= state.pagesCount - 1" class="bg-blue-800 py-1 px-2 rounded-3xl text-white disabled:bg-blue-200">Next</button>
      <button @click="fetchProfiles(state.pagesCount - 1)" :disabled="state.page >= state.pagesCount - 1" class="bg-blue-800 py-1 px-2 rounded-3xl text-white disabled:bg-blue-200">Last</button>
    </div>

    <div class="flex flex-row flex-wrap gap-3 p-4">


      <div v-for="profile in state.profiles" :key="profile.id" class="w-72 bg-blue-100 p-4">
        <p>ID: <strong> {{ profile.id }}</strong></p>
        <p>IRS-EIN: <strong> {{ profile.irsEin }}</strong></p>
        <p>Organization Name: <strong> {{ profile.orgName }}</strong></p>
        <a :href="profile.link" class="text-blue-800 underline">Link to web</a>
      </div>

    </div>

  </div>


</template>

<style scoped>

</style>