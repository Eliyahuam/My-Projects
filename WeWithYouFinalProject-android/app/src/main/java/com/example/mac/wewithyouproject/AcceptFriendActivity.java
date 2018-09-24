package com.example.mac.wewithyouproject;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AcceptFriendActivity extends AppCompatActivity {
    private ListView myList;
    private List<Friend> data = new LinkedList<Friend>();
    FriendCustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accept_friend);
        myList = (ListView) findViewById(R.id.acceptfriendlist_view);
        adapter = new FriendCustomAdapter();
        myList.setAdapter(adapter);
        loadData();

    }
    private void loadData() {
        FireBaseModel dataToLoad = new FireBaseModel();
        dataToLoad.getFriendRequestList(FirebaseAuth.getInstance().getCurrentUser().getUid(), new FireBaseModel.getFriendListListener() {
            @Override
            public void onSuccess(List<Friend> friendList) {
                data = friendList;
                adapter.notifyDataSetChanged();
            }
        });
    }


    class FriendCustomAdapter extends BaseAdapter {



        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.accept_friend_cell,null);
            }
            final ImageView imageCell = (ImageView) convertView.findViewById(R.id.image_accept_cell);
            MyFireBaseStorage imageFromStore = new MyFireBaseStorage();
            TextView acceptbtn = (TextView) convertView.findViewById(R.id.acceptbtn);
            TextView declinebtn = (TextView) convertView.findViewById(R.id.declinebtn);
            final TextView emailName = (TextView) convertView.findViewById(R.id.email_text_view);
            Friend f = data.get(position);
            imageFromStore.getImage(f.imageUrl, new MyFireBaseStorage.GetImageListener() {
                @Override
                public void onSccess(Bitmap image) {
                    imageCell.setImageBitmap(image);
                }

                @Override
                public void onFail() {

                }
            });
            emailName.setText(f.email);

            if (f.addedByUid.equals(FirebaseAuth.getInstance().getCurrentUser().getUid().toString())) {
                acceptbtn.setEnabled(false);
                acceptbtn.setText("Waiting");
                acceptbtn.setTextColor(Color.parseColor("#2E2E2E"));
            }
                acceptbtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        final FireBaseModel search = new FireBaseModel();
                        search.findFriendByEmail(emailName.getText().toString(), new FireBaseModel.getFriendListener() {
                            @Override
                            public void onSccess(Friend friend) {
                                final String friendUid = friend.uid;
                                search.findFriendByEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail(), new FireBaseModel.getFriendListener() {
                                    @Override
                                    public void onSccess(Friend friend) {
                                        String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("users").child(friendUid).child("myfriends").child(s);
                                        Map values = new HashMap();
                                        values.put("accepted","Y");
                                        myRef.updateChildren(values);
                                        loadData();
                                    }
                                });

                                String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                DatabaseReference myRef2 = database2.getReference("users").child(s).child("myfriends").child(friendUid);
                                Map values2 = new HashMap();
                                values2.put("accepted","Y");
                                myRef2.updateChildren(values2);
                                Toast.makeText(AcceptFriendActivity.this, friend.email + " added", Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();
                            }
                        });

                    }
                });






            acceptbtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final FireBaseModel search = new FireBaseModel();
                    search.findFriendByEmail(emailName.getText().toString(), new FireBaseModel.getFriendListener() {
                        @Override
                        public void onSccess(Friend friend) {
                            final String friendUid = friend.uid;
                            search.findFriendByEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail(), new FireBaseModel.getFriendListener() {
                                @Override
                                public void onSccess(Friend friend) {
                                    String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("users").child(friendUid).child("myfriends").child(s);
                                    Map values = new HashMap();
                                    values.put("accepted","Y");
                                    myRef.updateChildren(values);
                                    loadData();
                                }
                            });

                            String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                            DatabaseReference myRef2 = database2.getReference("users").child(s).child("myfriends").child(friendUid);
                            Map values2 = new HashMap();
                            values2.put("accepted","Y");
                            myRef2.updateChildren(values2);
                            Toast.makeText(AcceptFriendActivity.this, friend.email + " added", Toast.LENGTH_LONG).show();
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
            });
            declinebtn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    final FireBaseModel search = new FireBaseModel();
                    search.findFriendByEmail(emailName.getText().toString(), new FireBaseModel.getFriendListener() {
                        @Override
                        public void onSccess(Friend friend) {
                            final String friendUid = friend.uid;
                            search.findFriendByEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail(), new FireBaseModel.getFriendListener() {
                                @Override
                                public void onSccess(Friend friend) {
                                    String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("users").child(friendUid).child("myfriends").child(s);
                                    Map values = new HashMap();
                                    values.put("accepted","R");
                                    myRef.updateChildren(values);
                                    loadData();
                                }
                            });

                            String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                            DatabaseReference myRef2 = database2.getReference("users").child(s).child("myfriends").child(friendUid);
                            Map values2 = new HashMap();
                            values2.put("accepted","R");
                            myRef2.updateChildren(values2);
                            Toast.makeText(AcceptFriendActivity.this, friend.email + " added", Toast.LENGTH_LONG).show();
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
            });

            return convertView;
        }
    }
}
