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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostListAdapter extends ArrayAdapter<Post> {

    private static final String tag = "PostListAdapter";
    private Context mContext;
    private int mResource;
    private int lastPostion = -1;
    private int likes;
    private ArrayList<Post> thePosts;
    Datahandler datahandler;
    String title;
    String category;
    public ArrayList<Integer>idList;
    public int selectedItem = -1;

    static class ViewHolder {
        TextView username;
        TextView title;
        TextView text;
        TextView likesShow;
        TextView category;
        TextView postTimestamp;
        LinearLayout postTopSection;
        LinearLayout postBottomSection;
        LinearLayout postWhole;
        Button postLikeButton;
        Button postButtonRemove;

    }

    public PostListAdapter(Context context, int resource, ArrayList<Post> objects, Datahandler datahandler) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
        this.thePosts = objects;
        this.datahandler=datahandler;
        idList=this.datahandler.likeid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String username = getItem(position).getUsername();
        title = getItem(position).getTitle();
        String timestamp = getItem(position).getTimestamp();
        String text = getItem(position).getText();
        likes = getItem(position).getLikes();
        category = getItem(position).getCategory();
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
            holder.postTimestamp = (TextView) convertView.findViewById(R.id.postTimestamp);

            holder.postTopSection = (LinearLayout) convertView.findViewById(R.id.postTopSection);
            holder.postBottomSection = (LinearLayout) convertView.findViewById(R.id.postBottomSection);



                if (getItem(position).getEmail().equals( datahandler.credentials.getEmail())) {
                    holder.postButtonRemove = (Button) convertView.findViewById(R.id.postButtonRemove);
                    holder.postButtonRemove.setVisibility(convertView.VISIBLE);
                    holder.postButtonRemove.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            deletepost(getItem(position), position);
                        }
                    });
                }


            holder.postLikeButton = (Button) convertView.findViewById(R.id.postButtonLike);
            holder.postLikeButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    likes = getItem(position).getLikes();
                    System.out.println(datahandler.likeid);
                    System.out.println(getItem(position).getId());
                    if(datahandler.likeid.contains(getItem(position).getId())){
                        System.out.println("Du har unliked");
                        unlikepost(getItem(position), position,getItem(position).getId());
                    }
                    else{
                        System.out.println("Du har liked");
                        likepost(getItem(position), position, getItem(position).getId());
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
        holder.postTimestamp.setText(timestamp);


//        if(parentId != 0) {
//            holder.category.setText("Comment");
//            holder.postTopSection.setBackgroundResource(R.drawable.edittext_outline_comment);
//            holder.postBottomSection.setBackgroundResource(R.drawable.edittext_outline_comment);
//        }
//        else{
//
//            holder.postTopSection.setBackgroundResource(R.drawable.edittext_outline);
//            holder.postBottomSection.setBackgroundResource(R.drawable.edittext_outline);
//        }

        if(position == selectedItem) {
            holder.postTopSection.setBackgroundResource(R.drawable.edittext_outline_selected);
            holder.postBottomSection.setBackgroundResource(R.drawable.edittext_outline_selected);

        }
        else {
            holder.postTopSection.setBackgroundResource(R.drawable.edittext_outline);
            holder.postTopSection.setBackgroundResource(R.drawable.edittext_outline);


        }

        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPostion) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPostion = position;

        return convertView;
    }



    public void unlikepost(Post post, int position, int id){
        Call<Boolean> unlikepost=datahandler.clientAPI.unlikePost(
                datahandler.credentials.encrypt,
                datahandler.credentials.Email, (Integer) id);
        System.out.println(id);
        unlikepost.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "failed to like", Toast.LENGTH_LONG).show();
                }
                else {
                    likes = getItem(position).getLikes();
                    likes = likes - 1;
                    getItem(position).setLikes(likes);
                          //  idList.remove(int_test);
                            datahandler.likeid.remove(new Integer(id));


                    notifyDataSetChanged();

                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), "failed to conect to server", Toast.LENGTH_LONG).show();

            }
        });

    }

    public void deletepost(Post post, int position){
        System.out.println("vad är post " + post + " vad är position " + position);
        Call<Boolean> removepost=datahandler.clientAPI.DeletePost(
                datahandler.credentials.encrypt,
                datahandler.credentials.Email,post);
        removepost.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "failed to remove user", Toast.LENGTH_LONG).show();
                    System.out.println(response);
                }
                else {

                    thePosts.remove(getItem(position));
                    datahandler.viewPosts.remove(getItem(position));
                    notifyDataSetChanged();


                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });
    }

    public void likepost(Post post, int position, int id){
        Call<Boolean> likePost=datahandler.clientAPI.likePost(
                datahandler.credentials.encrypt,
                datahandler.credentials.Email,(Integer) id);
        likePost.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Toast.makeText(getContext(), "failed to like", Toast.LENGTH_LONG).show();
                }
                else {
                    likes= getItem(position).getLikes();
                    likes = likes + 1;
                    getItem(position).setLikes(likes);
                   // idList.add(getItem(position).getId());
                    datahandler.likeid.add(getItem(position).getId());
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(getContext(), "failed to conect to server", Toast.LENGTH_LONG).show();

            }
        });

    }



}
