getWeather = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_temp").empty()
    $("#weather_temp").append $("<b>").text weather.temperature
    $("#weather_sun").empty()
    $("#weather_sun").append $("<b>").text weather.humidity
    $("#weather_tree").empty()
    $("#weather_tree").append $("<b>").text weather.pressure
    $("#weather_moon").empty()
    $("#weather_moon").append $("<b>").text weather.clouds

$ ->

  getWeather()
