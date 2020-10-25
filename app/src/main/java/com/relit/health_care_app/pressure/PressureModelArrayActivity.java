package com.relit.health_care_app.pressure;

import android.app.Activity;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.relit.health_care_app.R;

import java.util.List;

import androidx.annotation.RequiresApi;

public class PressureModelArrayActivity extends ArrayAdapter<PressureModel> {
    private final List<PressureModel> list;
    private final Activity context;

    public PressureModelArrayActivity(Activity context, List<PressureModel> list){
        super(context, R.layout.listview_item, list);
        this.context = context;
        this.list = list;
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
            view = inflater.inflate(R.layout.listview_item, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewData = (TextView) view.findViewById(R.id.textViewData);
            view.setTag(viewHolder);
        } else {
            view = convertView;
        }
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.textViewData.setText(list.get(position).getDate() + " - " + list.get(position).getSystolic() + "/" + list.get(position).getDiastolic());
        return view;
    }
}
