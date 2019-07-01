<script>
import VueCharts from "vue-chartjs";

export default {
  extends: VueCharts.Pie,

  props: ["chartData", "options"],
  mounted() {
    this.renderChart(this.chartData, {
      responsive: true,
      maintainAspectRatio: false,
      title: {
        display: true,
        text: "Effectif"
      },
      legend: {
        position: "right",
        display: true
      }
    });
  }
};
</script>
