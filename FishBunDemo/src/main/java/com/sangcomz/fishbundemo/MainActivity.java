package com.sangcomz.fishbundemo;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.sangcomz.fishbun.FishBun;
import com.sangcomz.fishbun.define.Define;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Uri> path = new ArrayList<>();
    ImageView imgMain;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    MainAdapter mainAdapter;
    MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgMain = (ImageView) findViewById(R.id.img_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mainController = new MainController(this, imgMain);
        mainAdapter = new MainAdapter(this, mainController, path);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mainAdapter);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent imageData) {
        super.onActivityResult(requestCode, resultCode, imageData);

        switch (requestCode) {
            case Define.ALBUM_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    path = imageData.getParcelableArrayListExtra(Define.INTENT_PATH);
                    mainAdapter.changePath(path);
                    break;
                }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_plus) {

            FishBun.with(MainActivity.this)
                    .setPickerCount(20)
                    .setPickerSpanCount(3)
                    .setActionBarColor(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"))
                    .setActionBarTitleColor(Color.parseColor("#000000"))
                    .textOnImagesSelectionLimitReached("Limit Reached!")
                    .textOnNothingSelected("Nothing Selected")
                    .setArrayPaths(path)
                    .setAlbumSpanCount(2, 4)
                    .setButtonInAlbumActivity(true)
                    .setCamera(true)
                    .setReachLimitAutomaticClose(true)
                    .setAllViewTitle("All")
                    .setActionBarTitle("Image Library")
                    .setHomeAsUpIndicatorDrawable(ContextCompat.getDrawable(this, R.drawable.ic_arrow_back_black_24dp))
                    .setOkButtonDrawable(ContextCompat.getDrawable(this, R.drawable.ic_check_black_24dp))
                    .startAlbum();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}