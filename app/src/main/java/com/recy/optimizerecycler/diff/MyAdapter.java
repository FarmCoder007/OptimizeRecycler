package com.recy.optimizerecycler.diff;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.recy.optimizerecycler.R;
import java.util.List;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * author : xu
 * date : 2020/12/9 13:22
 * description :
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyVH> {
    private final static String TAG = "zxt";
    private List<TestBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public MyAdapter(Context mContext, List<TestBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(mContext);
    }

    public void setDatas(List<TestBean> mDatas) {
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public MyVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyVH(mInflater.inflate(R.layout.item_diff, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyVH holder, int position) {
        TestBean bean = mDatas.get(position);
        holder.tv_name.setText(bean.getName());
        holder.tv_id.setText(bean.getUserId() + "");
    }

    /**
     * @param holder
     * @param position
     * @param payloads 第三个参数就包含了我们在getChangePayload（）返回的Object
     */
    @Override
    public void onBindViewHolder(@NonNull MyVH holder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position);
        } else {
            // 取出新的变化的数据
            Bundle payload = (Bundle) payloads.get(0);
            TestBean bean = mDatas.get(position);
            for (String key : payload.keySet()) {
                switch (key) {
                    case "KEY_DESC":
                        //这里可以用payload里的数据，不过data也是新的 也可以用
                        holder.tv_name.setText(bean.getName());
                        break;
                    case "KEY_USERID":
                        holder.tv_id.setText(payload.getInt(key));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return mDatas != null ? mDatas.size() : 0;
    }

    class MyVH extends RecyclerView.ViewHolder {
        TextView tv_name, tv_id;

        public MyVH(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_id = itemView.findViewById(R.id.tv_id);
        }
    }
}
