package md.alishop.enter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
    public  void prose(View pros)
    {
        Intent intent =new Intent(  Menu.this,Pros.class);
        startActivity(intent);
        onStop();

    }

}
