package com.lzq.wanandroid.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> mList = new ArrayList<>();
    private FragmentManager fm;

    public FragmentAdapter(FragmentManager fm, List<Fragment> mList) {
        super(fm);
        this.fm = fm;
        this.mList = mList;

    }

    public FragmentTransaction getTransaction() {
        return fm.beginTransaction();
    }


    @Override
    public Fragment getItem(int i) {
        return mList.get(i);
    }


    @Override
    public int getCount() {
        return mList.size();
    }


    public void notifyDataSetChanged(List<Fragment> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

//    @NonNull
//    @Override
//    public Object instantiateItem(@NonNull ViewGroup container, int position) {
//        //ViewPager对于重新传进来的Adapter不会直接重新加载List内容
//        //只是根据Tag判断是否存在
//        if (position == 3)
//            updateFragment(container, position);
//        return super.instantiateItem(container, position);
//    }
////
//    private void updateFragment(ViewGroup container, int position) {
//        String tag = getFragmentTag(container.getId(), position);
//        Fragment fragment = fm.findFragmentByTag(tag);
//        if (fragment == null)
//            return;
//        FragmentTransaction ft = fm.beginTransaction();
//        ft.remove(fragment);
//        ft.commit();
//        ft = null;
//        fm.executePendingTransactions();
//    }

//    private String getFragmentTag(int id, int position) {
//        try {
//            Class<FragmentPagerAdapter> cls = FragmentPagerAdapter.class;
//            Class<?>[] parameterTypes = {int.class, long.class};
//            Method method = cls.getDeclaredMethod("makeFragmentName",
//                    parameterTypes);
//            method.setAccessible(true);
//            String tag = (String) method.invoke(this, id, position);
//            return tag;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "";
//        }
//    }
//
//
//    //若要更新Fragment必须重写此方法
//    @Override
//    public int getItemPosition(@NonNull Object object) {
////        if (mList.contains((View) object)) {
////            // 如果当前 item 未被 remove，则返回 item 的真实 position
////            return mList.indexOf((View) object);
////        } else {
////            // 否则返回状态值 POSITION_NONE
//            return POSITION_NONE;
////        }?
//    }
}
