package it.swiftelink.com.vcs_doctor.ui.activity.help;

import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.entity.Level0Item;
import it.swiftelink.com.vcs_doctor.entity.Level1Item;
import it.swiftelink.com.vcs_doctor.ui.activity.WebViewActivity;

public class HelpAdapter extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_LEVEL_0 = 0;
    public static final int TYPE_LEVEL_1 = 1;
    public static final int TYPE_PERSON = 2;

    public HelpAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_LEVEL_0, R.layout.menu_level_one_layout);
        addItemType(TYPE_LEVEL_1, R.layout.menu_level_tow_layout);
    }

    @Override
    protected void convert(final BaseViewHolder helper, MultiItemEntity item) {
        switch (item.getItemType()) {
            case TYPE_LEVEL_0:
                final Level0Item lv0 = (Level0Item) item;
                helper.setText(R.id.name, lv0.title);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = helper.getAdapterPosition();
                        if (lv0.isExpanded()) {
                            collapse(pos);
                        } else {
                            expand(pos);
                        }
                    }
                });
                break;
            case TYPE_LEVEL_1:
                final Level1Item lv1 = (Level1Item) item;
                helper.setText(R.id.name, lv1.title);
                helper.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, HelpDetailsActivity.class);
                        intent.putExtra("content", lv1.content);
                        intent.putExtra("title", lv1.title);
                        mContext.startActivity(intent);
                    }
                });

                break;

        }
    }

}
