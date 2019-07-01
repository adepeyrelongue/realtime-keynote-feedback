<template>
  <div class="file-upload">
    <v-layout wrap v-if="!terminated">
      <v-flex v-if="!uploading" xs12 class="text-xs-center">
        <input
          type="file"
          id="file"
          accept=".mp4, .mov, .avi"
          ref="file"
          style="display:none;"
          v-on:change="startUpload()"
        >
        <v-btn align-center color="primary" v-on:click="handleFileUpload()">
          Choisir un fichier&nbsp;
          <v-icon>mdi-arrow-collapse-down</v-icon>
        </v-btn>
      </v-flex>
      <transition name="fade">
        <v-flex xs12 v-if="!uploading && uploadPercentage == 0">
          <p class="text-xs-center">Uploadez un fichier vidéo au format (mp4, avi, mov)</p>
        </v-flex>
        <v-flex xs12 v-if="uploading && uploadPercentage != 100">
          <p>{{fileName}}</p>
          <v-progress-linear :value="uploadPercentage"></v-progress-linear>

          <p>
            Patientez pendant le transfert...
            <b class="body-2">{{uploadPercentage}} %</b>
          </p>
        </v-flex>
        <v-flex v-else-if="uploadPercentage == 100" class="text-xs-center">
          <v-progress-circular :size="50" color="primary" indeterminate></v-progress-circular>
          <p>
            <br>Votre fichier est bientôt prêt pour l'analyse
          </p>
        </v-flex>
      </transition>
    </v-layout>
    <transition name="fade">
      <v-layout v-if="terminated">
        <v-flex xs12>
          <p>
            Transfert de :
            <br>
            <b class="body-2">{{this.file.name}}</b>
            <br>exécuté avec succès.
            <br>
            <br>Pour que l'analyse puisse démarrer finalisez le formulaire de séance.
          </p>
        </v-flex>
      </v-layout>
    </transition>
  </div>
</template>

<script>
import UploadVideoService from "../../services/UploadVideo";
export default {
  inheritAttrs: false,
  name: "file-upload",
  components: {},

  props: {},

  data() {
    return {
      file: null,
      uploading: false,
      terminated: false,
      uploadPercentage: 0
    };
  },
  methods: {
    handleFileUpload() {
      this.$refs.file.click();
    },
    async startUpload() {
      this.file = this.$refs.file.files[0];
      this.uploading = true;
      let formData = new FormData();
      formData.append("file", this.file);
      try {
        var response = await UploadVideoService.uploadVideo(formData, this);
        if (response.data) {
          let state = response.data.success;
          this.terminated = state;
          this.$emit("uploaded", true, this.file.name);
        } else {
          this.$emit("uploaded", false);
        }
      } catch (err) {
        this.$router.push("/erreur");
        console.trace(err);
      }
    }
  },
  computed: {
    fileName() {
      return this.file ? this.file.name : "";
    }
  }
};
</script>

<style lang="scss">
.file-upload {
  margin-top: 20px;
}
.fade-enter-active,
.fade-leave-active {
  transition: opacity 1s;
}
.fade-enter, .fade-leave-to /* .fade-leave-active below version 2.1.8 */ {
  opacity: 0;
}
</style>
