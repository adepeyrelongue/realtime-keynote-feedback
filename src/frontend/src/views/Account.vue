<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md8 lg6>
        <material-card
          color="primary"
          title="Informations sur mon compte"
          text="Toutes les informations sur votre compte"
        >
          <v-form>
            <v-container py-0>
              <v-layout wrap>
                <v-flex xs12 md12>
                  <v-text-field label="Votre profession"/>
                </v-flex>
                <v-flex xs12 md6>
                  <v-text-field label="Votre prénom" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md6>
                  <v-text-field label="Votre nom" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md12>
                  <v-text-field label="Adresse mail" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md12>
                  <v-text-field label="Mot de passe" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field label="Pays" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field label="Ville" class="purple-input"/>
                </v-flex>
                <v-flex xs12 md4>
                  <v-text-field class="purple-input" label="Code postal" type="number"/>
                </v-flex>
                <v-flex xs12>
                  <v-textarea
                    class="purple-input"
                    label="A propos de moi"
                    value="Lorem ipsum dolor sit amet, consectetur adipiscing elit."
                  />
                </v-flex>
                <v-flex xs12 text-xs-right>
                  <v-btn class="mx-0 font-weight-light" color="primary">Mettre à jour le profil</v-btn>
                </v-flex>
              </v-layout>
            </v-container>
          </v-form>
        </material-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
export default {
  //
};
</script>
