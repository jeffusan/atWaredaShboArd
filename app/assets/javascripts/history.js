$( document ).ready(function() {

  $.getJSON('/rest/v1/weather/history', function(data){

    var graph_data = {
      element: 'morris-area-chart',
      data: data,
      xkey: 'period',
      ykeys: ['pressure', 'temperature', 'humidity'],
      labels: ['Pressure', 'Temperature', 'Humidity'],
      pointSize: 2,
      hideOver: 'auto',
      resize: true
    };

    Morris.Area(graph_data);

  });
});
