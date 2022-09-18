package com.example.firebase_readdata;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.math.MathContext;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

import com.example.firebase_readdata.Format;
import com.example.firebase_readdata.User;

public class MainActivity extends AppCompatActivity {

    private EditText ediTextUser, editTextPwd, editTextEmail, editTextBirth;
    private Button buttonClear, buttonSubmit;
    private boolean searchFlag;
    private String queryKey;
    private ListView listViewData;
    private DatabaseReference CustomerRef;
    private List<Map<String, String>> userData;
    private int count;
    private String encodedImage;
    private ImageView imageView;

    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("main", "FireDatabase=" + database);
//      FireDatabase=com.google.firebase.database.FirebaseDatabase@6c0dc1b

        FirebaseApp getApp = database.getApp();
        Log.d("main", "FireApp=" + getApp);
//      FirebaseApp{name=[DEFAULT], options=FirebaseOptions{applicationId=1:

        DatabaseReference dataRef = database.getReference();
        Log.d("main", "DatabaseReference=" + dataRef);
//      DatabaseReference=https://fir-project-4a35d-default-rtdb.firebaseio.com

        String dataKey = dataRef.getKey();
        Log.d("main", "key=" + dataKey);//key=null

        FirebaseDatabase data = dataRef.getDatabase();
        Log.d("main", "dataRef_database = " + data);
//      dataRef_database = com.google.firebase.database.FirebaseDatabase@b1021b8

        DatabaseReference dataParent = dataRef.getParent();
        Log.d("main", "dataParent=" + dataParent);

        DatabaseReference databaseRef = database.getReference("message");
        Log.d("main", "dataRef=" + databaseRef);
//      dataRef=https://fir-project-4a35d-default-rtdb.firebaseio.com/message

//      Writing data into Firebase-----------------------------------------------------------------
//      databaseRef.setValue("Hello World!");
//      message : Hello World!

        DatabaseReference getParent = databaseRef.getParent();
        Log.d("main", "getParent=" + getParent);
//      getParent=https://fir-project-4a35d-default-rtdb.firebaseio.com


//        ----------------------------------------------------
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

//      ----------------------------------------------------
//      Writing data into Firebase with class
//        1. a single value

//        databaseRef.child("1").setValue("abc");
//      message
//       |__ 1:"abc" (key : value)

//        2. multiple values

//      -------------------------------------------------------------------------
//      2.1 usign Map as input
//      databaseRef.child("1").setValue("def");
//        Map<String, String> Mapdata = new HashMap<String, String>();
//        Mapdata.put("2","gh");
//        Mapdata.put("3","ij");
//        databaseRef.child("1").setValue(Mapdata);
//      message
//       |__ 1 (key : value)
//             |__ 2:"gh"
//             |__ 3:"ij"

//        databaseRef.child("2").setValue("ghi");
//        Map<String, String> Mapdata2 = new HashMap<String, String>();
//        Mapdata2.put("2","kl");
//        Mapdata2.put("3","mn");
//        databaseRef.child("2").setValue(Mapdata2);
//      message
//       |__ 1 (key : value)
//       |      |__ 2:"gh"
//       |      |__ 3:"ij"
//       |__ 2 (key : value)
//             |__ 3:"kl"
//             |__ 4:"mn"
//-------------------------------------------------------------------------------
//        2.2 using object implementing self-defined class as input
//        String [] valueContent ={"a","b","c","d","e","f","g"};
//        for(int i=0; i < valueContent.length; i++){
//            String materKey = String.valueOf(i);
//            Log.d("main","masterKey="+materKey);
//            String subValue = valueContent[i];
//            Log.d("main","subValue="+subValue);
//            String subId = subValue.concat(materKey);
//            Log.d("main","subId="+subId);
//
//            Format multipleData = new Format(subId,subValue);
//            databaseRef.child(materKey).setValue(multipleData);
//        }
//      message
//       |__ 1 (key : value)
//       |      |__ id:"a0"
//       |      |__ value:"a"
//       |__ 2 (key : value)
//             |__ id:"b1"
//             |__ value:"b"
//        (一直到key : 6)


//      Read data from Firebase-----------------------------------------------------------------------
//        Query orderData = databaseRef.orderByChild("1").equalTo("ef");
//        Log.d("main","ordered_Data="+orderData);
//      ordered_Data=com.google.firebase.database.Query@431a5f1

//        String getFields = databaseRef.getClass().getFields().toString();
//        Log.d("main","getFields="+getFields);
//        getFields=[Ljava.lang.reflect.Field;@31945d6

//        Log.d("main","The length of getFields = "+databaseRef.getClass().getFields().length);
//        The length of getFields = 0

//      1. get all data in database "message"
//        1.1 return key and value (object)
//        databaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Map<String, ArrayList<?>> dataMap = new HashMap<String, ArrayList<?>>();
//
//                for(DataSnapshot data : snapshot.getChildren()){
//                    String Key = data.getKey();
//                    Log.d("main","databaseRef_key_snapshot="+data.getKey());
//                    Object Value = data.getValue();
//                    Log.d("main","Value="+Value);
////                    Map output:
////                    databaseRef_key_snapshot=1
////                    Value=[null, null, gh, ij]
////                    databaseRef_key_snapshot=2
////                    Value={3=kl, 4=mn}
//
//
////                    Map output:
////                    databaseRef_key_snapshot=1
////                    databaseRef_value_snapshot=[null, null, gh, ij]
//
////                    databaseRef_key_snapshot=2
////                    databaseRef_value_snapshot={3=kl, 4=mn}
//
////                    databaseRef_key_snapshot=4
////                    Value={id=3, value=fb}
////
////                    class output :
////                    databaseRef_key_snapshot=0
////                    Value={id=a0, value=a}
////                    databaseRef_key_snapshot=1
////                    Value={id=b1, value=b}
////                    databaseRef_key_snapshot=2
////                    Value={id=c2, value=c}
////                    databaseRef_key_snapshot=3
////                    Value={id=d3, value=d}
////                    databaseRef_key_snapshot=4
////                    Value={id=e4, value=e}
////                    databaseRef_key_snapshot=5
////                    Value={id=f5, value=f}
////                    databaseRef_key_snapshot=6
////                    Value={id=g6, value=g}
//
//
//                }

//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("error", "Failed to read value.", error.toException());
//
//            }
//        });


