package com.example.explosion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.explosionlib.BooleanFactory;
import com.example.explosionlib.ClickCallBack;
import com.example.explosionlib.ExplosionField;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ExplosionField explosionField = new ExplosionField(this, new BooleanFactory());
        explosionField.setClickListener(clickCallBack);
        explosionField.addListener(findViewById(R.id.tv));
        explosionField.addListener(findViewById(R.id.iv));

    }

    ClickCallBack clickCallBack = new ClickCallBack() {
        @Override
        public void onClick(View view) {
            Toast.makeText(MainActivity.this,"点击了", LENGTH_SHORT).show();
        }
    };
}
