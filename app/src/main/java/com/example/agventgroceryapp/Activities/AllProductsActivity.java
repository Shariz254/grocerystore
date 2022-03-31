package com.example.agventgroceryapp.Activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.agventgroceryapp.Adapters.PopularProductsAdapter;
import com.example.agventgroceryapp.Models.PopularProductsModel;
import com.example.agventgroceryapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class AllProductsActivity extends AppCompatActivity {

    PopularProductsAdapter popularProductsAdapter;
    ArrayList<PopularProductsModel> popularProductsData;

    RecyclerView allProductsRecyclerView;
    FirebaseFirestore db;
    Activity act;
    ProgressDialog pd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);

        pd = new ProgressDialog(this);
        pd.setCancelable(false);
        pd.setMessage("Processing Data..");
        pd.show();


        act = this;

        allProductsRecyclerView = findViewById(R.id.allProductsRecyclerView);
        allProductsRecyclerView.setHasFixedSize(true);
        allProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        allProductsRecyclerView.setAdapter(popularProductsAdapter);


        db = FirebaseFirestore.getInstance();
        popularProductsData = new ArrayList<PopularProductsModel>();
        popularProductsAdapter = new PopularProductsAdapter(AllProductsActivity.this, popularProductsData);

        EventChangeListener();
    }

    private void EventChangeListener() {

        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, "onComplete: data = "+document.get());
                                PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
                                //Log.e("-->", "check" +document.toObject(PopularProductsModel.class));
                                popularProductsData.add(popularProductsModel);
                                popularProductsAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(act, "Error>" +task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

//        db.collection("PopularProducts").addSnapshotListener(new EventListener<QuerySnapshot>() {
//            @Override
//            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
//
//                if (error != null){
//
//                    if (pd.isShowing())
//                        pd.dismiss();
//
//                    Log.e("Firestone Error", error.getMessage());
//                    return;
//                }
//
//                for (DocumentChange dc : value.getDocumentChanges()){
//
//                    if (dc.getType() == DocumentChange.Type.ADDED){
//
//                        popularProductsData.add(dc.getDocument().toObject(PopularProductsModel.class));
//
//                    }
//
//                    popularProductsAdapter.notifyDataSetChanged();
//                    if (pd.isShowing())
//                        pd.dismiss();
//                }
//
//
//            }
//        });
    }

//    void intUI(){
//        allProductsRecyclerView = findViewById(R.id.allProductsRecyclerView);
//    }
//
//    void populate(){
////        popularProductsData = new ArrayList<>();
//        popularProductsData = loadProducts();
//        allProductsRecyclerView.setAdapter(new PopularProductsAdapter(popularProductsData));
//        allProductsRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
//
//    }
//
//    public ArrayList<PopularProductsModel> loadProducts(){
//        ArrayList<PopularProductsModel> popularProductsData = new ArrayList<>();
//
//        db.collection("PopularProducts")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                PopularProductsModel popularProductsModel = document.toObject(PopularProductsModel.class);
//                                popularProductsData.add(popularProductsModel);
//                                popularProductsAdapter.notifyDataSetChanged();
//                            }
//                        } else {
//                            Toast.makeText(act, "Error>" +task.getException().toString(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//        return popularProductsData;
//    }
}