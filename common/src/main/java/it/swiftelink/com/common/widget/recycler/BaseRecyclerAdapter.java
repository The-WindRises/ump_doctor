package it.swiftelink.com.common.widget.recycler;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import it.swiftelink.com.common.R;

/**
 * Created by Administrator on 2018/7/10.
 */

public abstract class BaseRecyclerAdapter<Data>
        extends RecyclerView.Adapter<BaseRecyclerAdapter.ViewHolder<Data>>
        implements View.OnClickListener, View.OnLongClickListener, AdapterCallback<Data> {

    private List<Data> mDataList;
    private AdapterListener mListener;

    public BaseRecyclerAdapter() {
        this(null);
    }

    public BaseRecyclerAdapter(AdapterListener listener) {
        this(new ArrayList<Data>(), listener);
    }

    public BaseRecyclerAdapter(List<Data> dataList, AdapterListener listener) {
        mDataList = dataList;
        mListener = listener;
    }

    /**
     * @param position
     * @return 返回对应的xml文件的id
     */
    @Override
    public int getItemViewType(int position) {
        return getItemViewType(position, mDataList.get(position));
    }

    @LayoutRes
    public abstract int getItemViewType(int pos, Data data);

    @Override
    public ViewHolder<Data> onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rootView = inflater.inflate(viewType, parent, false);
        ViewHolder<Data> viewHolder = createViewHolder(rootView, viewType);


        rootView.setOnClickListener(this);
        rootView.setOnLongClickListener(this);
        //将view和holder进行绑定
        rootView.setTag(R.id.tag_recycler_holder, viewHolder);

        viewHolder.unbinder = ButterKnife.bind(viewHolder, rootView);
        //绑定callBack
        viewHolder.mCallback = this;

        return viewHolder;
    }

    /**
     * 子类必须实现的创建viewHolder的方法
     */
    protected abstract ViewHolder<Data> createViewHolder(View root, int viewType);

    /**
     * 绑定数据
     */
    @Override
    public void onBindViewHolder(ViewHolder<Data> holder, int position) {
        //绑定数据
        Data data = mDataList.get(position);
        holder.bind(data);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    /**
     * 返回整个集合
     *
     * @return List<Data>
     */
    public List<Data> getItems() {
        return mDataList;
    }

    /**
     * 插入一条数据
     *
     * @param data
     */
    public void addData(Data data) {
        mDataList.add(data);
        notifyItemInserted(mDataList.size() - 1);

    }

    /**
     * 插入一堆数据
     *
     * @param dataList
     */
    public void addData(Data... dataList) {

        if (dataList != null && dataList.length > 0) {
            Collections.addAll(mDataList, dataList);
            notifyItemRangeInserted(mDataList.size(), dataList.length);
        }
    }

    /**
     * 插入一个数据集合
     *
     * @param dataList
     */
    public void addData(List<Data> dataList) {
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
            notifyItemRangeInserted(mDataList.size(), dataList.size());
        }
    }

    /**
     * 数据清空
     */
    public void clear() {
        mDataList.clear();
        notifyDataSetChanged();
    }

    /**
     * 数据替换
     */
    public void replice(List<Data> dataList) {
        mDataList.clear();
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    /**
     * 点击
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

        ViewHolder<Data> viewHolder = (ViewHolder<Data>) v.getTag();

        if (mListener != null) {
            int position = viewHolder.getAdapterPosition();
            mListener.onItemClick(viewHolder, mDataList.get(position));
        }


    }

    /**
     * 长按
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {
        ViewHolder<Data> viewHolder = (ViewHolder<Data>) v.getTag();

        if (mListener != null) {
            int position = viewHolder.getAdapterPosition();
            mListener.onItemLongClick(viewHolder, mDataList.get(position));
            return true;
        }
        return false;
    }

    /**
     * 设置监听器
     *
     * @param listener
     */
    public void setItemListener(AdapterListener<Data> listener) {
        this.mListener = listener;
    }


    /**
     * item的监听器
     *
     * @param <Data> 泛型
     */
    public interface AdapterListener<Data> {
        void onItemClick(ViewHolder<Data> holder, Data data);

        void onItemLongClick(ViewHolder<Data> holder, Data data);
    }


    /**
     * 自定义的viewHolder
     *
     * @param <Data>
     */
    public static abstract class ViewHolder<Data> extends RecyclerView.ViewHolder {
        private Unbinder unbinder;
        protected Data mData;

        private AdapterCallback<Data> mCallback;

        public ViewHolder(View itemView) {
            super(itemView);
        }

        //用于绑定数据的触发
        void bind(Data data) {
            this.mData = data;
            onBind(data);
        }

        protected abstract void onBind(Data data);

        //holder对自己对应的data进行更新
        public void update(Data data) {
            if (this.mCallback != null) {
                mCallback.onUpdate(data, this);
            }
        }
    }
}
