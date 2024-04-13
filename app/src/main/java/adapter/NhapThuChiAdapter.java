package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.helloworld.R;

import java.util.ArrayList;
import java.util.List;

import model.LoaiGD;

public class NhapThuChiAdapter extends RecyclerView.Adapter<NhapThuChiAdapter.ViewHoder>{
    private List<LoaiGD> loaiGDS;
    private NhapThuChiListener nhapThuChiListener;
    private Context mContext;
    public NhapThuChiAdapter(Context context,List<LoaiGD> loaiGDS) {
        this.mContext = mContext;
        loaiGDS.remove(0);
        this.loaiGDS = loaiGDS;
    }

    public void setNhapThuChiListener(NhapThuChiListener nhapThuChiListener) {
        this.nhapThuChiListener = nhapThuChiListener;
    }

    @NonNull
    @Override
    public ViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_phanloai,parent,false);
        return new ViewHoder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder holder,int position) {
        LoaiGD gd = loaiGDS.get(position);
        holder.imageView.setImageResource(gd.getSrcIcon());
        holder.textView.setText(gd.getNameIcon());

    }

    @Override
    public int getItemCount() {
        return loaiGDS.size();
    }

    public  class ViewHoder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        private TextView textView;

        public ViewHoder(@NonNull View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.image);
            this.textView = itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(nhapThuChiListener!=null){
                nhapThuChiListener.onClick(v,getAdapterPosition());
            }
        }
    }
    public interface NhapThuChiListener{
        public void onClick(View view,int postition);
    }
}
