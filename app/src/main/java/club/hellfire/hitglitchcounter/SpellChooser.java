package club.hellfire.hitglitchcounter;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

class Item{
    private String name;
    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return name;
    }
    public String toString(){
        return name;
    }
}

public class SpellChooser extends AppCompatActivity {
    private ListView lvAllSpells;
    private ArrayAdapter adapterSpells;
    private ArrayList listAllSpells;
    private TextView edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell_chooser);
        edtSearch = (TextView)findViewById(R.id.edtSearch);
        lvAllSpells = (ListView)findViewById(R.id.lvAllSpells);
        listAllSpells = new ArrayList<String>();
        adapterSpells = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_list_item_1,listAllSpells);
        lvAllSpells.setAdapter(adapterSpells);



        try{
            JSONObject parent = new JSONObject(loadJSONFromAsset());
            JSONArray array = parent.getJSONArray("spells");
            for(int i =0;i<array.length();i++){
                JSONObject object = array.getJSONObject(i);
                listAllSpells.add(object.getString("name"));
            }
            adapterSpells.notifyDataSetChanged();

            edtSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    adapterSpells.getFilter().filter(charSequence);
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            lvAllSpells.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getBaseContext(),AddSpell.class);
                    intent.putExtra("name",(String) lvAllSpells.getAdapter().getItem(i));
                    startActivity(intent);
                }
            });

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
