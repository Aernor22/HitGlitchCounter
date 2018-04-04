package club.hellfire.hitglitchcounter;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ViewSpell extends AppCompatActivity {

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
    private Boolean match;
    private ArrayList choosenList;

    public String loadJSONFromAsset(String fileName) {
        String json = null;
        try {
            InputStream is = this.getAssets().open(fileName);
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

    private String readFromFile() {
        StringBuilder ret = new StringBuilder();
        String path = Environment.getExternalStorageDirectory()+ File.separator +"shadowrunCounter"+ File.separator;
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path,"choosenSpells.JSON");
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine())!=null){
                ret.append(line);
                ret.append("\n");
            }
            br.close();
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return ret.toString();
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

        match = false;
        choosenList = new ArrayList();

        btnAdd = (Button)findViewById(R.id.btnAddSpell);
        btnAdd.setVisibility(View.GONE);
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

            JSONObject parent = new JSONObject(loadJSONFromAsset("spellsJSON"));
            JSONArray array = parent.getJSONArray("spells");
            for(int i =0;i<array.length();i++){
                JSONObject aux = array.getJSONObject(i);
                if(aux.getString("name").equals(name)){
                    position = i;
                }
            }

            final JSONObject selected = new JSONObject(readFromFile());
            final JSONArray choosenArray = selected.getJSONArray("spells");
            for(int j = 0;j<choosenArray.length();j++){
                JSONObject obj = choosenArray.getJSONObject(j);
                choosenList.add(obj.getString("name"));
            }

            final JSONObject object = array.getJSONObject(position);

            tvName.setText(object.getString("name"));
            tvPage.setText(object.getString("page"));
            tvCategory.setText(object.getString("category"));
            tvDamage.setText(object.getString("damage"));
            tvDesc.setText(object.getString("descriptor"));
            tvDuration.setText(object.getString("duration"));
            tvDv.setText(object.getString("dv"));
            tvRange.setText(object.getString("range"));
            tvType.setText(object.getString("type"));

        }catch (Exception e){
            Log.d("VISH",e.getMessage());
        }
    }
    private void writeToFile(JSONObject obj){
        String path = Environment.getExternalStorageDirectory()+ File.separator +"shadowrunCounter"+ File.separator;
        File dir = new File(path);
        if(!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(path,"choosenSpells.JSON");
        try{
            FileOutputStream fop = new FileOutputStream(file);
            fop.write(obj.toString().getBytes());
            fop.close();
        }catch (Exception e){

        }
    }
}
