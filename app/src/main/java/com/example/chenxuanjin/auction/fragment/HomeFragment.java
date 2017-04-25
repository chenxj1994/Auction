package com.example.chenxuanjin.auction.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.chenxuanjin.auction.R;

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
                break;
            case R.id.elect_product:
                break;
            case R.id.clothes:
                break;
            case R.id.daily_necess:
                break;
            case R.id.book:
                break;
            case R.id.others:
                others.setBackgroundResource(R.color.backgroud_gray);
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
}
