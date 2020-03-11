package it.swiftelink.com.vcs_doctor.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import it.swiftelink.com.vcs_doctor.ui.activity.help.HelpAdapter;

public class Level1Item implements MultiItemEntity {


    public String id;
    public String helpCategoryId;
    public String title;
    public String platform;
    public String content;

    public Level1Item(String id, String helpCategoryId, String title, String platform, String content) {
        this.id = id;
        this.helpCategoryId = helpCategoryId;
        this.title = title;
        this.platform = platform;
        this.content = content;
    }

    @Override
    public int getItemType() {
        return HelpAdapter.TYPE_LEVEL_1;
    }

}
