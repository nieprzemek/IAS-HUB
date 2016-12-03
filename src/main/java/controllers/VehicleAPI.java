package controllers;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Documentation of this API can be find on
 * https://vpic.nhtsa.dot.gov/api/
 */
public class VehicleAPI extends JsonReader{

    private List<String> carTypes;
    private List<String> carMakerList;
    private List<String> modelList;

    public VehicleAPI() {
        carTypes = new ArrayList<>();
        carMakerList = new ArrayList<>();
        modelList = new ArrayList<>();
    }

    public List<String> getListOfCarTypes() throws IOException {
        JSONObject json = readJsonFromUrl("https://vpic.nhtsa.dot.gov/api/vehicles/GetVehicleTypesForMake/ford?format=json");
        JSONArray array = json.getJSONArray("Results");
        for(int i = 0 ; i < array.length() ; i++){
            String type = array.getJSONObject(i).getString("VehicleTypeName").trim();
            carTypes.add(type);
        }
        return carTypes.stream().sorted(
                Comparator.comparing(String::toString)).distinct().collect(Collectors.toList());
    }

    public List<String> getCarMakesListForType(String type) throws IOException {
        JSONObject json = readJsonFromUrl("https://vpic.nhtsa.dot.gov/api/vehicles/GetMakesForVehicleType/"+ type +"?format=json");
        JSONArray array = json.getJSONArray("Results");
        for(int i = 0 ; i < array.length() ; i++){
            String maker = array.getJSONObject(i).getString("MakeName").trim();
            carMakerList.add(maker);
        }
        return carMakerList.stream().sorted(
                    Comparator.comparing(String::toString)).distinct().collect(Collectors.toList());
    }

    public List<String> getModelsForMake(String make) throws IOException {
        JSONObject json = readJsonFromUrl("https://vpic.nhtsa.dot.gov/api/vehicles/getmodelsformake/" + make + "?format=json");
        JSONArray array = json.getJSONArray("Results");
        for(int i = 0 ; i < array.length() ; i++){
            String model = array.getJSONObject(i).getString("Model_Name").trim();
            modelList.add(model);
        }
        return modelList.stream().sorted(
                Comparator.comparing(String::toString)).distinct().collect(Collectors.toList());
    }
}
