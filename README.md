# Turf Booking Turf SAPI
Turf System API for the Turf Booking Application

## About the Integration
You can read about it [here](https://github.com/RatheeshRaghavendra/My-Projects/blob/main/Turf-Booking-Service.md)

## End Points

### GET /live

Health Endpoint to check if the App is running

### POST /turf

Creates a new turf in the Turf table

### GET /turf/{turfId}

Returns a Turf Object

### GET /turf/search-by

Returns a List of Turf filtered using the parameter (name, city, area, sports) and the value

### GET /turf/all

Returns a List of all the Turfs

### PATCH /turf/book/{turfId}

Books the given list of slot IDs (as query parameter) in the mentioned Turf

### DELETE /turf/cancel/{turfId}

Cancels the given list of slot IDs (as query parameter) in the mentioned Turf

## Object Structures

---

### ApiResponse

```json
{
    "statusCode": 200,
    "statusMessage": "OK",
    "payload": {
    
    },
    "apiError": null
}
```

### ApiError

```json
{
	"errorMessage": "No value present",
	"errorDescription": "java.util.NoSuchElementException: No value present",
	"customError": "No Turf with the Turf ID: 5, Present in the DB"
}
```

### Turf

```json
{
    "name": "Tiki",
    "city": "Chennai",
    "area": "Velachery",
    "address": "No 25, Velachery, Chennai, Tamil Nadu",
    "sports": "Football",
    "bookedSlotIds": [1,2,3,4,5,6],
    "pricePerHour": 2000
}
```
