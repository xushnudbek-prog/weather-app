package kube.weatherapp.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class WeatherApiClient {
    @Value("${openweathermap.api-key}")
    private String apiKey;
    WebClient webClient = WebClient.builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/weather")
            .build();

    @SuppressWarnings("unchecked")
    public Map<String, Object> getWeather(String latitude, String longitude) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("lat", latitude)
                        .queryParam("lon", longitude)
                        .queryParam("appid", apiKey)
                        .build()
                )
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> {
                    return clientResponse.bodyToMono(String.class)
                            .doOnNext(System.out::println)
                            .flatMap(body -> Mono.error(new RuntimeException("Client error: " + body)));
                })
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse ->
                        clientResponse.bodyToMono(String.class)
                                .flatMap(body -> Mono.error(new RuntimeException("Server error: " + body)))
                )
                .bodyToMono(Map.class)
                .block();
    }
}
