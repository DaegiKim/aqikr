package utils;

public class ConstantUtil {
    public static final String URL_SIDO = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty" +
            "?" + "numOfRows=999" + "&" + "_returnType=json" + "&" + "ver=1.3" + "&" + "pageNo=1" + "&" +
            "ServiceKey=kJ6Wrr7taAyDE%2FGBcx3ROeu%2FTpFfyOuTJeqKsnMbYkU8k2G5rq3bxgt2cO2f4xG4c2mL%2BXX75XFAPDuI6mBcKw%3D%3D" +
            "&" +
            "sidoName={param1}";

    public static final String URL_STATION_DATA = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty" +
            "?" + "_returnType=json" + "&" + "ver=1.3" + "&" + "pageNo=1" + "&" + "numOfRows=999" + "&" + "dataTerm=MONTH" + "&" +
            "ServiceKey=kJ6Wrr7taAyDE%2FGBcx3ROeu%2FTpFfyOuTJeqKsnMbYkU8k2G5rq3bxgt2cO2f4xG4c2mL%2BXX75XFAPDuI6mBcKw%3D%3D" +
            "&" +
            "stationName={param1}";

    public static final String URL_SEARCH_BY_TERM = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getTMStdrCrdnt" +
            "?_returnType=json&umdName={param1}&pageNo=1&numOfRows=999&ServiceKey=kJ6Wrr7taAyDE%2FGBcx3ROeu%2FTpFfyOuTJeqKsnMbYkU8k2G5rq3bxgt2cO2f4xG4c2mL%2BXX75XFAPDuI6mBcKw%3D%3D";

    public static final String URL_SEARCH_BY_XY = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getNearbyMsrstnList" +
            "?_returnType=json&tmX={param1}&tmY={param2}&pageNo=1&numOfRows=999" +
            "&ServiceKey=kJ6Wrr7taAyDE%2FGBcx3ROeu%2FTpFfyOuTJeqKsnMbYkU8k2G5rq3bxgt2cO2f4xG4c2mL%2BXX75XFAPDuI6mBcKw%3D%3D";

    public static final String URL_STATION_DETAIL = "http://openapi.airkorea.or.kr/openapi/services/rest/MsrstnInfoInqireSvc/getMsrstnList" +
            "?stationName={param1}" +
            "&pageNo=1&numOfRows=999" +
            "&ServiceKey=kJ6Wrr7taAyDE%2FGBcx3ROeu%2FTpFfyOuTJeqKsnMbYkU8k2G5rq3bxgt2cO2f4xG4c2mL%2BXX75XFAPDuI6mBcKw%3D%3D" +
            "&_returnType=json";
}
