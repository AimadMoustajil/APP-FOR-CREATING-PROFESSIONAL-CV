package com.example.app_creat_profesionell_cv.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_creat_profesionell_cv.BooksActivity;
import com.example.app_creat_profesionell_cv.Classes.Shapes;
import com.example.app_creat_profesionell_cv.Letters.LetterDeDimidion;
import com.example.app_creat_profesionell_cv.Letters.LetterDePromotion;
import com.example.app_creat_profesionell_cv.Letters.letterDeMotivation;
import com.example.app_creat_profesionell_cv.ModelsOfCV.ModelsActivity;
import com.example.app_creat_profesionell_cv.QuestionForEnterviewActivity;
import com.example.app_creat_profesionell_cv.R;

import java.util.ArrayList;
import java.util.Objects;

public class AdapterForShapeOnDashBoard extends RecyclerView.Adapter<AdapterForShapeOnDashBoard.ShapeViewHolder> {
    ArrayList<Shapes> myShapes;
    Context context;

    public AdapterForShapeOnDashBoard(ArrayList<Shapes> shapes, Context context) {
        this.myShapes = shapes;
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
        Shapes shape = myShapes.get(position);
        int resourceId = context.getResources().getIdentifier(shape.getImage(), "drawable", context.getPackageName());
        holder.img.setImageResource(resourceId);
        holder.txt.setText(shape.getDesc());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Objects.equals(shape.getDesc(), "creat cv")){
                    v.getContext().startActivity(new Intent(v.getContext(),ModelsActivity.class));
                } else if (Objects.equals(shape.getDesc(), "creat letter de motivation")){
                    v.getContext().startActivity(new Intent(v.getContext(), letterDeMotivation.class));
                }else if (Objects.equals(shape.getDesc(), "creat letter de dimission")){
                    v.getContext().startActivity(new Intent(v.getContext(), LetterDeDimidion.class));
                }else if (Objects.equals(shape.getDesc(), "creat letter de promotion")){
                    v.getContext().startActivity(new Intent(v.getContext(), LetterDePromotion.class));
                } else if (Objects.equals(shape.getDesc(), "Question d'entrevue")){
                    v.getContext().startActivity(new Intent(v.getContext(),QuestionForEnterviewActivity.class));
                }else if (Objects.equals(shape.getDesc(), "Books")){
                    v.getContext().startActivity(new Intent(v.getContext(), BooksActivity.class));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myShapes.size();
    }

    public class ShapeViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        TextView txt;

        public ShapeViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageOfLogo);
            txt = itemView.findViewById(R.id.description);
            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txt.getText().toString().equals("creat cv")) {
                        Intent intent = new Intent(v.getContext(), ModelsActivity.class);
                        v.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(v.getContext(), "Not Done", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txt.getText().toString().equals("Question d'entrevue")) {
                        Intent intent = new Intent(v.getContext(), QuestionForEnterviewActivity.class);
                        v.getContext().startActivity(intent);
                    } else {
                        Toast.makeText(v.getContext(), "Not Done", Toast.LENGTH_SHORT).show();
                    }
                }
            });*/
        }
    }
}
