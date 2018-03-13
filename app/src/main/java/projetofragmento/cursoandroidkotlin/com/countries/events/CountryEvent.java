package projetofragmento.cursoandroidkotlin.com.countries.events;

import java.util.List;

import projetofragmento.cursoandroidkotlin.com.countries.models.Country;


public class CountryEvent {

    private final Country shortName;

    public CountryEvent(Country shortName) {

        this.shortName = shortName;

    }

    public Country getShortName() {
        return shortName;
    }
}
