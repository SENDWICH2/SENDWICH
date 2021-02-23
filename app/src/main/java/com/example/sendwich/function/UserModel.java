package com.example.sendwich.function;

public class UserModel {
    // 사용자 기본정보

    public String userName;         // 유저 이름(닉네임)
    public String profileImageUrl;  // 유저 프로필사진 url
    public String uid;              // 현재 사용자 회원 토큰
    public String useremail;        // 유저 이메일
    public String follow;           // 팔로우 수
    public String follower;         // 팔로워 수
    public String postnum;          // 게시글 수
    public String userintroduce;    // 프로필 자기소개
    public String category;         // 카테고리 선택사항

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
