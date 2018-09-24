package com.example.mac.wewithyouproject;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class SecondViewActivity extends AppCompatActivity {

    private Button addFriendBtn;
    private EditText et;
    private ListView myList;
    private List<Friend> data = new LinkedList<Friend>();

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_view);

        addFriendBtn = (Button) findViewById(R.id.add_friend);
        et = (EditText) findViewById(R.id.search_email_tv);
        myList = (ListView) findViewById(R.id.friendlist_view);
        addFriend();

        adapter = new CustomAdapter();
        myList.setAdapter(adapter);

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {





                //need to implement get location from firebase

                FireBaseModel locData = new FireBaseModel();
                locData.getLocation(data.get(position).uid, new FireBaseModel.getLocationListener() {

                    @Override
                    public void onSuccess(com.example.mac.wewithyouproject.Location l) {
                        if (l.x != null) {
                            Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                            intent.putExtra("coordinateX",l.x + "");
                            intent.putExtra("coordinateY",l.y + "");
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(SecondViewActivity.this, "No location found", Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });

        loadData();
    }

    private void loadData() {
        FireBaseModel dataToLoad = new FireBaseModel();
        dataToLoad.getFriendList(FirebaseAuth.getInstance().getCurrentUser().getUid(), new FireBaseModel.getFriendListListener() {
            @Override
            public void onSuccess(List<Friend> friendList) {
                data = friendList;
                adapter.notifyDataSetChanged();
            }
        });
    }
    private void addFriend() {
        addFriendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!et.getText().toString().equals("")) {



                    final FireBaseModel search = new FireBaseModel();
                    search.findFriendByEmail(et.getText().toString(), new FireBaseModel.getFriendListener() {
                        @Override
                        public void onSccess(Friend friend) {

                            if (friend.email == null) {
                                Toast.makeText(SecondViewActivity.this, et.getText().toString() + "email not found search again", Toast.LENGTH_LONG).show();
                                et.setText("");

                            } else {
                                final String friendUid = friend.uid;


                                search.findFriendByEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail(), new FireBaseModel.getFriendListener() {
                                    @Override
                                    public void onSccess(Friend friend) {
                                        String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                        String m = FirebaseAuth.getInstance().getCurrentUser().getEmail();
                                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                                        DatabaseReference myRef = database.getReference("users").child(friendUid).child("myfriends").child(s);
                                        Map values = new HashMap();
                                        values.put("addedByUid",s);
                                        values.put("uid", s);
                                        values.put("email", m);
                                        values.put("imageUrl",friend.imageUrl);
                                        values.put("accepted","N");
                                        myRef.updateChildren(values);
                                        loadData();
                                    }
                                });



                                String s = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                FirebaseDatabase database2 = FirebaseDatabase.getInstance();
                                DatabaseReference myRef2 = database2.getReference("users").child(s).child("myfriends").child(friendUid);
                                Map values2 = new HashMap();
                                values2.put("addedByUid",s);
                                values2.put("uid", friend.uid);
                                values2.put("email",friend.email);
                                values2.put("imageUrl",friend.imageUrl);
                                values2.put("accepted","N");
                                myRef2.updateChildren(values2);
                                et.setText("");

                                Toast.makeText(SecondViewActivity.this,"Friend request sent to: " + friend.email, Toast.LENGTH_LONG).show();
                                adapter.notifyDataSetChanged();
                            }

                        }
                    });

                }
                else {
                    Toast.makeText(SecondViewActivity.this, "please enter email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
    class CustomAdapter extends BaseAdapter {

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
                convertView = inflater.inflate(R.layout.friend_cell,null);
            }
            final ImageView imageCell = (ImageView) convertView.findViewById(R.id.image_cell);
            MyFireBaseStorage imageFromStore = new MyFireBaseStorage();

            TextView emailName = (TextView) convertView.findViewById(R.id.cellData);
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
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.accept_friend:
                startActivity(new Intent(this,AcceptFriendActivity.class));
                return true;
            case R.id.logout:
                finish();
                startActivity(new Intent(this,LoginActivity.class));
                FirebaseAuth.getInstance().signOut();
                return true;
            case R.id.reset:
                FirebaseAuth.getInstance().sendPasswordResetEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
                Toast.makeText(this, "Email to reset password sent", Toast.LENGTH_LONG).show();

        }
        return super.onOptionsItemSelected(item);
    }

}
