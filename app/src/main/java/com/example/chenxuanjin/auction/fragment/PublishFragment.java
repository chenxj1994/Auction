package com.example.chenxuanjin.auction.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.chenxuanjin.auction.LoginActivity;
import com.example.chenxuanjin.auction.MainActivity;
import com.example.chenxuanjin.auction.R;
import com.example.chenxuanjin.auction.bean.Goods;
import com.example.chenxuanjin.auction.bean.MyUser;

import java.io.File;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

import static cn.bmob.v3.Bmob.getApplicationContext;
import static com.example.chenxuanjin.auction.utils.ImageCompression.compressBySize;
import static com.example.chenxuanjin.auction.utils.ImageCompression.saveBitmapToFile;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PublishFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PublishFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PublishFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int REQUEST_CODE_PICK_IMAGE = 0xa0;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String newPath;
    private ImageView goodsPic;
    private Button publishSureBtn;
    private EditText titleText, detailText, priceText;
    private Spinner typeSpinner;
    private Uri picUri;
    private Goods goods;

    private OnFragmentInteractionListener mListener;

    public PublishFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PublishFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PublishFragment newInstance(String param1, String param2) {
        PublishFragment fragment = new PublishFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_publish, container, false);
        initUI(view);
        return view;
    }

    public void initUI(View view){
        titleText = (EditText)view.findViewById(R.id.title);
        detailText = (EditText)view.findViewById(R.id.description);
        priceText = (EditText)view.findViewById(R.id.price);
        typeSpinner = (Spinner)view.findViewById(R.id.goods_type);
        publishSureBtn = (Button) view.findViewById(R.id.publish_sure);
        goodsPic = (ImageView)view.findViewById(R.id.goods_photo);
        publishSureBtn.setOnClickListener(listener);
        goodsPic.setOnClickListener(listener);
        if(BmobUser.getCurrentUser(MyUser.class)==null){
            showDialog();
        }
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

    /**
     * 点击事件监听器
     */
    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (BmobUser.getCurrentUser(MyUser.class) == null) {
                showDialog();
            } else {
                switch (view.getId()) {
                    case R.id.goods_photo:
                        getImageFromAlbum();
                        break;
                    case R.id.publish_sure:
                        uploadGoods();
                        break;
                }
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            picUri = data.getData();
            //to do find the path of pic by uri
            String photoPath=transUri(picUri);
            Bitmap bitmap = compressBySize(photoPath,400,400);
            try{
                newPath = saveBitmapToFile(bitmap);
                goodsPic.setImageBitmap(bitmap);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void uploadGoods(){
        //上传图片
        if(newPath != null){
            final BmobFile goodsPic = new BmobFile(new File(newPath));
            goodsPic.uploadblock(new UploadFileListener() {
                @Override
                public void done(BmobException e) {
                    if(e == null){
                        goods = new Goods();
                        goods.setPic(goodsPic);
                        uploadGoodsWithoutPic(goods);
                        Toast.makeText(getApplicationContext(),"图片上传成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getApplicationContext(),"图片上传失败"+e.getMessage()
                                ,Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            goods = new Goods();
            uploadGoodsWithoutPic(goods);
        }
    }

    public void uploadGoodsWithoutPic(Goods goods){
        String goodsTitle = titleText.getText().toString();
        String goodsDetail = detailText.getText().toString();
        String goodsPrice = priceText.getText().toString();
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        String goodsType = typeSpinner.getSelectedItem().toString();
        goods.setGoodsName(goodsTitle);
        goods.setDes(goodsDetail);
        goods.setPrice(Float.parseFloat(goodsPrice));
        goods.setState(false);
        goods.setSeller(user);
        goods.setType(goodsType);
        goods.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    Toast.makeText(getApplicationContext(),"上架成功",Toast.LENGTH_LONG).show();
                    HomeFragment homeFragment = new HomeFragment();
                    FragmentTransaction transaction = getActivity().getFragmentManager().beginTransaction();
                    transaction.replace(R.id.content, homeFragment);
                    transaction.commit();
                }else{
                    Toast.makeText(getApplicationContext(),"上架失败"+e.getMessage(),Toast.LENGTH_LONG).show();
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
