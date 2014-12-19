getWeather = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_temp").empty()
    $("#weather_temp").append $("<b>").text weather.temp

$ ->
  getWeather()
