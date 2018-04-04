package club.hellfire.hitglitchcounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SpellsList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SpellsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpellsList extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ListView lvSpells;
    private ArrayList spellnames;
    private ArrayAdapter spellAdapter;

    private OnFragmentInteractionListener mListener;
    private Button btnAddSpell;
    public SpellsList() {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpellsList.
     */
    // TODO: Rename and change types and number of parameters
    public static SpellsList newInstance(String param1, String param2) {
        SpellsList fragment = new SpellsList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode,resultCode,data);
        Log.d("RequestCODe", String.valueOf(requestCode));

        Log.d("ResultCODe", String.valueOf(resultCode));
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                updateAdapter();
                Log.d("ResultCODe", String.valueOf(Activity.RESULT_OK));
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }else{
            if(requestCode==4){

            }
        }
    }

    private void updateAdapter(){
        try {
            spellAdapter.clear();
            JSONObject parent = new JSONObject(readFromFile());
            JSONArray array = parent.getJSONArray("spells");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                spellnames.add(object.getString("name"));
            }
            spellAdapter.notifyDataSetChanged();
        }catch (Exception e){
            Log.d("VISH",e.getMessage());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup main  = (ViewGroup)inflater.inflate(R.layout.fragment_spells_list,container,false);

        btnAddSpell = (Button)main.findViewById(R.id.btnAdd);
        btnAddSpell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),SpellChooser.class);
                startActivityForResult(i,1);
            }
        });

        lvSpells = (ListView)main.findViewById(R.id.lvSpells);
        spellnames=new ArrayList();
        spellAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,spellnames);
        lvSpells.setAdapter(spellAdapter);

        updateAdapter();

        lvSpells.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent spell = new Intent(getContext(),ViewSpell.class);
                spell.putExtra("name",(String) lvSpells.getAdapter().getItem(position));
                startActivity(spell);
            }
        });

        registerForContextMenu(lvSpells);
        return main;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.spells_options, menu);
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void remove(int pos){
        ArrayList choosenList = new ArrayList();
        try{
            JSONObject selected = new JSONObject(readFromFile());
            JSONArray choosenArray = selected.getJSONArray("spells");
            choosenArray.remove(pos);
            //Log.d("Without",choosenArray.toString());
            writeToFile(selected);
            updateAdapter();
        }catch (Exception e){
            Log.d("VISH",e.getMessage());
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        switch (item.getItemId()) {
            case R.id.deleteSpell:
                remove(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
