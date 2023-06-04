package com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Activity.MainActivity;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Adapter.AnimalAdapter;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others.Animal;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.R;

import java.io.IOException;
import java.util.List;

public class MenuFragment extends Fragment {

    private Context mContext;
    private RecyclerView rvAnimal;
    private DrawerLayout mDrawer;
    private List<Animal> list;
    private String animalType;

    public MenuFragment(List<Animal> list, String animalType) {
        this.list = list;
        this.animalType = animalType;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in);
        rootView.startAnimation(animation);
        initView(rootView);
        return rootView;
    }

    public void initView(View v){
        mDrawer = v.findViewById(R.id.drawer);
        rvAnimal = v.findViewById(R.id.rv_list);

        ImageView iv = v.findViewById(R.id.iv_menu);
        iv.setImageResource(R.drawable.ic_menu);
        iv.setOnClickListener(v1 -> mDrawer.openDrawer(GravityCompat.START));

        v.findViewById(R.id.item_birds).setOnClickListener(v12 -> {
            animalType = "birds";
            try {
                showAnimal(animalType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        v.findViewById(R.id.item_mammals).setOnClickListener(v12 -> {
            animalType = "mammals";
            try {
                showAnimal(animalType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        v.findViewById(R.id.item_seas).setOnClickListener(v12 -> {
            animalType = "seas";
            try {
                showAnimal(animalType);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        if(this.list != null){
            AnimalAdapter animalAdapter = new AnimalAdapter(mContext, R.layout.item_animal, list);
            rvAnimal.setAdapter(animalAdapter);
            mDrawer.closeDrawers();
        }
    }

    public void showAnimal(String animalType) throws IOException {
        list = ((MainActivity)mContext).getListAnimal(animalType);
        if(list != null){
            AnimalAdapter animalAdapter = new AnimalAdapter(mContext, R.layout.item_animal, list);
            rvAnimal.setAdapter(animalAdapter);
            mDrawer.closeDrawers();
        }
    }
}