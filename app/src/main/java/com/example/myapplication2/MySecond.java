package com.example.myapplication2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.Map;

public class MySecond extends RecyclerView.Adapter<MySecond.ViewHolder> {
    private List<Map<String,Object>> list1;
    private Context context;
    public MySecond(Context context,List<Map<String,Object>> list1){
        this.context=context;
        this.list1=list1;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view= LayoutInflater.from(context).inflate(R.layout.item_comment,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MySecond.ViewHolder holder, int i) {
        Glide.with(context).load(list1.get(i).get("avatar").toString()).apply(RequestOptions.bitmapTransform(new CircleCrop())).into(holder.imageView);
        holder.textView1.setText(list1.get(i).get("author").toString());
        holder.textView2.setText(list1.get(i).get("content").toString());
    }

    @Override
    public int getItemCount() {
        return list1.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1,textView2;
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageview);
            textView1=itemView.findViewById(R.id.textView11);
            textView2=itemView.findViewById(R.id.textView22);
        }
    }
}
