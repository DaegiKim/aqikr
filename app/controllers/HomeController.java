package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.AirRawData;
import models.Station;
import org.apache.commons.lang3.tuple.Pair;
import org.ocpsoft.prettytime.PrettyTime;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.DataGoKrCopy;
import utils.DateTimeUtil;
import utils.MenuType;

import java.util.List;
import java.util.Locale;

public class HomeController extends Controller {
    public Result test() {
        return ok(views.html.test.render(MenuType.TEST));
    }

    public Result index() {
        return redirect(routes.HomeController.map());
    }

    public Result map() {
        return ok(views.html.map.render());
    }

    public Result station(String stationName) {
        JsonNode jsonNode = Json.parse(DataGoKrCopy.excute(stationName));

        for(JsonNode item : jsonNode.get("list")) {
            ObjectNode objectNode = ((ObjectNode) item);

            double aqipm25 = AirRawData.AQIPM25(item.get("pm25Value").asInt());
            double aqipm10 = AirRawData.AQIPM10(item.get("pm10Value").asInt());

            objectNode.put("pm25AQI", aqipm25);
            objectNode.put("pm10AQI", aqipm10);

            objectNode.put("pm25Color", AirRawData.getColor(aqipm25));
            objectNode.put("pm10Color", AirRawData.getColor(aqipm10));
            objectNode.put("timestamp", DateTimeUtil.parseFromDataGoKrFormat(item.get("dataTime").asText()).toString());
        }

        return ok(views.html.station.render(jsonNode, stationName));
    }

    public Result data() {
        Station.init();

        List<Station> stations = Station.find.all();

        List<AirRawData> airRawDataList = AirRawData.getList();

        ObjectNode result = Json.newObject();

        for(Station station : stations) {
            AirRawData airRawData = AirRawData.getByStation(station, airRawDataList);

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
            double aqipm10 = AirRawData.AQIPM10(airRawData.pm10Value);
            pm10.put("aqi", aqipm10);
            pm10.put("raw", airRawData.pm10Value);
            pm10.put("color", AirRawData.getColor(aqipm10));
            pm10.put("label", AirRawData.getLabel(aqipm10));
            stationNode.set("pm10", pm10);

            //pm25
            ObjectNode pm25 = Json.newObject();
            double aqipm25 = AirRawData.AQIPM25(airRawData.pm25Value);
            pm25.put("aqi", aqipm25);
            pm25.put("raw", airRawData.pm25Value);
            pm25.put("color", AirRawData.getColor(aqipm25));
            pm25.put("label", AirRawData.getLabel(aqipm25));
            stationNode.set("pm25", pm25);

            //center
            ObjectNode center = Json.newObject();
            Pair<String, String> centralGeoCoordinate = station.getCentralGeoCoordinate();
            center.put("lat", centralGeoCoordinate.getLeft());
            center.put("lng", centralGeoCoordinate.getRight());
            stationNode.set("center", center);

            //datetime
            PrettyTime prettyTime = new PrettyTime(Locale.KOREA);
            stationNode.put("datetime", prettyTime.format(airRawData.dateTime.toDate()));

            stationNode.put("link", routes.HomeController.station(station.name).url());

            result.set(station.name, stationNode);
        }

        return ok(result);
    }
}
