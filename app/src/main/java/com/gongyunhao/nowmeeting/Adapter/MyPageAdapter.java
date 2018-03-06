package com.gongyunhao.nowmeeting.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

//    ┏┓　   ┏┓
// ┏━━┛┻━━━━━┛┻ ┓ 
// ┃　　　　　　 ┃  
// ┃　　　━　    ┃  
// ┃　＞　　＜　 ┃  
// ┃　　　　　　 ┃  
// ┃... ⌒ ...  ┃  
// ┃　　　　　 　┃  
// ┗━━━┓　　　┏━┛  
//     ┃　　　┃　  
//     ┃　　　┃  
//     ┃　　　┃  神兽保佑  
//     ┃　　　┃  代码无bug　　  
//     ┃　　　┃  
//     ┃　　　┗━━━━━━━━━┓
//     ┃　　　　　　　    ┣┓
//     ┃　　　　         ┏┛
//     ┗━┓ ┓ ┏━━━┳ ┓ ┏━┛
//       ┃ ┫ ┫   ┃ ┫ ┫
//       ┗━┻━┛   ┗━┻━┛
//
//  作者：棒棒小糖
//  來源：简书
//
//  Creste by GongYunHao on 2018/3/5
// 
public class MyPageAdapter extends FragmentPagerAdapter{

    private List<String> mTitles;
    private List<Fragment> list_fragment;                         //fragment列表

    public MyPageAdapter(FragmentManager manager, List<Fragment> fragments, List<String> titles) {
        super(manager);
        this.list_fragment = fragments;
        this.mTitles = titles;
    }
    //getCount()返回List<View>的size:
    @Override
    public int getCount() {
        return list_fragment.size();
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get( position );
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get( position );
    }

}
