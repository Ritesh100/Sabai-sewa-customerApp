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
import com.cscodetech.townclap.model.ChildcatItem;
import com.cscodetech.townclap.retrofit.APIClient;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.MyViewHolder> {
    private Context mContext;
    private List<ChildcatItem> childlist;
    private RecyclerTouchListener listener;

    public interface RecyclerTouchListener {
        public void onClickSubCategoryItem(ChildcatItem item, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView thumbnail;
        public LinearLayout lvlclick;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txt_title);
            thumbnail = view.findViewById(R.id.imageView);
            lvlclick = view.findViewById(R.id.lvl_itemclick);

        }
    }

    public SubCategoryAdapter(Context mContext, List<ChildcatItem> mCatlist, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.childlist = mCatlist;
        this.listener = listener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sub_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        ChildcatItem item = childlist.get(position);
        holder.title.setText("" + item.getTitle());
        Glide.with(mContext).load(APIClient.baseUrl + "/" + item.getImg()).thumbnail(Glide.with(mContext).load(R.drawable.ezgifresize)).centerCrop().into(holder.thumbnail);
        holder.lvlclick.setOnClickListener(v -> {
            listener.onClickSubCategoryItem(item, position);
        });
    }

    @Override
    public int getItemCount() {
        return childlist.size();
    }
}