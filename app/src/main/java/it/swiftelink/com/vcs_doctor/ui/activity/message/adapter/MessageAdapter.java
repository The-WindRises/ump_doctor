package it.swiftelink.com.vcs_doctor.ui.activity.message.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import it.swiftelink.com.factory.model.message.MessageResModel;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.util.DateUtils;

public class MessageAdapter extends BaseQuickAdapter<MessageResModel.DataBean.DataListBean, BaseViewHolder> {

    public MessageAdapter() {
        super(R.layout.item_message_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, MessageResModel.DataBean.DataListBean item) {
        if (item != null) {
            if ("Unread".equals(item.getStatus())) {
                helper.setVisible(R.id.unreadIv, true);
            } else if ("AlreadyRead".equals(item.getStatus())) {
                helper.setVisible(R.id.unreadIv, false);
            }
            helper.setText(R.id.titleTv, item.getTitle())
                    .setText(R.id.dateTv, DateUtils.convertToString(item.getCreationDate(), DateUtils.TIME_FORMAT));
        }
    }
}
