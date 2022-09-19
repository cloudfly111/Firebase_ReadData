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

#### 4.4 轉換圖片為base64(String)後，存入到 Real Time Database 

```
DatabaseReference dataRef = database.getReference();
// convert png to base64
            try {
                Bitmap bm = BitmapFactory.decodeResource(getResources(), R.drawable.app_logo);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bm.compress(Bitmap.CompressFormat.PNG, 100, baos); // bm is the bitmap object
                byte[] b = baos.toByteArray();
                encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
                Log.d("main","encodedImage="+encodedImage);
            }catch (Exception e) {
            e.printStackTrace();
           }

//        base64 picture (String) save to firebase
        dataRef.child("picture").setValue(encodedImage);

```

### 5. 查詢 Real Time Database 資料 

```
FirebaseDatabase database = FirebaseDatabase.getInstance();
DatabaseReference databaseRef = database.getReference("message");
CustomerRef = database.getReference("Customer");
```

#### 5.1 SELECT * FROM Customer order by birthday

 CustomerRef.orderByChild("/birthday/") : 直接取得每個 birthday child location , 並以生日日期排序，由小到大
```
 CustomerRef.orderByChild("/birthday/").addValueEventListener(new ValueEventListener() {
                private List<Map<String, String>> list;

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    list = new ArrayList<Map<String,String>>();
                    for(DataSnapshot userKey : snapshot.getChildren()){
                        String userId = userKey.getKey(); //得到 user Key
                        Log.d("main","userKeybirth.getValue()="+userKey.getValue());
//                        userKeybirth.getValue()={birthday=1971/02/06, password=1qaz@wsx, user=user2, email=user2@gmail.com}
//                        userKeybirth.getValue()={birthday=1986/06/01, password=abc123, user=user4, email=user4@gmail.com}
//                        userKeybirth.getValue()={birthday=1987/07/14, password=123456, user=user1, email=user1@gmail.com}
//                        userKeybirth.getValue()={birthday=1994/03/08, password=123456, user=user5, email=user5@gmail.com}
//                        userKeybirth.getValue()={birthday=1996/11/11, password=1234561qaz, user=user3, email=user3@gmail.com}
//                      2. 以User 物件取出排序過後的資料
                        User orderedUserData = userKey.getValue(User.class);
//                      3. 將物件轉型為Map，並存入List
                        list.add(orderedUserData.ToMap());

                    }
//                  4. 以生日日期由小到大排序完的使用者資料，最外層是List，元素是Map，只能由小到大，不能由大到小
                    Log.d("main","list="+list);
//                    list=[{birthday=1971/02/06, password=1qaz@wsx, user=user2, email=user2@gmail.com},
//                    {birthday=1986/06/01, password=abc123, user=user4, email=user4@gmail.com},
//                    {birthday=1987/07/14, password=123456, user=user1, email=user1@gmail.com},
//                    {birthday=1994/03/08, password=123456, user=user5, email=user5@gmail.com},
//                    {birthday=1996/11/11, password=1234561qaz, user=user3, email=user3@gmail.com}]
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
```

#### 5.2 SELECT * FROM Customer WHERE age >35 :

```
CustomerRef.orderByChild("/birthday/").endBefore("1987-01-01").addValueEventListener(new ValueEventListener() {
                private List<User> listdata1;
                private List<Map<String, String>> listdata2;

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    Log.d("main","[endBefore(\"1987-01-01\")]snapshot.getKey()="+snapshot.getKey());
                    //Customer

                    Log.d("main","[endBefore(\"1987-01-01\")]snapshot.getValue()="+snapshot.getValue());
            //{-NC3B8P-qgRjx-OCYA_6={birthday=1971/02/06, password=1qaz@wsx, user=user2, email=user2@gmail.com},
            // -NC3ObzZJ2IKeFWVNLV4={birthday=1986/06/01, password=abc123, user=user4, email=user4@gmail.com}}
                    listdata1 = new ArrayList<User>();
                    listdata2 = new ArrayList<Map<String,String>>();

                    for (DataSnapshot querydata : snapshot.getChildren()){
//                      取Value 方法1: 由class做容器蒐集，儲存在List，再由類別個別取得不同欄位資料
                        User result=querydata.getValue(User.class);
                        listdata1.add(result);
//                      取Value 方法2: 取得Map存入List
                        listdata2.add(querydata.getValue(User.class).ToMap());
                    }
//                  由類別個別取得不同欄位資料
                    for(User user : listdata1){
                        Log.d("main","[listdata1]birthday="+user.getBirthday());
                        Log.d("main","[listdata1]password="+user.getPassword());
                        Log.d("main","[listdata1]user="+user.getUser());
                        Log.d("main","[listdata1]email="+user.getEmail());
                    }
//                  取得Map存入List
                    Log.d("main","listdata2="+listdata2);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
```

#### 5.3 `equalTo` =

```
//      key = 2 的子樹
        Query q1 = databaseRef.orderByKey().equalTo("2");
//        Log.d("main","[Query1]q1="+q1);
//        [Query1]q1=com.google.firebase.database.Query@f400730s
        q1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()){
                    Log.d("main","[Query1]getvalue="+d.getValue());
//                    output:
                    //[Query1]getvalue={id=c2, value=c}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

```

#### 5.4 `startAt` >=

```
//        key >= 2 的子樹
        Query q2 = databaseRef.orderByKey().startAt("2");
        q2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()){
                    Log.d("main","[Query2]getvalue="+d.getValue());
//                    output:
//                    [Query2]getvalue={id=c2, value=c}
//                    [Query2]getvalue={id=d3, value=d}
//                    [Query2]getvalue={id=e4, value=e}
//                    [Query2]getvalue={id=f5, value=f}
//                    [Query2]getvalue={id=g6, value=g}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
```

#### 5.5 `startAfter` >
```
//        key > 2 的子樹
        Query q4 = databaseRef.orderByKey().startAfter("2");
        q4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()) {
                    Log.d("main", "[Query4]getvalue=" + d.getValue());
//                    output:
//                    [Query4]getvalue={id=d3, value=d}
//                    [Query4]getvalue={id=e4, value=e}
//                    [Query4]getvalue={id=f5, value=f}
//                    [Query4]getvalue={id=g6, value=g}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
```
#### 5.6 `endBefore` <

```
//        key < 2 的子樹
        Query q5 = databaseRef.orderByKey().endBefore("2");
        q5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()) {
                    Log.d("main", "[Query5]getvalue=" + d.getValue());
//                    output:
//                    [Query5]getvalue={id=a0, value=a}
//                    [Query5]getvalue={id=b1, value=b}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
```

#### 5.7 合併 >,<,=,>=,<= 條件

```
//        key 介於 2 ~5 的子樹
        Query q6 = databaseRef.orderByKey().startAt("2").endAt("5");
        q6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()) {
                    Log.d("main", "[Query6]getvalue=" + d.getValue());
//                  output:
//                    [Query6]getvalue={id=c2, value=c}
//                    [Query6]getvalue={id=d3, value=d}
//                    [Query6]getvalue={id=e4, value=e}
//                    [Query6]getvalue={id=f5, value=f}
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
```


### 6. 更新 Real Time Database 資料 

```
 CustomerRef = database.getReference("Customer");
        CustomerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d : snapshot.getChildren()){
                    String key = (String) d.getKey();
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("password","123456789");
                    CustomerRef.child(key).updateChildren(map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
```

### 7. 刪除 Real Time Database 資料 

```
 CustomerRef.removeValue();// delete CustomerRef(database.getReference("Customer"))      
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

   

 
