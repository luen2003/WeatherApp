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

public class cityFinder extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_finder);

        final EditText editText = findViewById(R.id.searchCity);
        ImageView backButton = findViewById(R.id.backButton);
        Button btnSearch = findViewById(R.id.btnSearch);

        // Ánh xạ nút lấy vị trí hiện tại
        RelativeLayout btnGetCurrentLocation = findViewById(R.id.btnGetCurrentLocation);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newCity = editText.getText().toString().trim();
                Intent intent = new Intent(cityFinder.this, MainActivity.class);
                intent.putExtra("City", newCity);
                startActivity(intent);
            }
        });

        // Xử lý sự kiện khi bấm nút "Lấy vị trí hiện tại"
        btnGetCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(cityFinder.this, MainActivity.class);
                // Xoá ngăn xếp Activity để tránh lỗi xếp chồng màn hình
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String newCity = editText.getText().toString().trim();
                Intent intent = new Intent(cityFinder.this, MainActivity.class);
                intent.putExtra("City", newCity);
                startActivity(intent);
                return false;
            }
        });
    }
}