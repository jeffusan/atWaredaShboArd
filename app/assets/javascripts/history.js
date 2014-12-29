$( document ).ready(function() {

  $.getJSON('/rest/v1/weather/history', function(data){

    var graph_data = {
      element: 'morris-area-chart',
      data: data,
      xkey: 'period',
      ykeys: ['temperature', 'humidity'],
      labels: ['Temperature', 'Humidity'],
      pointSize: 2,
      hideOver: 'auto',
      resize: true
    };

    Morris.Line(graph_data);

  });
});
