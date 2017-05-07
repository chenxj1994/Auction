package com.example.chenxuanjin.auction.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.chenxuanjin.auction.GoodsDetailActivity;
import com.example.chenxuanjin.auction.R;
import com.example.chenxuanjin.auction.adapter.GoodsListAdapter;
import com.example.chenxuanjin.auction.bean.Goods;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.FindListener;

import static cn.bmob.v3.Bmob.getApplicationContext;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements RadioGroup.OnCheckedChangeListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RadioGroup tabRadioGroup ;
    private RadioButton all,eProduct,clothes,dailyNecess,book,others;
    private ListView mListView;
    private GoodsListAdapter mGoodsListAdapter;
    private List<Goods> listItems = new ArrayList<Goods>();

    private OnFragmentInteractionListener mListener;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        return view;
    }

    public void initView(View view){
        all = (RadioButton)view.findViewById(R.id.list_all);
        eProduct = (RadioButton)view.findViewById(R.id.elect_product);
        clothes = (RadioButton)view.findViewById(R.id.clothes);
        dailyNecess = (RadioButton)view.findViewById(R.id.daily_necess);
        book = (RadioButton)view.findViewById(R.id.book);
        others = (RadioButton)view.findViewById(R.id.others);
        tabRadioGroup = (RadioGroup)view.findViewById(R.id.tab_radioGroup);
        tabRadioGroup.setOnCheckedChangeListener(this);
        mListView = (ListView)view.findViewById(R.id.goods_list_item);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Goods goods = listItems.get(i);
                Intent intent = new Intent(getActivity(), GoodsDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods_data",goods);
                bundle.putString("start_activity","HomeFragment");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        query();
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup,int CheckId){
        setRadioBtnColor();
        switch (CheckId){
            case R.id.list_all:
                query();
                break;
            case R.id.elect_product:
                query("电子产品");
                break;
            case R.id.clothes:
                query("衣服");
                break;
            case R.id.daily_necess:
                query("日用品");
                break;
            case R.id.book:
                query("书籍");
                break;
            case R.id.others:
                query("其他");
                break;
        }
    }

    public void setRadioBtnColor(){
        RadioButton[] rdBtnArray = { all, eProduct, clothes, dailyNecess, book, others};
        for(int i = 0 ; i < 6 ; i++){
            if(rdBtnArray[i].isChecked()){
                rdBtnArray[i].setBackgroundResource(R.color.backgroud_gray);
            }else {
                rdBtnArray[i].setBackgroundResource(R.color.white);
            }
        }
    }

    public void query(){
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.addWhereEqualTo("state",false);
        query.include("seller");
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if(e == null){
                    Log.i("harden","查询成功");
                    listItems = list;
                    mGoodsListAdapter = new GoodsListAdapter(getActivity(),listItems);
                    mListView.setAdapter(mGoodsListAdapter);
                    mGoodsListAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void query(String type){
        BmobQuery<Goods> eq1 = new BmobQuery<Goods>();
        eq1.addWhereEqualTo("type",type);
        BmobQuery<Goods> eq2 = new BmobQuery<Goods>();
        eq2.addWhereEqualTo("state",false);
        List<BmobQuery<Goods>> andQuerys = new ArrayList<BmobQuery<Goods>>();
        andQuerys.add(eq1);
        andQuerys.add(eq2);
        BmobQuery<Goods> query = new BmobQuery<Goods>();
        query.and(andQuerys);
        query.include("seller");
        query.findObjects(new FindListener<Goods>() {
            @Override
            public void done(List<Goods> list, BmobException e) {
                if(e == null){
                    Log.i("harden","查询成功");
                    listItems = list;
                    mGoodsListAdapter = new GoodsListAdapter(getActivity(),listItems);
                    mListView.setAdapter(mGoodsListAdapter);
                    mGoodsListAdapter.notifyDataSetChanged();
                }
            }
        });
    }
}
