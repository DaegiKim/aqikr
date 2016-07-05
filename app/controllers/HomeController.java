package controllers;

import models.AirRawData;
import models.Station;
import play.mvc.Controller;
import play.mvc.Result;
import utils.MenuType;

import java.util.List;

public class HomeController extends Controller {
    public Result index() {
        return redirect(routes.HomeController.pm10());
    }

    public Result pm10() {
        return map(MenuType.PM10);
    }

    public Result pm25() {
        return map(MenuType.PM25);
    }

    private Result map(MenuType menuType) {
        Station.init();

        List<Station> stations = Station.find.all();

        List<AirRawData> airRawDataList = AirRawData.getList();

        return ok(views.html.map.render(stations, airRawDataList, menuType));
    }
}
