<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md8 lg4 v-if="!isParsing && store.sessionsInProcess.length >= 3">
        <material-card
          color="primary"
          title="Vous avez atteint votre limite d'upload parallèles"
          text="Vous êtes limités à 3 uploads en parallèle."
        >
          <v-container py-0 px-0>
            <v-layout wrap>
              <v-flex xs12 md12>
                <p>
                  Nous analysons 3 vidéos en arrière-plan pour vous,
                  attendez encore un peu et vous pourrez analyser une nouvelle vidéo.
                </p>
              </v-flex>
            </v-layout>
          </v-container>
        </material-card>
      </v-flex>
      <v-flex xs12 md8 lg4 v-if="!isParsing && store.sessionsInProcess.length < 3">
        <material-card
          color="primary"
          title="Uploadez un fichier vidéo"
          text="Cette vidéo sera analysée."
        >
          <v-container py-0 px-0>
            <v-layout wrap>
              <v-flex xs12 md12>
                <form-file-upload v-on:uploaded="uploadFinished"></form-file-upload>
              </v-flex>
            </v-layout>
          </v-container>
        </material-card>
      </v-flex>

      <v-flex xs12 md8 lg6 v-if="!isParsing && store.sessionsInProcess.length < 3">
        <material-card
          color="primary"
          title="Informations sur la séance"
          text="Avant de commencer une séance, complétez ce formulaire."
        >
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12 md12>
                  <v-text-field
                    :error-messages="subjectError.messages"
                    :rules="[subjectRules.required, subjectRules.min]"
                    :hint="'Ajoutez le sujet de cette séance'"
                    v-model="subject"
                    class="purple-input"
                    label="Sujet"
                  />
                </v-flex>
                <v-flex xs12 md6>
                  <v-text-field
                    :error="roomError.state"
                    :error-messages="roomError.messages"
                    :rules="[roomRules.required, roomRules.min]"
                    v-model="room"
                    label="Salle"
                    :hint="'Ajoutez le nom de salle de cette séance.'"
                    class="purple-input"
                  />
                </v-flex>
                <v-flex xs12 md6>
                  <v-text-field
                    :error-messages="publicError.messages"
                    :rules="[publicRules.required, publicRules.min]"
                    v-model="publiq"
                    label="Public"
                    :hint="'Ajoutez le nom du public de cette séance.'"
                    class="purple-input"
                  />
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field
                    :error-messages="participantsError.messages"
                    :rules="[participantsRules.required, participantsRules.min]"
                    v-model.number="participants"
                    label="Nombre de participants"
                    type="number"
                    :hint="'Ajoutez le nombre de participants attendus pour cette séance.'"
                    class="purple-input"
                  />
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field
                    :error-messages="dateError.messages"
                    :rules="[dateRules.required]"
                    v-model="date"
                    label="Date"
                    type="date"
                    class="purple-input"
                  />
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field
                    :error-messages="beginningTimeError.messages"
                    v-model="beginningTime"
                    :rules="[beginningTimeRules.required]"
                    label="Heure de début"
                    :hint="'Ajoutez l\'heure de début pour cette séance.'"
                    type="time"
                    class="purple-input"
                  />
                </v-flex>
                <v-flex xs12>
                  <v-textarea
                    class="purple-input"
                    v-model="description"
                    label="Descriptif"
                    value="Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                  />
                </v-flex>
                <v-flex xs12 text-xs-right>
                  <v-btn color="danger" @click="cancel">Annuler</v-btn>
                  <v-btn
                    class="mx-0 font-weight-light"
                    color="primary"
                    @click.prevent="createSession"
                    :disabled="!uploadStatusFinished"
                    :title="uploadStatusFinished ? 'Transmettez les données pour l\'analyse' :'Attendez la fin du téléchargement de la vidéo pour valider.' "
                  >Valider</v-btn>
                </v-flex>
              </v-layout>
            </v-container>
          </v-form>
        </material-card>
      </v-flex>
      <v-flex xs12 md8 lg8 v-if="isParsing">
        <material-card
          color="primary"
          title="Upload réussi !"
          text="Veuillez attendre la fin de l'analyse de votre séance."
        >
          <v-container py-0 px-0>
            <v-layout wrap justify-center>
              <v-flex xs6 md6>
                <h4 style="text-align:center;">
                  <br>Nous traitons votre vidéo en arrière plan.
                </h4>
                <p style="text-align:center;">
                  Pour voir l'avancement de votre vidéo cliquez dans la barre de navigation
                  sur
                  <b>"EN TRAITEMENT"</b>.
                </p>
              </v-flex>
            </v-layout>
          </v-container>
        </material-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
import SessionCreationService from "../services/SessionCreation";
import store from "../store.js";

