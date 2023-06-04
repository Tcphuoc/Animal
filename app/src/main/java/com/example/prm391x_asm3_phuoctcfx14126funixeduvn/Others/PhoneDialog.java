package com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Activity.MainActivity;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others.Animal;
import com.example.prm391x_asm3_phuoctcfx14126funixeduvn.R;

public class PhoneDialog extends Dialog {
    private final Context mContext;
    private final Animal animal;
    private String phone;
    private String prePhone;
    private final PhoneDialogListener listener;

    public PhoneDialog(@NonNull Context context, Animal animal, PhoneDialogListener phoneDialogListener) {
        super(context);
        this.mContext = context;
        this.animal = animal;
        this.listener = phoneDialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_dialog);
        initView();
    }

    public void initView(){
        SharedPreferences pref = mContext.getSharedPreferences(MainActivity.SAVE_PREF, Context.MODE_PRIVATE);
        ImageView icon = findViewById(R.id.iv_image_dialog);
        EditText number = findViewById(R.id.edt_phone);
        Button save = findViewById(R.id.bt_save);
        Button cancel = findViewById(R.id.bt_cancel);

        icon.setImageBitmap(animal.getIcon());

        if (animal.getPhone() != null){
            number.setText(animal.getPhone());
            prePhone = animal.getPhone();
        }

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void
            afterTextChanged(Editable s) {
                phone = number.getText().toString();
            }
        });

        save.setOnClickListener(v -> {
            this.dismiss();
            listener.onPositiveClick(phone);
            animal.setPhone(phone);
            pref.edit().putString(animal.getName()+"_phone", animal.getPhone()).apply();
            pref.edit().putString(animal.getPhone(), animal.getLink()).apply();
            if(!phone.equals(prePhone)){
                pref.edit().remove(prePhone).apply();
            }
        });

        cancel.setOnClickListener(v -> {
            this.dismiss();
            listener.onNegativeClick();
        });
    }

    public interface PhoneDialogListener{
        void onPositiveClick(String phone);
        void onNegativeClick();
    }
}
