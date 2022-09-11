package com.example.firebase_readdata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.math.MathContext;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.firebase_readdata.Format;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        Log.d("main","FireDatabase="+ database);
//      FireDatabase=com.google.firebase.database.FirebaseDatabase@6c0dc1b

        FirebaseApp getApp = database.getApp();
        Log.d("main","FireApp="+ getApp);
//      FirebaseApp{name=[DEFAULT], options=FirebaseOptions{applicationId=1:

        DatabaseReference dataRef = database.getReference();
        Log.d("main","DatabaseReference="+ dataRef);
//      DatabaseReference=https://fir-project-4a35d-default-rtdb.firebaseio.com

        String dataKey = dataRef.getKey();
        Log.d("main","key=" + dataKey);//key=null

        FirebaseDatabase data = dataRef.getDatabase();
        Log.d("main","dataRef_database = "+data);
//      dataRef_database = com.google.firebase.database.FirebaseDatabase@b1021b8

        DatabaseReference dataParent = dataRef.getParent();
        Log.d("main","dataParent="+dataParent);

        DatabaseReference databaseRef = database.getReference("message");
        Log.d("main","dataRef="+databaseRef);
//      dataRef=https://fir-project-4a35d-default-rtdb.firebaseio.com/message

//      Writing data into Firebase-----------------------------------------------------------------
//      databaseRef.setValue("Hello World!");
//      message : Hello World!

        DatabaseReference getParent = databaseRef.getParent();
        Log.d("main","getParent="+getParent);
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

        Log.d("main","The length of getFields = "+databaseRef.getClass().getFields().length);
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
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d2 : snapshot.getChildren()){
                    String datakey = d2.getKey();
                    Format dataValue = d2.getValue(Format.class);
                    Log.d("main","[Format.class]key="+datakey);
                    Log.d("main","[Format.class]value_1="+dataValue.getId());
                    Log.d("main","[Format.class]value_2="+dataValue.getValue());
//                    [Format.class]key=0
//                    [Format.class]value_1=a0
//                    [Format.class]value_2=a
//                    [Format.class]key=1
//                    [Format.class]value_1=b1
//                    .
//                    .
//                    [Format.class]key=6
//                    [Format.class]value_1=g6
//                    [Format.class]value_2=g

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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
        ValueEventListener messageListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

//              取出下一階層中的所有key
                int count = 0;
                for(DataSnapshot d : snapshot.getChildren()){
                    if(d.exists()){
                        Log.d("main", "["+count+"]"+"key=" + d.getKey());
                        count = count + 1;
                    }

                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseRef.child("1").addValueEventListener(messageListener);
//        //child1_Id = b1
//        //child1_Value = b
//                  output :
//                    [0]key=id
//                    [1]key=value
        databaseRef.addValueEventListener(messageListener);
//                    [0]key=0
//                    [1]key=1
//                    [2]key=2
//                    [3]key=3
//                    [4]key=4
//                    [5]key=5
//                    [6]key=6







    }
}