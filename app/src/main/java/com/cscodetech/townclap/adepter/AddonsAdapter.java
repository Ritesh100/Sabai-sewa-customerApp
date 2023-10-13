package com.cscodetech.townclap.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.AddOnDataItem;
import com.cscodetech.townclap.retrofit.APIClient;

import java.util.ArrayList;

public class AddonsAdapter extends RecyclerView.Adapter<AddonsAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<AddOnDataItem> mCatlist;
    private RecyclerTouchListener listener;


    public interface RecyclerTouchListener {
        public void onClickAddonsItem(String titel, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail ;
        public LinearLayout lvlclick;
        public LinearLayout imgSelect;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_title);
            thumbnail = view.findViewById(R.id.imageView);
            imgSelect = view.findViewById(R.id.img_select);
            lvlclick = view.findViewById(R.id.lvl_itemclick);
        }
    }

    public AddonsAdapter(Context mContext, ArrayList<AddOnDataItem> mCatlist, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.mCatlist = mCatlist;
        this.listener = listener;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_addone, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {


        final AddOnDataItem model = mCatlist.get(position);
        Glide.with(mContext).load(APIClient.baseUrl + "/" + model.getImg()).into(holder.thumbnail);
        holder.title.setText(model.getTitle()+"");
        holder.lvlclick.setOnClickListener(v -> {
            model.setSelected(!model.isSelected());
            if (model.isSelected()) {
                holder.imgSelect.setVisibility(View.VISIBLE);
            } else {
                holder.imgSelect.setVisibility(View.GONE);
            }
            listener.onClickAddonsItem("category.getCatname()", position);
        });
    }

    @Override
    public int getItemCount() {
        return mCatlist.size();
    }
    public ArrayList<AddOnDataItem> getSelectItem(){
        return  mCatlist;
    }
}