<script setup>
import axios from "axios";
import {onMounted, ref} from "vue";

defineProps({
  msg: {
    type: String,
    required: true,
  },
})
const airplanes = ref([])
const model = ref("")
const year = ref(2025)
onMounted(() => {
  getAirplanes()
})

async function getAirplanes() {
  await axios.get("/api/airplanes").then(
      response => (airplanes.value = response.data));
}

async function createAirplane() {
  await axios.post("/api/airplanes", {'name': model.value, 'prod_year': year.value}).then(
      response => (getAirplanes()));
}

async function takeOffPlane(id) {
  await axios.post("/api/airplanes/flight/take_off/" + id).then(
      response => (getAirplanes()));
}

async function landPlane(id) {
  await axios.post("/api/airplanes/flight/land/" + id).then(
      response => (getAirplanes()));
}

async function deleteAirplane(id) {
  await axios.get("/api/airplanes/" + id).then(
      response => (getAirplanes()));
}
</script>

<template>
  <h1>DFSBOVBSOEVBSOVBSOYVBSOYVBSODYVBSF</h1>
  <div>
    <h1>Список самолетов</h1>
    <table>
      <thead>
      <tr>
        <th>id</th>
        <th>name</th>
        <th>prod_year</th>
        <th>in_flight</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="airplane in airplanes" :key="airplane.id">
        <td>{{ airplane.id }}</td>
        <td>{{ airplane.model }}</td>
        <td>{{ airplane.prod_year }}</td>
        <td>{{ airplane.in_flight }}</td>
      </tr>
      </tbody>
    </table>
  </div>
  <div>
    <h1>Добавить самолет</h1>
    <input type="text" placeholder="Модель" v-model="model" v-on:enter="createAirplane"/>
    <br>
    <input type="number" placeholder="Год выпуска" v-model="year"/>
    <br>
    <button v-on:click="createAirplane()">Добавить</button>
  </div>
  <div>
    <h1>Отправить в полет</h1>
    <table>
      <thead>
      <tr>
        <th>id</th>
        <th>name</th>
        <th>Действие</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="airplane in airplanes" :key="airplane.id">
        <td v-if="airplane.in_flight === false">{{ airplane.id }}</td>
        <td v-if="airplane.in_flight === false">{{ airplane.model }}</td>
        <td v-if="airplane.in_flight === false">
          <button v-on:click="takeOffPlane(airplane.id)">Взлет</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
  <div>
    <h1>Отправить на посадку</h1>
    <table>
      <thead>
      <tr>
        <th>id</th>
        <th>name</th>
        <th>Действие</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="airplane in airplanes" :key="airplane.id">
        <td v-if="airplane.in_flight === true">{{ airplane.id }}</td>
        <td v-if="airplane.in_flight === true">{{ airplane.model }}</td>
        <td v-if="airplane.in_flight === true">
          <button v-on:click="landPlane(airplane.id)">Посадка</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
  <div>
    <h1>Удалить</h1>
    <table>
      <thead>
      <tr>
        <th>id</th>
        <th>name</th>
        <th>Действие</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="airplane in airplanes" :key="airplane.id">
        <td>{{ airplane.id }}</td>
        <td>{{ airplane.model }}</td>
        <td>
          <button v-on:click="deleteAirplane(airplane.id)">Удалить</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>

</template>

<style scoped>
</style>