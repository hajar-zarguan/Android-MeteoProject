# METEO

### Create OpenWeatherMap 
![image](https://user-images.githubusercontent.com/82539023/169137214-7281c35f-b156-4362-82c4-6ccc7db6364e.png)


## Travail demandé
#### 1. Créer un événement au clic sur une carte Google Maps pour récupérer la latitude et longitude du point sur lequel vous avez cliqué.
#### 2. Interroger l’API Current Weather Data en utilisant la requête suivante :
![image](https://user-images.githubusercontent.com/82539023/172385238-0e9b9a19-a839-415c-abe6-e0af45bee736.png)

Il faut afficher les données météorologiques du point sur lequel vous avez cliqué sur la map. Les
données à afficher sont :
a. La date et l’heure à partir du champ "dt": 1654343407,
b. Les champs "weather"/ "main": et "weather"/ "icon"
c. Les champs : "main": {
 "temp": x,
 "feels_like":x,
 "temp_min":x,
 "temp_max":x,
 "pressure":x,
 "humidity":x,
 "sea_level":x,
 },
d. wind": { "speed": x }

#### 3. Interroger l’API Call 5 day / 3 hour forecast data pour afficher les données météorologiques du même point sur 5 jours en utilisant la requête suivante :
api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={appid={API key}

### Screenshots

https://user-images.githubusercontent.com/82539023/172383913-957f01d6-495d-4137-8ffd-09034d1cc211.mp4

