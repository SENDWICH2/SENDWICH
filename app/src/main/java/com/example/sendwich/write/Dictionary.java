package com.example.sendwich.write;

import android.net.Uri;

public class Dictionary {

    private Uri uri;

    public Dictionary(Uri uri) {
        this.uri = uri;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }
}