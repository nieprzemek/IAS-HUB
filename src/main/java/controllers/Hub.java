package controllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Projekt zaliczeniowy z przedmiotu IAS - laboratoria
 *
 * Sekcja 1
 * Kamil Krzykawski
 * Przemysław Niedroszlański
 * Furgał Andrzej
 *
 * Uniwersytet Ekonomiczny Grudzień 2016
 */
@WebServlet(name = "hub", urlPatterns = "/")
public class Hub extends HttpServlet{

    private String response ="";
    private List<String> allTypes = new ArrayList<>();
    private List<String> allMakes = new ArrayList<>();
    private List<String> allModels = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        switch (req.getServletPath()) {
            case "/cars-types": {
                getAllTypesOfCars();
                break;
            }
            case "/makes": {
                getAllMakersByType(req.getQueryString());
                break;
            }
            case "/models": {
                getAllModelsForMake(req.getQueryString());
                break;
            }
            default: {
                response = "Wrong URL";
            }
        }
        if (response.isEmpty()) {
            resp.getWriter().write("No Data found");
        } else {
            resp.getWriter().write(response);
        }
    }

    private void getAllTypesOfCars() throws IOException {
        response = "";
        allTypes.clear();
        VehicleAPI vehicleAPI = new VehicleAPI();
        vehicleAPI.getListOfCarTypes().forEach(el -> putAllValuesIntoList(el, CarsData.TYPES));
        PolishCarsAPI polishCarsAPI = new PolishCarsAPI();
        polishCarsAPI.getCarTypes().forEach(el -> putAllValuesIntoList(el, CarsData.TYPES));

        allTypes.stream().distinct().sorted().forEach(this::createAnswere);
    }

    private void getAllMakersByType(String type) throws IOException {
        response = "";
        allMakes.clear();
        VehicleAPI vehicleAPI = new VehicleAPI();
        vehicleAPI.getCarMakesListForType(type).forEach(el -> putAllValuesIntoList(el, CarsData.MAKES));
        PolishCarsAPI polishCarsAPI = new PolishCarsAPI();
        polishCarsAPI.getMakerList(type).forEach(el -> putAllValuesIntoList(el, CarsData.MAKES));

        allMakes.stream().distinct().sorted().forEach(this::createAnswere);
    }

    private void getAllModelsForMake(String make) throws IOException {
        response = "";
        allModels.clear();
        VehicleAPI vehicleAPI = new VehicleAPI();
        vehicleAPI.getModelsForMake(make).forEach(el -> putAllValuesIntoList(el, CarsData.MODELS));
        PolishCarsAPI polishCarsAPI = new PolishCarsAPI();
        polishCarsAPI.getModelList(make).forEach(el -> putAllValuesIntoList(el, CarsData.MODELS));

        allModels.stream().distinct().sorted().forEach(this::createAnswere);
    }

    private void createAnswere(String el) {
        response += el+ '\n';
    }

    private void putAllValuesIntoList(String value, CarsData listType) {
        switch (listType) {
            case TYPES: {
                allTypes.add(value);
                break;
            }
            case MAKES: {
                allMakes.add(value);
                break;
            }
            case MODELS: {
                allModels.add(value);
                break;
            }
        }
    }


}