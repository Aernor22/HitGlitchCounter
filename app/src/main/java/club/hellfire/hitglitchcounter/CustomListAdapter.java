package club.hellfire.hitglitchcounter;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter {
    private ArrayList arrayList;
    private String color;
    private StringBuilder builder;

    public CustomListAdapter(@NonNull Context context, int resource, ArrayList arrayList,String color) {
        super(context, resource, arrayList);
        this.arrayList = arrayList;
        this.color = color;

        builder = new StringBuilder(color);
        builder.setCharAt(1,'2');
        builder.setCharAt(2,'6');
        Log.d("CLICK",builder.toString());

    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(getCount() - position - 1);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the current item from ListView

        View view = super.getView(position,convertView,parent);
        if(position == 0) {
            // Set a background color for ListView regular row/item

            view.setBackgroundColor(Color.parseColor(builder.toString()));
        }
        return view;
    }
}
