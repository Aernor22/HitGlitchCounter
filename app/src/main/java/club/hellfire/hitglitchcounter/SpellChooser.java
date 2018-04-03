package club.hellfire.hitglitchcounter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class SpellChooser extends AppCompatActivity {
    private ListView lvAllSpells;
    private ArrayAdapter adapterSpells;
    private ArrayList listAllSpells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_chooser);

        lvAllSpells = (ListView)findViewById(R.id.lvAllSpells);
        listAllSpells = new ArrayList<String>();
        adapterSpells = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,listAllSpells);
        lvAllSpells.setAdapter(adapterSpells);

        try{
            JSONObject parent = new JSONObject(loadJSONFromAsset());
            JSONArray array = parent.getJSONArray("spells");
            for(int i =0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                listAllSpells.add(object.getString("name"));
                Log.d("Name",String.valueOf(listAllSpells.get(i)));
            }

            adapterSpells.notifyDataSetChanged();
        }catch (Exception e){
            Log.d("VISH",e.getMessage());
        }

    }
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
    public void onBackPressed(){
        Intent returnResult = new Intent();
        returnResult.putExtra("result",1);
        setResult(Activity.RESULT_OK,returnResult);
        finish();
    }
}