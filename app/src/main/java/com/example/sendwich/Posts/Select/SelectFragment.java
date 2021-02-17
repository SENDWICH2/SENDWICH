package com.example.sendwich.Posts.Select;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sendwich.PostClickActivity;
import com.example.sendwich.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class SelectFragment extends Fragment {

    private String name;

    ArrayList<Post> post;
    ListView selectListView;
    private static SelectAdapter selectAdapter;

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    private static final String TAG = "DocSnippets";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_select, container, false);

        //데이터를 가져와서 어댑터와 연결해 준다.
        post = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    name = datas.child("id").getValue(String.class);
                    String key= datas.getKey();
                    Log.d(TAG,"이름 => " + name);

                    post.add(new Post(name));

                    selectListView = (ListView) rootView.findViewById(R.id.select_list);
                    selectAdapter = new SelectAdapter(getContext(), post);
                    selectListView.setAdapter(selectAdapter);

                    selectListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Intent intent = new Intent(getContext(), PostClickActivity.class);
                            startActivity(intent);
                        }
                    });
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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