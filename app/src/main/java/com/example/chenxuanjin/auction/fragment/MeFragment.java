package com.example.chenxuanjin.auction.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chenxuanjin.auction.BoughtGoodsActivity;
import com.example.chenxuanjin.auction.LoginActivity;
import com.example.chenxuanjin.auction.MyCollectionActivity;
import com.example.chenxuanjin.auction.PersonalGoodsListActivity;
import com.example.chenxuanjin.auction.R;
import com.example.chenxuanjin.auction.SellOutGoodsActivity;
import com.example.chenxuanjin.auction.SetupActivity;
import com.example.chenxuanjin.auction.bean.MyUser;
import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static com.example.chenxuanjin.auction.utils.ImageCompression.compressBySize;
import static com.example.chenxuanjin.auction.utils.ImageCompression.saveBitmapToFile;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE_PICK_IMAGE = 0xa0;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private TextView display_user;
    private ImageView header;
    private Uri uri1;
    private LinearLayout publishLayout,sellOutLayout,boughtLayout,myLikeLayout,setUpLayout;

    private OnFragmentInteractionListener mListener;

    public MeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
//        Intent intent = new Intent(getActivity(), LoginActivity.class);
//        startActivity(intent);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_me, container, false);
        display_user = (TextView)view.findViewById(R.id.display_user);
        header = (ImageView)view.findViewById(R.id.header);
        publishLayout = (LinearLayout)view.findViewById(R.id.layout_publish);
        sellOutLayout = (LinearLayout)view.findViewById(R.id.layout_sell_out);
        boughtLayout = (LinearLayout)view.findViewById(R.id.layout_bought);
        myLikeLayout = (LinearLayout)view.findViewById(R.id.layout_my_like);
        setUpLayout = (LinearLayout)view.findViewById(R.id.layout_setup);
        publishLayout.setOnClickListener(listener);
        sellOutLayout.setOnClickListener(listener);
        boughtLayout.setOnClickListener(listener);
        myLikeLayout.setOnClickListener(listener);
        setUpLayout.setOnClickListener(listener);
        initUI();
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (BmobUser.getCurrentUser(MyUser.class) == null) {
                showDialog();
            }else{
                switch (view.getId()){
                    case R.id.header:
                        getImageFromAlbum();
                        break;
                    case R.id.layout_publish:
                        Intent publishIntent = new Intent(getActivity(), PersonalGoodsListActivity.class);
                        startActivity(publishIntent);
                        break;
                    case R.id.layout_sell_out:
                        Intent sellIntent = new Intent(getActivity(), SellOutGoodsActivity.class);
                        startActivity(sellIntent);
                        break;
                    case R.id.layout_bought:
                        Intent boughtIntent = new Intent(getActivity(), BoughtGoodsActivity.class);
                        startActivity(boughtIntent);
                        break;
                    case R.id.layout_my_like:
                        Intent myCollectionIntent = new Intent(getActivity(), MyCollectionActivity.class);
                        startActivity(myCollectionIntent);
                        break;
                    case R.id.layout_setup:
                        Intent setupIntent = new Intent(getActivity(), SetupActivity.class);
                        startActivity(setupIntent);
                        break;
                }
            }
        }
    };

    public void initUI(){
        if(BmobUser.getCurrentUser(MyUser.class)!=null) {
            MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
            String user = myUser.getUsername();
            display_user.setText(user);
            if(myUser.getHead()!=null){
                BmobFile bmobFile = myUser.getHead();
                if(fileIsExists(bmobFile)){
                    String s = "/storage/emulated/0/"+bmobFile.getFilename();
                    Bitmap bitmap = BitmapFactory.decodeFile(s);
                    header.setImageBitmap(bitmap);
                }else {
                    downloadFile(bmobFile);
                }
            }else{
                header.setImageResource(R.drawable.head_big);
            }
        }else {
            display_user.setText("尚未登陆");
            header.setImageResource(R.drawable.head_big);
        }
        header.setOnClickListener(listener);
    }

    public boolean fileIsExists(BmobFile file){
        try{
            File f = new File(Environment.getExternalStorageDirectory(),file.getFilename());
            if(!f.exists()){
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }

    private void downloadFile(BmobFile file){
        File saveFile = new File(Environment.getExternalStorageDirectory(),file.getFilename());
        file.download(saveFile, new DownloadFileListener() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Bitmap bitmap = BitmapFactory.decodeFile(s);
                    header.setImageBitmap(bitmap);
                    Log.i("smile",s);
                    Toast.makeText(getApplicationContext(),"下载成功,保存路径:"+s,Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"下载失败："+e.getErrorCode()+","+e.getMessage()
                            ,Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onProgress(Integer integer, long l) {

            }
        });
    }

    /**
     * 打开相册选择图片
     */
    protected void getImageFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,REQUEST_CODE_PICK_IMAGE);
    }

    /**
     * 将URI将换成String类型
     */
    public String transUri(Uri uri) {
        String[] imgs1 = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, imgs1, null, null, null);
        int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String img_url = cursor.getString(index);
        return img_url;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            uri1 = data.getData();
            //to do find the path of pic by uri
            String photoPath=transUri(uri1);
            Bitmap bitmap = compressBySize(photoPath,150,200);
            try{
                String newPath = saveBitmapToFile(bitmap);
                header.setImageBitmap(bitmap);
                uploadPhoto(newPath);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 将照片上传至Bmob
     */
    private void uploadPhoto(String imgpath){
        final BmobFile head = new BmobFile(new File(imgpath));
        head.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                    if (bmobUser != null) {
                        MyUser myUser = new MyUser();
                        myUser.setHead(head);
                        myUser.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    Toast.makeText(getApplicationContext(), "头像上传成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Log.i("smile", "头像上传失败" + e.getLocalizedMessage());
                                }
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "上传失败：" + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }

    public void showDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("请先登陆！");
        builder.setPositiveButton("去登陆", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
        builder.create().show();
    }
}

