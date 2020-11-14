package com.mobilefintech09.dripbank;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IconRecyclerAdapter extends RecyclerView.Adapter<IconRecyclerAdapter.ViewHolder> {

    private final List<Integer> imageList;
    private final List<String> txtList;
    private final List<String> tagList;

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;

    public IconRecyclerAdapter(Context context, List<Integer> iconList, List<String> textList, List<String> tagList) {
        mContext = context;
        imageList = iconList;
        txtList = textList;
        this.tagList = tagList;
        mLayoutInflater = LayoutInflater.from(mContext);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView =  mLayoutInflater.inflate(R.layout.icons_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImageView.setImageResource(imageList.get(position));
        holder.mTextView.setText(txtList.get(position));
        holder.mImageView.setTag(tagList.get(position));
        holder.mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        public final ImageView mImageView;
        public final TextView mTextView;
        public int mCurrentPosition;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.image);
            mTextView =itemView.findViewById(R.id.text);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent;

                    switch (mImageView.getTag().toString())
                    {
                        case "profile":
                        intent = new Intent(mContext, ProfileActivity.class);
                        intent.putExtra("Current Position: ", 1);
                        mContext.startActivity(intent);
                            break;
                        case "messenger" :
                            intent = new Intent(mContext, ChatActivity.class);
                            intent.putExtra("Current Position: ", 2);
                            mContext.startActivity(intent);
                            break;
                        case "deposit" :
                            intent = new Intent(mContext, DepositActivity.class);
                            intent.putExtra("Current Position: ", 3);
                            mContext.startActivity(intent);
                            break;
                        case "withdraw" :
                            intent = new Intent(mContext, WithdrawActivity.class);
                            intent.putExtra("Current Position: ", 4);
                            mContext.startActivity(intent);
                            break;
                        case "transfer" :
                            intent = new Intent(mContext, TransferActivity.class);
                            intent.putExtra("Current Position: ", 5);
                            mContext.startActivity(intent);
                            break;
                        case "faq" :
                            intent = new Intent(mContext, FAQActivity.class);
                            intent.putExtra("Current Position: ", 5);
                            mContext.startActivity(intent);

                         break;
                    }
                }
            });
        }
    }

}