        //      1.2. return key and value (String)
//        databaseRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d2 : snapshot.getChildren()){
//                    String datakey = d2.getKey();
//                    Format dataValue = d2.getValue(Format.class);
//                    Log.d("main","[Format.class]key="+datakey);
//                    Log.d("main","[Format.class]value_1="+dataValue.getId());
//                    Log.d("main","[Format.class]value_2="+dataValue.getValue());
//////                    [Format.class]key=0
//////                    [Format.class]value_1=a0
//////                    [Format.class]value_2=a
//////                    [Format.class]key=1
//////                    [Format.class]value_1=b1
//////                    .
//////                    .
//////                    [Format.class]key=6
//////                    [Format.class]value_1=g6
//////                    [Format.class]value_2=g
////
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });

//        2. In specific child ,return key and value (object)
//        databaseRef.child("1").orderByKey().addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Log.d("main","Key_ordered_Data="+snapshot);
////                    Map output:
////                DataSnapshot { key = 1, value = {2=gh, 3=ij} }
//
////                class output :
////                Key_ordered_Data=DataSnapshot { key = 1, value = {id=b1, value=b} }
//                for(DataSnapshot data : snapshot.getChildren()){
//                    Log.d("main","Key_ordered_snapshot="+data.getValue(String.class));
////                    Map output:
////                    Key_ordered_snapshot=gh
////                    Key_ordered_snapshot=ij
//
////                    class output :
////                    Key_ordered_snapshot=b1
////                    Key_ordered_snapshot=b
//                    Log.d("main","Key_ordered_snapshot_="+data.getValue());
////                    Map output:
////                    Key_ordered_snapshot_=gh
////                    Key_ordered_snapshot_=ij
//
////                    class output :
////                    Key_ordered_snapshot_=b1
////                    Key_ordered_snapshot_=b
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("error", "Failed to read value.", error.toException());
//            }
//        });

//

