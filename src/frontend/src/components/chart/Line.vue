<script>
import VueCharts from "vue-chartjs";

export default {
  extends: VueCharts.Line,

  props: ["chartData", "options"],
  mounted() {
    this.renderChart(this.chartData, {
      responsive: true,
      maintainAspectRatio: false,
      title: {
        display: true,
        text: "Indice d'attention"
      },
      legend: {
        display: false,
        labels: {
          //fontColor: "white",
          fontSize: 18
        }
      },
      scales: {
        yAxes: [
          {
            gridLines: {
              display: true,
              color: "#eee"
            },

            ticks: {
              suggestedMin: 0,
              suggestedMax: 50
            }
          }
        ],
        xAxes: [
          {
            gridLines: {
              display: true,
              color: "#eee"
            }
            /* ticks: {
              fontColor: "white"
            }*/
          }
        ]
      }
    });
  }
};
</script>
