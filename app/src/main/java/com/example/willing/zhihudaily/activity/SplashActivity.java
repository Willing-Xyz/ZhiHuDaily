package com.example.willing.zhihudaily.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.willing.zhihudaily.R;
import com.example.willing.zhihudaily.model.SplashImageEntity;
import com.example.willing.zhihudaily.utils.HttpUtils;
import com.example.willing.zhihudaily.utils.Utils;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashActivity extends BaseActivity
{

    private static final String SPLASH_IMAGE_FILENAME = "splash_image";
    private static final String SPLASH_IMAGE_URL_FILENAME = "splash_url";
    private static final String SPLASH_AUTHOR_FILENAME = "splash_author";
    private static final String SPLASH_IMAGE_URL = "http://news-at.zhihu.com/api/4/start-image/";

    private ImageView mSplashImageView;
    private TextView mSplashAuthorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getSupportActionBar().hide();

        setContentView(R.layout.activity_splash);

        initView();
        displaySplashImage();
        downloadSplashImage();
    }

    private void initView() {

        mSplashImageView = (ImageView) findViewById(R.id.splashImageView);
        mSplashAuthorView = (TextView) findViewById(R.id.splashAuthorView);
    }

    private void displaySplashImage() {

        File splashImageFile = new File(getCacheDir(), SPLASH_IMAGE_FILENAME);
        File splashAuthorFile = new File(getCacheDir(), SPLASH_AUTHOR_FILENAME);

        if (splashImageFile.exists())
        {
            Bitmap splashBitmap = BitmapFactory.decodeFile(splashImageFile.getAbsolutePath());
            mSplashImageView.setImageBitmap(splashBitmap);

            if (splashAuthorFile.exists())
            {
                String author = readLine(splashAuthorFile);
                mSplashAuthorView.setText(author);
            }
        }
        else
        {
            mSplashImageView.setImageResource(R.mipmap.splash);
        }


        // 缩放动画
        mSplashImageView.animate().setDuration(300).scaleX(1.1f).scaleY(1.1f)
                .setListener(new AnimatorListenerAdapter() {

                    @Override
                    public void onAnimationEnd(Animator animation) {

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);

                        overridePendingTransition(0, 0);

                        finish();
                    }

                }).start();
    }

    private void downloadSplashImage() {

        final OkHttpClient client = HttpUtils.getInstance();

        Point point = Utils.getScreenResolution(this);

        String args = "";
        if (point.x <= 320)
        {
            args = "320*432";
        }
        else if (point.x  <= 480)
        {
            args = "480*728";
        }
        else if (point.x  <= 720)
        {
            args = "720*1184";
        }
        else
        {
            args = "1080*1776";
        }

        Request request = new Request.Builder().url(SPLASH_IMAGE_URL + args).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                Gson gson = new Gson();
                SplashImageEntity splashImageEntity = gson.fromJson(response.body().string(), SplashImageEntity.class);

                if (splashImageEntity != null) {
                    String author = splashImageEntity.getText();
                    String imageUrl = splashImageEntity.getImg();

                    // 如果从服务器获取的url和本地保存的url相同的话，就不再下载。
                    File splashUrl = new File(getCacheDir(), SPLASH_IMAGE_URL_FILENAME);

                    String savedUrl = readLine(splashUrl);

                    if (!imageUrl.equals(savedUrl))
                    {
                        Request downloadReqest = new Request.Builder().url(imageUrl).build();
                        Response downloadResponse = client.newCall(downloadReqest).execute();

                        if (downloadResponse.code() == 200) {
                            File splashFile = new File(getCacheDir(), SPLASH_IMAGE_FILENAME);
                            writeImage(splashFile, downloadResponse.body().byteStream());

                            writeLine(splashUrl, imageUrl);
                        }
                    }

                    File splashTextFile = new File(getCacheDir(), SPLASH_AUTHOR_FILENAME);
                    writeLine(splashTextFile, author);
                }
            }
        });

    }

    // 缓存启动界面的图片
    private void writeImage(File splashFile, InputStream inputStream) {

        BufferedOutputStream out = null;
        try {
            out = new BufferedOutputStream(new FileOutputStream(splashFile));

            byte[] buffer = new byte[2048];
            int readed = 0;
            while ((readed = inputStream.read(buffer)) != -1)
            {
                out.write(buffer, 0, readed);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (out != null)
            {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null)
            {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    // 写入文件中的一行
    private void writeLine(File splashTextFile, String author) {

        Writer writer = null;
        try {
            writer = new FileWriter(splashTextFile);
            writer.write(author);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (writer != null)
            {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 读取文件中的一行
    private String readLine(File splashTextFile) {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(splashTextFile));
            return reader.readLine();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (reader != null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }


}
