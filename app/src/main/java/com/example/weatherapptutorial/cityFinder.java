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
import android.widget.Toast; // Quan trọng: Nhớ import Toast

public class cityFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);

        final EditText editText = findViewById(R.id.searchCity);
        ImageView backButton = findViewById(R.id.backButton);
        Button btnSearch = findViewById(R.id.btnSearch);
        RelativeLayout btnGetCurrentLocation = findViewById(R.id.btnGetCurrentLocation);

        // 1. Xử lý sự kiện khi bấm nút "Tìm kiếm"
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = editText.getText().toString().trim();

                // KIỂM TRA RỖNG Ở ĐÂY
                if (newCity.isEmpty()) {
                    // Hiện cảnh báo màu đỏ ở ô nhập liệu
                    editText.setError("Vui lòng nhập tên thành phố!");
                    // Hiện thông báo pop-up nhỏ ở dưới màn hình
                    Toast.makeText(cityFinder.this, "Tên thành phố không được để trống", Toast.LENGTH_SHORT).show();
                    return; // Kết thúc hàm tại đây, không chạy lệnh chuyển trang bên dưới
                }

                Intent intent = new Intent(cityFinder.this, MainActivity.class);
                intent.putExtra("City", newCity);
                startActivity(intent);
            }
        });

        // 2. Xử lý sự kiện khi bấm phím Enter/Search trên bàn phím ảo điện thoại
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String newCity = editText.getText().toString().trim();

                // KIỂM TRA RỖNG TƯƠNG TỰ
                if (newCity.isEmpty()) {
                    editText.setError("Vui lòng nhập tên thành phố!");
                    Toast.makeText(cityFinder.this, "Tên thành phố không được để trống", Toast.LENGTH_SHORT).show();
                    return true; // Trả về true để giữ bàn phím và không thực hiện hành động mặc định
                }

                Intent intent = new Intent(cityFinder.this, MainActivity.class);
                intent.putExtra("City", newCity);
                startActivity(intent);
                return false;
            }
        });

        // Xử lý sự kiện lấy vị trí hiện tại
        btnGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cityFinder.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        // Xử lý nút quay lại
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}