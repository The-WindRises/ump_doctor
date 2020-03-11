package it.swiftelink.com.vcs_doctor.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import it.swiftelink.com.vcs_doctor.ui.activity.help.HelpAdapter;

public class Level0Item extends AbstractExpandableItem<Level1Item> implements MultiItemEntity {

    public String title;
    public String id;

    public Level0Item(String title, String id) {
        this.title = title;
        this.id = id;
    }

    @Override
    public int getItemType() {
        return HelpAdapter.TYPE_LEVEL_0;
    }

    @Override
    public int getLevel() {
        return 0;
    }
}
