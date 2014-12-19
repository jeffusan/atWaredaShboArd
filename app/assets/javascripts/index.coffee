getWeather = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_temp").empty()
    $("#weather_temp").append $("<b>").text weather.temp

getSunDays = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_sun").empty()
    $("#weather_sun").append $("<b>").text weather.temp

getTreeDays = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_tree").empty()
    $("#weather_tree").append $("<b>").text weather.temp

getMoonDays = () ->
  $.get "/rest/v1/weather/today", (weather) ->
    $("#weather_moon").empty()
    $("#weather_moon").append $("<b>").text weather.temp

$ ->
  getWeather()
  getSunDays()
  getTreeDays()
  getMoonDays()
