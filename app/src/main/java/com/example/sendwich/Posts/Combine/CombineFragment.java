package com.example.sendwich.Posts.Combine;

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

import com.example.sendwich.PostClickActivity;
import com.example.sendwich.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CombineFragment extends Fragment {

    private String name;
    private String time;

    ArrayList<Post2> post2s;
    ListView combineListView;
    private static CombineAdapter combineAdapter;
    private static final String TAG = "DocSnippets";

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_combine, container, false);

        post2s = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot datas: dataSnapshot.getChildren()){
                    name = datas.child("id").getValue(String.class);
                    time = datas.child("time").getValue(String.class);

                    String key= datas.getKey();

                    post2s.add(new Post2(name, time));

                    combineListView = (ListView) rootView.findViewById(R.id.combine_list);
                    combineAdapter = new CombineAdapter(getContext(), post2s);
                    combineListView.setAdapter(combineAdapter);

                    combineListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
    class Post2 {
        private String UserID;
        private String ThumbnailName;

        public Post2(String UserID, String thumbnailName) {
            this.UserID = UserID;
            this.ThumbnailName = thumbnailName;
        }

        public String getUserID() {
            return UserID;
        }

        public void setUserID(String userID) {
            UserID = userID;
        }

        public String getThumbnailName() {
            return ThumbnailName;
        }

        public void setThumbnailName(String thumbnailName) {
            ThumbnailName = thumbnailName;
        }
    }

