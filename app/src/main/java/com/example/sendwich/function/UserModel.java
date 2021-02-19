package com.example.sendwich.function;

public class UserModel {
    // 사용자 기본정보

    public String userName; // 사용자 이름(닉네임)
    public String profileImageUrl; // 사용자 프로필사진
    public String uid; // 현재 사용자(로그인한)
    public String useremail;
    public String follow;
    public String follower;
    public String postnum;
    public String userintroduce;
    public String category;

    //    public String pushToken;
    public UserModel() {
    // Default constructor required for calls to DataSnapshot.getValue(User.class)
}
    public UserModel(String userName, String profileImageUrl, String uid, String useremail, String category , String follow, String postnum, String follower,
    String userintroduce) {
        this.userName = userName;
        this.profileImageUrl = profileImageUrl;
        this.uid = uid;
        this.useremail = useremail;
        this.category = category;
        this.follow = follow;
        this.postnum = postnum;
        this.follower = follower;
        this.userintroduce = userintroduce;
    }

    public String getfollow(){
        return follow;
    }
    public String getUserintroduce(){
        return userintroduce;
    }
    public String getfollower(){
        return follower;
    }
    public String getpost(){
        return postnum;
    }

    public String getprofileImageUrl() {
        return profileImageUrl;
    }
    public String getCategory() {
        return category;
    }

    public String getUserName() {
        return userName;
    }
    public String getuid() {
        return uid;
    }
    public String getusermail() {
        return useremail;
    }
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", profileImageUrl='" + profileImageUrl + '\'' +
                ", uid='" + uid + '\'' +
                ", category='" + category+ '\'' +
                ", userintroduce='" + userintroduce+ '\'' +
                ", follow='" + follow+'}';
    }
}
