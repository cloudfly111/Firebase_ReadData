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

//        databaseRef.child("1").setValue("abc");
//      message
//       |__ 1:"abc" (key : value)
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
//        ----------------------------------------------------
//      databaseRef.child("1").setValue("def");
//        Map<String, String> Mapdata = new HashMap<String, String>();
//        Mapdata.put("2","gh");
//        Mapdata.put("3","ij");
//        databaseRef.child("1").setValue(Mapdata);
//      message
//       |__ 1 (key : value)
//             |__ 2:"gh"
//             |__ 3:"ij"
//      ----------------------------------------------------
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
//      ----------------------------------------------------
//      Writing data into Firebase with class
//        1. a single value
//        Format dataformat = new Format("3", "fb");
//        databaseRef.child("4").setValue(dataformat);
//        2. multiple values
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
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Map<String, ArrayList<?>> dataMap = new HashMap<String, ArrayList<?>>();
                
                for(DataSnapshot data : snapshot.getChildren()){
                    String Key = data.getKey();
                    Log.d("main","databaseRef_key_snapshot="+data.getKey());
                    Object Value = data.getValue();
                    Log.d("main","Value="+Value);
//                    databaseRef_key_snapshot=1
//                    Value=[null, null, gh, ij]
//                    databaseRef_key_snapshot=2
//                    Value={3=kl, 4=mn}



//                    databaseRef_key_snapshot=1
//                    databaseRef_value_snapshot=[null, null, gh, ij]

//                    databaseRef_key_snapshot=2
//                    databaseRef_value_snapshot={3=kl, 4=mn}

//                    databaseRef_key_snapshot=4
//                    Value={id=3, value=fb}

                }
//                Log.d("main","snapshot_value="+snapshot.getValue(Format.class));
//                Log.d("main","dataformat ="+dataformat );
//                Log.d("main","id ="+dataformat.getId());
//                Log.d("main","value ="+dataformat.getValue());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("error", "Failed to read value.", error.toException());

            }
        });


        databaseRef.child("1").orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("main","Key_ordered_Data="+snapshot);
//                DataSnapshot { key = 1, value = {2=gh, 3=ij} }
//                Key_ordered_Data=DataSnapshot { key = 1, value = {id=b1, value=b} }
                for(DataSnapshot data : snapshot.getChildren()){
                    Log.d("main","Key_ordered_snapshot="+data.getValue(String.class));
//                    Key_ordered_snapshot=gh
//                    Key_ordered_snapshot=ij
//                    Key_ordered_snapshot=b1
//                    Key_ordered_snapshot=b
                    Log.d("main","Key_ordered_snapshot_="+data.getValue());
//                    Key_ordered_snapshot_=gh
//                    Key_ordered_snapshot_=ij
//                    Key_ordered_snapshot_=b1
//                    Key_ordered_snapshot_=b
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("error", "Failed to read value.", error.toException());
            }
        });



//      google example
        ValueEventListener messageListener = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Format dataFormat = snapshot.getValue(Format.class);
                Log.d("main","child1_Id = "+dataFormat.getId()); //child1_Id = b1
                Log.d("main","child1_Value = "+dataFormat.getValue()); //child1_Value = b

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };


        databaseRef.child("1").addValueEventListener(messageListener);


        ValueEventListener messageListenerv2 = new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        databaseRef.addValueEventListener();
       



    }
}