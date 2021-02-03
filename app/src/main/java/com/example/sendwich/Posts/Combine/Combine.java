package com.example.sendwich.Posts.Combine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.sendwich.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Combine#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Combine extends Fragment {

    ArrayList<Post> posts;
    ListView combineListView;
    private static CombineAdapter combineAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Combine() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Combine.
     */
    // TODO: Rename and change types and number of parameters
    public static Combine newInstance(String param1, String param2) {
        Combine fragment = new Combine();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_combine, container, false);

        posts = new ArrayList<>();
        posts.add(new Post("1111"));
        posts.add(new Post("2222"));
        posts.add(new Post("3333"));

        combineListView = (ListView)rootView.findViewById(R.id.listView_custom);
        combineAdapter = new CombineAdapter(getContext(), posts);
        combineListView.setAdapter(combineAdapter);

        return rootView;
    }

    class Post {
        private String id;

        public Post(String id) {
            this.id = id;
        }

        public String getid() {
            return id;
        }
    }
}