package it.swiftelink.com.vcs_doctor.weight.fileSelector;


import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import it.swiftelink.com.common.app.BaseFragment;
import it.swiftelink.com.common.widget.recycler.BaseRecyclerAdapter;
import it.swiftelink.com.vcs_doctor.App;
import it.swiftelink.com.vcs_doctor.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FolderDataFragment extends BaseFragment {


    @BindView(R.id.rvc_file_list)
    RecyclerView rvDoc;

    private boolean isPhoto = false;
    private ArrayList<FileInfo> data = new ArrayList();

    private BaseRecyclerAdapter<FileInfo> mAdapter;


    public FolderDataFragment() {
        // Required empty public constructor
    }

    public void setData(ArrayList<FileInfo> data, boolean isPhoto) {
        // Required empty public constructor

        this.data = data;
        this.isPhoto = isPhoto;

    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_folder_data;
    }

    @Override
    protected void initWidget(View view) {
        super.initWidget(view);

        rvDoc.setLayoutManager(new LinearLayoutManager(getContext()));

        rvDoc.setAdapter(mAdapter = new BaseRecyclerAdapter<FileInfo>() {
            @Override
            public int getItemViewType(int pos, FileInfo fileInfo) {
                return R.layout.item_rcv_file_list;
            }

            @Override
            protected ViewHolder<FileInfo> createViewHolder(View root, int viewType) {
                return new MyViewHolder(root);
            }

            @Override
            public void onUpdate(FileInfo fileInfo, ViewHolder<FileInfo> viewHolder) {

            }
        });
    }

    @Override
    protected void initData() {
        super.initData();

        if (data != null && data.size() > 0) {
            mAdapter.replice(data);
        }
    }

    class MyViewHolder extends BaseRecyclerAdapter.ViewHolder<FileInfo> {

        @BindView(R.id.iv_cover)
        ImageView ivCover;
        @BindView(R.id.tv_content)
        TextView tvContent;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_time)
        TextView tvTime;

        public MyViewHolder(View itemView) {
            super(itemView);

            LayoutInflater.from(getContext()).inflate(R.layout.item_rcv_file_list, null);
        }

        @Override
        protected void onBind(FileInfo fileInfo) {

            tvContent.setText(fileInfo.getFileName());
            tvSize.setText(FileUtil.FormetFileSize(fileInfo.getFileSize()));
            tvTime.setText(fileInfo.getTime());

            //封面图
            if (isPhoto) {
                Glide.with(getActivity()).load(fileInfo.getFilePath()).into(ivCover);
            } else {
                Glide.with(getActivity()).load(FileUtil.getFileTypeImageId(getContext(), fileInfo.getFilePath())).into(ivCover);
            }

        }

        @OnClick(R.id.rl_main)
        public void onViewClicked() {
            App.showToast("path:" + mData.getFilePath());
        }
    }
}
