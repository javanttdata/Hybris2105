package de.hybris.training.core.hgbrasil.impl;

import de.hybris.platform.util.Config;
import de.hybris.training.core.hgbrasil.WeatherInfoService;
import de.hybris.training.core.hgbrasil.response.WeatherInfoResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class DefaultWeatherInfoService implements WeatherInfoService {

    private static final Logger LOG = LoggerFactory.getLogger(DefaultWeatherInfoService.class);

    private static final String API_URL = "api.hgbrasil.weather.url";

    private static final String API_KEY = "api.hgbrasil.key";

    @Override
    public WeatherInfoResponse getWeatherInfo() {
        final String apiUrl = Config.getString(API_URL, "Erro A");
        final String apiKey = Config.getString(API_KEY, "Erro B");
        LOG.info("Valores dos params: {}, {}", apiUrl, apiKey);

        final String url = apiUrl + "?key=" + apiKey + "&user_ip=remote";

       // https://api.hgbrasil.com/weather?key=f4a0892c&user_ip=remote

        LOG.info("Request: {}", url);

        final RestTemplate restTemplate = new RestTemplate();
        final ResponseEntity<WeatherInfoResponse> entity = restTemplate.getForEntity(url, WeatherInfoResponse.class);

        LOG.info("Response: {}", restTemplate.getForEntity(url, String.class).getBody());

        return entity.getBody();
    }
}
