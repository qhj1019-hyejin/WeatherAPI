import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherAPIData {
    public static void main(String[] args){
        try {
            Scanner scanner = new Scanner(System.in);
            String city;
            do{
                System.out.println("============================");
                System.out.println("지역 입력 (종료하려면 No를 입력해주세요): ");
                city = scanner.nextLine();

                // No 입력 시 대소문자 구분 없이 전체 중단
                if(city.equalsIgnoreCase("No")) break;

                JSONObject cityLocationData = (JSONObject) getLocationData(city);
                double latitude  = (double) cityLocationData.get("latitude");
                double longitude = (double) cityLocationData.get("longitude");

                displayWeatherData(latitude , longitude);


            }while (!city.equalsIgnoreCase("No"));

        }catch(Exception e){
            e.printStackTrace();
        }

    }

    private static JSONObject getLocationData(String city){ // 사용자가 입력한 city 문자열 값을 가져온다.
        city = city.replaceAll(" ","+");

        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + city + "&count=1&language=en&format=json";

        try {
            HttpURLConnection apiConnection = fetchApiResponse(urlString);

            if(apiConnection.getResponseCode() != 200){ // 정상적으로 연결이 안됐을 경우
                System.out.println("Error : api에 연결할 수 없습니다.");
                return null;
            }

            String jsonResponse = readApiResponse(apiConnection); // 메소드에서 문자열로 리턴 해준다.

            // 문자열을 json 데이터로 파싱
            JSONParser parser = new JSONParser();

            // json 문자열을 jsonObject 로 변환
            JSONObject resultJsonObj = (JSONObject) parser.parse(jsonResponse);

            // 위치 데이터 검색
            JSONArray locationData = (JSONArray) resultJsonObj.get("results");
            return (JSONObject) locationData.get(0);


        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }

    private static void displayWeatherData (double latitude, double longitude){
        try{
            String url = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                    "&longitude=" + longitude + "&current=temperature_2m,relative_humidity_2m,wind_speed_10m";

            HttpURLConnection apiConnection = fetchApiResponse(url);

            if(apiConnection.getResponseCode() != 200){ // ok 아닐 경우
                System.out.println("Error : api에 연결할 수 없습니다.");
            }

            // api 통신 후 문자열로 반환 한다.
            String jsonResponse = readApiResponse(apiConnection);

            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(jsonResponse); // json key, value로 변환한다.
            JSONObject currentWeatherJson =  (JSONObject) jsonObject.get("current");

            //System.out.println(currentWeatherJson.toJSONString());

            String time = (String) currentWeatherJson.get("time"); // time을 문자열로 변환
            System.out.println("현재 시간 : " + time);

            double temperature = (double) currentWeatherJson.get("temperature_2m"); // 온도를 실수로 변환
            System.out.println("현재 온도 : " + temperature);

            //long relativeHumidity = ; test



        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private static String readApiResponse(HttpURLConnection apiConnection){
        try{
            // json data 저장을 위해 StringBuilder 생성
            StringBuilder resultJson = new StringBuilder();

            Scanner scanner = new Scanner(apiConnection.getInputStream());

            while (scanner.hasNext()){ // 값이 있을 경우 (true 일 경우)
                resultJson.append(scanner.nextLine());
            }

            scanner.close();

            // 전달받은 json 데이터를 문자열로 리턴한다.
            return resultJson.toString();

        }catch(IOException e){
            e.printStackTrace();
        }

        return null;
    }

    private static HttpURLConnection fetchApiResponse(String urlString){ // api 통신 연결 메소드

        try{
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            return conn;
        }catch (IOException e){
            e.printStackTrace();
        }

        // 연결 할 수 없을 경우 반환 값 없음.
        return null;
    }

}