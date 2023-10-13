package com.cscodetech.townclap.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cscodetech.townclap.R;
import com.cscodetech.townclap.model.TestimonialItem;
import com.cscodetech.townclap.retrofit.APIClient;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context mContext;
    private List<TestimonialItem> mTestimonial;


    public interface RecyclerTouchListener {
        public void onClickUserItem(String titel, int position);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtDesignetion;
        public CircleImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txt_name);
            txtDesignetion = (TextView) view.findViewById(R.id.txt_designetion);
            thumbnail = view.findViewById(R.id.profile_image);
        }
    }

    public UserAdapter(Context mContext, List<TestimonialItem> mTestimonial) {
        this.mContext = mContext;
        this.mTestimonial = mTestimonial;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        TestimonialItem testimonial = mTestimonial.get(position);
        holder.txtName.setText(testimonial.getTitle());
        holder.txtDesignetion.setText(testimonial.getComment());
        Glide.with(mContext).load(APIClient.baseUrl + "/" + testimonial.getImg()).into(holder.thumbnail);

    }

    @Override
    public int getItemCount() {
        return mTestimonial.size();
    }
}