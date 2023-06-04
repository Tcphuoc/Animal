package com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Activity.MainActivity;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others.Animal;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.R;

import java.util.List;

public class AnimalAdapter extends RecyclerView.Adapter<AnimalAdapter.ViewHolder> {
    private final Context mContext;
    private final int layout;
    private final List<Animal> list;

    public AnimalAdapter(Context mContext, int layout, List<Animal> list) {
        this.mContext = mContext;
        this.layout = layout;
        this.list = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconAnimal;
        private TextView nameAnimal;
        private ImageView iconLove;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void setAnimal(ImageView icon, TextView name, ImageView iconLove){
            this.iconAnimal = icon;
            this.iconLove = iconLove;
            this.nameAnimal = name;
        }

        public ImageView getIconAnimal() {
            return iconAnimal;
        }

        public TextView getNameAnimal() {
            return nameAnimal;
        }

        public ImageView getIconLove() {
            return iconLove;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.setAnimal(view.findViewById(R.id.iv_icon), view.findViewById(R.id.tv_name_animal), view.findViewById(R.id.iv_love));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.getNameAnimal().setText(list.get(position).getName());
        holder.getIconAnimal().setImageBitmap(list.get(position).getIcon());
        holder.getNameAnimal().setTag(list.get(position));
        if(list.get(position).isFav())
            holder.getIconLove().setVisibility(View.VISIBLE);
        else
            holder.getIconLove().setVisibility(View.GONE);

        holder.itemView.setOnClickListener(v -> {
            setAnimation(v);
            ((MainActivity)mContext).gotoDetailFrg(list,(Animal) holder.getNameAnimal().getTag());
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setAnimation(View view){
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.blur);
        view.startAnimation(animation);
    }
}