import config from "../config";
export default {
  data: () => ({
    uploadStatusFinished: false,
    subject: "",
    room: "",
    publiq: "",
    date: new Date().toISOString().slice(0, 10),
    beginningTime: "12:30",
    description: "",
    participants: 1,
    file: "",
    isParsing: false,
    store,
    subjectRules: {
      required: value => !!value || "Champs requis.",
      min: v =>
        v.length >= 8 ||
        "Entrez au moins 8 charactères pour le sujet de la séance.",
      emailMatch: () => "The email and password you entered don't match"
    },
    roomRules: {
      required: value => !!value || "Champs requis.",
      min: v =>
        v.length >= 3 ||
        "Entrez au moins 3 charactères pour le nom de la salle.",
      emailMatch: () => "The email and password you entered don't match"
    },
    publicRules: {
      required: value => !!value || "Champs requis.",
      min: v =>
        v.length >= 4 ||
        "Entrez au moins 4 charactères pour le public de la séance.",
      emailMatch: () => "The email and password you entered don't match"
    },
    participantsRules: {
      required: value => !!value || value === 0 || "Champs requis.",
      min: v =>
        v > 0 || "Le nombre de participants doit être strictement positif.",
      emailMatch: () => "The email and password you entered don't match"
    },
    dateRules: {
      required: value => !!value || "Champs requis."
    },
    beginningTimeRules: {
      required: value => !!value || "Champs requis."
    },
    subjectError: {
      messages: []
    },
    roomError: {
      messages: []
    },
    publicError: {
      messages: []
    },
    participantsError: {
      messages: []
    },
    dateError: {
      messages: []
    },
    beginningTimeError: {
      messages: []
    }
  }),
  methods: {
    clearErrorMessages(error) {
      error.messages = [];
    },
    checkFormValidity() {
      if (!this.subject) {
        this.subjectError.messages.push("Le sujet de la salle est requis.");
      } else if (this.subject.length < 8) {
        this.subjectError.messages.push(
          "Entrez au moins 8 charactères pour le sujet de la séance."
        );
      }

      if (!this.room) {
        this.roomError.messages.push("Le nom de salle est requis.");
      } else if (this.room.length < 3) {
        this.roomError.messages.push(
          "Entrez au moins 3 charactères pour le sujet de la séance."
        );
      }

      if (!this.publiq) {
        this.publicError.messages.push("Le nom du public est requis.");
      } else if (this.publiq.length < 4) {
        this.roomError.messages.push(
          "Entrez au moins 4 charactères pour le sujet de la séance."
        );
      }

      if (!this.participants && this.participants !== 0) {
        console.log("1");
        this.participantsError.messages.push(
          "Le nombre de participants est requis sous forme de nombre."
        );
      } else if (this.participants <= 0) {
        console.log("2");
        this.participantsError.messages.push(
          "Le nombre de participants doit être strictement positif."
        );
      }

      if (!this.date) {
        this.dateError.messages.push("La date est requise.");
      }

      if (!this.beginningTime) {
        this.beginningTimeError.messages.push("Le temps de début est requis.");
      }

      return (
        this.subjectError.messages.length == 0 &&
        this.roomError.messages.length == 0 &&
        this.publicError.messages.length == 0 &&
        this.participantsError.messages.length == 0 &&
        this.dateError.messages.length == 0 &&
        this.beginningTimeError.messages.length == 0
      );
    },
    async createSession() {
      if (config.apiCallEnabled && this.checkFormValidity()) {
        const sessionData = {
          subject: this.subject,
          room: this.room,
          publiq: this.publiq,
          date: this.date,
          beginningTime: this.date + "T" + this.beginningTime,
          description: this.description,
          participants: this.participants,
          file: this.file
        };
        try {
          this.isParsing = true;
          var response = await SessionCreationService.createSession(
            sessionData
          );
          this.store.sessionsInProcess.push({
            SUBJECT: response.data.SUBJECT,
            ID: response.data.ID
          });
        } catch (error) {
          console.trace(error);
          this.$router.push("/erreur");
        }
        setTimeout(() => {
          this.$router.push("/mes-seances");
        }, 3000);
      }
    },
    async uploadFinished(status, file) {
      this.uploadStatusFinished = status;
      this.file = file;
    },
    cancel() {
      this.$router.push("/");
    }
  },
  watch: {
    subject() {
      if (this.subjectError.messages.length)
        this.clearErrorMessages(this.subjectError);
    },
    room() {
      if (this.roomError.messages.length)
        this.clearErrorMessages(this.roomError);
    },
    participants() {
      if (this.participantsError.messages.length)
        this.clearErrorMessages(this.participantsError);
    },
    publiq() {
      if (this.publicError.messages.length)
        this.clearErrorMessages(this.publicError);
    },
    date() {
      if (this.dateError.messages.length)
        this.clearErrorMessages(this.dateError);
    },
    beginningTime() {
      if (this.beginningTimeError.messages.length)
        this.clearErrorMessages(this.beginningTimeError);
    },
    subject() {
      if (this.subjectError.messages.length)
        this.clearErrorMessages(this.subjectError);
    }
  }
};
</script>

<style lang="css">
h3 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 14px;
  color: #555;
}
</style>
