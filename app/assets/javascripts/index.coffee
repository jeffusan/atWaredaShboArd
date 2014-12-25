getWeather = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_temp").empty()
    $("#weather_temp").append $("<b>").text (weather.main.temp - 273).toFixed(2)
    $("#weather_sun").empty()
    $("#weather_sun").append $("<b>").text weather.main.humidity
    $("#weather_tree").empty()
    $("#weather_tree").append $("<b>").text weather.main.pressure
    $("#weather_moon").empty()
    $("#weather_moon").append $("<b>").text weather.clouds.all

$ ->

  getWeather()
