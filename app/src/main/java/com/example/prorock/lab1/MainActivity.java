package com.example.prorock.lab1;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView easterEggView;
    TextView authorNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        easterEggView = (ImageView)findViewById(R.id.eggImageView);
        authorNameTextView = (TextView)findViewById(R.id.authorInfoTextView);
        easterEggView.setVisibility(View.INVISIBLE);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.menu_author:
                authorNameTextView.setText(R.string.author_info);
                return true;
            case R.id.menu_refresh:
                authorNameTextView.setText("");
                easterEggView.setVisibility(View.INVISIBLE);
                return true;
            case R.id.menu_egg:
                authorNameTextView.setVisibility(View.VISIBLE);
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
        authorNameTextView.setText(R.string.author_info);
    }
}
