package com.example.sendwich.Posts.Select;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sendwich.PostClickActivity;
import com.example.sendwich.R;

import java.util.ArrayList;

public class SelectFragment extends Fragment {

    ArrayList<Post> post;
    ListView customListView;
    private static SelectAdapter selectAdapter;
    private static final String TAG = "DocSnippets";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        //데이터를 가져와서 어댑터와 연결해 준다.
        post = new ArrayList<>();
        post.add(new Post("1111"));
        post.add(new Post("2222"));
        post.add(new Post("3333"));

        customListView = (ListView) rootView.findViewById(R.id.select_list);
        selectAdapter = new SelectAdapter(getContext(), post);
        customListView.setAdapter(selectAdapter);

        customListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "아이템 클릭 !!!ㅜㅜ" + position, Toast.LENGTH_SHORT).show();
                Log.d(TAG, "아이템 클릭");
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