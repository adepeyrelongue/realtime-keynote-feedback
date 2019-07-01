<template>
  <v-container style="margin-top:-20px;" fill-height fluid>
    <v-layout wrap>
      <v-flex xs12 lg6>
        <keynote-video :sources="video.sources" :options="video.options"></keynote-video>
      </v-flex>

      <v-flex md12 sm12 lg6>
        <v-flex md12 sm12 lg12>
          <v-btn
            style="margin-top:30px;"
            color="primary"
            v-if="!started"
            @click="start"
            :small="true"
          >
            <v-icon>mdi-record</v-icon>Démarrer
          </v-btn>
          <v-btn
            style="margin-top:30px;"
            v-if="!running & started"
            :disabled="!started || running"
            @click="run"
            color="primary"
            :small="true"
          >
            <v-icon>mdi-play</v-icon>Reprendre
          </v-btn>
          <v-btn
            style="margin-top:30px;"
            v-if="started && running"
            @click="pause"
            color="primary"
            :small="true"
          >
            <v-icon>mdi-pause</v-icon>Pause
          </v-btn>

          <v-btn
            style="margin-top:30px;"
            @click="showDialog"
            v-if="started"
            color="primary"
            :small="true"
          >
            <v-icon>mdi-check</v-icon>&nbsp;Terminer la séance
          </v-btn>
        </v-flex>
        <material-chart-card
          :ratio="'ct-major-twelfth'"
          :data="attentionChart.data"
          :options="attentionChart.options"
          color="#444"
          type="Line"
        >
          <h4 class="title font-weight-light">Évolution de l'attention</h4>
          <p
            class="category d-inline-flex font-weight-light"
          >Moyenne calculée sur l'attention de chaque étudiant lors de la séance.</p>
        </material-chart-card>

        <v-dialog v-model="dialog" max-width="600">
          <v-card>
            <v-card-title class="headline">Êtes-vous sur de vouloir terminer cette séance ?</v-card-title>
            <v-card-text>
              Vous êtes sur le point d'arréter l'analyse de la séance, une fois terminée vous ne pourrez plus reprendre la capture vidéo.
              Consultez votre liste de séances pour lire toutes les statistiques réalisées.
            </v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>

              <v-btn
                color="primary darken-1"
                flat="flat"
                @click="stayAfterDialog"
              >Reprendre l'analyse</v-btn>

              <v-btn
                color="primary darken-1"
                flat="flat"
                @click="quitAfterDialog"
                :to="fowardPage"
              >Terminer la séance</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import keynoteVideo from "vue-video";
//import CssCircle from "v-circle/components/css-circle.vue";
export default {
  data: function() {
    return {
      dialog: false,
      started: false,
      running: false,
      timer: null,
      fowardPage: "/statistiques-seance",
      attentionChart: {
        data: {
          series: [
            [
              12,
              17,
              7,
              17,
              23,
              18,
              38,
              12,
              17,
              7,
              17,
              23,
              18,
              38,
              12,
              17,
              7,
              17,
              23,
              18,
              38,
              12,
              17,
              7,
              17,
              23,
              18,
              38
            ]
          ]
        },
        options: {
          lineSmooth: this.$chartist.Interpolation.cardinal({
            tension: 10
          }),
          low: 0,
          high: 50,
          chartPadding: {
            top: 20,
            right: 10,
            bottom: 0,
            left: 0
          }
        }
      },

      video: {
        sources: [
          {
            src: "http://covteam.u.qiniudn.com/oceans.mp4",
            type: "video/mp4"
          }
        ],
        options: {
          autoplay: false,
          volume: 0.6,
          poster:
            "https://i1.wp.com/voiceable.ai/wp-content/uploads/2016/12/Audience-Listening-to-Public-Speaker-.jpg?ssl=1"
        }
      }
    };
  },
  methods: {
    start() {
      this.started = true;
      this.run();
    },
    run() {
      var min = 0,
        max = 50;
      this.running = true;
      this.timer = setInterval(() => {
        var tab = this.attentionChart.data.series[0];
        var firstVal = Math.floor(Math.random() * (max - min + 1) + min);
        tab.shift();
        tab.push(firstVal);
      }, 100);
    },
    pause() {
      this.running = false;
      clearInterval(this.timer);
    },
    showDialog() {
      this.dialog = true;
    },
    stayAfterDialog() {
      this.dialog = false;
    },
    quitAfterDialog() {
      this.dialog = false;
      this.stop();
    },
    stop() {
      this.running = false;
      this.started = false;
      clearInterval(this.timer);
    }
  },
  components: {
    keynoteVideo
  }
};
</script>

<style lang="scss">
</style>
