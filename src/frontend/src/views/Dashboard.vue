<template>
  <v-container fill-height fluid grid-list-xl v-if="responseData">
    <v-layout wrap>
      <v-flex md12 sm12 lg4>
        <material-chart-card
          :data="attentionChart.data"
          :options="attentionChart.options"
          color="blue"
          type="Line"
        >
          <h4 class="title font-weight-light">Évolution de l'attention</h4>
          <p class="category d-inline-flex font-weight-light">Attention moyenne par mois.</p>
          <template slot="actions">
            <v-icon class="mr-2" small>mdi-clock-outline</v-icon>
            <span class="caption grey--text font-weight-light">Sur les 6 derniers mois</span>
          </template>
        </material-chart-card>
      </v-flex>
      <v-flex md12 sm12 lg4>
        <material-chart-card
          :data="absentChart.data"
          :options="absentChart.options"
          :responsive-options="absentChart.responsiveOptions"
          color="red"
          type="Bar"
        >
          <h4 class="title font-weight-light">Évolution des absences</h4>
          <p class="category d-inline-flex font-weight-light">Pourcentage d'absents par mois.</p>

          <template slot="actions">
            <v-icon class="mr-2" small>mdi-clock-outline</v-icon>
            <span class="caption grey--text font-weight-light">Sur les 6 derniers mois</span>
          </template>
        </material-chart-card>
      </v-flex>
      <v-flex md12 sm12 lg4>
        <material-chart-card
          :data="attentionVariationChart.data"
          :options="attentionVariationChart.options"
          color="primary"
          type="Line"
        >
          <h3 class="title font-weight-light">Stabilité de l'attention</h3>
          <p class="category d-inline-flex font-weight-light">Évolution de la variation d'attention.</p>

          <template slot="actions">
            <v-icon class="mr-2" small>mdi-clock-outline</v-icon>
            <span class="caption grey--text font-weight-light">Sur les 6 derniers mois</span>
          </template>
        </material-chart-card>
      </v-flex>
      <v-flex sm6 xs12 md6 lg4>
        <material-stats-card
          color="primary"
          icon="mdi-account-group"
          title="Attention moyenne"
          :value="avgAttention+'/50'"
          sub-icon="mdi-information-outline"
          sub-text="sur les 6 derniers mois"
        />
      </v-flex>

      <v-flex sm6 xs12 md6 lg8>
        <v-alert
          :value="true"
          type="info"
          :color="analysisComputed.color"
          style="margin-top:25px;"
        >{{analysisComputed.message}}</v-alert>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import DashboardService from "../services/Dashboard";
import StatisticsHelper from "../helpers/StatisticsHelper";
import FormatterHelper from "../helpers/FormatterHelper";

export default {
  data() {
    return {
      analysis: {
        low: {
          color: "red",
          message: `Le niveau d'attention sur les 6 derniers mois est dans la moyenne basse (inférieur à 20).
         Vous devez améliorer l'attention de votre auditoire en suivant nos conseils personalisés.`
        },
        medium: {
          color: "orange",
          message: `Le niveau d'attention sur les 6 derniers mois est dans la moyenne (entre 20 et 35).
          Ce niveau correspond à une attention satisfaisante mais perfectible.
          Si vous souhaitez en savoir plus, consultez nos conseils pour accroitre l'attention de votre public.`
        },
        high: {
          color: "green",
          message: `Le niveau d'attention sur les 6 derniers mois est dans la moyenne haute (entre 35 et 50).
          Si vous souhaitez en savoir plus, consultez nos conseils pour améliorer l'attention de votre public.`
        }
      },
      responseData: null,
      avgAttention: "",
      attentionChart: {
        data: {
          labels: [],
          series: [[]]
        },
        options: {
          lineSmooth: this.$chartist.Interpolation.cardinal({
            tension: 0
          }),
          low: 0,
          high: 50, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
          chartPadding: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
          }
        }
      },
      attentionVariationChart: {
        data: {
          labels: [],
          series: [[]]
        },
        options: {
          lineSmooth: this.$chartist.Interpolation.cardinal({
            tension: 0
          }),
          low: 0,
          high: 50, // creative tim: we recommend you to set the high sa the biggest value + something for a better look
          chartPadding: {
            top: 0,
            right: 0,
            bottom: 0,
            left: 0
          }
        }
      },
      absentChart: {
        data: {
          labels: [],
          series: [[]]
        },
        options: {
          axisX: {
            showGrid: false
          },
          low: 0,
          high: 100,
          chartPadding: {
            top: 0,
            right: 5,
            bottom: 0,
            left: 0
          }
        },
        responsiveOptions: [
          [
            "screen and (max-width: 640px)",
            {
              seriesBarDistance: 5,
              axisX: {
                labelInterpolationFnc: function(value) {
                  return value[0];
                }
              }
            }
          ]
        ]
      },

      tabs: 0,
      list: {
        0: false,
        1: false,
        2: false
      }
    };
  },
  methods: {
    complete(index) {
      this.list[index] = !this.list[index];
    }
  },
  async created() {
    try {
      var response = await DashboardService.getDashboard();
      if (response.data) {
        this.responseData = response.data;
        this.attentionChart.data.series = [
          this.responseData.ATTENTION_AVG_PER_MONTH
        ];
        var labels = FormatterHelper.getMonthsLabelsFromMonthsString(
          this.responseData.MONTHS
        );
        this.attentionChart.data.labels = [...labels];
        this.absentChart.data.series = [this.responseData.ABSENT_AVG_PER_MONTH];
        this.absentChart.data.labels = [...labels];
        this.attentionVariationChart.data.series = [
          this.responseData.ATTENTION_DIFF_PER_MONTH
        ];
        this.attentionVariationChart.data.labels = [...labels];
        this.avgAttention = StatisticsHelper.roundStat(
          this.responseData.ATTENTION_AVG
        );
      } else {
        this.$router.push("/erreur");
      }
    } catch (error) {
      console.trace(error);
      this.$router.push("/erreur");
    }
  },
  computed: {
    analysisComputed() {
      var avg = this.responseData.ATTENTION_AVG;
      if (avg < 20) {
        return this.analysis.low;
      } else if (avg < 35) {
        return this.analysis.medium;
      } else {
        return this.analysis.high;
      }
    }
  }
};
</script>
