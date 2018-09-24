package com.example.eliyahugalfinal.picapp;

/**
 * Created by eliba on 20/03/2017.
 */

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.eliyahugalfinal.picapp.Model.Model;
import com.example.eliyahugalfinal.picapp.Model.Picture;

import java.util.LinkedList;
import java.util.List;

public class SearchView  extends Fragment {

    ListView myList2;
    List<Picture> data2 = new LinkedList<Picture>();
    SearchCustomAdapter adapter;
    EditText searchText;
    ImageView searchImage;
    TextView logout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.search, container, false);
        searchText = (EditText) rootView.findViewById(R.id.searchEditText);
        searchImage = (ImageView) rootView.findViewById(R.id.searchImage);

        myList2 = (ListView) rootView.findViewById(R.id.searchListView);
        adapter = new SearchCustomAdapter();
        myList2.setAdapter(adapter);

        logout = (TextView) rootView.findViewById(R.id.logOutSearch);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(rootView.getContext(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        searchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchText.getText().toString() != "") {
                    loadPictureList(searchText.getText().toString());
                }
            }
        });

        myList2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(rootView.getContext(), "clicked :" + position, Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(rootView.getContext(),picDetailesActivity.class);
                intent.putExtra("DESCRIPTION",data2.get(position).imageName);
                intent.putExtra("IMAGE_URL",data2.get(position).url);
                startActivity(intent);
            }
        });
        return rootView;
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
    public void loadPictureList(String imageName) {
        Model.getInstance().getImageByName(imageName,new Model.GetAllPicturesByUidListeners() {

            @Override
            public void onResult(List<Picture> picList) {
                data2 = picList;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancel() {

            }
        });

    }
    class SearchCustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data2.size();
        }

        @Override
        public Object getItem(int position) {
            return data2.get(position);
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
            Picture pic = data2.get(position);
            description.setText(pic.imageName);


        Model.getInstance().storeFireBase.getImage(pic.url, new Model.GetImageListener() {
            @Override
            public void onSccess(Bitmap image) {
                imageCell.setImageBitmap(getRoundedShape(image));
            }

            @Override
            public void onFail() {

            }
        });

            return convertView;
        }
    }
}
