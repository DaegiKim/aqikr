package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class DataGoKr {
    public static final String endpoint = "http://openapi.airkorea.or.kr/openapi/services/rest/ArpltnInforInqireSvc/getCtprvnRltmMesureDnsty";
    public static final String params = "?" +
            "sidoName=서울" +
            "&" +
            "_returnType=json" +
            "&" +
            "ServiceKey=kJ6Wrr7taAyDE%2FGBcx3ROeu%2FTpFfyOuTJeqKsnMbYkU8k2G5rq3bxgt2cO2f4xG4c2mL%2BXX75XFAPDuI6mBcKw%3D%3D" +
            "&" +
            "ver=1.2" +
            "&" +
            "pageNo=1" +
            "&" +
            "numOfRows=100";

    public static String excute() {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(endpoint+params);
            connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder();
            String line;
            while((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null) {
                connection.disconnect();
            }
        }
    }
}
