<template>
  <v-container fill-height fluid grid-list-xl>
    <v-layout justify-center wrap>
      <v-flex xs12 md8 lg8>
        <material-card
          color="primary"
          title="Oups une erreur s'est produite"
          text="Erreur de connexion avec le serveur"
        >
          <v-container py-0 px-0>
            <v-layout wrap justify-center>
              <v-flex xs6 md6>
                <h4 style="text-align:center;">
                  <br>Il semble qu'une erreur se soit produite :/
                </h4>
                <p style="text-align:center;">
                  Nous retentons de vous connecter avec le serveur.
                  <br>En attendant vous pouvez vérifier votre connexion internet.
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
</script>

<style lang="css">
h3 {
  text-align: center;
  margin-bottom: 30px;
  font-size: 14px;
  color: #555;
}
</style>