//     example in Firebase document--------------------------------------------------
//       1. retun all key in the child
//        ValueEventListener messageListener = new ValueEventListener() {
//
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
////              取出下一階層中的所有key
//                int count = 0;
//                for(DataSnapshot d : snapshot.getChildren()){
//                    if(d.exists()){
//                        Log.d("main", "["+count+"]"+"key=" + d.getKey());
//                        count = count + 1;
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        };
//
//        databaseRef.child("1").addValueEventListener(messageListener);
////        //child1_Id = b1
////        //child1_Value = b
////                  output :
////                    [0]key=id
////                    [1]key=value
//        databaseRef.addValueEventListener(messageListener);
//                    [0]key=0
//                    [1]key=1
//                    [2]key=2
//                    [3]key=3
//                    [4]key=4
//                    [5]key=5
//                    [6]key=6
//---------------------------------------------------------------
//      3. Query data from Firebase
//      key = 2 的子樹
//        Query q1 = databaseRef.orderByKey().equalTo("2");
//        Log.d("main","[Query1]q1="+q1);
////        [Query1]q1=com.google.firebase.database.Query@f400730s
//        q1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d : snapshot.getChildren()){
//                    Log.d("main","[Query1]getvalue="+d.getValue());
////                    output:
//                    //[Query1]getvalue={id=c2, value=c}
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
////        key >= 2 的子樹
//        Query q2 = databaseRef.orderByKey().startAt("2");
//        q2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d : snapshot.getChildren()){
//                    Log.d("main","[Query2]getvalue="+d.getValue());
////                    output:
////                    [Query2]getvalue={id=c2, value=c}
////                    [Query2]getvalue={id=d3, value=d}
////                    [Query2]getvalue={id=e4, value=e}
////                    [Query2]getvalue={id=f5, value=f}
////                    [Query2]getvalue={id=g6, value=g}
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
////        key <= 2 的子樹
//        Query q3 = databaseRef.orderByKey().endAt("2");
//        q3.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d : snapshot.getChildren()) {
//                    Log.d("main", "[Query3]getvalue=" + d.getValue());
////                    output :
////                    [Query3]getvalue={id=a0, value=a}
////                    [Query3]getvalue={id=b1, value=b}
////                    [Query3]getvalue={id=c2, value=c}
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
////        key > 2 的子樹
//        Query q4 = databaseRef.orderByKey().startAfter("2");
//        q4.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d : snapshot.getChildren()) {
//                    Log.d("main", "[Query4]getvalue=" + d.getValue());
////                    output:
////                    [Query4]getvalue={id=d3, value=d}
////                    [Query4]getvalue={id=e4, value=e}
////                    [Query4]getvalue={id=f5, value=f}
////                    [Query4]getvalue={id=g6, value=g}
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
////        key < 2 的子樹
//        Query q5 = databaseRef.orderByKey().endBefore("2");
//        q5.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d : snapshot.getChildren()) {
//                    Log.d("main", "[Query5]getvalue=" + d.getValue());
////                    output:
////                    [Query5]getvalue={id=a0, value=a}
////                    [Query5]getvalue={id=b1, value=b}
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
////        key 介於 2 ~5 的子樹
//        Query q6 = databaseRef.orderByKey().startAt("2").endAt("5");
//        q6.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d : snapshot.getChildren()) {
//                    Log.d("main", "[Query6]getvalue=" + d.getValue());
////                  output:
////                    [Query6]getvalue={id=c2, value=c}
////                    [Query6]getvalue={id=d3, value=d}
////                    [Query6]getvalue={id=e4, value=e}
////                    [Query6]getvalue={id=f5, value=f}
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
////        key 小於 2 或 大於5 的子樹
//        Query q7_1 = databaseRef.orderByKey().endBefore("2");
//        Query q7_2 = databaseRef.orderByKey().startAfter("5");
////      Method 1 : two Listener
//        q7_1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d : snapshot.getChildren()) {
//                    Log.d("main", "[Query7_1]getvalue=" + d.getValue());
////                    output:
////                    [Query7_1]getvalue={id=a0, value=a}
////                    [Query7_1]getvalue={id=b1, value=b}
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        q7_2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot d : snapshot.getChildren()) {
//                    Log.d("main", "[Query7_2]getvalue=" + d.getValue());
////                    output:
////                    [Query7_2]getvalue={id=g6, value=g}
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
////      Method 2 : combine two query into Task
//        Task<DataSnapshot> firstTask = q7_1.get();
//        Task<DataSnapshot> secondTask = q7_2.get();
//        Task<List<Object>> combineTask = Tasks.whenAllSuccess(firstTask, secondTask);
//        combineTask.addOnSuccessListener(new OnSuccessListener<List<Object>>() {
//            @Override
//            public void onSuccess(List<Object> objects) {
//                for(Object d : objects){
//                    Log.d("main","d="+d);
////                  output:
////                    d=DataSnapshot { key = message, value = {0={id=a0, value=a}, 1={id=b1, value=b}} }
////                    d=DataSnapshot { key = mssage, value = {6={id=g6, value=g}} }
//                    DataSnapshot ds = (DataSnapshot) d;
//                    Log.d("main","ds="+ds.getValue());
//
//
//                }
//            }
//        });


//      Test for delete data------------------------------------------------
//      1. create new table "Customer"
//      database.getReference("Customer") : get the location of Customer key.
       CustomerRef = database.getReference("Customer");
//        Log.d("main","database.getReference(\"Customer\")="+CustomerRef);
//      https://fir-project-4a35d-default-rtdb.firebaseio.com/Customer/

//        CustomerRef.child("user").setValue(100); // add a data (key=user , value=100)
//        Customer
//          |__ user : 100

//        Delete table Customer------------------------------------------------------
//        CustomerRef.removeValue();// delete CustomerRef(database.getReference("Customer"))
//        CustomerRef.child("user").getRef().removeValue();// delete CustomerRef , the same output as CustomerRef.removeValue()

//        [V] try to update child
//        Conclusion : cannot only delete a data( user : 100 )

//        [The Methods of class "DatabaseReference"]
//        getRoot() : 取得資料庫網址
//        getParent() : 取得指向上一層key資料庫網址
//        getRef() : 取得指向目前階層key的資料庫網址
//        DatabaseReference getRoot = CustomerRef.child("user").getRoot();
//        Log.d("main","getRoot="+getRoot);
//      CustomerRef.child("user").getRoot(); // https://fir-project-4a35d-default-rtdb.firebaseio.com/
//      CustomerRef.child("user").getParent() // https://fir-project-4a35d-default-rtdb.firebaseio.com/Customer/
//      CustomerRef.child("user").getRef() // https://fir-project-4a35d-default-rtdb.firebaseio.com/Customer/user

//      Test for searching value --------------------------------------------------------------------------------

//      create table "Customer" for example
//        DatabaseReference CustomerRef = database.getReference("Customer");
//        CustomerRef.removeValue(); // delete table "Customer"
//      get input from editText and store it into table "Customer"
        ediTextUser = (EditText) findViewById(R.id.editText_user);
        editTextPwd = (EditText) findViewById(R.id.editText_password);
        editTextEmail = (EditText) findViewById(R.id.editText_email);
        editTextBirth = (EditText) findViewById(R.id.editText_birthday);
//      get the button to clear or submit data
        buttonClear = (Button) findViewById(R.id.button_clear);
        buttonSubmit = (Button) findViewById(R.id.button_submit);

//      listView for displaying all firebase data
        listViewData = (ListView) findViewById(R.id.listView_id );
        CustomerRef.addValueEventListener(new ValueEventListener() {
            private HashMap<String, String> Mapdata;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userData = new ArrayList<Map<String, String>>();
                for(DataSnapshot userKay:snapshot.getChildren()){
                    Log.d("main","userKay.getValue = "+userKay.getValue());
                    Mapdata = new HashMap<String, String>();
                    if (userKay.exists()){
                        Log.d("main","userKay.exists()="+userKay.exists());
                        User userDataClass = userKay.getValue(User.class);
                        Mapdata.put("user",userDataClass.getUser());
                        Mapdata.put("password",userDataClass.getPassword());
                        Mapdata.put("email",userDataClass.getEmail());
                        Mapdata.put("birthday",userDataClass.getBirthday());

                    }else{
                        String defaultStr = "";
                        Mapdata.put("user",defaultStr);
                        Mapdata.put("password",defaultStr);
                        Mapdata.put("email",defaultStr);
                        Mapdata.put("birthday",defaultStr);
                    }
                    userData.add(Mapdata);
                }
                SimpleAdapter adapter = new SimpleAdapter(MainActivity.this, userData, R.layout.listview_layout
                        , new String[]{"user", "password", "email", "birthday"}
                        , new int[]{R.id.textView_list_user, R.id.textView_list_pwd, R.id.textView_list_email, R.id.textView_list_birthday});
                adapter.notifyDataSetChanged();
                listViewData.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




//      buttonClear : clear the input of editText
        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ediTextUser.length() > 0) {
                    ediTextUser.setText("");
                }
                if (editTextPwd.length() > 0) {
                    editTextPwd.setText("");
                }
                if (editTextEmail.length() > 0) {
                    editTextEmail.setText("");
                }
                if (editTextBirth.length() > 0) {
                    editTextBirth.setText("");
                }
            }
        });
