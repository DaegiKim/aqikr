package models;

import com.fasterxml.jackson.databind.JsonNode;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import play.libs.Json;
import services.DataGoKr;
import utils.DateTimeUtil;

import java.util.ArrayList;
import java.util.List;

public class AirRawData {
    public String stationName;
    public int pm10Grade;
    public int pm10Value;
    public int pm25Grade;
    public int pm25Value;
    public DateTime dateTime;

    public static List<AirRawData> getList() {
        List<AirRawData> airRawDataList = new ArrayList<>();

        JsonNode response = Json.parse(DataGoKr.excute());

        for(JsonNode item : response.findValue("list")) {
            AirRawData airRawData = new AirRawData();
            airRawData.stationName = item.get("stationName").asText();
            airRawData.pm10Grade = item.get("pm10Grade").asInt();
            airRawData.pm10Value = item.get("pm10Value").asInt();
            airRawData.pm25Grade = item.get("pm25Grade").asInt();
            airRawData.pm25Value = item.get("pm25Value").asInt();

            airRawData.dateTime = DateTimeUtil.parseFromDataGoKrFormat(item.get("dataTime").asText());

            airRawDataList.add(airRawData);
        }

        return airRawDataList;
    }

    public static AirRawData getByStation(Station station, List<AirRawData> airRawDatas) {
        for(AirRawData airRawData : airRawDatas) {
            if(airRawData.stationName.equals(station.name)) {
                return airRawData;
            }
        }
        return null;
    }

    public static String getColor(double aqi) {
        if(aqi <= 0) {
            return "#AAAAAA";
        } else if(0 < aqi && aqi <= 50) {
            return "#00FF00"; //Good
        } else if(51 <= aqi && aqi <= 100) {
            return "#FFFF00"; //Moderate
        } else if(101 <= aqi && aqi <= 150) {
            return "#EE7600"; //Unhealthy for Sensitive Groups
        } else if(151 <= aqi && aqi <= 200) {
            return "#FF0000"; //Unhealthy
        } else if(201 <= aqi && aqi <= 300) {
            return "#800080"; //Very Unhealthy
        } else if(301 <= aqi && aqi <= 500) {
            return "#FF34B3"; //Hazardous
        } else {
            return "#FFFFFF";
        }
    }

    public static String getLabel(double aqi) {
        if(aqi <= 0) {
            return "없음";
        } else if(0 < aqi && aqi <= 50) {
            return "좋음"; //Good
        } else if(51 <= aqi && aqi <= 100) {
            return "보통"; //Moderate
        } else if(101 <= aqi && aqi <= 150) {
            return "민감한 사람에게 해로움"; //Unhealthy for Sensitive Groups
        } else if(151 <= aqi && aqi <= 200) {
            return "해로움"; //Unhealthy
        } else if(201 <= aqi && aqi <= 300) {
            return "매우 해로움"; //Very Unhealthy
        } else if(301 <= aqi && aqi <= 500) {
            return "위험"; //Hazardous
        } else {
            return "없음";
        }
    }

    public static double AQIPM25(double conc) {
        double c;
        double AQI;

        c = Math.floor(conc);

        if(c>=0 && c<12.1) {
            AQI=linear(50, 0, 12, 0, c);
        }
        else if (c>=12.1 && c<35.5) {
            AQI=linear(100, 51, 35.4, 12.1, c);
        }
        else if (c>=35.5 && c<55.5) {
            AQI=linear(150, 101, 55.4, 35.5, c);
        }
        else if (c>=55.5 && c<150.5) {
            AQI=linear(200, 151, 150.4, 55.5, c);
        }
        else if (c>=150.5 && c<250.5) {
            AQI=linear(300, 201, 250.4, 150.5, c);
        }
        else if (c>=250.5 && c<350.5) {
            AQI=linear(400, 301, 350.4, 250.5, c);
        }
        else if (c>=350.5 && c<500.5) {
            AQI=linear(500, 401, 500.4, 350.5, c);
        }
        else {
            AQI = -1;
        }

        return AQI;
    }

    public static double AQIPM10(double conc) {
        double c;
        double AQI;

        c = Math.floor(conc);

        if (c>=0 && c<55) {
            AQI=linear(50,0,54,0,c);
        }
        else if (c>=55 && c<155) {
            AQI=linear(100,51,154,55,c);
        }
        else if (c>=155 && c<255) {
            AQI=linear(150,101,254,155,c);
        }
        else if (c>=255 && c<355) {
            AQI=linear(200,151,354,255,c);
        }
        else if (c>=355 && c<425) {
            AQI=linear(300,201,424,355,c);
        }
        else if (c>=425 && c<505) {
            AQI=linear(400,301,504,425,c);
        }
        else if (c>=505 && c<605) {
            AQI=linear(500,401,604,505,c);
        }
        else {
            AQI=-1;
        }

        return AQI;
    }

    public static double linear(double aqiHigh, double aqiLow, double concHigh, double concLow, double concentration) {
        return Math.round(((concentration - concLow) / (concHigh - concLow)) * (aqiHigh - aqiLow) + aqiLow);
    }
}
