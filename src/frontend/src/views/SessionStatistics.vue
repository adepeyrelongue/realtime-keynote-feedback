<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout wrap>
      <v-flex md4>
        <material-card color="primary" title="Conférence Pierre et Marie Currie" text>
          <v-data-table :headers="headers" :items="itemsComputed" hide-actions>
            <template slot="headerCell" slot-scope="{ header }">
              <span class="subheading font-weight-light text--darken-3" v-text="header.text"/>
            </template>
            <template slot="items" slot-scope="{ item }">
              <th>{{ item.label }}</th>
              <td>{{ item.value }}</td>
            </template>
          </v-data-table>
        </material-card>
        <material-stats-card
          color="primary"
          icon="mdi-account-group"
          title="Attention moyenne"
          :value="attentionAverage+'/50'"
          sub-icon="mdi-information-outline"
          sub-text="sur les 2 dernieres heures"
        />
        <material-stats-card
          color="blue"
          icon="mdi-arrow-up"
          title="Attention maximum"
          :value="attentionMax+'/50'"
          sub-icon="mdi-information-outline"
          sub-text="sur les 2 dernieres heures"
        />
        <material-stats-card
          color="red"
          icon="mdi-arrow-down"
          title="Attention minimum"
          :value="attentionMin+'/50'"
          sub-icon="mdi-information-outline"
          sub-text="sur les 2 dernieres heures"
        />
      </v-flex>
      <v-flex md8>
        <material-complex-chart-card
          v-if="attentionChart"
          color="white"
          chart-type="Line"
          :data="attentionChart.data"
          :options="attentionChart.options"
        >
          <h4 class="title font-weight-light">Évolution de l'attention au fil du temps</h4>
          <p
            class="category d-inline-flex font-weight-light"
          >Analysé depuis l'attention individuelle de chaque étudiant.</p>
        </material-complex-chart-card>

        <v-flex sm6 xs12 md12 lg12 v-if="responseData">
          <v-alert
            :value="true"
            type="info"
            :color="analysisComputed.color"
            style="margin-top:25px;"
          >{{analysisComputed.message}}</v-alert>
        </v-flex>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import SessionStatisticsService from "../services/SessionStatistics";
import config from "../config";
import FormatterHelper from "../helpers/FormatterHelper";
import StatisticsHelper from "../helpers/StatisticsHelper";

var chartColor = "#2196f3";
chartColor = "#fff";
export default {
  data() {
    return {
      responseData: null,
      subject: "",
      items: [],
      attentionAverage: 0,
      attentionMax: 0,
      attentionMin: 0,
      attentionChart: null,
      analysis: {
        low: {
          color: "red",
          message: `Votre niveau d'attention globale pour cette séance est dans la moyenne basse (inférieur à 20).
         Vous devez améliorer l'attention de votre auditoire en suivant nos conseils personalisés.`
        },
        medium: {
          color: "orange",
          message: `Votre niveau d'attention globale est dans la moyenne (entre 20 et 35).
          Ce niveau correspond à une attention satisfaisante mais perfectible.
          Si vous souhaitez en savoir plus, consultez nos conseils pour accroitre l'attention de votre public.`
        },
        high: {
          color: "green",
          message: `Votre niveau d'attention globale est dans la moyenne haute (entre 35 et 50).
          Si vous souhaitez en savoir plus, consultez nos conseils pour améliorer l'attention de votre public.`
        }
      }
    };
  },
  async mounted() {
    if (config.apiCallEnabled) {
      try {
        var response = await SessionStatisticsService.getSeanceStatistics(
          this.$route.params.id
        );
        if (response.data) {
          this.responseData = response.data;
          this.attentionAverage = StatisticsHelper.roundStat(
            this.responseData.ATTENTION_AVG
          );
          this.attentionMax = StatisticsHelper.roundStat(
            this.responseData.ATTENTION_MAX
          );
          this.attentionMin = StatisticsHelper.roundStat(
            this.responseData.ATTENTION_MIN
          );
          this.attentionChart = {
            data: {
              labels: response.data.SESSION_ANALYTICS_DATA.map(item => {
                return FormatterHelper.getTimeFromDateTime(item.LABEL);
              }),
              datasets: [
                {
                  label: "Attention lors de la séance",
                  backgroundColor: "#b8efe2",
                  data: response.data.SESSION_ANALYTICS_DATA.map(item => {
                    return StatisticsHelper.roundStat(item.DATA);
                  })
                }
              ]
            },
            options: {
              legend: {
                labels: {
                  fontColor: "white"
                }
              },
              scales: {
                yAxes: [
                  {
                    ticks: {
                      suggestedMin: 50,
                      suggestedMax: 100
                    }
                  }
                ]
              }
            }
          };
        }
      } catch (error) {
        console.trace(error);
        this.$router.push("/erreur");
      }
    }
  },
  computed: {
    analysisComputed() {
      if (this.responseData) {
        var avg = this.responseData.ATTENTION_AVG;
        if (avg < 20) {
          return this.analysis.low;
        } else if (avg < 35) {
          return this.analysis.medium;
        } else {
          return this.analysis.high;
        }
      }
    },
    itemsComputed() {
      if (this.responseData) {
        const session = this.responseData.SESSION;
        return [
          {
            label: "Public",
            value: session.PUBLIC
          },
          {
            label: "Salle",
            value: session.ROOM
          },
          {
            label: "Date",
            value: FormatterHelper.getDateFromDateTime(session.DATE)
          },
          {
            label: "Durée",
            value: FormatterHelper.getDurationFromString(session.DURATION)
          },
          {
            label: "Effectif attendu",
            value: session.PARTICIPANTS
          },
          {
            label: "Effectif observé",
            value: session.OBSERVED_PARTICIPANTS
          },
          {
            label: "Absents",
            value: session.ABSENT_PARTICIPANTS
          },
          {
            label: "Début",
            value: FormatterHelper.getTimeFromDateTime(session.BEGINNING_TIME)
          }
        ];
      } else {
        return [
          {
            label: "Public",
            value: ""
          },
          {
            label: "Salle",
            value: ""
          },
          {
            label: "Date",
            value: ""
          },
          {
            label: "Durée",
            value: ""
          },
          {
            label: "Effectif",
            value: ""
          },
          {
            label: "Début",
            value: ""
          },
          {
            label: "Fin",
            value: ""
          }
        ];
      }
    }
  }
};
</script>
<style>
</style>
