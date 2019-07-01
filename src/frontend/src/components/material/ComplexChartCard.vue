<template>
  <material-card v-bind="$attrs" class="v-card--material-chart" v-on="$listeners">
    <chart-line v-if="chartType === 'Line'" slot="header" :chart-data="data" :options="options"/>
    <chart-pie v-if="chartType === 'Pie'" slot="header" :chart-data="data" :options="options"/>
    <chart-polar v-if="chartType === 'Polar'" slot="header" :chart-data="data" :options="options"/>

    <slot/>
    <slot slot="actions" name="actions"/>
  </material-card>
</template>

<script>
import ChartLine from "../chart/Line";
import ChartPie from "../chart/Pie";
import ChartPolar from "../chart/Polar";
export default {
  inheritAttrs: false,
  components: {
    ChartLine,
    ChartPie,
    ChartPolar
  },
  props: {
    data: Object,
    options: Object,
    chartType: {
      type: String,
      required: true
    }
  }
};
</script>

<style lang="scss">
.v-card--material-chart {
  .v-card--material__header {
    .ct-label {
      color: inherit;
      opacity: 0.7;
      font-size: 0.975rem;
      font-weight: 100;
    }

    .ct-grid {
      stroke: rgba(255, 255, 255, 0.2);
    }
    .ct-series-a .ct-point,
    .ct-series-a .ct-line,
    .ct-series-a .ct-bar,
    .ct-series-a .ct-slice-donut {
      stroke: rgba(255, 255, 255, 0.8);
    }
    .ct-series-a .ct-slice-pie,
    .ct-series-a .ct-area {
      fill: rgba(255, 255, 255, 0.4);
    }
  }
}
</style>
