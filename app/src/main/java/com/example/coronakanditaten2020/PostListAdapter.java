package com.example.coronakanditaten2020;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends ArrayAdapter<Post> {

    private static final String tag = "PostListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPostion = -1;

    private ArrayList<Post> all_values;
    private ArrayList<Post> filter_values;

    static class ViewHolder {
        TextView username;
        TextView title;
        TextView text;
        TextView likesShow;
        TextView category;
       // LinearLayout postTopSection;

    }

    public PostListAdapter(Context context, int resource, ArrayList<Post> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;

//        all_values = new ArrayList<Post>();
//        all_values.addAll(objects);
//        filter_values = all_values.clone();


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = getItem(position).getUsername();
        String title = getItem(position).getTitle();
        String timestamp = getItem(position).getTimestamp();
        String text = getItem(position).getText();
        ArrayList <String> likes = getItem(position).getLikes();
        String category = getItem(position).getCategory();
        int id = getItem(position).getId();
        int parentId = getItem(position).getParentId();

        Post post = new Post(username, title, timestamp, text, likes, category, id, parentId);

        final View result;
        ViewHolder holder;

        if (convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            //convertView.setBackgroundColor(Color.BLUE);
            holder.username = (TextView) convertView.findViewById(R.id.postUsername);
            holder.title = (TextView) convertView.findViewById(R.id.postTitle);
            holder.category = (TextView) convertView.findViewById(R.id.postCategory);
            holder.text = (TextView) convertView.findViewById(R.id.postMessage);
            holder.likesShow = (TextView) convertView.findViewById(R.id.postLikes);
            //holder.postTopSection = (LinearLayout) convertView.findViewById(R.id.postTopSection);


            result = convertView;
            convertView.setTag(holder);

        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPostion) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPostion = position;

        holder.username.setText(username);
        holder.title.setText(title);
        holder.category.setText(category);
        holder.text.setText(text);
        holder.likesShow.setText("Likes: " + likes.get(0));

        if(parentId != 0) {
            holder.category.setText("Comment");

           // holder.postTopSection.setBackgroundResource(R.drawable.edittext_outline_comment);
//          holder.title.setBackgroundResource(R.drawable.edittext_outline_comment);
//          holder.text.setBackgroundResource(R.drawable.edittext_outline_comment);

        }

        return convertView;

    }
}
