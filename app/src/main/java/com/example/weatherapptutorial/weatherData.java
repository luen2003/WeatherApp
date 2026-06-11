package com.example.weatherapptutorial;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class weatherData {

    private String mTemperature, micon, mcity, mWeatherType;
    private int mCondition;

    // 1. Hàm parse dữ liệu thời tiết hiện tại
    public static weatherData fromJson(JSONObject jsonObject) {
        try {
            weatherData weatherD = new weatherData();
            weatherD.mcity = jsonObject.getString("name");
            weatherD.mCondition = jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id");
            weatherD.mWeatherType = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
            weatherD.micon = updateWeatherIcon(weatherD.mCondition);

            // Chuyển đổi từ Kelvin sang Celsius
            double tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15;
            int roundedValue = (int) Math.rint(tempResult);
            weatherD.mTemperature = Integer.toString(roundedValue);

            return weatherD;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 2. Hàm parse dữ liệu dự báo thời tiết theo giờ
    // Hàm parse dữ liệu dự báo thời tiết (Mỗi 3 giờ)
    public static List<ForecastItem> parseHourlyForecastFromJson(JSONObject jsonObject) {
        List<ForecastItem> forecastItems = new ArrayList<>();
        try {
            JSONArray listArray = jsonObject.getJSONArray("list");

            // Lấy 8 mốc thời gian tiếp theo (24h)
            int limit = Math.min(listArray.length(), 8);

            for (int i = 0; i < limit; i++) {
                JSONObject itemObj = listArray.getJSONObject(i);

                double temp = itemObj.getJSONObject("main").getDouble("temp") - 273.15;

                JSONObject weatherObj = itemObj.getJSONArray("weather").getJSONObject(0);
                int condition = weatherObj.getInt("id");
                String iconName = updateWeatherIcon(condition);

                // Lấy trạng thái thời tiết (VD: "Clear", "Clouds", "Rain")
                String description = weatherObj.getString("main");

                long dt = itemObj.getLong("dt") * 1000L;
                Date date = new Date(dt);

                // CẬP NHẬT: Định dạng Ngày/Tháng Giờ:Phút (VD: 15/06 14:00)
                String timeStr = new SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()).format(date);

                // Thêm description vào constructor
                forecastItems.add(new ForecastItem(
                        timeStr,
                        String.valueOf((int)Math.rint(temp)),
                        iconName,
                        description
                ));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return forecastItems;
    }

    // 3. Hàm map mã thời tiết với tên file icon trong thư mục drawable
    public static String updateWeatherIcon(int condition) {
        if (condition >= 0 && condition <= 300) {
            return "thunderstrom1";
        } else if (condition >= 300 && condition <= 500) {
            return "lightrain";
        } else if (condition >= 500 && condition <= 600) {
            return "shower";
        } else if (condition >= 600 && condition <= 700) {
            return "snow2";
        } else if (condition >= 701 && condition <= 771) {
            return "fog";
        } else if (condition >= 772 && condition <= 800) {
            return "overcast";
        } else if (condition == 800) {
            return "sunny";
        } else if (condition >= 801 && condition <= 804) {
            return "cloudy";
        } else if (condition >= 900 && condition <= 902) {
            return "thunderstrom1";
        } else if (condition == 903) {
            return "snow1";
        } else if (condition == 904) {
            return "sunny";
        } else if (condition >= 905 && condition <= 1000) {
            return "thunderstrom2";
        }
        return "dunno";
    }

    // Các hàm Getter để lấy dữ liệu ra giao diện
    public String getmTemperature() {
        return mTemperature + "°C";
    }

    public String getMicon() {
        return micon;
    }

    public String getMcity() {
        return mcity;
    }

    public String getmWeatherType() {
        return mWeatherType;
    }
}