//      buttonSubmit : submit the input of editText
        buttonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ediTextUser.length() > 0 && editTextPwd.length() > 0
                        && editTextEmail.length() > 0 && editTextBirth.length() > 0) {
                    String user = ediTextUser.getText().toString();
                    String password = editTextPwd.getText().toString();
                    String email = editTextEmail.getText().toString();
                    String birthday = editTextBirth.getText().toString();

                    User data = new User(user,password,email,birthday);
//                  method 1 :
//                    CustomerRef.setValue(data);
//                  method 2 :
                    CustomerRef.push().setValue(data);
                }
            }
        });

//       create 8 user data with unique key

//        List<String> idList = new ArrayList<String>();

//        ["SELECT * FROM Customer WHERE user = "user3"]-------------------------------------------------------------

//       1. get the unique key of user(就是那一長串亂碼)
//         CustomerRef.orderByKey().addValueEventListener(new ValueEventListener() {
//
//             @Override
//             public void onDataChange(@NonNull DataSnapshot snapshot) {
//                 for(DataSnapshot userData : snapshot.getChildren()){
//                     String userId = userData.getKey();
//                     searchFlag = false;
////                     idList.add(userId);
////                   2. using the unique key of user to access user data
////                     指向user key
//                     DatabaseReference userRef = CustomerRef.child(userId);
//                     Log.d("main","userRef="+userRef);
////                   userRef=https://fir-project-4a35d-default-rtdb.firebaseio.com/Customer/-NByymlMDWI1QEo9JC4G
//
////                   3. execute query like "SELECT * FROM Customer WHERE user = "user3"
////
//                     DatabaseReference Parent = userRef.orderByValue().equalTo("user3", "user").getRef().getParent();
//                     Log.d("main","DataReference_parent="+Parent);
////                   DataReference_parent= https://fir-project-4a35d-default-rtdb.firebaseio.com/Customer/
//                     DatabaseReference Ref = userRef.orderByValue().equalTo("user3", "user").getRef();
//                     Log.d("main","DataReference_Ref="+Ref);//取出 userRef 指向的key的位置
////                   DataReference_Ref= https://fir-project-4a35d-default-rtdb.firebaseio.com/Customer/-NByxXSDMXe_JwLJ8l1i
//
////------------------------------------------------------------------------------------------------------------------
////                     3.1 [Test 1] 只抓到user:user3的節點QQ
////                     userRef.orderByValue().equalTo("user3", "user")
////                             .get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
////                         @Override
////                         public void onSuccess(DataSnapshot dataSnapshot) {
//////                           每次由 user id 進入 user 資料的時候，DataSnapshot都會有傳回值，沒有查詢到就傳回null，否則傳回查詢結果
//////                             Log.d("main","["+userId+"]"+"output="+dataSnapshot.getValue());
//////                             [-NByxXSDMXe_JwLJ8l1i]output=null
//////                             [-NByxe7aiWvow4qRTaoZ]output=null
//////                             [-NByxl1501U_xU0lewHi]output={user=user3}
//////                             [-NByxxKBIYAqt0drWuyx]output=null
//////                             [-NByyA4xbF8eswxQthgd]output=null
//////                             [-NByydYM2B1xzEx6YqqB]output=null
//////                             [-NByymlMDWI1QEo9JC4G]output=null
//////                             [-NByyw8isemRha_Fyhbk]output=null
////                             if(dataSnapshot.exists()){ // when dataSnapshot is not null
////                                 Log.d("main","useExportFormat(True)="+dataSnapshot.getValue(true));
//////                                 useExportFormat(True)={user=user3}
////                                 Log.d("main","useExportFormat(False)="+dataSnapshot.getValue(false));
//////                                 useExportFormat(False)={user=user3}
////                                 User queryOuput= dataSnapshot.getValue(User.class);
////                                 String userAccount = queryOuput.getUser();
////                                 String userpwd = queryOuput.getPassword();
////                                 Long userAge = queryOuput.getAge();
////                                 String userbirth = queryOuput.getBirthday();
////                                 Log.d("main","userAccount="+userAccount);
////                                 Log.d("main","userpwd="+userpwd);
////                                 Log.d("main","userAge="+userAge);
////                                 Log.d("main","userbirth="+userbirth);
////
////                                 Log.d("main","["+userId+"]"+"output="+dataSnapshot.getValue());
////                             }
////
////
////                         }
////                     });
////                     3.2 [Test 2] 抓到user:user3的key之後，取得此key的value，value用User.class取出資料
//                     userRef.orderByValue().equalTo("user3","user")
//                             .get().addOnSuccessListener(new OnSuccessListener<DataSnapshot>() {
//                                 @Override
//                                 public void onSuccess(DataSnapshot dataSnapshot) {
//                                     if(dataSnapshot.exists()) { // 當有查詢到user:user3時(不是回傳null時)，否則dataSnapshot會傳回null
//                                         searchFlag = true;
//                                         Log.d("main","searchFlag="+searchFlag);
//                                         //3.2.1 抓到user:user3的key
//                                         queryKey = dataSnapshot.getKey();
//                                         Log.d("main","queryKey="+queryKey);
//
//                                         Object queryValue = dataSnapshot.getValue();
//                                         Log.d("main","queryValue="+queryValue);
//
//                                     }
//                                 }
//                             });//the end of userRef.orderByValue().equalTo("user3","user").get().addOnSuccessListener
//
//                 }
//
//
//             }//the end of onDataChange
//
//             @Override
//             public void onCancelled(@NonNull DatabaseError error) {
//
//             }
//         });
        // 3.2.2 取得此key的value
