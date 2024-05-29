package com.example.app_creat_profesionell_cv.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.app_creat_profesionell_cv.Classes.Shapes;
import com.example.app_creat_profesionell_cv.R;
import java.util.ArrayList;

public class AdapterForShapeOnDashBoard extends RecyclerView.Adapter<AdapterForShapeOnDashBoard.ShapeViewHolder> {
    ArrayList<Shapes> Myshapes;
    Context context;

    public AdapterForShapeOnDashBoard(ArrayList<Shapes> shapes, Context context) {
        this.Myshapes = shapes;
        this.context = context;
    }

    @NonNull
    @Override
    public ShapeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shape_for_adapter, parent, false);
        return new ShapeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShapeViewHolder holder, int position) {
        Shapes shape = Myshapes.get(position);
        int resourceId = context.getResources().getIdentifier(shape.getImage(), "drawable", context.getPackageName());
        holder.img.setImageResource(resourceId);
        holder.txt.setText(shape.getDesc());
        holder.txt.setText(shape.getDesc());
    }

    @Override
    public int getItemCount() {
        return Myshapes.size();
    }

    public class ShapeViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt;

        public ShapeViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageOfLogo);
            txt = itemView.findViewById(R.id.description);
        }
    }
}
