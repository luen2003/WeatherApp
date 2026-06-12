package com.example.weatherapptutorial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

// Các thư viện cần thiết để gọi API kiểm tra thành phố
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class cityFinder extends AppCompatActivity {

    // Khai báo APP_ID và URL giống như bên MainActivity
    final String APP_ID = "7d5b8634b1df2e08455cef623b46dcad";
    final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);

        final EditText editText = findViewById(R.id.searchCity);
        ImageView backButton = findViewById(R.id.backButton);
        Button btnSearch = findViewById(R.id.btnSearch);
        RelativeLayout btnGetCurrentLocation = findViewById(R.id.btnGetCurrentLocation);

        // 1. Xử lý sự kiện nút Tìm kiếm
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = editText.getText().toString().trim();

                // Kiểm tra rỗng
                if (newCity.isEmpty()) {
                    editText.setError("Please enter a city name!");
                    Toast.makeText(cityFinder.this, "City Name Required!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Gọi hàm kiểm tra tên thành phố xem có tồn tại không
                checkCityValidity(newCity, editText);
            }
        });

        // 2. Xử lý sự kiện bàn phím ảo (nhấn Enter)
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String newCity = editText.getText().toString().trim();

                if (newCity.isEmpty()) {
                    editText.setError("Please enter a city name!");
                    Toast.makeText(cityFinder.this, "City Name Required!", Toast.LENGTH_SHORT).show();
                    return true;
                }

                checkCityValidity(newCity, editText);
                // Trả về true để bàn phím không tự đóng khi đang chờ API phản hồi
                return true;
            }
        });

        // 3. Xử lý nút Lấy vị trí hiện tại
        btnGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cityFinder.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // 4. Xử lý nút quay lại
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // ==========================================
    // HÀM KIỂM TRA THÀNH PHỐ CÓ TỒN TẠI KHÔNG
    // ==========================================
    private void checkCityValidity(final String cityName, final EditText editText) {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("q", cityName);
        params.put("appid", APP_ID);

        // Gửi request nhẹ nhàng lên OpenWeatherMap để test tên thành phố
        client.get(WEATHER_URL, params, new JsonHttpResponseHandler() {

            // Nếu API trả về thành công (Thành phố có thật)
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Intent intent = new Intent(cityFinder.this, MainActivity.class);
                intent.putExtra("City", cityName);
                startActivity(intent);
            }

            // Nếu API báo lỗi (Thành phố không tồn tại hoặc lỗi mạng)
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                editText.setError("City Name Not Found!");
                Toast.makeText(cityFinder.this, "City Name Not Found!", Toast.LENGTH_LONG).show();
            }
        });
    }
}