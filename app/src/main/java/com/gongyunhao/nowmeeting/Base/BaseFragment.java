package com.gongyunhao.nowmeeting.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cn.jpush.im.android.api.JMessageClient;

/**
 * Created by yuanyuanlai on 2018/3/7.
 */

abstract public class BaseFragment extends Fragment {

    private String Tag = this.getClass().getSimpleName();
    private boolean isDataLoaded;
    private boolean isViewInitiated;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Tag,"----> onCreate");
    }



    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        prepareRequestData();
        Log.d(Tag,"----> isVisibleToUser "+isVisibleToUser);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(Tag,"----> onAttach");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isViewInitiated = true;
        prepareRequestData();
        Log.d(Tag,"---->onActivityCreated");
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        JMessageClient.registerEventReceiver(this);
        Log.d(Tag,"----> onViewCreated");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        JMessageClient.unRegisterEventReceiver(this);
    }


    //
//    @Override
//    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
//        super.onViewStateRestored(savedInstanceState);
//        Log.d(Tag,"---->onViewStateRestored");
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//       // mSavedInstanceState.put
//        Log.d(Tag,"----> onDestroyView");
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//
//        Log.d(Tag,"----> onResume");
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        Log.d(Tag,"---->onPause");
//    }

    //初始化数据
    protected abstract void requestData();

    public boolean prepareRequestData(){
       return  prepareRequestData(false);
    }

    public boolean prepareRequestData(boolean forceUpdate) {
        if (getUserVisibleHint() && isViewInitiated && (!isDataLoaded || forceUpdate)) {
            requestData();
            Log.d(Tag,"---->请求数据");
            isDataLoaded = true;
            return true;
        }
        return false;
    }

    protected abstract void setListener();

    protected abstract void initViews(View view);

}
