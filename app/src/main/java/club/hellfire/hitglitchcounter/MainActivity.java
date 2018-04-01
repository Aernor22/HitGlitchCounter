package club.hellfire.hitglitchcounter;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends FragmentActivity implements VSRoll.OnFragmentInteractionListener,AddRoll.OnFragmentInteractionListener,SpellsList.OnFragmentInteractionListener {


    private ViewPager vPager;
    private  ImageButton btnVS;
    private  ImageButton btnAdd;
    private ImageButton btnSpells;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnAdd = (ImageButton) findViewById(R.id.btnTabSum) ;
        btnVS = (ImageButton) findViewById(R.id.btnVs);
        btnSpells = (ImageButton) findViewById(R.id.btnSpell);

        TabChanger tabChanger = new TabChanger(getSupportFragmentManager());
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
    public void onFragmentInteraction(Uri uri) {

    }
}
