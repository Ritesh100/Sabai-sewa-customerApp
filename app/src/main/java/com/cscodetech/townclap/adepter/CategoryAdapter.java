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
import com.cscodetech.townclap.model.CatlistItem;
import com.cscodetech.townclap.retrofit.APIClient;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList<CatlistItem> mCatlist;
    private RecyclerTouchListener listener;
    private String typeview;

    public interface RecyclerTouchListener {
        public void onClickCategoryItem(CatlistItem item, int position);
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

    public CategoryAdapter(Context mContext, ArrayList<CatlistItem> mCatlist, final RecyclerTouchListener listener, String viewtype) {
        this.mContext = mContext;
        this.mCatlist = mCatlist;
        this.listener = listener;
        this.typeview = viewtype;
    }
    public ArrayList<CatlistItem> getmCatlist(){
        return mCatlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;
        if (typeview.equalsIgnoreCase("viewall")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category, parent, false);
        } else if (typeview.equalsIgnoreCase("hairviewall")) {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_haircategory, parent, false);
        }else {
            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_category, parent, false);
        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        CatlistItem category = mCatlist.get(position);
        holder.title.setText(category.getCatName() + "");
        Glide.with(mContext).load(APIClient.baseUrl + "/" + category.getCatImg()).into(holder.thumbnail);
        holder.lvlclick.setOnClickListener(v -> {
            listener.onClickCategoryItem(category, position);
        });
    }

    @Override
    public int getItemCount() {
        return mCatlist.size();
    }
}