package club.hellfire.hitglitchcounter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends FragmentActivity implements VSRoll.OnFragmentInteractionListener,AddRoll.OnFragmentInteractionListener,SpellsList.OnFragmentInteractionListener {


    private ViewPager vPager;
    private  ImageButton btnVS;
    private  ImageButton btnAdd;
    private ImageButton btnSpells;
    private TabChanger tabChanger;

    private void colorChange(int button){
        switch (button){
            case 0:{
                btnAdd.setBackgroundColor(Color.LTGRAY);
                btnVS.setBackgroundColor(getResources().getColor(R.color.googleBack));
                btnSpells.setBackgroundColor(getResources().getColor(R.color.googleBack));
                break;
            }
            case 1:{
                btnAdd.setBackgroundColor(getResources().getColor(R.color.googleBack));
                btnVS.setBackgroundColor(Color.LTGRAY);
                btnSpells.setBackgroundColor(getResources().getColor(R.color.googleBack));
                break;
            }
            case 2:{
                btnAdd.setBackgroundColor(getResources().getColor(R.color.googleBack));
                btnVS.setBackgroundColor(getResources().getColor(R.color.googleBack));
                btnSpells.setBackgroundColor(Color.LTGRAY);
                break;
            }
        }
    }
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("choosenSpells");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            Log.d("VISHLOAD",ex.getMessage());
            return null;
        }
        return json;
    }

    private String getFragmentTag(int viewPagerId, int fragmentPosition)
    {
        return "android:switcher:" + R.id.pager + ":" + fragmentPosition;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("VISHLOAD",getFragmentTag(R.id.pager,2));
        String tag = getFragmentTag(R.id.pager,2);
        super.onActivityResult(requestCode, resultCode, data);
        //Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        //fragment.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        String path = Environment.getExternalStorageDirectory()+ File.separator +"shadowrunCounter"+ File.separator;
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }

        File user = new File(dir,"choosenSpells.JSON");
        if(!user.exists()){
            String base = loadJSONFromAsset();
            try{
                FileOutputStream fop = new FileOutputStream(user);
                fop.write(base.getBytes());
                fop.close();
            }catch (Exception e){

            }
        }

        btnAdd = (ImageButton) findViewById(R.id.btnTabSum) ;
        btnVS = (ImageButton) findViewById(R.id.btnVs);
        btnSpells = (ImageButton) findViewById(R.id.btnSpell);

        tabChanger = new TabChanger(getSupportFragmentManager());
        vPager = (ViewPager)findViewById(R.id.pager);
        vPager.setAdapter(tabChanger);
        vPager.setCurrentItem(1);
        colorChange(1);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vPager.setCurrentItem(0);
                colorChange(0);
            }
        });

        btnVS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vPager.setCurrentItem(1);
                colorChange(1);
            }
        });

        btnSpells.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vPager.setCurrentItem(2);
                colorChange(2);
            }
        });

        ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                switch (vPager.getCurrentItem()){
                    case 0:{
                        colorChange(0);
                        break;
                    }
                    case 1:{
                        colorChange(1);
                        break;
                    }
                    case 2:{
                        colorChange(2);
                        break;
                    }
                    default:{
                        btnAdd.setBackgroundColor(getResources().getColor(R.color.googleBack));
                        btnVS.setBackgroundColor(Color.LTGRAY);
                        btnSpells.setBackgroundColor(getResources().getColor(R.color.googleBack));
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };
        vPager.addOnPageChangeListener(pageChangeListener);

    }

    @Override
    public void onBackPressed() {
        if (vPager.getCurrentItem() == 1) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            vPager.setCurrentItem(1);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
