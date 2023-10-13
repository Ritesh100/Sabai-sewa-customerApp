package com.cscodetech.townclap.adepter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cscodetech.townclap.R;
import com.cscodetech.townclap.activity.CartActivity;
import com.cscodetech.townclap.activity.HomeActivity;
import com.cscodetech.townclap.model.ServiceListItem;
import com.cscodetech.townclap.utils.MyDatabase;
import com.cscodetech.townclap.utils.SessionManager;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartHolder> {
    private Context context;
    private List<ServiceListItem> mBanner;
    SessionManager sessionManager;
    MyDatabase myDatabase;

    public CartAdapter(Context context, List<ServiceListItem> mBanner) {
        this.context = context;
        this.mBanner = mBanner;
        sessionManager = new SessionManager(context);
        myDatabase = new MyDatabase(context);
    }

    @NonNull
    @Override
    public CartHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_cart_item, parent, false);
        return new CartHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartHolder holder, int position) {
        ServiceListItem item = mBanner.get(position);
        holder.txtTitle.setText("" + item.getServiceTitle());
        double res = (item.getServicePrice() / 100.0f) * item.getServiceDiscount();
        double pp=item.getServicePrice()-res;
        holder.txtPriced.setText(sessionManager.getStringData(SessionManager.currency) + new DecimalFormat("##.##").format(pp));
        holder.txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + item.getServicePrice());
        holder.txtPrice.setPaintFlags(holder.txtPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        addCartList(holder.lvlCart, item);


    }

    @Override
    public int getItemCount() {
        return mBanner.size();

    }

    public class CartHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_title)
        TextView txtTitle;
        @BindView(R.id.lvl_cart)
        LinearLayout lvlCart;
        @BindView(R.id.txt_price)
        TextView txtPrice;
        @BindView(R.id.txt_priced)
        TextView txtPriced;

        public CartHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private void addCartList(LinearLayout lnrView, ServiceListItem item) {
        lnrView.removeAllViews();


        final int[] count = {0};
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_pluse_minus, null);
        TextView txtcount = view.findViewById(R.id.txtcount);

        LinearLayout lvlAddremove = view.findViewById(R.id.lvl_addremove);
        LinearLayout lvlAddcart = view.findViewById(R.id.lvl_addcart);
        LinearLayout imgMins = view.findViewById(R.id.img_mins);
        LinearLayout imgPlus = view.findViewById(R.id.img_plus);


        int qrt = myDatabase.getCard(item.getServiceId());
        if (qrt != -1) {
            count[0] = qrt;
            txtcount.setText("" + count[0]);
            lvlAddremove.setVisibility(View.VISIBLE);
            lvlAddcart.setVisibility(View.GONE);
        } else {
            lvlAddremove.setVisibility(View.GONE);
            lvlAddcart.setVisibility(View.VISIBLE);
        }


        imgMins.setOnClickListener(v -> {
            count[0] = Integer.parseInt(txtcount.getText().toString());
            count[0] = count[0] - 1;
            if (count[0] <= 0) {
                txtcount.setText("" + count[0]);
                lvlAddremove.setVisibility(View.GONE);
                lvlAddcart.setVisibility(View.VISIBLE);
                myDatabase.deleteRData(item.getServiceCatId(), item.getServiceId());
                mBanner.remove(item);

                notifyDataSetChanged();
                if (mBanner.isEmpty()) {
                    context.startActivity(new Intent(context, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                }
            } else {
                txtcount.setVisibility(View.VISIBLE);
                txtcount.setText("" + count[0]);
                item.setServiceQty(String.valueOf(count[0]));
                myDatabase.insertData(item);
                Log.e("Postion","-->"+mBanner.indexOf(item.getServiceId()));
                mBanner.set(mBanner.indexOf(item),item);

            }
            CartActivity.getInstance().cardData(mBanner);


        });
        imgPlus.setOnClickListener(v -> {

            count[0] = Integer.parseInt(txtcount.getText().toString());
            count[0] = count[0] + 1;


            if (count[0] <= Integer.parseInt(item.getServiceMqty())) {
                if (myDatabase.insertData(item)) {
                    item.setServiceQty(String.valueOf(count[0]));
                    txtcount.setText("" + count[0]);
                    Log.e("Postion","-->"+mBanner.indexOf(item.getServiceId()));
                    mBanner.set(mBanner.indexOf(item),item);
                    CartActivity.getInstance().cardData(mBanner);

                }

            } else {
                Toast.makeText(context, context.getResources().getString(R.string.youcantadd), Toast.LENGTH_LONG).show();
            }

        });
        lvlAddcart.setOnClickListener(v -> {

            count[0] = Integer.parseInt(txtcount.getText().toString());
            count[0] = count[0] + 1;
            item.setServiceQty(String.valueOf(count[0]));
            if (myDatabase.insertData(item)) {
                txtcount.setText("" + count[0]);
                lvlAddcart.setVisibility(View.GONE);
                lvlAddremove.setVisibility(View.VISIBLE);
            }
        });
        lnrView.addView(view);
    }

}