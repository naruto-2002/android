package com.example.helloworld;

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

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import DATABASE.SQLiteHelper;
import adapter.MucBieuDoSSTCAdapter;
import model.GiaoDich;
import model.MucBieuDoSSTC;

public class ChiTiet_BieuDoSoSanhThuChi extends AppCompatActivity {
    ListView describeBarChartSSTC;
    BarChart barChartSSTC;
    private ImageView back,home;
    private SQLiteHelper db;
    private List<GiaoDich> listgd;
    private int checkTime;
    Button btnYearCTSSTC, btnMonthCTSSTC, btnWeekCTSSTC, btnDayCTSSTC;
    private String yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd;
    private ArrayList<MucBieuDoSSTC> barChartItems = new ArrayList<>();
    private ArrayList<String> listTime = new ArrayList<>();
    private int p = 0;
    private int top = 0;
    private TextView textViewTimeCTSSTC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_bieu_do_so_sanh_thu_chi);
        initView();
        handleBarChartSSTC();
        handleEvent();
    }
    private void handleBarChartSSTC() {
        //Lấy dữ liệu khoảng thời gian đã trọn
        Intent intent = getIntent();
        yearStart = intent.getStringExtra("yearStart");
        monthStart = intent.getStringExtra("monthStart");
        dayStart = intent.getStringExtra("dayStart");
        yearEnd = intent.getStringExtra("yearEnd");
        monthEnd = intent.getStringExtra("monthEnd");
        dayEnd = intent.getStringExtra("dayEnd");

        textViewTimeCTSSTC.setText(dayStart + "/" + (Integer.parseInt(monthStart) + 1) + "/" + yearStart + " - " + dayEnd + "/" + (Integer.parseInt(monthEnd) + 1) + "/" + yearEnd);

        Calendar timeStart = Calendar.getInstance();
        timeStart.set(Calendar.YEAR, Integer.parseInt(yearStart));
        timeStart.set(Calendar.MONTH, Integer.parseInt(monthStart)); // Lưu ý: Tháng trong Calendar bắt đầu từ 0
        timeStart.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayStart));

        Calendar timeEnd = Calendar.getInstance();
        timeEnd.set(Calendar.YEAR, Integer.parseInt(yearEnd));
        timeEnd.set(Calendar.MONTH, Integer.parseInt(monthEnd)); // Lưu ý: Tháng trong Calendar bắt đầu từ 0
        timeEnd.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dayEnd));

        // Dummy data for the bar chart (three groups of bars)
        ArrayList<BarEntry> group1 = new ArrayList<>();
        ArrayList<BarEntry> group2 = new ArrayList<>();
        ArrayList<BarEntry> group3 = new ArrayList<>();
        ArrayList<BarEntry> group4 = new ArrayList<>();
        group1.add(new BarEntry(0, 0));
        group1.add(new BarEntry(1, 0));
        group1.add(new BarEntry(2, 0));
        group1.add(new BarEntry(3, 0));
        group1.add(new BarEntry(4, 0));
        group1.add(new BarEntry(5, 0));

        group2.add(new BarEntry(0, 0));
        group2.add(new BarEntry(1, 0));
        group2.add(new BarEntry(2, 0));
        group2.add(new BarEntry(3, 0));
        group2.add(new BarEntry(4, 0));
        group2.add(new BarEntry(5, 0));

        group3.add(new BarEntry(0, 0));
        group3.add(new BarEntry(1, 0));
        group3.add(new BarEntry(2, 0));
        group3.add(new BarEntry(3, 0));
        group3.add(new BarEntry(4, 0));
        group3.add(new BarEntry(5, 0));

        group4.add(new BarEntry(0, 0));
        group4.add(new BarEntry(1, 0));
        group4.add(new BarEntry(2, 0));
        group4.add(new BarEntry(3, 0));
        group4.add(new BarEntry(4, 0));
        group4.add(new BarEntry(5, 0));

        //Sắp xếp listgd theo ngày tháng từ quá khứ đến hiện tại
        listgd.sort(new Comparator<GiaoDich>() {
            @Override
            public int compare(
                    GiaoDich gd1,
                    GiaoDich gd2
            ) {
                Date date1 = gd1.getDate();
                Date date2 = gd2.getDate();
                //Tăng dần
                return date1.compareTo(date2);
            }
        });

        // Xử lý lấy dữ liệu thu nhập, chi phí, lợi nhuận, lỗ
        barChartItems.clear();
        listTime.clear();

        for(int i = 0 ; i < listgd.size(); i++) {
            Date date = listgd.get(i).getDate();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            String y = c.get(Calendar.YEAR) + "";
            String m = c.get(Calendar.MONTH) + 1 + "";
            String w = c.get(Calendar.WEEK_OF_MONTH) + "";
            String d = c.get(Calendar.DAY_OF_MONTH) + "";

            String time1 = y;
            String time2 = m + "/" + y;
            String time3 = "Tuần_" + w + " " + m + "/" + y;
            String time4 = d + "/" + m + "/" + y;

            float value = listgd.get(i).getSotien();
            float income = 0, expense = 0, profit = 0, loss = 0;
            if(listgd.get(i).getKieugd() == 1) {
                expense += value;
            }else if(listgd.get(i).getKieugd() == 2) {
                income += value;
            }
            if(income > expense) {
                profit += income - expense;
            }else if(income < expense) {
                loss += expense - income;
            }

            String time = "";
            if (checkTime == 1)
                time += time1;
            else if (checkTime == 2)
                time += time2;
            else if (checkTime == 3)
                time += time3;
            else if (checkTime == 4)
                time += time4;

            if(time.isEmpty())
                break;

            if(cmp(c, timeStart) >= 0 && cmp(c, timeEnd) <= 0) {
                if (listTime.contains(time)) {
                    int index = listTime.indexOf(time);
                    float income1 = barChartItems.get(index).getIncome();
                    float expense1 = barChartItems.get(index).getExpense();
                    float profit1 = barChartItems.get(index).getProfit();
                    float loss1 = barChartItems.get(index).getLoss();
                    barChartItems.set(index, new MucBieuDoSSTC(time, income1 + income, expense1 + expense, profit1 + profit, loss1 + loss, false));
                } else {
                    listTime.add(time);
                    barChartItems.add(new MucBieuDoSSTC(time, income, expense, profit, loss, false));
                }
            }

        }


        int j = 0, k = 0;
        if(barChartItems.size() - p > 6)
            j += barChartItems.size() - p - 6;
        for(int i = j; i < barChartItems.size() - p; i++) {
            group1.set(k, new BarEntry(k, barChartItems.get(i).getIncome()));
            group2.set(k, new BarEntry(k, barChartItems.get(i).getExpense()));
            group3.set(k, new BarEntry(k, barChartItems.get(i).getProfit()));
            group4.set(k, new BarEntry(k, barChartItems.get(i).getLoss()));
            k += 1;
        }

        BarDataSet dataSet1 = new BarDataSet(group1, "Thu nhập");
        BarDataSet dataSet2 = new BarDataSet(group2, "Chi phí");
        BarDataSet dataSet3 = new BarDataSet(group3, "Lợi nhuận");
        BarDataSet dataSet4 = new BarDataSet(group4, "Lỗ");

        // Tắt hiển thị giá trị trên đầu mỗi cột
        dataSet1.setDrawValues(false);
        dataSet2.setDrawValues(false);
        dataSet3.setDrawValues(false);
        dataSet4.setDrawValues(false);

        // Set colors for each group
        dataSet1.setColor(0xFF2E8B57);
        dataSet2.setColor(0xFFFFA500);
        dataSet3.setColor(0xFF0000FF);
        dataSet4.setColor(0xFFFF0000);

        // Adjust spacing between groups
        float groupSpace = 0.2f; // Space between groups of bars
        float barSpace = 0f; // Space between each bar within a group
        float barWidth = 0.2f; // Width of each bar

        // Create a BarData object with the datasets
        BarData barData = new BarData(dataSet1, dataSet2, dataSet3, dataSet4);
        barData.setBarWidth(barWidth);

        // Set the data for the chart
        barChartSSTC.setData(barData);

        // Set description (optional)
        Description description = new Description();
        description.setText("");
        barChartSSTC.setDescription(description);

        // Set labels for X-axis (time descriptions for each group)
        XAxis xAxis = barChartSSTC.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(listTime.subList(j, barChartItems.size() - p)));
        xAxis.setTextSize(12f);  // Thiết lập cỡ chữ
        xAxis.setTextColor(Color.BLACK);  // Thiết lập màu sắc
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);  // Thiết lập vị trí
        xAxis.setLabelRotationAngle(45f);  // Đặt độ nghiêng là 45 độ
        xAxis.setAxisMinimum(0);
        xAxis.setAxisMaximum(6);
        xAxis.setCenterAxisLabels(true);
        barChartSSTC.groupBars(0, groupSpace, barSpace);

        // Điều chỉnh mô tả màu
        barChartSSTC.getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);

        // Refresh chart
        barChartSSTC.invalidate();

        // Hiển thị mô tả biểu đồ so sánh thu nhập
        if(!barChartItems.isEmpty()) {
            ArrayList<MucBieuDoSSTC> barChartItems1 = barChartItems;
            Collections.reverse(barChartItems1);
            barChartItems1.get(p).setSelected(true);
            MucBieuDoSSTCAdapter adapter = new MucBieuDoSSTCAdapter(this, barChartItems1);
            describeBarChartSSTC.setAdapter(adapter);
        }
    }

    private long cmp(Calendar c1, Calendar c2) {
        // Thiết lập giờ, phút, giây và mili giây cho c1
        c1.set(Calendar.HOUR_OF_DAY, 0);
        c1.set(Calendar.MINUTE, 0);
        c1.set(Calendar.SECOND, 0);
        c1.set(Calendar.MILLISECOND, 0);
        // Thiết lập giờ, phút, giây và mili giây cho cal1
        c2.set(Calendar.HOUR_OF_DAY, 0);
        c2.set(Calendar.MINUTE, 0);
        c2.set(Calendar.SECOND, 0);
        c2.set(Calendar.MILLISECOND, 0);
        return c1.compareTo(c2);
    }

    private void handleEvent() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTiet_BieuDoSoSanhThuChi.this, BieuDoSoSanhThuChi.class);
                startActivity(intent);
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ChiTiet_BieuDoSoSanhThuChi.this, MainActivity.class);
                startActivity(intent);
            }
        });

        btnYearCTSSTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.setBackgroundTintList(btnYearCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_on)));
                ViewCompat.setBackgroundTintList(btnMonthCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnWeekCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnDayCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                checkTime=1;
                p=0;
                handleBarChartSSTC();
            }
        });
        btnMonthCTSSTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.setBackgroundTintList(btnYearCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnMonthCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_on)));
                ViewCompat.setBackgroundTintList(btnWeekCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnDayCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                checkTime=2;
                p=0;
                handleBarChartSSTC();
            }
        });
        btnWeekCTSSTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.setBackgroundTintList(btnYearCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnMonthCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnWeekCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_on)));
                ViewCompat.setBackgroundTintList(btnDayCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                checkTime=3;
                p=0;
                handleBarChartSSTC();
            }
        });
        btnDayCTSSTC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.setBackgroundTintList(btnYearCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnMonthCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnWeekCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_off)));
                ViewCompat.setBackgroundTintList(btnDayCTSSTC,
                        ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(),
                                R.color.bg_button_on)));
                checkTime=4;
                p=0;
                handleBarChartSSTC();
            }
        });
        describeBarChartSSTC.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                p = position;
                handleBarChartSSTC();
                describeBarChartSSTC.setSelectionFromTop(p, view.getTop());
            }
        });
    }
    private void initView() {
        db = new SQLiteHelper(getApplicationContext());
        listgd = db.getAllGD("");

        back = findViewById(R.id.back);
        home = findViewById(R.id.home);

        barChartSSTC = findViewById(R.id.bar_chart_sstc);
        describeBarChartSSTC = findViewById(R.id.describe_bar_chart_sstc);

        btnYearCTSSTC = findViewById(R.id.btn_year_ctsstc);
        btnMonthCTSSTC = findViewById(R.id.btn_month_ctsstc);
        btnWeekCTSSTC = findViewById(R.id.btn_week_ctsstc);
        btnDayCTSSTC = findViewById(R.id.btn_day_ctsstc);

        ViewCompat.setBackgroundTintList(btnYearCTSSTC,
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.bg_button_on)));
        ViewCompat.setBackgroundTintList(btnMonthCTSSTC,
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.bg_button_off)));
        ViewCompat.setBackgroundTintList(btnWeekCTSSTC,
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.bg_button_off)));
        ViewCompat.setBackgroundTintList(btnDayCTSSTC,
                ColorStateList.valueOf(ContextCompat.getColor(this,
                        R.color.bg_button_off)));
        checkTime=1;

        textViewTimeCTSSTC = findViewById(R.id.text_view_time_ctsstc);



    }
}