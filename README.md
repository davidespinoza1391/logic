# Información

El presente proyecto se lo realizó en Quarkus con java 17.


## Ejecutar la aplicación en modo de desarrollo

Puede ejecutar su aplicación en modo de desarrollo que permite la codificación en vivo usando:
```shell script
./mvnw compile quarkus:dev
```
Si el código anterior no funciona pruebe el siguiente:
```shell script
mvnw compile quarkus:dev
```

La aplicación se levantará en: http://localhost:9090

## Consumir desde postman 

Se puede consumir la aplicacion usando postman con una petición tipo POST: 

URL: http://localhost:9090/api/calcular

BODY: 
```
{
    "entradas": "AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7",
    "tests": [
        { "type": "distance", "route": "ABC" },
        { "type": "distance", "route": "AD" },
        { "type": "distance", "route": "ADC" },
        { "type": "distance", "route": "AEBCD" },
        { "type": "distance", "route": "AED" },
        { "type": "maxStops", "start": "C", "end": "C", "maxStops": 3 },
        { "type": "exactStops", "start": "A", "end": "C", "exactStops": 4 },
        { "type": "shortestRoute", "start": "A", "end": "C" },
        { "type": "shortestRoute", "start": "B", "end": "B" },
        { "type": "maxDistance", "start": "C", "end": "C", "maxDistance": 30 }
    ]
}
```

RESPUESTA: 
```
{
    "responses": [
        "Output for route ABC: 9",
        "Output for route AD: 5",
        "Output for route ADC: 13",
        "Output for route AEBCD: 22",
        "Output for route AED: NO SUCH ROUTE",
        "Output for max stops C to C with max 3 stops: 2",
        "Output for exact stops A to C with 4 stops: 3",
        "Output for shortest route A to C: 9",
        "Output for shortest route B to B: 0",
        "Output for routes from C to C with max distance 30: 7"
    ]
}
```
