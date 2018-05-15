package club.hellfire.hitglitchcounter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddRoll.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddRoll#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddRoll extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText edtQt;
    private TextView lblTotalSum;

    private DiceRoller dr;
    private int pastQT;

    private CustomListAdapter listAdapterCustom;
    private ArrayList pastRolls;
    private ArrayList pastRollsIndividual;
    private ListView listRolls;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddRoll() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddRoll.
     */
    // TODO: Rename and change types and number of parameters
    public static AddRoll newInstance(String param1, String param2) {
        AddRoll fragment = new AddRoll();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup main  = (ViewGroup)inflater.inflate(R.layout.fragment_add_roll,container,false);
        pastQT = 0;
        dr = new DiceRoller();

        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.colorAccent, typedValue, true);
        @ColorInt int color = typedValue.data;
        String hexColor = String.format("#%06X", (0xFFFFFF & color));

        listRolls = (ListView)main.findViewById(R.id.lvAddResults);
        pastRolls = new ArrayList();
        pastRollsIndividual = new ArrayList();
        listAdapterCustom = new CustomListAdapter(getContext(),android.R.layout.simple_list_item_1,pastRolls,hexColor);
        listRolls.setAdapter(listAdapterCustom);

        edtQt = (EditText)main.findViewById(R.id.diceqtSum);
        edtQt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });
        lblTotalSum = (TextView)main.findViewById(R.id.lblSomaResult);

        listRolls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getContext(),ViewRolls.class);
                i.putExtra("rolls",pastRollsIndividual.get(pastRollsIndividual.size()-position-1).toString());
                startActivity(i);
            }
        });

        Button btnRoll = (Button)main.findViewById(R.id.btnRollSum);
        btnRoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard(getActivity());
                try{
                    if(edtQt.getText().toString().isEmpty()){
                        throw new Exception("Please insert a number on Dice quantity.");
                    }else{
                        int qt= Integer.valueOf(edtQt.getText().toString());
                        if(qt>0){
                            pastQT=qt;
                            dr.roll(qt);
                            lblTotalSum.setText(String.valueOf(dr.getTotal()));
                            String aux = "Dice: "+ String.valueOf(pastQT)+"     Total: "+lblTotalSum.getText();
                            pastRolls.add(aux);
                            pastRollsIndividual.add(dr.getRolls());
                            listAdapterCustom.notifyDataSetChanged();

                        }else{
                            throw new Exception("The value should be bigger than 0");
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });

        return main;
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
