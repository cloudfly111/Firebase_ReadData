# Firebase_ReadData 
**建立目的 : 引用 google 官方技術文件示範如何以 Android 操作 FireBase (Real Time Database)。**

## Firebase - Real Time Database 介紹
>google 建立的即時雲端資料庫服務，以 JSON 格式儲存資料為 NoSQL 非關聯式資料庫，Android App可以透過 HTTP requests 存取 Real Time Database 資料庫內容，重點是不要存太多資料的話，即時雲端資料庫服務都是免費的。

## 如何以 Android 操作 FireBase (Real Time Database)

### 1. Firebase 建立 Real Time Database 專案
建立自己的即時雲端資料庫，多個 Android App 可以共用一個 Real Time Database 專案(資料庫)。

### 2. Android App 設定 Firebase SDK，而連接 google Firebase 服務

* 會需要 Android 專案 的 SHA-1 指紋 :

>Gradle > Execute Gradle Task > 輸入 "signingreport" > 按 Enter > 下方開始跑出 SHA-1 指紋(如下圖紅框所示)

<img width="973" alt="FireBase_create_SHA1" src="https://user-images.githubusercontent.com/37395516/190666312-ddd8297d-bf58-4a1f-8853-8b8d5220e782.png">


* Project顯示層級下的app目錄裡放入Firebase提供的JSON檔，如下圖所示。

<img width="321" alt="google-json-path" src="https://user-images.githubusercontent.com/37395516/190666967-dd3568d7-3967-49a4-8bd7-19422cc33faa.png">


* 如下設定兩個 build.gradle(project name) 和 build.gradle(app) :

build.gradle (Project : Project Name) : 

```

// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    dependencies {
        // Add the dependency for the Google services Gradle plugin
        classpath 'com.google.gms:google-services:4.3.13'
    }
}


plugins {
  ......
}


task clean(type: Delete) {
    .....
}

```

build.gradle(Module : projectName.app) : 

```
plugins {
    id 'com.android.application'

    //  for FireBase
    id 'com.google.gms.google-services'
}

android {
    compileSdk XX

    defaultConfig {
        .....
    }

    buildTypes {
        release {
            ....
        }
    }
    compileOptions {
        .....
    }
}

dependencies {

 .......

    // Import the Firebase BoM
    implementation platform('com.google.firebase:firebase-bom:30.3.2')

    implementation 'com.google.firebase:firebase-analytics'

    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
    //   指定firebase版本
    implementation("com.google.firebase:firebase-database:20.0.5")
}


```

### 3. 初始化 Real Time Database 服務

* `FirebaseDatabase.getInstance()` : return `FirebaseDatabase` 物件，能透過此物件使用 Firebase 服務。

```
// 使用 Firebase 服務
FirebaseDatabase database = FirebaseDatabase.getInstance();
Log.d("main", "FireDatabase=" + database); 
//      FireDatabase=com.google.firebase.database.FirebaseDatabaseXXXXX
```

* `database.getReference()` : return `DatabaseReference` 物件，取得與 Android App 連接的 Firebase GET網址。

```
DatabaseReference databaseRef = database.getReference();
Log.d("main", "dataRef=" + databaseRef);//可以由Log看下你的Firebase是由怎麼樣的網址連接的
//      dataRef=https://fir-project-XXXXX-default-rtdb.firebaseio.com/
```

### 4. 寫入資料到 Real Time Database 

[Write data - Basic write operations](https://firebase.google.com/docs/database/android/read-and-write#write_data) 

<br> 基本上JSON格式資料都是 Key 和 Value 建立一組資料，類似`Map`內部元素的建立方式，，Key只能是`String`，Value可以是以下6種資料屬性和自訂類別 :

* `String`
* `Long`
* `Double`
* `Boolean`
* `Map<String, Object>`
* `List<Object>`
* 自訂 `Class`

以下示範如何以`String`、`Map<String, Object>`和`Class`寫入資料到FireBase。

#### 4.1 字串`String`

* [Connect your App to Firebase  - Write to your database](https://firebase.google.com/docs/database/android/start#write_to_your_database) : 
Firebase版的Hello World

* Key : child("message")

* Value : setValue("Hello World!")

```
databaseRef.child("message").setValue("Hello World!");
//{
//      message : Hello World!
//}
```

#### 4.2 `Map`

```
//        Map<String, String> Mapdata = new HashMap<String, String>();
//
//        Mapdata.put("2","cd");
//        Mapdata.put("3","ef");
//
//        databaseRef.child("1").setValue(Mapdata);
//      message
//       |__ 1 
//             |__ 2:"cd"
//             |__ 3:"ef"
```

#### 4.3 類別`Class`

* 自訂類別中的屬性設為`private`，屬性名稱為key，屬性資料為value存入資料，注意必須有預設無參數的建構子和`public`方法來取得屬性資料(EX: object.getuser() 傳回使用者名稱字串)，實作自訂類別的物件有方便寫入和讀出資料，與方便維護程式碼的優點，也提升可讀性，能清楚知道這串資料主要儲存何種資訊。

  >Pass a custom Java object, if the class that defines it has a default constructor that takes no arguments and has public getters for the properties to be assigned.
  >
  >If you use a Java object, the contents of your object are automatically mapped to child locations in a nested fashion. Using a Java object also typically makes your code more readable and easier to maintain.
  >
  >Basic write operations, accessed 2022/09/16, https://firebase.google.com/docs/database/android/read-and-write#basic_writ 

* 以下`User.class`內容，主要儲存使用者的資料。

```
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

### 5. 查詢 Real Time Database 資料 

```
```

## 參考資料
* Firebase google 官方技術文件 for Android : 
   - [Connect your App to Firebase](https://firebase.google.com/docs/database/android/start) : Android App 如何連接 Firebase
   - [Structure Your Database](https://firebase.google.com/docs/database/android/structure-data) : 如何設計 JSON 資料結構
   - [Real Time Database](https://firebase.google.com/docs/database/android/start) :
      - [Read and Write Data on Android - Write data](https://firebase.google.com/docs/database/android/read-and-write#write_data) : 如何寫入資料
      - [Read and Write Data on Android - Read data](https://firebase.google.com/docs/database/android/read-and-write#read_data) : 如何查詢資料
      - [Read and Write Data on Android - Update specific fields](https://firebase.google.com/docs/database/android/read-and-write#update_specific_fields) : 如何更新資料
      - [Read and Write Data on Android - Delete data](https://firebase.google.com/docs/database/android/read-and-write#delete_data) : 如何刪除資料
      
 * 設計與呈現 JSON 資料結構相關資料 :     
   - [Learning NoSQL — NoSQL Database Designing](https://medium.com/tech-tajawal/nosql-modeling-database-structuring-part-ii-4c364c4bc17a) : 如何把SQL結構轉成NOSQL，幫助原本不會NOSQL，但是會SQL的人轉換概念，用圖示標註的很清楚。

   - [Flatten data structures](https://firebase.google.com/docs/database/android/structure-data#flatten_data_structures) : 因為搜尋資料上的考量，JSON格式是不建議太多層，儘量平一點，少層一點。

   - [JSON Crack - Seamlessly visualize your JSON data instantly into graphs](https://jsoncrack.com/) : 可以輕鬆把複雜的JSON結構視覺化的編輯器，方便複雜參數的整理。

   

 
