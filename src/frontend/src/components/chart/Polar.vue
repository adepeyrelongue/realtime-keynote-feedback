<script>
import VueCharts from "vue-chartjs";

export default {
  extends: VueCharts.PolarArea,
  props: ["chartData", "options"],
  mounted() {
    this.renderChart(this.chartData, {
      responsive: true,
      legend: {
        position: "right"
      },
      title: {
        display: true,
        text: "Stimulus"
      },
      scale: {
        lineArc: true,
        ticks: {
          beginAtZero: true
        },
        reverse: false
      },
      animation: {
        animateRotate: false,
        animateScale: true
      }
    });
  }
};
</script>
