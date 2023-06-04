package com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others.Animal;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others.PhoneDialog;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.R;

import java.util.List;

public class DetailPageAdapter extends PagerAdapter {
    private final List<Animal> list;
    private final Context mContext;
    private PhoneDialog.PhoneDialogListener listener;

    public DetailPageAdapter(List<Animal> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_detail, container, false);
        Animal item = list.get(position);

        //View trong item_detail
        TextView title = view.findViewById(R.id.tv_title_animal);
        TextView content = view.findViewById(R.id.tv_content);
        ImageView bg = view.findViewById(R.id.iv_bg);
        ImageView unlike = view.findViewById(R.id.iv_emotion);
        ImageView like = view.findViewById(R.id.iv_emotion_like);
        ImageView phone = view.findViewById(R.id.iv_phone);
        TextView phoneNumber = view.findViewById(R.id.tv_phone_number);

        //Cài đặt giá trị view
        title.setText(item.getName());
        content.setText(item.getContent());
        bg.setImageBitmap(item.getPhoto());
        container.addView(view);
        if(item.isFav()){
            like.setVisibility(View.VISIBLE);
            unlike.setVisibility(View.GONE);
        } else {
            like.setVisibility(View.GONE);
            unlike.setVisibility(View.VISIBLE);
        }
        phoneNumber.setText(item.getPhone());

        unlike.setOnClickListener(v -> {
            like.setVisibility(View.VISIBLE);
            unlike.setVisibility(View.GONE);
            item.setFav(true);
        });

        like.setOnClickListener(v -> {
            like.setVisibility(View.GONE);
            unlike.setVisibility(View.VISIBLE);
            item.setFav(false);
        });

        phone.setOnClickListener(v -> {
            listener = new PhoneDialog.PhoneDialogListener() {
                @Override
                public void onPositiveClick(String phone) {
                    Toast.makeText(mContext, "Edit successfully", Toast.LENGTH_SHORT).show();
                    phoneNumber.setText(phone);
                }

                @Override
                public void onNegativeClick() {
                    Toast.makeText(mContext, "Edit failure", Toast.LENGTH_SHORT).show();
                }
            };
            PhoneDialog phoneDialog = new PhoneDialog(mContext, item, listener);
            phoneDialog.show();
        });

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position,Object object) {
        container.removeView((View) object);
    }

    public List<Animal> getList(){
        return this.list;
    }

}
