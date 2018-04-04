package club.hellfire.hitglitchcounter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class AddSpell extends AppCompatActivity {

    private TextView tvName;
    private TextView tvPage;
    private TextView tvCategory;
    private TextView tvDamage;
    private TextView tvDesc;
    private TextView tvDuration;
    private TextView tvDv;
    private TextView tvRange;
    private TextView tvType;

    private Button btnAdd;

    private int position;


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("spellsJSON");
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spell);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        getWindow().setLayout((int) (dm.widthPixels * 0.70), (int) (dm.heightPixels * 0.50));

        WindowManager.LayoutParams wlp = getWindow().getAttributes();
        wlp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        wlp.dimAmount = (float) 0.6;
        getWindow().setAttributes(wlp);

        btnAdd = (Button)findViewById(R.id.btnAddSpell);
        tvName = (TextView)findViewById(R.id.tvName);
        tvPage = (TextView)findViewById(R.id.tvPage);
        tvCategory = (TextView)findViewById(R.id.tvCategoria);
        tvDamage = (TextView)findViewById(R.id.tvDamage);
        tvDesc = (TextView)findViewById(R.id.tvDescription);
        tvDuration =(TextView)findViewById(R.id.tvDuration);
        tvDv = (TextView)findViewById(R.id.tvDV);
        tvRange = (TextView)findViewById(R.id.tvRange);
        tvType = (TextView)findViewById(R.id.tvType);

        String name = getIntent().getExtras().getString("name");

        try {

            JSONObject parent = new JSONObject(loadJSONFromAsset());
            JSONArray array = parent.getJSONArray("spells");
            for(int i =0;i<array.length();i++){
                JSONObject aux = array.getJSONObject(i);
                if(aux.getString("name").equals(name)){
                    position = i;
                }
            }

            JSONObject object = array.getJSONObject(position);
            tvName.setText(object.getString("name"));
            tvPage.setText(object.getString("page"));
            tvCategory.setText(object.getString("category"));
            tvDamage.setText(object.getString("damage"));
            tvDesc.setText(object.getString("descriptor"));
            tvDuration.setText(object.getString("duration"));
            tvDv.setText(object.getString("dv"));
            tvRange.setText(object.getString("range"));
            tvType.setText(object.getString("type"));

            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }catch (Exception e){
            Log.d("VISH",e.getMessage());
        }



    }
}
