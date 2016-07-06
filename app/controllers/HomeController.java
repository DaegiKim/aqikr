package controllers;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.AirRawData;
import models.Station;
import org.apache.commons.lang3.tuple.Pair;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import utils.MenuType;

import java.util.List;

public class HomeController extends Controller {
    public Result index() {
        return redirect(routes.HomeController.pm10());
    }

    public Result pm10() {
        return ok(views.html.map.render(MenuType.PM10));
    }

    public Result pm25() {
        return ok(views.html.map.render(MenuType.PM25));
    }

    public Result data() {
        Station.init();

        List<Station> stations = Station.find.all();

        List<AirRawData> airRawDataList = AirRawData.getList();

        ObjectNode result = Json.newObject();

        for(Station station : stations) {
            ObjectNode stationNode = Json.newObject();

            ArrayNode polygonNode = Json.newArray();

            for(Pair<String, String> pair : station.parsePolygon()) {
                ObjectNode latlng = Json.newObject();
                latlng.put("lat", pair.getLeft());
                latlng.put("lng", pair.getRight());
                polygonNode.add(latlng);
            }


            stationNode.set("polygon", polygonNode);

            //pm10
            ObjectNode pm10 = Json.newObject();
            double aqipm10 = AirRawData.AQIPM10(AirRawData.getByStation(station, airRawDataList).pm10Value);
            pm10.put("aqi", aqipm10);
            pm10.put("color", AirRawData.getColor(aqipm10));
            stationNode.set("pm10", pm10);

            //pm25
            ObjectNode pm25 = Json.newObject();
            double aqipm25 = AirRawData.AQIPM25(AirRawData.getByStation(station, airRawDataList).pm25Value);
            pm25.put("aqi", aqipm25);
            pm25.put("color", AirRawData.getColor(aqipm25));
            stationNode.set("pm25", pm25);

            //center
            ObjectNode center = Json.newObject();
            Pair<String, String> centralGeoCoordinate = station.getCentralGeoCoordinate();
            center.put("lat", centralGeoCoordinate.getLeft());
            center.put("lng", centralGeoCoordinate.getRight());
            stationNode.set("center", center);

            result.set(station.name, stationNode);
        }

        return ok(result);
    }
}