//        CustomerRef.child("-NByxl1501U_xU0lewHi").addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    Log.d("main","snapshot.getValue()="+snapshot.getValue());
//                    User queryOuput= snapshot.getValue(User.class);
//                    String userAccount = queryOuput.getUser(); //userAccount=user3
//                    String userpwd = queryOuput.getPassword(); //userpwd=h83649fjfk
//                    String userEmail = queryOuput.getEmail();//userAge=50
//                    String userbirth = queryOuput.getBirthday();//userbirth=1976/11/10
//                    Log.d("main","userAccount="+userAccount);
//                    Log.d("main","userpwd="+userpwd);
//                    Log.d("main","userEmail="+userEmail);
//                    Log.d("main","userbirth="+userbirth);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });// the end of equalTo(queryKey).addValueEventListener
//           ["SELECT * FROM Customer WHERE age >35 ]-------------------------------------------------------------
//          測試取得日期小於 1987-01-01 的生日和使用者id
//        CustomerRef.orderByChild("/birthday/").endBefore("1987-01-01") :
//        直接取得每個 birthday child location , 並以value排序，取出日期小於1987-01-01的使用者ID和所有個人資料
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
//           ["SELECT * FROM Customer order by birthday ]-------------------------------------------------------------
//        1. CustomerRef.orderByChild("/birthday/") : 直接取得每個 birthday child location , 並以生日日期排序，由小到大
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

//      get base64 picture (String) from firebase and set in ImageView
        imageView = (ImageView)findViewById(R.id.imageView);
        dataRef.child("picture").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot pic : snapshot.getChildren()){
                    String base64Pic = (String)snapshot.getValue();
                    Log.d("main","base64Pic="+base64Pic);
                    byte[] decodedString = Base64.decode(base64Pic, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                    Drawable d = new BitmapDrawable(getResources(), decodedByte);
                    imageView.setImageBitmap(decodedByte);
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // update data






    }



}