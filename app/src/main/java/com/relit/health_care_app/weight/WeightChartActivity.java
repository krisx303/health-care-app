package com.relit.health_care_app.weight;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.relit.health_care_app.CreateActivityHelper;
import com.relit.health_care_app.DataHelper;
import com.relit.health_care_app.R;
import com.relit.health_care_app.sugar.SugarAddActivity;
import com.relit.health_care_app.sugar.SugarDataBaseHelper;
import com.relit.health_care_app.sugar.SugarModel;
import com.relit.health_care_app.sugar.SugarModelArrayAdapter;
import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;

import java.util.ArrayList;
import java.util.List;

public class WeightChartActivity extends AppCompatActivity {

    private Point[] points;
    private List<String> dates = new ArrayList<>();
    private ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_chart);
        CreateActivityHelper.setToolbar(this, R.string.weight);
        CreateActivityHelper.setNavigationButtonListener(this, R.id.goToBtn, WeightAddActivity.class);
        CreateActivityHelper.initGraph(this);
        loadGraphElements();
        listView = findViewById(R.id.list_view);
        drawGraph();
        refreshAdapter();
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void refreshAdapter(){
        WeightDataBaseHelper dataBase = DataHelper.getWeightDataBaseHelper();
        List<WeightModel> list = dataBase.getEveryone();
        List<WeightModel> reversedList = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            reversedList.add(list.get(i));
        }
        ArrayAdapter<WeightModel> adapter = new WeightModelArrayAdapter(this, reversedList.subList(0, Math.min(reversedList.size(), 5)));
        ((ListView)listView).setAdapter(adapter);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadGraphElements() {
        WeightDataBaseHelper dataBase = DataHelper.getWeightDataBaseHelper();
        List<WeightModel> list = dataBase.getEveryone();
        points = new Point[list.size()];
        for (int i = 0; i < list.size(); i++) {
            WeightModel model = list.get(i);
            points[i] = new Point(i, ((double)model.getWeight())/20);
            dates.add(model.getDate().toString().substring(0,model.getDate().toString().length()-5));
        }
    }

    private void drawGraph() {
        Graph graph = new Graph.Builder().setYLabels(getYLabels()).setXLabels(getXLabels()).setWorldCoordinates(-1-(points.length)/4, points.length+2, -0.5, 12)
                .setAxes(0, 0).addLineGraph(points, Color.BLUE).build();
        GraphView graphView = findViewById(R.id.graph_view);
        graphView.setGraph(graph);
    }

    private List<Label> getYLabels(){
        List<Label> labels = new ArrayList<>();
        int tick = 0;
        labels.add(new Label(tick, ""));
        tick++;
        for (int i = 20; i <= 220; i=i+20) {
            labels.add(new Label(tick, i + " kg"));
            tick++;
        }
        return labels;
    }

    private List<Label> getXLabels() {
        List<Label> labels = new ArrayList<>();
        for (int i = 0; i < dates.size(); i++) {
            labels.add(new Label(i, dates.get(i)));
        }
        return labels;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==16908332)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}