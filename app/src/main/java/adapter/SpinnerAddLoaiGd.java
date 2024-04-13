package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.helloworld.R;

import java.util.List;

import model.LoaiGD;

public class SpinnerAddLoaiGd extends BaseAdapter {
    private List<LoaiGD> list;
    private Context context;

    public SpinnerAddLoaiGd(List<LoaiGD> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.spiner_add_loai_gd,parent,false);
        ImageView image = view.findViewById(R.id.image);
        image.setImageResource(list.get(position).getSrcIcon());
        return view;
    }
}
