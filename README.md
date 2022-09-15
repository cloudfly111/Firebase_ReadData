# Firebase_ReadData
建立目的 : 引用 google 官方技術文件示範如何以 Android 操作 FireBase (Real Time Database)。 

# Firebase - Real Time Database 介紹
google 建立的即時雲端資料庫服務，以 JSON 格式儲存資料為 NoSQL 非關聯式資料庫，Android App可以透過 HTTP requests 存取 Real Time Database 資料庫內容，重點是不要存太多資料的話，即時雲端資料庫服務都是免費的。

## 1. Firebase 建立 Real Time Database 專案
建立自己的即時雲端資料庫，多個 Android App 可以共用一個 Real Time Database 專案(資料庫)。

## 2. Android App 設定 Firebase SDK，而連接 google Firebase 服務
會需要 Android 專案 的 SHA-1 指紋，設定兩個 build.gradle(project name) 和 build.gradle(app)，Project顯示層級下的app目錄裡放入Firebase提供的JSON檔。

## 3. 寫入資料到 Real Time Database 

```
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("main", "FireDatabase=" + database);
//      FireDatabase=com.google.firebase.database.FirebaseDatabase@6c0dc1b

DatabaseReference databaseRef = database.getReference("message");
        Log.d("main", "dataRef=" + databaseRef);
//      dataRef=https://fir-project-4a35d-default-rtdb.firebaseio.com/message
```

### 3.1 String

```
databaseRef.setValue("Hello World!");
//      message : Hello World!
```

### 3.2 Map

```
//        Map<String, String> Mapdata = new HashMap<String, String>();
//
//        Mapdata.put("2","cd");
//        Mapdata.put("3","ef");
//
//        databaseRef.child("1").setValue(Mapdata);
//      message
//       |__ 1 (key : value)
//             |__ 2:"cd"
//             |__ 3:"ef"
```

### 3.3 Class

* User.class
```
package com.example.firebase_readdata;

public class User {
    private String user;
    private String password;
    private Long age;
    private String birthday;

    User() {
    }

    User(String user, String password, Long age, String birthday) {
        this.user = user;
        this.password = password;
        this.age = age;
        this.birthday = birthday;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Long getAge() {
        return age;
    }

    public String getBirthday() {
        return birthday;
    }
}

```

```
    String [] valueContent ={"a","b","c","d","e","f","g"};
    for(int i=0; i < valueContent.length; i++){
            String materKey = String.valueOf(i);
            Log.d("main","masterKey="+materKey);
            String subValue = valueContent[i];
            Log.d("main","subValue="+subValue);
            String subId = subValue.concat(materKey);
            Log.d("main","subId="+subId);

            Format multipleData = new Format(subId,subValue);
            databaseRef.child(materKey).setValue(multipleData);
        }
```

## 4. 查詢 Real Time Database 資料 

```
```

# 參考資料
* Firebase google 官方技術文件 : 
   - [Android App 如何連接 Firebase](https://firebase.google.com/docs/database/android/start)
   - [如何設計 JSON 資料結構](https://firebase.google.com/docs/database/android/structure-data)
   - Real Time Database :
      - [如何寫入資料](https://firebase.google.com/docs/database/android/read-and-write#write_data)
      - [如何查詢資料](https://firebase.google.com/docs/database/android/read-and-write#read_data)
      - [如何更新資料](https://firebase.google.com/docs/database/android/read-and-write#update_specific_fields)
      - [如何刪除資料](https://firebase.google.com/docs/database/android/read-and-write#delete_data)
