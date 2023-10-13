package com.cscodetech.townclap.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.PartnerListDataItem;
import com.cscodetech.townclap.retrofit.APIClient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HearExportAdapter extends RecyclerView.Adapter<HearExportAdapter.MyViewHolder> {
    private Context mContext;
    private List<PartnerListDataItem> mCatlist;
    private RecyclerTouchListener listener;


    public interface RecyclerTouchListener {
        public void onClickPartnerItem(PartnerListDataItem item, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_export)
        ImageView imgExport;
        @BindView(R.id.txt_name)
        TextView txtName;
        @BindView(R.id.txt_subtitle)
        TextView txtSubtitle;
        @BindView(R.id.txt_rating)
        TextView txtRating;
        @BindView(R.id.crd_service)
        CardView crdService;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }
    }

    public HearExportAdapter(Context mContext, List<PartnerListDataItem> mCatlist, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.mCatlist = mCatlist;
        this.listener = listener;

    }
    public List<PartnerListDataItem> getmCatlist(){
        return mCatlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

            itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_haerexport, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        PartnerListDataItem partner = mCatlist.get(position);
        holder.txtName.setText(partner.getName() + "");
        holder.txtSubtitle.setText(partner.getCity() + "");
        holder.txtRating.setText(partner.getRate() + "");
        Glide.with(mContext).load(APIClient.baseUrl + "/" + partner.getPimg()).into(holder.imgExport);
        holder.imgExport.setOnClickListener(v -> {

            listener.onClickPartnerItem(partner, 10);
        });
        holder.crdService.setOnClickListener(v -> {

            listener.onClickPartnerItem(partner, 20);
        });
    }

    @Override
    public int getItemCount() {
        return mCatlist.size();

    }
}