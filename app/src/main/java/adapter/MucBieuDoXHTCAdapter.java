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

import model.MucBieuDoXHTC;

public class MucBieuDoXHTCAdapter extends ArrayAdapter<MucBieuDoXHTC> {
    private LinearLayout mucBieuDoXHTC;
    private TextView pieChartItemLable, pieChartItemPercent, pieChartItemTotalAmount;
    private Context context;
    private ArrayList<MucBieuDoXHTC> pieChartItems;


    public MucBieuDoXHTCAdapter(@NonNull Context context, ArrayList<MucBieuDoXHTC> pieChartItems) {
        super(context, R.layout.muc_bieu_do_xhtc, pieChartItems);
        this.context = context;
        this.pieChartItems = pieChartItems;
    }

    @SuppressLint({"DefaultLocale", "MissingInflatedId"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        @SuppressLint("ViewHolder") View view = inflater.inflate(R.layout.muc_bieu_do_xhtc, null, true);

        mucBieuDoXHTC = view.findViewById(R.id.muc_bieu_do_xhtc);
        pieChartItemLable = view.findViewById(R.id.pie_chart_item_lable_xhtc);
        pieChartItemPercent = view.findViewById(R.id.pie_chart_item_percent_xhtc);
        pieChartItemTotalAmount = view.findViewById(R.id.pie_chart_item_total_amount_xhtc);

        MucBieuDoXHTC m = pieChartItems.get(position);

        if(m.isSelected()) {
            mucBieuDoXHTC.setBackgroundColor(rgb(231,234,243));
        }else {
            mucBieuDoXHTC.setBackgroundColor(rgb(248,249,250));
        }

        pieChartItemLable.setText(m.getLable());
        pieChartItemPercent.setText(String.format("%.2f", m.getPercent()) + " %");
        pieChartItemTotalAmount.setText(String.format("%.2f", m.getTotalAmount()) + " đ");
        pieChartItemLable.setTextColor(m.getColor());
        pieChartItemPercent.setTextColor(m.getColor());
        pieChartItemTotalAmount.setTextColor(m.getColor());

        return view;
    }

    public MucBieuDoXHTC getItem(int position) {
        return pieChartItems.get(position);
    }
}
