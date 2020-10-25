package com.relit.health_care_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;
import com.softmoore.android.graphlib.Label;
import com.softmoore.android.graphlib.Point;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class Coronavirus extends AppCompatActivity {
    Point[] points;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coronavirus);
        CreateActivityHelper.setToolbar(this, R.string.covid);
        findViewById(R.id.button4).setOnClickListener(e -> onBrowser1Click());
        findViewById(R.id.button5).setOnClickListener(e -> onBrowser2Click());
        Graph graph = new Graph.Builder().build();
        GraphView graphView = findViewById(R.id.graph_view);
        graphView.setGraph(graph);
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String data = mPrefs.getString("covid-data", null);
        if(data==null)
            getDataFromFireStore();
        else{
            Log.d("App", "We have covid data");
        }
    }

    private void onBrowser1Click() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://news.google.com/covid19/map?hl=pl&mid=%2Fm%2F01bwj7&gl=PL&ceid=PL%3Apl"));
        startActivity(intent);
    }
    private void onBrowser2Click() {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.gov.pl/web/koronawirus"));
        startActivity(intent);
    }

    private void getDataFromFireStore() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("cases").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    doWorkWithData(document.getData());
                }
            } else {
                Log.w("XD", "Error getting documents.", task.getException());
            }
        });
    }

    private void drawGraph() {
        Graph graph = new Graph.Builder().setXTicks(new double[]{200, 220, 240, 260, 280, 300}).setYTicks(new double[] {0, 1000, 2000, 3000, 4000, 5000, 6000, 7000, 8000, 9000, 10000, 11000, 12000, 13000, 14000}).setWorldCoordinates(-40, 300, -500, 14000)
                .setXLabels(Arrays.asList(new Label(50, "12/03"), new Label(100, "01/05"), new Label(150, "20/06"), new Label(200, "09/08"), new Label(250, "28/09"))).setAxes(0, 0).addLineGraph(points, Color.RED).build();
        GraphView graphView = findViewById(R.id.graph_view);
        graphView.setGraph(graph);
    }

    private void doWorkWithData(Map<String, Object> data) {
        String o = (String) data.get("first");
        SharedPreferences mPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mPrefs.edit().putString("covid-data", o);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        DataList dataList = gson.fromJson("{\"list\":" + o + "}", DataList.class);
        points = new Point[dataList.getList().size()];
        for (int i = 0; i < dataList.getList().size(); i++) {
            DataObject dataObject = dataList.getList().get(i);
            int cases = Integer.parseInt(dataObject.getInfo().split(",")[0]);
            points[i] = new Point(i, cases);
            if(i%50==0)
                System.out.println(dataObject.getData());
        }
        drawGraph();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==16908332)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }
}
class DataList{
    private List<DataObject> list;

    public DataList() {
    }

    public List<DataObject> getList() {
        return list;
    }

    public void setList(List<DataObject> list) {
        this.list = list;
    }
}
class DataObject{
    private String data;
    private String info;

    public DataObject() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "data='" + data + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}