package de.hybris.training.core.hgbrasil.response;

public class WeatherInfoResponse {

    private String by;

    private Boolean valid_key;

    private WeatherInfoResultsResponse results;


    public String getBy() {
        return by;
    }

    public void setBy(String by) {
        this.by = by;
    }

    public Boolean getValid_key() {
        return valid_key;
    }

    public void setValid_key(Boolean valid_key) {
        this.valid_key = valid_key;
    }

    public WeatherInfoResultsResponse getResults() {
        return results;
    }

    public void setResults(WeatherInfoResultsResponse results) {
        this.results = results;
    }
}
