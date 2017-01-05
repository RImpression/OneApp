package com.example.oneapp;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class AboutAuthorActivity extends BaseActivity {

    ContentLoadingProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_example);

//        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//        collapsingToolbar.setTitle("title2");

        progressBar = (ContentLoadingProgressBar) findViewById(R.id.progressBar);
        Button btnClick = (Button) findViewById(R.id.btnProgress);
        progressBar.show();

    }


    float y1 = 0;
    float y2 = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            y2 = event.getY();
            if(y1 - y2 > 50) {
                Toast.makeText(this, "向上滑", Toast.LENGTH_SHORT).show();
            } else if(y2 - y1 > 50) {
                Toast.makeText(this, "向下滑", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onTouchEvent(event);
    }

    public void showProgress(View v) {
        progressBar.hide();
    }

















    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about_author, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
