package models;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

public class StationWeekly {
    public DateTime dateTime;

    public int avgPM25;
    public int minPM25;
    public int maxPM25;

    public int avgPM10;
    public int minPM10;
    public int maxPM10;

    public int count;

    public static List<StationWeekly> getStationWeekly(JsonNode jsonNode) {
        List<StationWeekly> stationWeeklyList = new ArrayList<>();

        int count = 0;
        int sumPM25 = 0, minPM25 = Integer.MAX_VALUE, maxPM25 = 0,
            sumPM10 = 0, minPM10 = Integer.MAX_VALUE, maxPM10 = 0;

        int currentDay = 0;
        DateTime currentDateTime = DateTime.now();

        for(JsonNode item : jsonNode.get("list")) {
            DateTime time = DateTime.parse(item.get("dataTime").asText());

            int dayOfYear = time.getDayOfYear();

            if(currentDay == 0) {
                currentDay = dayOfYear;
            } else if(currentDay != dayOfYear) {
                StationWeekly stationWeekly = new StationWeekly();

                stationWeekly.dateTime = currentDateTime;

                stationWeekly.avgPM25 = sumPM25/count;
                stationWeekly.minPM25 = minPM25;
                stationWeekly.maxPM25 = maxPM25;

                stationWeekly.avgPM10 = sumPM10/count;
                stationWeekly.minPM10 = minPM10;
                stationWeekly.maxPM10 = maxPM10;

                stationWeekly.count = count;

                stationWeeklyList.add(stationWeekly);

                count = 0;
                sumPM25 = 0; minPM25 = Integer.MAX_VALUE; maxPM25 = 0;
                sumPM10 = 0; minPM10 = Integer.MAX_VALUE; maxPM10 = 0;

                currentDay = dayOfYear;
            }

            sumPM25 += item.get("pm25Value").asInt();
            minPM25 = Math.min(minPM25, item.get("pm25Value").asInt());
            maxPM25 = Math.max(maxPM25, item.get("pm25Value").asInt());

            sumPM10 += item.get("pm10Value").asInt();
            minPM10 = Math.min(minPM10, item.get("pm10Value").asInt());
            maxPM10 = Math.max(maxPM10, item.get("pm10Value").asInt());

            count++;
            currentDateTime = time;
        }

        stationWeeklyList = stationWeeklyList.subList(0, 7);
        stationWeeklyList = Lists.reverse(stationWeeklyList);

        return stationWeeklyList;
    }
}
