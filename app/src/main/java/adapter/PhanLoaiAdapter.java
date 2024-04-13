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

public class PhanLoaiAdapter extends RecyclerView.Adapter<PhanLoaiAdapter.ViewHolder>{
    private Context mContext;
    private List<LoaiGD> list;
    private PhanLoaiListen phanLoaiListen;

    public void setPhanLoaiListen(PhanLoaiListen phanLoaiListen) {
        this.phanLoaiListen = phanLoaiListen;
    }

    public PhanLoaiAdapter(Context mContext, List<LoaiGD> list) {
        this.mContext = mContext;
        this.list = list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_phanloai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LoaiGD hero = list.get(position);
        holder.text.setText(hero.getNameIcon());
        holder.image.setImageResource(hero.getSrcIcon());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView image;
        private TextView text;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            text = itemView.findViewById(R.id.text);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            phanLoaiListen.onClickLoai(v,getAdapterPosition());
        }
    }
    public interface PhanLoaiListen{
        public void onClickLoai(View view,int position);
    }

}
