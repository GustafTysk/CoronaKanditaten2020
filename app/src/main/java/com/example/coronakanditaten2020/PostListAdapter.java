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
import android.widget.Button;
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
    private int likes;
    String title;
    public ArrayList<Integer>idList = new ArrayList<Integer>();

    static class ViewHolder {
        TextView username;
        TextView title;
        TextView text;
        TextView likesShow;
        TextView category;
        LinearLayout postTopSection;
        LinearLayout postBottomSection;
        Button postLikeButton;

    }

    public PostListAdapter(Context context, int resource, ArrayList<Post> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = getItem(position).getUsername();
        title = getItem(position).getTitle();
        String timestamp = getItem(position).getTimestamp();
        String text = getItem(position).getText();
        likes = getItem(position).getLikes();
        String category = getItem(position).getCategory();
        int id = getItem(position).getId();
        int parentId = getItem(position).getParentId();

        final View result;
        ViewHolder holder=null;
        convertView=null;

        if (convertView==null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.username = (TextView) convertView.findViewById(R.id.postUsername);
            holder.title = (TextView) convertView.findViewById(R.id.postTitle);
            holder.category = (TextView) convertView.findViewById(R.id.postCategory);
            holder.text = (TextView) convertView.findViewById(R.id.postMessage);
            holder.likesShow = (TextView) convertView.findViewById(R.id.postLikes);
            holder.postTopSection = (LinearLayout) convertView.findViewById(R.id.postTopSection);
            holder.postBottomSection = (LinearLayout) convertView.findViewById(R.id.postBottomSection);
            holder.postLikeButton = (Button) convertView.findViewById(R.id.postButtonLike);
            holder.postLikeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    likes = getItem(position).getLikes();
                    if(idList.contains(getItem(position).getId())){
                        likes = likes - 1;
                        getItem(position).setLikes(likes);
                        likes = getItem(position).getLikes();
                        title = getItem(position).getTitle();
                        for (Integer int_test : idList){
                            if(int_test == getItem(position).getId()){
                                idList.remove(int_test);
                            }
                        }
                    }
                    else{
                        likes = likes + 1;
                        getItem(position).setLikes(likes);
                        idList.add(getItem(position).getId());
                    }

                    notifyDataSetChanged();
                    }

                                                     });
            result = convertView;
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.username.setText(username);
        holder.title.setText(title);
        holder.category.setText(category);
        holder.text.setText(text);
        holder.likesShow.setText("Likes: " + likes);


        if(category == "comment") {
            holder.category.setText("Comment");
            holder.postTopSection.setBackgroundResource(R.drawable.edittext_outline_comment);
            holder.postBottomSection.setBackgroundResource(R.drawable.edittext_outline_comment);
        }
        else{

            holder.postTopSection.setBackgroundResource(R.drawable.edittext_outline);
            holder.postBottomSection.setBackgroundResource(R.drawable.edittext_outline);
        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPostion) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPostion = position;

        return convertView;
    }
}
