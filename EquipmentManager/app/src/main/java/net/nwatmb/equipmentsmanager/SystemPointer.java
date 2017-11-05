package net.nwatmb.equipmentsmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


public class SystemPointer extends Activity {
    public static final String Extra_User_Name = "net.nwatmb.equipmentsmanager.username";
    private String mUserName;

    // UI interface
    private Button mSystem_1;
    private Button mSystem_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_pointer);

        mSystem_1 = (Button) findViewById(R.id.btn_stuff_manager);
        mSystem_2 = (Button) findViewById(R.id.btn_equipment_manager);

        mUserName = getIntent().getStringExtra(Extra_User_Name);

        mSystem_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //say_Var(mUserName);
                Intent intent = new Intent(SystemPointer.this, StuffManagerActivity.class);
                intent.putExtra(SystemPointer.Extra_User_Name,mUserName.toString());
                startActivity(intent);
            }
        });

        mSystem_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SystemPointer.this, EquipmentManager.class);
                intent.putExtra(SystemPointer.Extra_User_Name,mUserName.toString());
                startActivity(intent);
            }
        });




    }

    /**
     *
     */
//    private void say_Var(String s) {
//        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_system_pointer, menu);
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
