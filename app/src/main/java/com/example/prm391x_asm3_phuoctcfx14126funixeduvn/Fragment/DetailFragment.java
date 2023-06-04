package com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Activity.MainActivity;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Adapter.DetailPageAdapter;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others.Animal;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.R;

import java.util.List;

public class DetailFragment extends Fragment {

    private List<Animal> animalList;
    private final Animal animal;
    private Context mContext;

    public DetailFragment(List<Animal> animalList, Animal animal) {
        this.animalList = animalList;
        this.animal = animal;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.slide_in);
        rootView.startAnimation(animation);
        initView(rootView);
        return rootView;
    }

    public void initView(View v){
        ImageView back = v.findViewById(R.id.iv_menu);
        back.setImageResource(R.drawable.ic_back);
        back.setOnClickListener(v12 -> {
            ((MainActivity)mContext).gotoMenuFrg(animalList, animal.getAnimalType());
            saveData();
        });

        ViewPager viewPager = v.findViewById(R.id.pv_info);
        DetailPageAdapter detailPageAdapter = new DetailPageAdapter(animalList, mContext);
        viewPager.setAdapter(detailPageAdapter);
        viewPager.setCurrentItem(animalList.indexOf(animal));
        animalList = detailPageAdapter.getList();
    }

    public void saveData(){
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF, Context.MODE_PRIVATE);
        for(Animal item : animalList){
            pref.edit().putBoolean(item.getName(), item.isFav()).apply();
            pref.edit().putString(item.getName()+"_phone", item.getPhone()).apply();
            pref.edit().putString(item.getPhone(), item.getLink()).apply();
        }
    }
}