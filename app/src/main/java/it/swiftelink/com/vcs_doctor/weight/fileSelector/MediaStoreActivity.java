package it.swiftelink.com.vcs_doctor.weight.fileSelector;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseActivity;
import it.swiftelink.com.vcs_doctor.R;
import it.swiftelink.com.vcs_doctor.ui.fragment.MyPagerAdapter;

public class MediaStoreActivity extends BaseActivity {


    @BindView(R.id.tl_file)
    TabLayout tlFile;
    @BindView(R.id.vp_file)
    ViewPager vpFile;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    private ProgressDialog progressDialog;

    private ArrayList<FileInfo> imageData = new ArrayList<>();
    private ArrayList<FileInfo> wordData = new ArrayList<>();
    private ArrayList<FileInfo> xlsData = new ArrayList<>();
    private ArrayList<FileInfo> pptData = new ArrayList<>();
    private ArrayList<FileInfo> pdfData = new ArrayList<>();

    private List<Fragment> mFragment = new ArrayList<>();

    private Integer[] mTabTile = {R.string.str_file1, R.string.str_file2, R.string.str_file3, R.string.str_file4, R.string.str_file5};


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                setData();
            }
        }
    };

    public static void show(Activity activity) {
        activity.startActivity(new Intent(activity, MediaStoreActivity.class));
    }

    private void setData() {

        FolderDataFragment imageFragment = new FolderDataFragment();
        imageFragment.setData(imageData,true);
        mFragment.add(imageFragment);

        FolderDataFragment wordFragment = new FolderDataFragment();
        wordFragment.setData(wordData,false);
        mFragment.add(wordFragment);

        FolderDataFragment xlsFragment = new FolderDataFragment();
        xlsFragment.setData(xlsData,false);
        mFragment.add(xlsFragment);

        FolderDataFragment pptFragment = new FolderDataFragment();
        pptFragment.setData(pptData,false);
        mFragment.add(pptFragment);

        FolderDataFragment pdfFragment = new FolderDataFragment();
        pdfFragment.setData(pdfData,false);
        mFragment.add(pdfFragment);

        FragmentManager fragmentManager = getSupportFragmentManager();

        MyPagerAdapter tabViewPagerAdapter = new MyPagerAdapter(fragmentManager, this, mFragment, mTabTile);
        vpFile.setAdapter(tabViewPagerAdapter);

        tlFile.setupWithViewPager(vpFile);

        tlFile.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                vpFile.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        progressDialog.dismiss();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_media_store;
    }

    @Override
    protected void initWidget() {
        super.initWidget();

        tvTitle.setText("选择文件");

        progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_LIGHT);
        progressDialog.setMessage("正在加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    @Override
    protected void initData() {
        super.initData();

        new Thread() {
            @Override
            public void run() {
                super.run();
                getFolderData();
            }
        }.start();
    }

    /**
     * 遍历文件夹中资源
     */
    public void getFolderData() {

        getImages();

        getDocumentData(1);
        getDocumentData(2);
        getDocumentData(3);
        getDocumentData(4);

        handler.sendEmptyMessage(1);
    }

    /**
     * 加载图片
     */
    public void getImages() {

        String[] projection = new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA, MediaStore.Images.ImageColumns.DISPLAY_NAME};

        //asc 按升序排列
        //desc 按降序排列
        //projection 是定义返回的数据，selection 通常的sql 语句，例如  selection=MediaStore.Images.ImageColumns.MIME_TYPE+"=? " 那么 selectionArgs=new String[]{"jpg"};
        ContentResolver mContentResolver = this.getContentResolver();
        Cursor cursor = mContentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.ImageColumns.DATE_MODIFIED + "  desc");


        String imageId = null;

        String fileName;

        String filePath;

        while (cursor.moveToNext()) {

            imageId = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));

            fileName = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DISPLAY_NAME));

            filePath = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));


            Log.e("photo", imageId + " -- " + fileName + " -- " + filePath);
            FileInfo fileInfo = FileUtil.getFileInfoFromFile(new File(filePath));
            imageData.add(fileInfo);
        }
        cursor.close();

        cursor = null;
    }


    /**
     * 获取手机文档数据
     *
     * @param selectType
     */
    public void getDocumentData(int selectType) {

        String[] columns = new String[]{MediaStore.Files.FileColumns._ID, MediaStore.Files.FileColumns.MIME_TYPE, MediaStore.Files.FileColumns.SIZE, MediaStore.Files.FileColumns.DATE_MODIFIED, MediaStore.Files.FileColumns.DATA};

        String select = "";

        switch (selectType) {
            //word
            case 1:
                select = "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.doc'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.docx'" + ")";
                break;
            //xls
            case 2:
                select = "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.xls'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.xlsx'" + ")";
                break;
            //ppt
            case 3:
                select = "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.ppt'" + " or " + MediaStore.Files.FileColumns.DATA + " LIKE '%.pptx'" + ")";
                break;
            //pdf
            case 4:
                select = "(" + MediaStore.Files.FileColumns.DATA + " LIKE '%.pdf'" + ")";
                break;
        }

//        List<FileInfo> dataList = new ArrayList<>();
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, select, null, null);
//        Cursor cursor = contentResolver.query(Uri.parse(Environment.getExternalStorageDirectory() + "/tencent/QQfile_recv/"), columns, select, null, null);

        int columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA);

        if (cursor != null) {
            while (cursor.moveToNext()) {

                String path = cursor.getString(columnIndexOrThrow_DATA);

                FileInfo document = FileUtil.getFileInfoFromFile(new File(path));

//                dataList.add(document);
                switch (selectType) {
                    //word
                    case 1:
                        wordData.add(document);
                        break;
                    //xls
                    case 2:
                        xlsData.add(document);
                        break;
                    //ppt
                    case 3:
                        pptData.add(document);
                        break;
                    //pdf
                    case 4:
                        pdfData.add(document);
                        break;
                }
            }
            cursor.close();
        }
    }


    @OnClick(R.id.btn_back)
    public void onViewClicked() {
        finish();
    }
}
