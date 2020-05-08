package com.example.coronakanditaten2020;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PostListAdapter extends ArrayAdapter<Post> {

    private static final String tag = "PostListAdapter";
    private Context mContext;
    private int mResource;

    public PostListAdapter(Context context, int resource, ArrayList<Post> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
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
        int parentId =getItem(position).getParentId();

        Post post = new Post(username, title, timestamp, text, likes, category, id, parentId);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView postTitle = (TextView) convertView.findViewById(R.id.postTitle);
        TextView postMessage = (TextView) convertView.findViewById(R.id.postMessage);
        TextView postCategory = (TextView) convertView.findViewById(R.id.postCategory);

        postTitle.setText(title);
        postMessage.setText(text);
        postCategory.setText(category);

        return convertView;

    }
}
