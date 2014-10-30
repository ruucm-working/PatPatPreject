package com.example.picassotest;
 
import java.io.File;
import java.io.FileOutputStream;
 
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
 
import com.squareup.picasso.Callback.EmptyCallback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
 
public class MainActivity extends ActionBarActivity{
 
 ImageView imageView;
 ProgressBar progressBar;
 
 String currentUrl = "http://i.imgur.com/DvpvklR.png";
 
 @Override
 protected void onCreate(Bundle savedInstanceState) {
 super.onCreate(savedInstanceState);
 setContentView(R.layout.image_detail); 
 
 imageView = (ImageView) findViewById(R.id.image);
 
 progressBar = (ProgressBar) findViewById(R.id.loading);
 
 Picasso.with(this)
 .load(currentUrl)
 .error(R.drawable.error_detail)
 .into(imageView, new EmptyCallback() {
 @Override public void onSuccess() {
 progressBar.setVisibility(View.GONE);
 } 
 @Override
 public void onError() {
 progressBar.setVisibility(View.GONE);
 } 
 });
 
 Picasso.with(this)
 .load(currentUrl)
 .into(target);
 
 }
 
 private Target target = new Target() {
 @Override
 public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
 new Thread(new Runnable() {
 @Override
 public void run() {           
 
 File file = new File(Environment.getExternalStorageDirectory().getPath() +"/actress_wallpaper.jpg");
 try 
 {
 file.createNewFile();
 FileOutputStream ostream = new FileOutputStream(file);
 bitmap.compress(CompressFormat.JPEG, 75, ostream);
 ostream.close();
 } 
 catch (Exception e) 
 {
 e.printStackTrace();
 }
 
 }
 }).start();
 }
 @Override
 public void onBitmapFailed(Drawable errorDrawable) {
 }
 
 @Override
 public void onPrepareLoad(Drawable placeHolderDrawable) {
 if (placeHolderDrawable != null) {
 }
 }
 };
 
}