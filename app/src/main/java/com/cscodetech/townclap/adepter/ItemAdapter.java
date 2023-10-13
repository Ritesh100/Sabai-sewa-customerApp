package com.cscodetech.townclap.adepter;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.ServiceListItem;
import com.cscodetech.townclap.retrofit.APIClient;
import com.cscodetech.townclap.utils.MyDatabase;
import com.cscodetech.townclap.utils.SessionManager;

import java.text.DecimalFormat;
import java.util.List;

import kr.co.prnd.readmore.ReadMoreTextView;

public class ItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<ServiceListItem> mCatlist;
    private RecyclerTouchListener listener;
    SessionManager sessionManager;
    MyDatabase myDatabase;

    public interface RecyclerTouchListener {
        public void onClickItem(String titel, int position);
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder {
        public TextView title;
        public ReadMoreTextView txtDetails;
        public TextView txtRating;
        public TextView txtTotalrating;
        public TextView txtPrice;
        public TextView txtPriced;
        public TextView txtTime;
        public ImageView imgIcon;
        public LinearLayout lvlclick;
        public LinearLayout lvlCart;
        public LinearLayout lvlImage;

        public MyViewHolder1(View view) {
            super(view);
            title = view.findViewById(R.id.txt_title);
            txtDetails = view.findViewById(R.id.txt_details);
            txtRating = view.findViewById(R.id.txt_rating);
            txtTotalrating = view.findViewById(R.id.txt_totalrating);
            txtPrice = view.findViewById(R.id.txt_price);
            txtPriced = view.findViewById(R.id.txt_priced);
            txtTime = view.findViewById(R.id.txt_time);
            imgIcon = view.findViewById(R.id.img_icon);
            lvlclick = view.findViewById(R.id.lvl_itemclick);
            lvlCart = view.findViewById(R.id.lvl_cart);
            lvlImage = view.findViewById(R.id.lvl_image);

        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {
        public TextView title;

        public TextView txtRating;
        public ReadMoreTextView txtDetails;
        public TextView txtTotalrating;
        public TextView txtPrice;
        public TextView txtPriced;
        public TextView txtTime;
        public LinearLayout lvlclick;
        public LinearLayout lvlCart;
        public LinearLayout lvlImage;

        public MyViewHolder2(View view) {
            super(view);
            title = view.findViewById(R.id.txt_title);
            txtDetails = view.findViewById(R.id.txt_details);
            txtRating = view.findViewById(R.id.txt_rating);
            txtTotalrating = view.findViewById(R.id.txt_totalrating);
            txtPrice = view.findViewById(R.id.txt_price);
            txtPriced = view.findViewById(R.id.txt_priced);
            txtTime = view.findViewById(R.id.txt_time);

            lvlclick = view.findViewById(R.id.lvl_itemclick);
            lvlCart = view.findViewById(R.id.lvl_cart);
            lvlImage = view.findViewById(R.id.lvl_image);

        }
    }


    public class MyViewHolder3 extends RecyclerView.ViewHolder {
        public TextView title;
        public ImageView imgPlay;
        public ReadMoreTextView txtDetails;
        public TextView txtRating;
        public TextView txtTotalrating;
        public TextView txtPrice;
        public TextView txtPriced;
        public TextView txtTime;
        public LinearLayout lvlclick;
        public LinearLayout lvlCart;
        public VideoView videoView;

        public MyViewHolder3(View view) {
            super(view);
            title = view.findViewById(R.id.txt_title);

            imgPlay = view.findViewById(R.id.img_play);
            txtDetails = view.findViewById(R.id.txt_details);
            txtRating = view.findViewById(R.id.txt_rating);
            txtTotalrating = view.findViewById(R.id.txt_totalrating);
            txtPrice = view.findViewById(R.id.txt_price);
            txtPriced = view.findViewById(R.id.txt_priced);
            txtTime = view.findViewById(R.id.txt_time);

            lvlclick = view.findViewById(R.id.lvl_itemclick);
            lvlCart = view.findViewById(R.id.lvl_cart);
            videoView = view.findViewById(R.id.videoView);

        }
    }


    public ItemAdapter(Context mContext, List<ServiceListItem> mCatlist, final RecyclerTouchListener listener) {
        this.mContext = mContext;
        this.mCatlist = mCatlist;
        this.listener = listener;
        sessionManager = new SessionManager(mContext);
        myDatabase = new MyDatabase(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView;

        switch (viewType) {
            case 1:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view1, parent, false);
                return new MyViewHolder1(itemView);
            case 2:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view2, parent, false);
                return new MyViewHolder2(itemView);
            case 3:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view3, parent, false);
                return new MyViewHolder3(itemView);
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_view4, parent, false);
                return new MyViewHolder1(itemView);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        ServiceListItem item = mCatlist.get(position);
        Log.e("Item Number","-->"+position);
        switch (holder.getItemViewType()) {
            case 1:

                MyViewHolder1 holder1 = (MyViewHolder1) holder;
                holder1.title.setText("" + item.getServiceTitle());

                double res = (item.getServicePrice() / 100.0f) * item.getServiceDiscount();
                double pp = item.getServicePrice() - res;
                holder1.txtPriced.setPaintFlags(holder1.txtPriced.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder1.txtPriced.setText("" + item.getServicePrice());
                holder1.txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + new DecimalFormat("##.##").format(pp));

                holder1.txtTime.setText("" + item.getServiceTtken() + "M");
                Glide.with(mContext).load(APIClient.baseUrl + "/" + item.getServiceImg().get(0)).centerCrop().into(holder1.imgIcon);
                holder1.lvlclick.setOnClickListener(v -> listener.onClickItem("category.getCatname()", position));
                holder1.txtDetails.setText("" +Html.fromHtml(item.getServiceSdesc()));


                addCartList(holder1.lvlCart, item);
                break;
            case 2:
                MyViewHolder2 holder2 = (MyViewHolder2) holder;

                holder2.title.setText("" + item.getServiceTitle());

                double res2 = (item.getServicePrice() / 100.0f) * item.getServiceDiscount();
                double pp2 = item.getServicePrice() - res2;
                holder2.txtPriced.setPaintFlags(holder2.txtPriced.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder2.txtPriced.setText("" + item.getServicePrice());
                holder2.txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + new DecimalFormat("##.##").format(pp2));

                holder2.txtTime.setText("" + item.getServiceTtken() + "M");
                multImageList(holder2.lvlImage, mCatlist.get(position).getServiceImg());
                holder2.txtDetails.setText("" +Html.fromHtml(item.getServiceSdesc()));

                addCartList(holder2.lvlCart, item);
                break;
            case 3:
                MyViewHolder3 holder3 = (MyViewHolder3) holder;


                holder3.title.setText("" + item.getServiceTitle());

                double res3 = (item.getServicePrice() / 100.0f) * item.getServiceDiscount();
                double pp3 = item.getServicePrice() - res3;
                holder3.txtPriced.setPaintFlags(holder3.txtPriced.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder3.txtPriced.setText("" + item.getServicePrice());
                holder3.txtPrice.setText(sessionManager.getStringData(SessionManager.currency) + new DecimalFormat("##.##").format(pp3));

                holder3.txtTime.setText("" + item.getServiceTtken() + "M");

                holder3.videoView.setVideoURI(Uri.parse(APIClient.baseUrl + "/" + mCatlist.get(position).getServiceVideo()));

                holder3.videoView.requestFocus();
                holder3.videoView.setOnCompletionListener(mp -> holder3.imgPlay.setVisibility(View.VISIBLE));
                holder3.imgPlay.setOnClickListener(v -> {
                    holder3.videoView.start();
                    holder3.imgPlay.setVisibility(View.GONE);
                });
                holder3.txtDetails.setText("" + Html.fromHtml(item.getServiceSdesc()));

                addCartList(holder3.lvlCart, item);
                break;

            default:
                throw new IllegalStateException("Unexpected value: " + holder.getItemViewType());
        }


    }

    @Override
    public int getItemCount() {
        Log.e("size","--->"+mCatlist.size());
        return mCatlist.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (mCatlist.get(position).getServiceSShow()) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 3;
            default:
                return super.getItemViewType(position);
        }

    }

    private void addCartList(LinearLayout lnrView, ServiceListItem item) {

        lnrView.removeAllViews();
        final int[] count = {0};
        LayoutInflater inflater = LayoutInflater.from(mContext);
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
            } else {
                txtcount.setVisibility(View.VISIBLE);
                txtcount.setText("" + count[0]);
                item.setServiceQty(String.valueOf(count[0]));
                myDatabase.insertData(item);
            }

        });
        imgPlus.setOnClickListener(v -> {

            count[0] = Integer.parseInt(txtcount.getText().toString());
            count[0] = count[0] + 1;
            item.setServiceQty(String.valueOf(count[0]));
            if (count[0] <= Integer.parseInt(item.getServiceMqty())) {
                if (myDatabase.insertData(item)) {
                    txtcount.setText("" + count[0]);

                }
            } else {
                Toast.makeText(mContext, mContext.getResources().getString(R.string.youcantadd), Toast.LENGTH_LONG).show();
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

    private void multImageList(LinearLayout lnrView, List<String> images) {

        lnrView.removeAllViews();
        for (int i = 0; i < images.size(); i++) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            View view = inflater.inflate(R.layout.images, null);
            ImageView itemTitle = view.findViewById(R.id.itemimage);
            Glide.with(mContext).load(APIClient.baseUrl + "/" + images.get(i)).into(itemTitle);

            lnrView.addView(view);
        }
    }


}