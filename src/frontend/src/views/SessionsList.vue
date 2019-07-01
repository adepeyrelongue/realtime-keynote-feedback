<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap v-if="responseData.length">
      <v-flex md10>
        <material-card
          color="primary"
          title="Liste de vos séances"
          text="Ci-dessous la liste de vos séances. cliquez sur une ligne pour voir plus de détails."
        >
          <v-data-table :headers="headers" :items="responseDataComputed" hide-actions>
            <template slot="headerCell" slot-scope="{ header }">
              <span class="subheading font-weight-light text--darken-3" v-text="header.text"/>
            </template>
            <template slot="items" slot-scope="{ item }">
              <td :title="title">{{ item.subject}}</td>
              <td :title="title">{{ item.public }}</td>
              <td :title="title">{{ item.room }}</td>
              <td :title="title">{{ item.date }}</td>
              <td :title="title">{{ item.beginningTime }}</td>
              <td :title="title">{{ item.duration }}</td>
              <td class="text-xs-right">{{ item.attention }}/50</td>
              <td :title="title" class="text-xs-right">
                <v-btn
                  color="primary"
                  :small="true"
                  :to="`/statistiques-seance/${item.sessionId}` "
                >
                  <v-icon>mdi-chart-bar</v-icon>&nbsp;Détails
                </v-btn>
              </td>
            </template>
          </v-data-table>
        </material-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import SessionsListService from "../services/SessionsList";
import config from "../config";
import StatisticsHelper from "../helpers/StatisticsHelper";
import FormatterHelper from "../helpers/FormatterHelper";
import store from "../store.js";
export default {
  data: () => ({
    title: "Cliquez pour accéder aux statistiques de cette séance",
    headers: [
      {
        sortable: false,
        text: "Titre"
      },
      {
        sortable: false,
        text: "Public"
      },
      {
        sortable: false,
        text: "Salle"
      },
      {
        sortable: false,
        text: "Date"
      },
      {
        sortable: false,
        text: "Heure de début"
      },
      {
        sortable: false,
        text: "Durée"
      },
      {
        sortable: false,
        text: "Attention moyenne",
        align: "right"
      }
    ],
    responseData: [],
    store
  }),
  async mounted() {
    this.fetch();
  },
  methods: {
    async fetch() {
      if (config.apiCallEnabled) {
        try {
          var response = await SessionsListService.getSessionsList();
          if (response.data) this.responseData = response.data;
        } catch (error) {
          console.trace(error);
          this.$router.push("/erreur");
        }
      }
    }
  },
  computed: {
    responseDataComputed() {
      if (this.responseData && this.responseData.length) {
        var computed = [...this.responseData];
        return computed.map(data => {
          return {
            subject: data.SUBJECT,
            public: data.PUBLIC,
            room: data.ROOM,
            date: FormatterHelper.getDateFromDateTime(data.DATE),
            beginningTime: FormatterHelper.getTimeFromDateTime(
              data.BEGINNING_TIME
            ),
            duration: FormatterHelper.getDurationFromString(data.DURATION),
            attention: StatisticsHelper.roundStat(data.ATTENTION_AVG),
            sessionId: data.ID
          };
        });
      } else return [];
    }
  },
  watch: {
    store: {
      handler: function(val, oldVal) {
        this.fetch();
      },
      deep: true
    }
  }
};
</script>
<style lang="scss">
tr:hover {
  cursor: pointer;
}
</style>