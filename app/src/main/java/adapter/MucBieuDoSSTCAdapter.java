package adapter;

import static android.graphics.Color.rgb;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.helloworld.R;

import java.util.ArrayList;

import model.MucBieuDoSSTC;

public class MucBieuDoSSTCAdapter extends ArrayAdapter<MucBieuDoSSTC> {
    private LinearLayout mucBieuDoSSTC;
    private TextView barChartItemTime, barChartItemIncome, barChartItemExpense, barChartItemProfit, barChartItemLoss;
    private Context context;
    private ArrayList<MucBieuDoSSTC> barChartItems;


    public MucBieuDoSSTCAdapter(@NonNull Context context, ArrayList<MucBieuDoSSTC> barChartItems) {
        super(context, R.layout.muc_bieu_do_sstc, barChartItems);
        this.context = context;
        this.barChartItems = barChartItems;
    }

    @SuppressLint({"DefaultLocale", "MissingInflatedId"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.muc_bieu_do_sstc, null, true);

        mucBieuDoSSTC = view.findViewById(R.id.muc_bieu_do_sstc);
        barChartItemTime = view.findViewById(R.id.bar_chart_item_time_sstc);
        barChartItemIncome = view.findViewById(R.id.bar_chart_item_income_sstc);
        barChartItemExpense = view.findViewById(R.id.bar_chart_item_expense_sstc);
        barChartItemProfit = view.findViewById(R.id.bar_chart_item_profit_sstc);
        barChartItemLoss = view.findViewById(R.id.bar_chart_item_loss_sstc);

        MucBieuDoSSTC m = barChartItems.get(position);

        if(m.isSelected()) {
            mucBieuDoSSTC.setBackgroundColor(rgb(231,234,243));
        }else {
            mucBieuDoSSTC.setBackgroundColor(rgb(248,249,250));
        }

        barChartItemTime.setText(m.getTime());
        barChartItemIncome.setText("Thu nhập: " + String.format("%.2f",m.getIncome()) + " đ");
        barChartItemExpense.setText("Chi phí: " + String.format("%.2f",m.getExpense()) + " đ");
        barChartItemProfit.setText("Lợi nhuận: " + String.format("%.2f",m.getProfit()) + " đ");
        barChartItemLoss.setText("Lỗ: " + String.format("%.2f",m.getLoss()) + " đ");

        return view;
    }

    public MucBieuDoSSTC getItem(int position) {
        return barChartItems.get(position);
    }
}
