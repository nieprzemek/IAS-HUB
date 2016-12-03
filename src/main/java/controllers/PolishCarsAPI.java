package controllers;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a private API.
 * http://niedroszlanski.pl
 */
public class PolishCarsAPI extends JsonReader {

    private List<String> carTypes;
    private List<String> carMakerList;
    private List<String> modelList;

    public PolishCarsAPI() {
        carTypes = new ArrayList<>();
        carMakerList = new ArrayList<>();
        modelList = new ArrayList<>();
    }

    public List<String> getCarTypes() throws IOException {
        JSONObject json = readJsonFromUrl("http://niedroszlanski.pl/car-api/typy-samochodow");
        JSONArray array = json.getJSONArray("Typy");
        for(int i = 0 ; i < array.length() ; i++){
            String model = array.getJSONObject(i).getString("typ").trim();
            carTypes.add(model);
        }
        return carTypes.stream().sorted(
                Comparator.comparing(String::toString)).distinct().collect(Collectors.toList());
    }

    public List<String> getMakerList(String type) throws IOException {
        JSONObject json = readJsonFromUrl("http://niedroszlanski.pl/car-api/marki");
        if(!json.isNull("Marki")) {
            JSONArray array = json.getJSONArray("Marki");
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).getString("typ").equals(type.toLowerCase())) {
                    String model = array.getJSONObject(i).getString("NazwaMarki").trim();
                    carMakerList.add(model);
                }
            }
        }
        return carMakerList.stream().sorted(
                Comparator.comparing(String::toString)).distinct().collect(Collectors.toList());
    }

    public List<String> getModelList(String marka) throws IOException {
        JSONObject json = readJsonFromUrl("http://niedroszlanski.pl/car-api/modele-wg-marek/" + marka.toLowerCase());
        if(!json.isNull("Modele")) {
            JSONArray array = json.getJSONArray("Modele");
            for (int i = 0; i < array.length(); i++) {
                String model = array.getJSONObject(i).getString("NazwaModelu").trim();
                modelList.add(model);
            }
        }
        return modelList.stream().sorted(Comparator.comparing(String::toString)).distinct().collect(Collectors.toList());
    }
}
