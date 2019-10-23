package com.baby.app.modules.channel;import androidx.annotation.Nullable;import androidx.fragment.app.Fragment;import androidx.fragment.app.FragmentManager;import androidx.fragment.app.FragmentStatePagerAdapter;import androidx.viewpager.widget.ViewPager;import android.view.View;import com.android.baselibrary.base.standard.YQApi;import com.android.baselibrary.widget.tabLayout.TabLayout;import com.android.baselibrary.widget.title.TitleBuilder;import com.baby.app.R;import com.baby.app.application.IBaseFragment;import com.baby.app.modules.channel.page.ChannelRecommendFragment;import com.baby.app.modules.channel.page.ChannelTagFragment;import java.util.ArrayList;import java.util.List;/** * Created by chen.huarong on 2019-05-19. * 频道fragment */@YQApi(        swipeback = false,        openAnimation = -1,        closAnimatione = -1)public class ChannelFragment extends IBaseFragment {    public static final String TAG = "ChannelFragment";    private static final String[] sTitles = {"专栏推荐", "喜欢选项"};    private List<Fragment> mFragments;    @Override    protected int getLayoutView() {        return R.layout.fragment_channel;    }    @Override    public void initToolBar(TitleBuilder mTitleBuilder) {        setToolBarVisible(View.GONE);    }    @Override    public void initUiAndListener(View view) {        TabLayout tabLayout = view.findViewById(R.id.tabLayout);        ViewPager viewPager = view.findViewById(R.id.viewPager);        mFragments = new ArrayList<>();        mFragments.add(new ChannelRecommendFragment());        mFragments.add(new ChannelTagFragment());        viewPager.setAdapter(new ChannelPagerAdapter(getFragmentManager()));        tabLayout.setViewPager(viewPager);    }    public class ChannelPagerAdapter extends FragmentStatePagerAdapter {        ChannelPagerAdapter(FragmentManager fm) {            super(fm);        }        @Override        public int getCount() {            return mFragments == null ? 0 : mFragments.size();        }        @Nullable        @Override        public CharSequence getPageTitle(int position) {            return sTitles[position];        }        @Override        public Fragment getItem(int position) {            return mFragments.get(position);        }    }}