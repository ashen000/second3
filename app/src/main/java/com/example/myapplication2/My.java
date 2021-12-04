package com.example.myapplication2;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class My extends RecyclerView.Adapter<My.ViewHolder> {//将数据源传进来

    private List<Map<String,Object>>list;
    private Context context;

    public My(Context context,List<Map<String,Object>> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {//将item布局传进来


        View view= LayoutInflater.from(context).inflate(R.layout.item_list,parent,false);
            return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {//将数据赋到item中
        Glide.with(context).load(list.get(i).get("images").toString()).into(holder.imageView);
            holder.textView1.setText(list.get(i).get("title").toString());
            holder.textView2.setText(list.get(i).get("hint").toString());
           if(list.get(i).get("bj").toString()=="0")
           {
               holder.itemView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Intent intent=new Intent(v.getContext(),InnerPage.class);
                       Log.i("12", v.getContext().toString());
                       intent.putExtra("url",list.get(i).get("url").toString());
                       intent.putExtra("id",list.get(i).get("id").toString());
                       v.getContext().startActivity(intent);

                   }
               });

           }



    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{//将成员变量与item布局绑定
        TextView textView1,textView2;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview1);
            textView1=itemView.findViewById(R.id.textView1);
            textView2=itemView.findViewById(R.id.textView2);
        }
    }
}

