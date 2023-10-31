# Weather Forecast API Integration

This project demonstrates the integration of a weather forecast API from RapidAPI using Spring Boot. The application provides RESTful endpoints to retrieve weather forecast summaries and hourly details for a given location.

## Prerequisites

1. **RapidAPI Key:**
    - Register for an account at [RapidAPI](https://rapidapi.com/wettercom-wettercom-default/api/forecast9/).
    - Subscribe to the API to obtain your personal `X-RapidAPI-Key`.
    - Place the obtained key in the `application.yml` file under `rapidapi.rapid-api-key`.

## Getting Started

1. **Run the Application:**
    - Build and run the Spring Boot application.

2. **RESTful Endpoints:**
    - The application exposes the following RESTful endpoints:

        - `/weather/summary`: Get the weather forecast summary for a specific city.
          ```bash
          curl -X GET "http://localhost:8080/weather/summary?locationName=CityName" -H "X-Client-Id: YourClientId" -H "X-Client-Secret: YourClientSecret"
          ```

        - `/weather/hourly`: Get hourly weather forecast details for a specific city.
          ```bash
          curl -X GET "http://localhost:8080/weather/hourly?locationName=CityName" -H "X-Client-Id: YourClientId" -H "X-Client-Secret: YourClientSecret"
          ```

        - `/admin/register-client/{client-id}`: Register a new client with a unique ID.
          ```bash
          curl -X GET "http://localhost:8080/admin/register-client/YourClientId"
          ```

3. **Client Registration:**
    - Register your client ID using the `/admin/register-client/{client-id}` API. It will generate the client secret for you. Use the following curl command for registration:
      ```bash
      curl --location 'http://localhost:8080/admin/register-client/{client-id}' \
      --header 'X-Client-Id: {client-id}' \
      --header 'X-Client-Secret: {client-secret}'
      ```

4. **Authentication for Weather Forecast API:**
    - After successful registration, use the client ID as the value for X-Client-Id and the generated client secret as the value for X-Client-Secret in the following curl command to access the weather forecast API:
      ```bash
      curl --location 'http://localhost:8080/weather/hourly?locationName=delhi' \
      --header 'X-Client-Id: {client-id}' \
      --header 'X-Client-Secret: {client-secret}'
      ```

## Authentication

- Header-based authentication is required for accessing the weather forecast endpoints.
- Include the following headers in your request:
    - `X-Client-Id`: Your Client ID
    - `X-Client-Secret`: Your Client Secret

## Service Class

The `WeatherService` class interacts with the RapidAPI to fetch weather forecast data. The `WeatherController` class provides the RESTful endpoints and handles client authentication.

For administrative purposes, a client can be registered using the `/admin/register-client/{client-id}` endpoint.

## Logging

Developers can utilize the SLF4J logging framework to gather information by leveraging the logger. Logs are accessible for tracking authentication status and client registration.