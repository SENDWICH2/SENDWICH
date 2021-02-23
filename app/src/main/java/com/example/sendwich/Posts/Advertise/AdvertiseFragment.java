package com.example.sendwich.Posts.Advertise;

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
/*
 Hot위치 프래그먼트
 */

public class AdvertiseFragment extends Fragment {

    private String name;
    private String time;

    private int count=0;

    ArrayList<Post3> post3s;
    ListView advertiseListView;
    ArrayList<String> keys = new ArrayList<String>();
    private static AdvertiseAdapter advertiseAdapter;

    final FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private static final String TAG = "DocSnippets";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_advertise, container, false);

        post3s = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("posts");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datas : dataSnapshot.getChildren()) {
                    name = datas.child("id").getValue(String.class);
                    time = datas.child("time").getValue(String.class);

                    String key = datas.getKey();
                    keys.add(key);

                    count++;
                    post3s.add(new Post3(name, time));

                    advertiseListView = (ListView) rootView.findViewById(R.id.advertise_list);
                    advertiseAdapter = new AdvertiseAdapter(getContext(), post3s);
                    advertiseListView.setAdapter(advertiseAdapter);
                }
                if (count >= 1) {
                    advertiseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Toast.makeText(getContext(), "클릭 : " + position, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "키값22 => " + keys.get(position));

                            Intent intent = new Intent(getContext(), PostClickActivity.class);
                            intent.putExtra("게시물키값", keys.get(position));

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

    class Post3 {
        private String UserID;
        private String ThumbnailName;

        public Post3(String UserID,  String thumbnailName) {
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