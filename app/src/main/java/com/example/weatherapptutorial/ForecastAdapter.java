package com.example.weatherapptutorial;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ViewHolder> {

    private List<ForecastItem> forecastList;
    private Context context;

    public ForecastAdapter(Context context, List<ForecastItem> forecastList) {
        this.context = context;
        this.forecastList = forecastList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.forecast_item, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ForecastItem item = forecastList.get(position);

        holder.dayText.setText(item.getTime());
        holder.tempText.setText(item.getTemp());

        // CẬP NHẬT: Gán trạng thái thời tiết vào TextView
        holder.descText.setText(item.getDescription());

        int resourceID = context.getResources().getIdentifier(item.getIconName(), "drawable", context.getPackageName());
        holder.iconImage.setImageResource(resourceID);
    }

    @Override
    public int getItemCount() {
        return forecastList.size();
    }

    public void updateData(List<ForecastItem> newForecasts) {
        forecastList.clear();
        forecastList.addAll(newForecasts);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView dayText, tempText, descText; // Khai báo thêm descText
        ImageView iconImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            dayText = itemView.findViewById(R.id.tvForecastDay);
            tempText = itemView.findViewById(R.id.tvForecastTemp);
            iconImage = itemView.findViewById(R.id.ivForecastIcon);

            // CẬP NHẬT: Ánh xạ biến với ID trong XML
            descText = itemView.findViewById(R.id.tvForecastDesc);
        }
    }
}