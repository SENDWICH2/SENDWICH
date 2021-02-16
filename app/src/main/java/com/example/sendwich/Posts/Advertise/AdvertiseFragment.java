package com.example.sendwich.Posts.Advertise;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.sendwich.PostClickActivity;
import com.example.sendwich.R;

import java.util.ArrayList;

public class AdvertiseFragment extends Fragment {

    ArrayList<Post> posts;
    ListView advertiseListView;
    private static AdvertiseAdapter advertiseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_advertise, container, false);

        posts = new ArrayList<>();
        posts.add(new Post("4444"));
        posts.add(new Post("5555"));
        posts.add(new Post("6666"));

        advertiseListView = (ListView) rootView.findViewById(R.id.advertise_list);
        advertiseAdapter = new AdvertiseAdapter(getContext(), posts);
        advertiseListView.setAdapter(advertiseAdapter);

        advertiseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), PostClickActivity.class);
                startActivity(intent);
            }
        });
        return rootView;
    }
}

    class Post {
        private String UserID;

        public Post(String UserID) {
            this.UserID = UserID;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
        }
    }