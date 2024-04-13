package com.example.helloworld;

import static android.graphics.Color.rgb;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import DATABASE.SQLiteHelper;
import adapter.MucBieuDoXHTCAdapter;
import model.GiaoDich;
import model.MucBieuDoXHTC;

public class ChiTiet_BieuDoXuHuongTaiChinh extends AppCompatActivity {
    ListView describePieChartXHTC;
    PieChart pieChartXHTC;
    private ImageView back,home;
    private Button btnIncomeCTXHTC = null, btnExpenseCTXHTC = null;
    private int checkCPTN;
    private SQLiteHelper db;
    private List<GiaoDich> listgd;
    private String month, year;
    private TextView textViewTimeCTXHTC;
    private List<PieEntry> pieEntries = new ArrayList<>();
    private List<String> listLoaiGiaoDichRa = new ArrayList<>();
    private List<String> listLoaiGiaoDichVao = new ArrayList<>();
    private ArrayList<MucBieuDoXHTC> pieChartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bieu_do_xu_huong_tai_chinh);
        initView();
        handlePieChartXHTC();
        handleEvent();
    }

    private void handlePieChartXHTC() {
        int[] colors = {
                0xFFFF0000, // Đỏ
                0xFFFF00FF, // Magenta
                0xFF0000FF, // Xanh dương
                0xFF008080, // Teal
                0xFF800080, // Tím
                0xFF808000, // Olive
                0xFF800000, // Nâu đậm
                0xFFA52A2A, // Nâu đỏ
                0xFF000080, // Xanh đậm
                0xFF2E8B57, // Xanh lá đậm
                0xFF7CFC00, // Lawn Green
                0xFF8B4513, // Saddle Brown
                0xFF8B008B, // Xanh lam đậm
                0xFFB8860B, // Dark Goldenrod
                0xFF4682B4, // Steel Blue
                0xFFCD5C5C, // Indian Red
                0xFF20B2AA, // Light Sea Green
                0xFFDC143C, // Crimson
                0xFFD2691E, // Chocolate
                0xFF4B0082, // Indigo
                0xFFADFF2F, // Green Yellow
                0xFF800000, // Maroon
                0xFF8B0000, // Dark Red
                0xFF808080, // Gray
                0xFFD3D3D3, // Light Gray
                0xFF708090, // Slate Gray
                0xFF2F4F4F, // Dark Slate Gray
                0xFF556B2F, // Dark Olive Green
                0xFFB0E0E6, // Powder Blue
                0xFF00CED1, // Dark Turquoise
                0xFF9370DB, // Medium Purple
                0xFF00FFFF, // Cyan
                0xFF8A2BE2,  // Blue Violet
                0xFF00FF00, // Xanh lá
                0xFFFFA500 // Cam
        };

        //Lấy dữ liệu ngày tháng đã chọn
        Intent intent = getIntent();
        month = intent.getStringExtra("month");
        year = intent.getStringExtra("year");
        //Xử lý dữ liệu
        pieEntries.clear();
        listLoaiGiaoDichRa.clear();
        listLoaiGiaoDichVao.clear();
        pieChartItems.clear();
        for(int i = 0; i < listgd.size(); i++) {
            Date d = listgd.get(i).getDate();
            Calendar c = Calendar.getInstance();
            c.setTime(d);

            String m = c.get(Calendar.MONTH) + 1 + "";
            String y = c.get(Calendar.YEAR) + "";

            String lable = listgd.get(i).getLoaiGD().getNameIcon();
            float value = listgd.get(i).getSotien();
            if(Objects.equals(month, m) && Objects.equals(year, y)) {
                if(checkCPTN == 1 && listgd.get(i).getKieugd() == 1) {
                    if(listLoaiGiaoDichRa.contains(lable)) {
                        int index = listLoaiGiaoDichRa.indexOf(lable);
                        pieEntries.set(index, new PieEntry(pieEntries.get(index).getValue() + value, lable));
                    }else{
                        listLoaiGiaoDichRa.add(lable);
                        pieEntries.add(new PieEntry(value, lable));
                    }
                }else if(checkCPTN == 2 && listgd.get(i).getKieugd() == 2) {
                    if(listLoaiGiaoDichVao.contains(lable)) {
                        int index = listLoaiGiaoDichVao.indexOf(lable);
                        pieEntries.set(index, new PieEntry(pieEntries.get(index).getValue() + value, lable));
                    }else{
                        listLoaiGiaoDichVao.add(lable);
                        pieEntries.add(new PieEntry(value, lable));
                    }
                }
            }

        }

        // Tính tổng của tất cả các giá trị trên biểu đồ
        float totalValue = 0;
        for (PieEntry entry : pieEntries) {
            totalValue += entry.getValue();
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        // Màu của các phần tử trong biểu đồ
        dataSet.setColors(colors);
        dataSet.setDrawValues(false);
        dataSet.setDrawIcons(false);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(pieChartXHTC)); // Hiển thị giá trị phần trăm

        // Tùy chỉnh các thuộc tính của biểu đồ Pie
        pieChartXHTC.getDescription().setEnabled(false);
        pieChartXHTC.setDrawHoleEnabled(true);
        pieChartXHTC.setHoleColor(Color.WHITE);
        pieChartXHTC.setTransparentCircleRadius(61f);
        pieChartXHTC.setCenterText(String.format("%.2f", totalValue) + " đ"); // Hiển thị giá trị tổng ở giữa biểu đồ
        pieChartXHTC.setCenterTextSize(20);
        pieChartXHTC.setDrawEntryLabels(false);

        // Điều chỉnh mô tả màu
        pieChartXHTC.getLegend().setEnabled(false);

        pieChartXHTC.setData(data);
        pieChartXHTC.animateY(1000); // Animation

        //Hiển thị ngày tháng
        textViewTimeCTXHTC.setText(month + " / " + year);

        // Mổ tả biểu đồ xu hướng tài chính
        for (int i = 0; i < pieEntries.size(); i++) {
            pieChartItems.add(new MucBieuDoXHTC(pieEntries.get(i).getLabel() ,(pieEntries.get(i).getValue()/totalValue)*100, pieEntries.get(i).getValue(), dataSet.getColor(i), false));
        }
        MucBieuDoXHTCAdapter adapter = new MucBieuDoXHTCAdapter(this, pieChartItems);
        describePieChartXHTC.setAdapter(adapter);

    }
    private void handleEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTiet_BieuDoXuHuongTaiChinh.this, BieuDoXuHuongTaiChinh.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTiet_BieuDoXuHuongTaiChinh.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnExpenseCTXHTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.setBackgroundTintList(btnExpenseCTXHTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_on)));
                ViewCompat.setBackgroundTintList(btnIncomeCTXHTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                checkCPTN=1;
                handlePieChartXHTC();
                pieChartXHTC.highlightValue(null);
            }
        });
        btnIncomeCTXHTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.setBackgroundTintList(btnExpenseCTXHTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnIncomeCTXHTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_on)));
                checkCPTN=2;
                handlePieChartXHTC();
                pieChartXHTC.highlightValue(null);
            }
        });
        // Bắt sự kiện click vào khối
        pieChartXHTC.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry entry, Highlight highlight) {
                // Xử lý sự kiện khi click vào khối
                PieEntry pieEntry = (PieEntry) entry;
                int position = pieEntries.indexOf(entry);
                for (MucBieuDoXHTC pieChartItem: pieChartItems) {
                    pieChartItem.setSelected(false);
                }
                pieChartItems.get(position).setSelected(true);
                MucBieuDoXHTCAdapter adapter = new MucBieuDoXHTCAdapter(getApplicationContext(), pieChartItems);
                describePieChartXHTC.setAdapter(adapter);
                describePieChartXHTC.setSelectionFromTop(position, 0);
            }

            @Override
            public void onNothingSelected() {
                // Xử lý khi không có khối nào được chọn
                for(int i = 0; i < pieEntries.size(); i++) {
                    View itemView = describePieChartXHTC.getChildAt(i);
                    if (itemView != null) {
                        itemView.setBackgroundColor(rgb(248,249,250));
                    }
                }
            }
        });
        describePieChartXHTC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (MucBieuDoXHTC pieChartItem: pieChartItems) {
                    pieChartItem.setSelected(false);
                }
                pieChartItems.get(position).setSelected(true);
                MucBieuDoXHTCAdapter adapter = new MucBieuDoXHTCAdapter(getApplicationContext(), pieChartItems);
                describePieChartXHTC.setAdapter(adapter);
                describePieChartXHTC.setSelectionFromTop(position, view.getTop());
                pieChartXHTC.highlightValue(position, 0, false);
            }
        });

    }
    private void initView() {
        db = new SQLiteHelper(getApplicationContext());
        listgd = db.getAllGD("");

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);

        pieChartXHTC = findViewById(R.id.pie_chart_xhtc);
        describePieChartXHTC = findViewById(R.id.describe_pie_chart_xhtc);

        btnExpenseCTXHTC = findViewById(R.id.btn_expense_ctxhtc);
        btnIncomeCTXHTC = findViewById(R.id.btn_income_ctxhtc);
        ViewCompat.setBackgroundTintList(btnExpenseCTXHTC,
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.bg_button_on)));
        ViewCompat.setBackgroundTintList(btnIncomeCTXHTC,
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.bg_button_off)));
        checkCPTN=1;

        textViewTimeCTXHTC = findViewById(R.id.text_view_time_ctxhtc);
    }
}