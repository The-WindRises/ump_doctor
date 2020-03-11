package it.swiftelink.com.common.widget.recycler;

/**
 * Created by Administrator on 2018/7/10.
 */

public interface AdapterCallback<Data> {

    void onUpdate(Data data, BaseRecyclerAdapter.ViewHolder<Data> viewHolder);
}
