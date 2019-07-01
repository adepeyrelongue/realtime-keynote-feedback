export default {
  getTimeFromDateTime (date) {
    var timeParts = date
      .split(':')
    return `${timeParts[0]}h ${timeParts[1]}`
  },
  getDateFromDateTime (date) {
    var timeParts = date.split('-')
    return `${timeParts[2]}/${timeParts[1]}/${timeParts[0]}`
  },
  getDurationFromString (str) {
    var parts = str.split(':')
    return `${parts[0]}h ${parts[1]} min`
  },
  getMonthsLabelsFromMonthsString (months) {
    var labels = [
      'Jan',
      'Fev',
      'Mar',
      'Avr',
      'Mai',
      'Juin',
      'Jui',
      'Aou',
      'Sep',
      'Nov',
      'Dec'
    ]
    return months.map(month => {
      return labels[parseInt(month.split('-')[1]) - 1]
    })
  }
}
