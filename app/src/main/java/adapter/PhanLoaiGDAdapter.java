package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.R;

import java.util.List;

import model.GiaoDich;

public class PhanLoaiGDAdapter extends RecyclerView.Adapter<PhanLoaiGDAdapter.ViewHolder>{
    private PLGDListen plgdListen;
    private List<GiaoDich> listgd;

    public PhanLoaiGDAdapter(List<GiaoDich> listgd) {
        this.listgd = listgd;
    }

    public void setPlgdListen(PLGDListen plgdListen) {
        this.plgdListen = plgdListen;
    }

    @NonNull
    @Override
    public PhanLoaiGDAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_listgd,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhanLoaiGDAdapter.ViewHolder holder, int position) {
        GiaoDich giaoDich = listgd.get(position);
        holder.idgd.setText("Mã giao dịch: "+ giaoDich.getID());
        holder.sotien.setText("Số tiền: "+giaoDich.getSotien()+" đồng");
        holder.date.setText("Ngày: "+giaoDich.getDate());
        holder.loaigd.setText("Loại giao dịch: "+giaoDich.getLoaiGD().getNameIcon());
        if(giaoDich.getKieugd()==1) holder.kieugd.setText("Tiền ra");
        else holder.kieugd.setText("Tiền vào");
    }

    @Override
    public int getItemCount() {
        return listgd.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private LinearLayout idxem;
        private TextView idgd,sotien,date,loaigd,kieugd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.idxem = itemView.findViewById(R.id.idxem);
            this.idgd = itemView.findViewById(R.id.idgd);
            this.sotien = itemView.findViewById(R.id.sotien);
            this.date = itemView.findViewById(R.id.date);
            this.loaigd = itemView.findViewById(R.id.loaigd);
            this.kieugd = itemView.findViewById(R.id.kieugd);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            plgdListen.onClickGD(view,getAdapterPosition());
        }
    }
    public interface PLGDListen{
        public void onClickGD(View view, int positon);
    }
}
