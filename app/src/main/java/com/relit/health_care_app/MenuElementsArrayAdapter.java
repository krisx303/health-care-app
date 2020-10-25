package com.relit.health_care_app;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.relit.health_care_app.models.Choice;

import java.util.List;

import androidx.annotation.RequiresApi;

public class MenuElementsArrayAdapter extends ArrayAdapter<Choice> {

    private final List<Choice> list;
    private final Activity context;
    private Menu menu;

    public MenuElementsArrayAdapter(Activity context, List<Choice> list, Menu menu){
        super(context, R.layout.rowbuttonlayout, list);
        this.context = context;
        this.list = list;
        this.menu = menu;
    }
    static class ViewHolder {
        protected Button button;
        protected TextView textViewData, textViewDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        if (convertView == null) {
            LayoutInflater inflater = context.getLayoutInflater();
            view = inflater.inflate(R.layout.rowbuttonlayout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.button = (Button) view.findViewById(R.id.button);
            viewHolder.button.setOnClickListener(e -> {
                menu.openActivity(list.get(position));
            });
            viewHolder.textViewData = (TextView) view.findViewById(R.id.textViewData);
            viewHolder.textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.button.setBackgroundResource(DataHelper.getImageResourceByChoice(list.get(position)));
        holder.textViewData.setText(list.get(position).getLastDataString());
        holder.textViewDate.setText(list.get(position).getLastDateString());
        return view;
    }
}
