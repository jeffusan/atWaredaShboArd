getWeather = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_temp").empty()
    $("#weather_temp").append $("<b>").text weather.temperature

getSunDays = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_sun").empty()
    $("#weather_sun").append $("<b>").text weather.humidity

getTreeDays = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_tree").empty()
    $("#weather_tree").append $("<b>").text weather.pressure

getMoonDays = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_moon").empty()
    $("#weather_moon").append $("<b>").text weather.cloudsPercent

$ ->
  getWeather()
  getSunDays()
  getTreeDays()
  getMoonDays()
