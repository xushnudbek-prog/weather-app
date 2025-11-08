package kube.weatherapp.controller;

import kube.weatherapp.client.WeatherApiClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    private final WeatherApiClient client;

    public WeatherController(WeatherApiClient client) {
        this.client = client;
    }

    @GetMapping("/tashkent")
    public Map<String, Object> getWeather() {
        return client.getWeather("41.2995", "69.2401");
    }

}
