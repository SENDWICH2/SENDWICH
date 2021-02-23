# SENDWICH

### 사용 API : Maps SDK for Android, Places API, Identity Toolkit API, Firebase Installations API, Token Service API, Service Usage API, Cloud Firestore API


##### 프로젝트 버전
    compileSdkVersion 30
    buildToolsVersion "30.0.3"
    minSdkVersion 21
    targetSdkVersion 30
    JavaVersion.VERSION_1_8


* Firebase 데이터베이스와 연동하여 추출한 google-services.json 파일을 app 폴더 상위에 교체하여야함
* AndroidManifest.xml 에서 api키값을 입력 해 주어야함 (firebase기반)
* MainActivity에서 showPlaceInformation함수에서 .key에 키값. --> Places API 
* Manifest에서 meta-data에 valuer값에 key값. --> Maps SDK for Android
