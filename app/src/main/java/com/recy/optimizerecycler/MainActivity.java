package com.recy.optimizerecycler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.recy.optimizerecycler.diff.DiffRecyclerCallBack;
import com.recy.optimizerecycler.diff.MyAdapter;
import com.recy.optimizerecycler.diff.TestBean;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<TestBean> mDatas;
    private RecyclerView mRv;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRv = findViewById(R.id.recycler);
        mRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyAdapter(this, mDatas);
        mRv.setAdapter(mAdapter);
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add(new TestBean(1, "name1", 112));
        mDatas.add(new TestBean(2, "name2", 113));
        mDatas.add(new TestBean(3, "name3", 114));
        mDatas.add(new TestBean(4, "name4", 115));
        mDatas.add(new TestBean(5, "name5", 116));
    }

    public void onRefresh(View view) {
        List<TestBean> newDatas = new ArrayList<>();
        newDatas.addAll(mDatas);
        newDatas.add(new TestBean(6, "帅", 117));//模拟新增数据
        newDatas.get(0).setUserId(122);
        newDatas.get(0).setName("测试");//模拟修改数据
        TestBean testBean = newDatas.get(1);//模拟数据位移
        newDatas.remove(testBean);
        newDatas.add(testBean);
        //别忘了将新数据给Adapter
//        mDatas = newDatas;
//        mAdapter.setDatas(mDatas);
//        mAdapter.notifyDataSetChanged();//以前我们大多数情况下只能这样


        //文艺青年新宠
       //利用DiffUtil.calculateDiff()方法，传入一个规则DiffUtil.Callback对象，和是否检测移动item的 boolean变量，得到DiffUtil.DiffResult 的对象
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffRecyclerCallBack(mDatas, newDatas), true);
       //利用DiffUtil.DiffResult对象的dispatchUpdatesTo（）方法，传入RecyclerView的Adapter，轻松成为文艺青年
        diffResult.dispatchUpdatesTo(mAdapter);
       //别忘了将新数据给Adapter
        mDatas = newDatas;
        mAdapter.setDatas(mDatas);
    }
}
