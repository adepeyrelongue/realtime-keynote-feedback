<template>
  <v-app>
    <core-toolbar/>
    <core-view/>
  </v-app>
</template>

<style lang="scss">
@import "@/styles/index.scss";

/* Remove in 1.2 */
.v-datatable thead th.column.sortable i {
  vertical-align: unset;
}
body > #app {
  /* background: url("../public/img/montagne2.jpg");*/
  background-repeat: no-repeat;
  background-size: 2000px;
  background-attachment: fixed;
  background-position: center;
}
</style>
