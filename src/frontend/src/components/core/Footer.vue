<template>
  <v-footer height="auto" color="#000">
    <v-btn v-for="(link,index) in links" :key="index" color="white" flat round>{{ link.name }}</v-btn>
    <v-spacer></v-spacer>
    <p style="color:rgb(255,255,255,0.8);">
      <strong>&copy;2019 — Realtime Keynote Feedback</strong>
    </p>
  </v-footer>
</template>

<script>
export default {
  data: () => ({
    links: [
      { name: "Accueil", Link: "/dashboard" },
      { name: "À propos", Link: "/a-propos" }
    ]
  })
};
</script>

<style>
#core-footer {
  z-index: 0;
}
</style>
