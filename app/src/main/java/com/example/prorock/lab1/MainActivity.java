package com.example.prorock.lab1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView im = (ImageView)findViewById(R.id.imageView2);
        im.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        TextView sayMyName = (TextView)findViewById(R.id.textView);
        ImageView im = (ImageView)findViewById(R.id.imageView2);
        switch(id){
            case R.id.menu_button1:
                sayMyName.setText(R.string.author_info);
                return true;
            case R.id.menu_button2:
                sayMyName.setText("");
                im.setVisibility(View.INVISIBLE);
                return true;
            case R.id.menu_button3:
                im.setVisibility(View.VISIBLE);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void buttonClicked(View view) {
        TextView sayMyName = (TextView)findViewById(R.id.textView);
        sayMyName.setText(R.string.author_info);
    }
}
