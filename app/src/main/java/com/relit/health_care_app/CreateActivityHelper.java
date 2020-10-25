package com.relit.health_care_app;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;

import com.softmoore.android.graphlib.Graph;
import com.softmoore.android.graphlib.GraphView;

import androidx.activity.ComponentActivity;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CreateActivityHelper {

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setToolbar(@Nullable AppCompatActivity activity, int titleTextID){
        Toolbar toolbar = activity.findViewById(R.id.toolbar);
        toolbar.setTitle(titleTextID);
/*        toolbar.setNavigationIcon(android.R.drawable.btn_star);
        toolbar.setNavigationOnClickListener(e -> ((ComponentActivity) activity).onBackPressed());*/

        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public static void setOnGoBackListener(@Nullable AppCompatActivity activity, int buttonID, Class<?> cls){
        activity.findViewById(buttonID).setOnClickListener(e -> activity.startActivity(new Intent(activity, cls)));
    }
    public static void setNavigationButtonListener(@Nullable AppCompatActivity activity, int buttonID, Class<?> cls){
        activity.findViewById(buttonID).setOnClickListener(e -> activity.startActivity(new Intent(activity, cls)));
    }

    public static void setOnGoBackListener(@Nullable AppCompatActivity activity){
        setOnGoBackListener(activity, R.id.goToBtn, Menu.class);
    }

    public static void initGraph(@Nullable AppCompatActivity activity, int graphID){
        Graph graph = new Graph.Builder().build();
        GraphView graphView = activity.findViewById(graphID);
        graphView.setGraph(graph);
    }

    public static void initGraph(@Nullable AppCompatActivity activity){
        initGraph(activity, R.id.graph_view);
    }
}
