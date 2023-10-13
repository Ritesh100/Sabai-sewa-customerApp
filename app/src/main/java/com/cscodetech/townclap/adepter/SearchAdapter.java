package com.cscodetech.townclap.adepter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.SearchChildcatdataItem;
import com.cscodetech.townclap.retrofit.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    private List<SearchChildcatdataItem> mCatlist;
    private RecyclerTouchListener listener;


    public interface RecyclerTouchListener {
        public void onClickSearch(SearchChildcatdataItem dataItem, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public ImageView imgItem;
        public LinearLayout lvlclick;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_title);
            imgItem = view.findViewById(R.id.image_service);
            lvlclick = view.findViewById(R.id.lvl_itemclick);
        }
    }

    public SearchAdapter(List<SearchChildcatdataItem> mCatlist, final RecyclerTouchListener listener) {

        this.mCatlist = mCatlist;
        this.listener = listener;


    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service_view, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        SearchChildcatdataItem category = mCatlist.get(position);
        holder.title.setText(category.getTitle() + "");
        Picasso.get().load(APIClient.baseUrl + "/" + category.getImg()).into(holder.imgItem);

        holder.lvlclick.setOnClickListener(v -> {

            listener.onClickSearch(category, position);
        });
    }

    @Override
    public int getItemCount() {
        return mCatlist.size();


    }
}