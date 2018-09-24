package com.example.eliyahugalfinal.picapp;

/**
 * Created by eliba on 20/03/2017.
 */



import android.app.Activity;
import android.app.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.eliyahugalfinal.picapp.Model.Model;
import com.example.eliyahugalfinal.picapp.Model.Picture;

import java.util.LinkedList;
import java.util.List;

import static com.example.eliyahugalfinal.picapp.rootViewTabBarActivity.MYACTIVITY_REQUEST_CODE;


public class MyAreaView extends Fragment {


    ListView myList;
    List<Picture> data = new LinkedList<Picture>();
    CustomAdapter adapter;
    TextView logout;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == 101) && (resultCode == Activity.RESULT_OK))
            loadPictureList();
    }

    public Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 50;
        int targetHeight = 50;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight,Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.my_area, container, false);
        myList = (ListView) rootView.findViewById(R.id.myAreaListView);
        adapter = new CustomAdapter();
        myList.setAdapter(adapter);
        logout = (TextView) rootView.findViewById(R.id.logOutMyArea);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        myList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(rootView.getContext(), "clicked :" + position, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(rootView.getContext(),picDetailesActivity.class);
                intent.putExtra("DESCRIPTION",data.get(position).imageName);
                intent.putExtra("IMAGE_URL",data.get(position).url);
                startActivity(intent);
            }
        });

        loadPictureList();
        return rootView;
    }



    public void loadPictureList() {
        /*
        Model.getInstance().getAllPictureByUserId(new Model.GetAllPicturesByUidListeners(){
            @Override
            public void onResult(List<Picture> pictureList) {
                data = pictureList;
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancel() {


            }
        });
        */
        Model.getInstance().getAllPicsByUid(new Model.GetAllPicturesByUidListeners(){
            @Override
            public void onResult(List<Picture> pictureList) {
                data = pictureList;
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancel() {

            }
        });
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
            LayoutInflater inflater = getActivity().getLayoutInflater();
            convertView = inflater.inflate(R.layout.my_area_cell,null);
        }
        final ImageView imageCell = (ImageView) convertView.findViewById(R.id.image_cell);
        TextView description = (TextView) convertView.findViewById(R.id.cellData);
        Picture pic = data.get(position);
        description.setText(pic.imageName);


        Model.getInstance().getImage(pic.url, new Model.GetImageListener() {
            @Override
            public void onSccess(Bitmap image) {

                    imageCell.setImageBitmap(getRoundedShape(image));
            }

            @Override
            public void onFail() {

            }
        });



        /*
        Model.getInstance().storeFireBase.getImage(pic.url, new Model.GetImageListener() {
            @Override
            public void onSccess(Bitmap image) {
                imageCell.setImageBitmap(image);
            }

            @Override
            public void onFail() {

            }
        });
*/
        return convertView;
    }
}

}
