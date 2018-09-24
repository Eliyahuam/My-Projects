package com.example.mac.wewithyouproject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by mac on 25.4.2017.
 */


public class FireBaseModel {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    public interface getFriendListener{
        void onSccess(Friend friend);
    }
    public interface  getFriendListListener {
        void onSuccess(List<Friend> friendList);
    }
    public interface  getLocationListener {
        void onSuccess(Location l);
    }
    public interface  getImageUrlListener {
        void onSuccess(String url);
    }

    public void findFriendByEmail(String email, final getFriendListener listener) {
        DatabaseReference myRef = database.getReference("users");
        Query query = myRef.orderByChild("email").equalTo(email);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Friend f =new Friend();
                for (DataSnapshot freindSnap: dataSnapshot.getChildren()) {
                     f = freindSnap.getValue(Friend.class);
                }
                listener.onSccess(f);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getFriendList(String uid,final getFriendListListener listListener) {
        DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("myfriends");
        Query query = myRef.orderByChild("accepted").equalTo("Y");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<Friend> friendList = new LinkedList<Friend>();
                for (DataSnapshot freindSnap: dataSnapshot.getChildren()) {

                     Friend f = freindSnap.getValue(Friend.class);
                    friendList.add(f);
                }
                listListener.onSuccess(friendList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void getFriendRequestList(String uid,final getFriendListListener listListener) {
        DatabaseReference myRef = database.getReference("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("myfriends");
        Query query = myRef.orderByChild("accepted").equalTo("N");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                final List<Friend> friendList = new LinkedList<Friend>();
                for (DataSnapshot freindSnap: dataSnapshot.getChildren()) {

                    Friend f = freindSnap.getValue(Friend.class);
                    friendList.add(f);
                }
                listListener.onSuccess(friendList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    public void getImageUrlFromFireBase(String uid, final  getImageUrlListener listener) {

        DatabaseReference myRef = database.getReference("users").child(uid);
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            String imageUrl;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                imageUrl = dataSnapshot.getValue(String.class);
                listener.onSuccess(imageUrl);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
    public void getLocation(String uid,final getLocationListener listener) {
        DatabaseReference myRef = database.getReference("users").child(uid).child("lastlocation");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            Location l = new Location();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot freindSnap: dataSnapshot.getChildren()) {

                     l = freindSnap.getValue(Location.class);


                }
                listener.onSuccess(l);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
