package com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Toast;

import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others.Animal;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Fragment.DetailFragment;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Fragment.MenuFragment;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others.Broadcast;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String SAVE_PREF = "save_pref";
    private List<Animal> currentList;
    private final List<List<Animal>> lists = new ArrayList<>();
    private Broadcast broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        gotoMenuFrg(this.currentList, "birds");

        broadcast = new Broadcast();
        IntentFilter intentFilter = new IntentFilter("android.intent.action.PHONE_STATE");
        registerReceiver(broadcast, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcast);
    }

    /**
     * kiểm tra quyền đã được chấp nhận chưa?
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 101 && !(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED)){
            Toast.makeText(this, "Please allow this permission to use features of the app", Toast.LENGTH_SHORT).show();
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Khởi tạo giá trị ban đầu
     */
    public void initView(){
        currentList = new ArrayList<>();
        List<Animal> mammals = new ArrayList<>();
        List<Animal> seas = new ArrayList<>();
        try {
            loadData(currentList, "birds");
            loadData(mammals, "mammals");
            loadData(seas, "seas");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.lists.add(0, this.currentList);
        this.lists.add(1, mammals);
        this.lists.add(2, seas);

        checkPermission();
    }

    /**
     * Xin quyền nếu chưa được cấp quyền
     */
    public void checkPermission(){
        if(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{
                    Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CALL_LOG},
                    101);
        }
    }

    /**
     * Hàm chung đi đến trang fragment muốn đến
     * @param fragment fragment muốn đến
     */
    public void showFrg(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.ln_main, fragment, null).commit();
    }

    public void gotoMenuFrg(List<Animal> list, String animalType){
        this.currentList = list;
        showFrg(new MenuFragment(list, animalType));
    }

    public void gotoDetailFrg(List<Animal> list, Animal animal){
        this.currentList = list;
        showFrg(new DetailFragment(list, animal));
    }

    public List<Animal> getListAnimal(String animalType) {
        switch (animalType){
            case "birds":
                return lists.get(0);
            case "mammals":
                return lists.get(1);
            case "seas":
                return lists.get(2);
        }
        return null;
    }

    /**
     * Tải dữ liệu từ file assets
     * @param list danh sách con vật theo loài
     * @param type loại danh sách con vật
     * @throws IOException loại trừ TH lỗi file
     */
    public void loadData(List<Animal> list, String type) throws IOException {
        //Lấy dữ liệu hình nên động vật
        String[] listPhotos = this.getAssets().list(type+"/photo");
        for (String nameAnimal : listPhotos){
            String name = nameAnimal.substring(3,nameAnimal.indexOf("."));

            InputStream inputStream = this.getAssets().open(type+"/text/txt_"+name+".txt");
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder content = new StringBuilder();
            String string = bf.readLine();
            while (string != null){
                content.append(string).append("\n");
                string = bf.readLine();
            }

            Bitmap photo = BitmapFactory.decodeStream(this.getAssets().open(type+"/photo/bg_"+name+".jpg"));
            Bitmap icon = BitmapFactory.decodeStream(this.getAssets().open(type+"/icon/ic_"+name+".png"));
            String link = type+"/icon/ic_"+name+".png";

            Animal animal = new Animal(name, content.toString(), icon, photo,false, type, link);
            list.add(animal);
        }

        loadChange(list);
    }

    public void loadChange(List<Animal> list){
        SharedPreferences pref = this.getSharedPreferences(SAVE_PREF, MODE_PRIVATE);
        for (Animal animal : list){
            boolean fav = pref.getBoolean(animal.getName(), false);
            String phoneNumber = pref.getString(animal.getName()+"_phone", null);
            animal.setFav(fav);
            animal.setPhone(phoneNumber);
        }
    }
}