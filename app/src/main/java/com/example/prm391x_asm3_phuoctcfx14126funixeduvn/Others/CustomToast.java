package com.example.prm391x_asm3_phuoctcfx14126funixeduvn.Others;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.Toast;

public class CustomToast extends Toast {
    public CustomToast(Context context) {
        super(context);
    }

    public static void makeText(Context context, Bitmap image, int duration){
        Toast toast = new Toast(context);

        ImageView imageView = new ImageView(context);
        imageView.setImageBitmap(image);
        toast.setView(imageView);
        toast.setDuration(duration);

        toast.show();
    }
}
