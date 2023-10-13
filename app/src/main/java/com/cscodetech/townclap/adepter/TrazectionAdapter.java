package com.cscodetech.townclap.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.WalletitemItem;
import com.cscodetech.townclap.utils.SessionManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrazectionAdapter extends RecyclerView.Adapter<TrazectionAdapter.BannerHolder> {
    private Context context;
    private List<WalletitemItem> trazection;
    SessionManager sessionManager;
    public TrazectionAdapter(Context context, List<WalletitemItem> mBanner) {
        this.context = context;
        this.trazection = mBanner;
        sessionManager  =new SessionManager(context);
    }

    @NonNull
    @Override
    public BannerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_trazection, parent, false);
        return new BannerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BannerHolder holder, int position) {
        WalletitemItem item=trazection.get(position);
        holder.txtTital.setText(""+item.getMessage());
        holder.txtDate.setText(""+item.getStatus());
        holder.txtAmount.setText(sessionManager.getStringData(SessionManager.currency)+item.getAmt());
    }

    @Override
    public int getItemCount() {

        return trazection.size();
    }

    public class BannerHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_tital)
        TextView txtTital;
        @BindView(R.id.txt_date)
        TextView txtDate;
        @BindView(R.id.txt_amount)
        TextView txtAmount;

        public BannerHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}