package com.cscodetech.townclap.adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.SubcatDatum;
import com.cscodetech.townclap.retrofit.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private List<SubcatDatum> mCatlist;
    private RecyclerTouchListener listener;
    private String typeview;

    public interface RecyclerTouchListener {
        public void onClickServiceItem(SubcatDatum dataItem, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView txtSubtitle;
        public ImageView imgItem;
        public LinearLayout lvlclick;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_title);
            txtSubtitle = (TextView) view.findViewById(R.id.txt_subtitle);
            imgItem = view.findViewById(R.id.image_service);
            lvlclick = view.findViewById(R.id.lvl_itemclick);
        }
    }

    public ServiceAdapter(List<SubcatDatum> mCatlist, final RecyclerTouchListener listener, String typeview) {

        this.mCatlist = mCatlist;
        this.listener = listener;
        this.typeview = typeview;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (typeview.equalsIgnoreCase("viewall")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_service_view, parent, false);
        } else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_service, parent, false);
        }
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        SubcatDatum category = mCatlist.get(position);
        holder.title.setText(category.getTitle() + "");
        holder.txtSubtitle.setText("" + category.getSubtitle());
        Picasso.get().load(APIClient.baseUrl + "/" + category.getImg()).into(holder.imgItem);

        holder.lvlclick.setOnClickListener(v -> {

            listener.onClickServiceItem(category, position);
        });
    }

    @Override
    public int getItemCount() {

        if (mCatlist.size() <= 4 || typeview.equalsIgnoreCase("viewall")) {
            return mCatlist.size();
        } else {
            return 4;
        }

    }
}