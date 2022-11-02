package de.hybris.training.core.hgbrasil.response;

public class WeatherInfoResultsResponse {

    private String city_name;
    private Integer temp;
    private String description;

    public String getCity_name() {return city_name;
    }

    public void setCity_name(String city_name) {  this.city_name = city_name;
    }

    public Integer getTemp() {
        return temp;
    }

    public void setTemp(Integer temp) {
        this.temp = temp;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
