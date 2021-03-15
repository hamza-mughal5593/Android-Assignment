package com.renesis.tech.androidassignment.quizgame;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.renesis.tech.androidassignment.quizgame.Utils.BackgroundMusic;

import net.alhazmy13.mediapicker.Image.ImagePicker;

import java.io.ByteArrayOutputStream;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;

public class SelectNameActivity extends AppCompatActivity {
    Button startbtn;
    ImageView emo1, emo2, emo3, emo4, emo5, selectprofile, addprofile;
    CircleImageView profile_image;
    Bitmap bitmap_profile = null;
    Drawable drawable;
    List<String> mPaths;
    EditText name;
    BackgroundMusic backgroundMusic;

    @Override
    protected void onPause() {
        backgroundMusic.pausemusic();
        super.onPause();
    }

    @Override
    protected void onResume() {
        backgroundMusic.playmusic();
        super.onResume();
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_name);

        backgroundMusic = BackgroundMusic.getInstance(this);

        Paper.init(this);
        init();


        Paper.book().write("splash", false);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                }, 1);
            }
        }


        byte[] byteArray = Paper.book().read("profile", null);
        if (byteArray != null) {

            bitmap_profile = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            if (bitmap_profile != null) {
                profile_image.setImageBitmap(bitmap_profile);
            }
        }


        String user_name = Paper.book().read("user_name", "");
        if (!user_name.equalsIgnoreCase("")) {
            name.setText(user_name);
        }

        startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawable != null) {

                    bitmap_profile = ((BitmapDrawable) drawable).getBitmap();
                } else if (mPaths != null) {
                    bitmap_profile = BitmapFactory.decodeFile(mPaths.get(0));
                }


                if (!name.getText().toString().equalsIgnoreCase("")) {
                    if (bitmap_profile != null) {


                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bitmap_profile.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        Paper.book().write("profile", byteArray);


                        Paper.book().write("user_name", name.getText().toString());
                        Intent intent = new Intent(SelectNameActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(SelectNameActivity.this, "Please select profile picture..!", Toast.LENGTH_SHORT).show();

                    }


                } else {
                    Toast.makeText(SelectNameActivity.this, "Please Enter Your Name..!", Toast.LENGTH_SHORT).show();
                }

            }
        });


        emo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveimg(R.drawable.em1);
                profile_image.setImageResource(R.drawable.em1);
            }
        });
        emo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveimg(R.drawable.em2);
                profile_image.setImageResource(R.drawable.em2);
            }
        });
        emo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveimg(R.drawable.em3);
                profile_image.setImageResource(R.drawable.em3);
            }
        });
        emo4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveimg(R.drawable.em4);
                profile_image.setImageResource(R.drawable.em4);
            }
        });
        emo5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveimg(R.drawable.em5);
                profile_image.setImageResource(R.drawable.em5);
            }
        });
        selectprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImagePicker.Builder(SelectNameActivity.this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.PNG)
                        .scale(600, 600)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
            }
        });
        addprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImagePicker.Builder(SelectNameActivity.this)
                        .mode(ImagePicker.Mode.CAMERA_AND_GALLERY)
                        .compressLevel(ImagePicker.ComperesLevel.MEDIUM)
                        .directory(ImagePicker.Directory.DEFAULT)
                        .extension(ImagePicker.Extension.PNG)
                        .scale(600, 600)
                        .allowMultipleImages(false)
                        .enableDebuggingMode(true)
                        .build();
            }
        });

    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImagePicker.IMAGE_PICKER_REQUEST_CODE && resultCode == RESULT_OK) {
            mPaths = data.getStringArrayListExtra(ImagePicker.EXTRA_IMAGE_PATH);


            profile_image.setImageBitmap(BitmapFactory.decodeFile(mPaths.get(0)));

        }


    }

    public void saveimg(int img) {
        drawable = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            drawable = getDrawable(img);
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "PERMISSION_GRANTED", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "PERMISSION_DENIED", Toast.LENGTH_SHORT).show();
                }
            }
            break;
        }
    }

    private void init() {
        startbtn = findViewById(R.id.startbtn);
        emo1 = findViewById(R.id.emo1);
        emo2 = findViewById(R.id.emo2);
        emo3 = findViewById(R.id.emo3);
        emo4 = findViewById(R.id.emo4);
        emo5 = findViewById(R.id.emo5);
        profile_image = findViewById(R.id.profile_image);
        selectprofile = findViewById(R.id.selectprofile);
        addprofile = findViewById(R.id.addprofile);
        name = findViewById(R.id.name);

    }

    @Override
    protected void onStop() {

        super.onStop();
    }
}