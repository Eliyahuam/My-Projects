package com.example.eliyahugalfinal.picapp.Model;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by eliba on 28/03/2017.
 */

public class ModelFireBase {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public void addPicture(Picture pic) {

        DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pictures").child("image_" + pic.descreption);
        myRef.setValue(pic.toFirebase());

        DatabaseReference myRef2 = database.getReference("allPics").child("image_" + pic.descreption);
        myRef2.setValue(pic.imageSearchtoFirebase());

    }
    public void getAllPictureByUid(double lastUpdateDate,final Model.GetAllPicturesByUidListeners listener) {
        DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pictures");
        Query query = myRef.orderByChild("lastUpdate").startAt(lastUpdateDate + 1);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Picture> pList = new LinkedList<Picture>();
                for (DataSnapshot picSnapshot: dataSnapshot.getChildren()) {
                    Picture pic = picSnapshot.getValue(Picture.class);

                    pList.add(pic);
                }
                listener.onResult(pList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAllPictureByName(String name,final Model.GetAllPicturesByUidListeners listener) {
        DatabaseReference myRef = database.getReference("allPics");
        Query query = myRef.orderByChild("imageName").equalTo(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<Picture> pList = new LinkedList<Picture>();
                for (DataSnapshot picSnapshot: dataSnapshot.getChildren()) {
                    Picture pic = picSnapshot.getValue(Picture.class);

                    pList.add(pic);
                }
                listener.onResult(pList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
