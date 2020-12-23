package com.example.final_year_project_android;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class faultViewHolder extends RecyclerView.ViewHolder {

    public TextView faultTitle;
    public TextView faultInput1;
    public TextView faultInput2;
    public TextView faultInput3;
    public faultViewHolder(@NonNull View itemView) {
        super(itemView);
        faultTitle = (TextView)itemView.findViewById(R.id.fault_title);
        faultInput1 = (TextView)itemView.findViewById(R.id.fault_input1);
        faultInput2 = (TextView)itemView.findViewById(R.id.fault_input2);
        faultInput3 = (TextView)itemView.findViewById(R.id.fault_input3);
    }
}
