package com.recy.optimizerecycler.diff;

import android.os.Bundle;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

/**
 * author : xu
 * date : 2020/12/8 18:22
 * description :  对比RecyclerView  数据源   是否有变化
 */
public class DiffRecyclerCallBack extends DiffUtil.Callback {
    private List<TestBean> mOldDatas, mNewDatas;

    public DiffRecyclerCallBack(List<TestBean> mOldDatas, List<TestBean> mNewDatas) {
        this.mOldDatas = mOldDatas;
        this.mNewDatas = mNewDatas;
    }

    /**
     * 老数据的size
     *
     * @return
     */
    @Override
    public int getOldListSize() {
        return mOldDatas != null ? mOldDatas.size() : 0;
    }

    /**
     * 新数据的size
     *
     * @return
     */
    @Override
    public int getNewListSize() {
        return mNewDatas != null ? mNewDatas.size() : 0;
    }

    /**
     * 新老数据集在同一个postion的Item是否是一个对象？（可能内容不同，如果这里返回true，会调用下面的方法）
     * * Called by the DiffUtil to decide whether two object represent the same Item.
     * 由DiffUtil调用以决定两个对象是否表示同一项
     * * For example, if your items have unique ids, this method should check their id equality.
     * 例如，如果您的项目有唯一的id，这个方法应该检查它们的id是否相等。
     *
     * @param oldItemPosition The position of the item in the old list
     *                        该项在旧列表中的位置
     * @param newItemPosition The position of the item in the new list
     * @return True if the two items represent the same object or false if they are different.
     * 如果两个项代表同一个对象，则为True；如果两项不同，则为false。
     */
    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldDatas.get(oldItemPosition).getId() == mNewDatas.get(newItemPosition).getId();
    }

    /**
     * 被DiffUtil调用，用来检查 两个item是否含有相同的数据
     * DiffUtil用返回的信息（true false）来检测当前item的内容是否发生了变化
     * DiffUtil 用这个方法替代equals方法去检查是否相等。
     * 这个方法仅仅在areItemsTheSame()返回true时，才调用。
     * areItemsTheSame()返回true而areContentsTheSame()返回false，也就是说两个对象代表的数据是一条，但是内容更新了。
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return
     */
    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        TestBean beanOld = mOldDatas.get(oldItemPosition);
        TestBean beanNew = mNewDatas.get(newItemPosition);
        if (!beanOld.getName().equals(beanNew.getName())) {
            //如果有内容不同，就返回false
            return false;
        }
        if (beanOld.getUserId() != beanNew.getUserId()) {
            //如果有内容不同，就返回false
            return false;
        }
        //默认两个data内容是相同的
        return true;
    }

    /**
     * 去得到这个Item（有哪些）改变的payload。
     * 例如，如果你用RecyclerView配合DiffUtils，你可以返回  这个Item改变的那些字段，
     *
     * @param oldItemPosition
     * @param newItemPosition
     * @return 返回的Object就是表示Item改变了哪些内容。
     */
    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // 定向刷新中的部分更新
        // 效率最高
        //只是没有了ItemChange的白光一闪动画，
        TestBean oldBean = mOldDatas.get(oldItemPosition);
        TestBean newBean = mNewDatas.get(newItemPosition);

        //这里就不用比较核心字段了,一定相等
        Bundle payload = new Bundle();
        if (!oldBean.getName().equals(newBean.getName())) {
            payload.putString("KEY_DESC", newBean.getName());
        }
        if (oldBean.getUserId() != newBean.getUserId()) {
            payload.putInt("KEY_USERID", newBean.getUserId());
        }
        //如果没有变化 就传空
        if (payload.size() == 0)
            return null;
        // 这个方法返回一个Object类型的payload，它包含了某个item的变化了的那些内容。  我们这里使用Bundle保存这些变化。
        return payload;
    }
}
