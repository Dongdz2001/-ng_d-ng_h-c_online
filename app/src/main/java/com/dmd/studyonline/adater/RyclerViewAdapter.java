package com.dmd.studyonline.adater;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dmd.studyonline.MainActivity;
import com.dmd.studyonline.R;
import com.dmd.studyonline.model.ClassLink;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class RyclerViewAdapter extends RecyclerView.Adapter<RyclerViewAdapter.ViewHolder> {
    private Context context ;
    private List<ClassLink> listClasslink = new ArrayList<>();
    private  OnItemClickListener onItemClickListener ;
    private ArrayList<ViewHolder> listviewhoder = new ArrayList<>();
    private int layout_res;
    private int imageCount = 0;
    public static final String FIRST_NAME = "first_name";

    public RyclerViewAdapter(Context context, OnItemClickListener onItemClickListener, ArrayList<ClassLink> listClasslink, int layout_res) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
        this.listClasslink = listClasslink;
        this.layout_res = layout_res;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layout_res,parent,false);
        ViewHolder viewHolder  = new ViewHolder(view);
        listviewhoder.add(viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ClassLink classLink = listClasslink.get(position);
        holder.vhd_tvTitle.setText(classLink.getTitle());
        holder.vhd_tvDetail.setText("("+classLink.getNameTeacher()+")");
        setImageIcons(holder,classLink.getImage());
        holder.vhd_tvTime.setText(classLink.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null ){
                    onItemClickListener.ItemOnClick(classLink);
                }
            }
        });
        holder.vhd_imvIcons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                classLink.UpImage();
                imageCount = classLink.getImage();
                if (imageCount % 4 == 0){
                    holder.vhd_imvIcons.setImageResource(R.drawable.meet);
                }else if (imageCount % 4 == 1) {
                    holder.vhd_imvIcons.setImageResource(R.drawable.zoom);
                }else if(imageCount % 4 == 2){
                    holder.vhd_imvIcons.setImageResource(R.drawable.team);
                }else {
                    holder.vhd_imvIcons.setImageResource(R.drawable.web);
                }
                    listClasslink.get(listClasslink.indexOf(classLink)).setImage(imageCount);
                    SharedPreferences mPrefs = context.getSharedPreferences("share preference",context.MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    Gson gson = new Gson();
                    String json = gson.toJson(listClasslink);
                    prefsEditor.putString("ListClassLink", json);
                    prefsEditor.apply();
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.d("dmd","Long Click");
                if (onItemClickListener != null){
                    onItemClickListener.ItemOnLongClick(classLink);
                }
                return true;
            }
        });
        holder.vhd_imvIcons.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(context instanceof MainActivity){
                    ((MainActivity)context).changeScreen(classLink);
                    ((MainActivity)context).setPosition(listClasslink.indexOf(classLink));
                }
                return true;
            }
        });
    }

    private void setImageIcons(ViewHolder holder,int imageCount){
        if (imageCount % 4 == 0){
            holder.vhd_imvIcons.setImageResource(R.drawable.meet);
        }else if (imageCount % 4 == 1) {
            holder.vhd_imvIcons.setImageResource(R.drawable.zoom);
        }else if(imageCount % 4 == 2){
            holder.vhd_imvIcons.setImageResource(R.drawable.team);
        }else {
            holder.vhd_imvIcons.setImageResource(R.drawable.web);
        }
    }
    @Override
    public int getItemCount() {
        if ( listClasslink != null )  {
            return listClasslink.size();
        }
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView vhd_tvTitle;
        private TextView vhd_tvDetail;
        private ImageView vhd_imvIcons;
        private TextView vhd_tvTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vhd_tvTitle = itemView.findViewById(R.id.tv_Title);
            vhd_tvDetail = itemView.findViewById(R.id.tv_Detail);
            vhd_imvIcons = itemView.findViewById(R.id.imvIcons);
            vhd_tvTime = itemView.findViewById(R.id.tv_Time);
        }
    }
}
