<template>
  <v-content>
    <div id="core-view">
      <v-fade-transition mode="out-in">
        <h1>{{title}}</h1>
      </v-fade-transition>
      <v-fade-transition mode="out-in">
        <router-view/>
      </v-fade-transition>
    </div>

    <core-footer v-if="$route.name !== 'Maps'"/>
  </v-content>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  metaInfo() {
    return {
      title: "Realtime Keynote Feedback"
    };
  },

  data() {
    return {
      title: null
    };
  },

  watch: {
    $route(val) {
      this.title = val.name;
    }
  },
  methods: {
    ...mapMutations("app", ["setDrawer", "toggleDrawer"]),
    onClickBtn() {
      this.setDrawer(!this.$store.state.app.drawer);
    }
  }
};
</script>

<style>
#core-view {
  margin-top: 70px;
  min-height: calc(100vh - 93px - 70px);
}
h1 {
  padding: 0;
  padding-top: 20px;
  padding-left: 30px;
  padding: 10px;
  text-transform: uppercase;
  color: rgb(34, 34, 34);
  display: none;

  font-weight: bold;
  font-size: 12px;
  /* background-color: #3a3a3a7a;*/
  margin-bottom: 0;
}
.container {
  padding-top: 0;
}
</style>
