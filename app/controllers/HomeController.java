package controllers;

import models.AirRawData;
import models.Station;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

public class HomeController extends Controller {
    public Result index() {
        Station.init();

        List<Station> stations = Station.find.all();

        List<AirRawData> airRawDataList = AirRawData.getList();

        return ok(views.html.index.render(stations, airRawDataList));
    }
}
