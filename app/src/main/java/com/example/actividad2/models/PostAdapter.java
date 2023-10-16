
package com.example.actividad2.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.actividad2.R;

import java.util.List;

public class PostAdapter extends BaseAdapter {
    private List<Post> postList;
    private Context context;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @Override
    public int getCount() {
        return postList.size();
    }

    @Override
    public Object getItem(int position) {
        return postList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item_layout, null);
        }

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView bodyTextView = convertView.findViewById(R.id.bodyTextView);

        Post post = postList.get(position);

        titleTextView.setText(post.getTitle());
        bodyTextView.setText(post.getBody());

        return convertView;
    }
}
