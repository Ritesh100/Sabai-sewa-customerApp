package com.cscodetech.townclap.adepter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.ServiceDataItem;
import com.cscodetech.townclap.retrofit.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DynamicAdapter extends RecyclerView.Adapter<DynamicAdapter.MyViewHolder> {

    private List<ServiceDataItem> mCatlist;
    private RecyclerTouchListener listener;
    private String typeview;

    public interface RecyclerTouchListener {
        public void onClickDynamicItem(ServiceDataItem dataItem, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView txtSubtitle;
        public ImageView imgDynamic;
        public LinearLayout lvlclick;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_title);
            txtSubtitle = (TextView) view.findViewById(R.id.txt_subtitle);
            imgDynamic = view.findViewById(R.id.img_daynamic);
            lvlclick = view.findViewById(R.id.lvl_itemclick);
        }
    }

    public DynamicAdapter(List<ServiceDataItem> mCatlist, final RecyclerTouchListener listener, String typeview) {

        this.mCatlist = mCatlist;
        this.listener = listener;
        this.typeview = typeview;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (typeview.equalsIgnoreCase("viewall")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_dynamic_view, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_dynamic, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        ServiceDataItem category = mCatlist.get(position);
        holder.title.setText(category.getTitle() + "");
        holder.txtSubtitle.setText(category.getSubtitle() + "");
        Picasso.get().load(APIClient.baseUrl + "/" + category.getImg()).into(holder.imgDynamic);
        holder.lvlclick.setOnClickListener(v -> {

            listener.onClickDynamicItem(category, position);
        });
    }

    @Override
    public int getItemCount() {
        if (mCatlist.size() <= 4) {
            return mCatlist.size();
        } else {
            return 4;
        }

    }
}