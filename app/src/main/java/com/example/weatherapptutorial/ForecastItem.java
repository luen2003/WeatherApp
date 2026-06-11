package com.example.weatherapptutorial;

public class ForecastItem {
    private String time;
    private String temp;
    private String iconName;
    private String description; // Thêm trạng thái thời tiết

    public ForecastItem(String time, String temp, String iconName, String description) {
        this.time = time;
        this.temp = temp;
        this.iconName = iconName;
        this.description = description;
    }

    public String getTime() { return time; }
    public String getTemp() { return temp + "°"; }
    public String getIconName() { return iconName; }
    public String getDescription() { return description; } // Getter mới
}