package viewutil;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.renlei.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by  renlei
 * DATE: 16/7/5
 * Time: 10:22
 */
public class ChooseEmailDialog extends Dialog {
    private ListView mListView;
    private Context mContext;
    private View mRootView;
    private TextView mTitleTV;
    private BaseAdapter mAdapter;

    private ChooseEmailDialog(Context context) {
        this(context, R.style.EstateDialog);
    }

    private ChooseEmailDialog(Context context, int themeResId) {

        super(context, themeResId);
        Log.d("ChooseEmailDialog","ChooseEmailDialog");
        this.mContext = context;
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.choose_email_dialog_layout, null);
        init();

    }

    private void init() {
        Log.d("ChooseEmailDialog","init");

        mListView = (ListView) mRootView.findViewById(R.id.choose_email_dialog_listview);
        mTitleTV = (TextView) mRootView.findViewById(R.id.choose_email_dialog_title_tv);
    }

    @Override
    public void show() {
        if (mContext instanceof Activity && !((Activity) mContext).isFinishing()) {
            super.show();
            //设置dialog大小
            Window window = this.getWindow();
            window.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL);  //此处可以设置dialog显示的位置
            Display d = window.getWindowManager().getDefaultDisplay();  //为获取屏幕宽、高
            WindowManager.LayoutParams p = window.getAttributes();  //获取对话框当前的参数值
            p.width = (int) (d.getWidth() * 1.0);    //宽度设置为屏幕宽度
            window.setAttributes(p);     //设置生效
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ChooseEmailDialog","oncreate");
        super.onCreate(savedInstanceState);
//        setContentView(mRootView,new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        setContentView(mRootView);

    }

    private void setmTitleTV(String title) {
        mTitleTV.setText(title);
        mTitleTV.setVisibility(View.VISIBLE);
    }

    private void setAdapter(BaseAdapter adapter) {
        this.mAdapter = adapter;
        mListView.setAdapter(mAdapter);
    }

    public void setInfos(List<EmailDialogInfo> infos, final AdapterView.OnItemClickListener itemClickListener) {
        if (infos!=null){
            ((EmailChooseDialogAdapter)mAdapter).setData(infos);
        }
        if (itemClickListener!=null){
            mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    itemClickListener.onItemClick(parent,view,position,id);
                }
            });
        }

    }

    private static class EmailChooseDialogAdapter extends BaseAdapter {
        private Context context;
        private List<EmailDialogInfo> mInfos = new ArrayList<>();
        private AdapterView.OnItemClickListener onItemClickListener;
        LayoutInflater inflater;

        public EmailChooseDialogAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mInfos.size();
        }

        @Override
        public Object getItem(int position) {
            return mInfos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.choose_email_dialog_item, null);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            EmailDialogInfo info = mInfos.get(position);
            if (!TextUtils.isEmpty(info.description)) {
                viewHolder.mDesc.setText(info.description);
            } else {
                viewHolder.mDesc.setText("");
            }
            if (!TextUtils.isEmpty(info.emailAdd)){
                viewHolder.mEmail.setText(info.emailAdd);
                viewHolder.mEmail.setVisibility(View.VISIBLE);
            }else {
                viewHolder.mEmail.setVisibility(View.GONE);
            }
            return convertView;
        }

        public void setData(List<EmailDialogInfo> infos) {
            this.mInfos.clear();
            this.mInfos.addAll(infos);
            notifyDataSetChanged();
        }

        class ViewHolder {
            RoundImageView mRiv;
            TextView mDesc;
            TextView mEmail;

            public ViewHolder(View view) {
                mDesc = (TextView) view.findViewById(R.id.choose_email_dialog_item_content);
                mEmail = (TextView) view.findViewById(R.id.choose_email_dialog_item_email);
            }
        }

    }

    public static class Builder {
        private Context mContext;
        private String title;
        private AdapterView.OnItemClickListener itemClickListener;
        private BaseAdapter adapter;
        private List<EmailDialogInfo> infos;

        public Builder(Context mContext) {
            this.mContext = mContext;
        }

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setInfos(List<EmailDialogInfo> infos, AdapterView.OnItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
            this.infos = infos;
            return this;
        }

        public Builder setAdapter(BaseAdapter adapter) {
            this.adapter = adapter;
            return this;
        }

        public ChooseEmailDialog create() {
            ChooseEmailDialog dialog = new ChooseEmailDialog(mContext, R.style.EstateDialog);
            if (this.title != null) {
                dialog.setmTitleTV(this.title);
            }
            if (adapter == null){
                dialog.setAdapter(new  EmailChooseDialogAdapter(mContext));
            }else {
                dialog.setAdapter(adapter);
            }
            dialog.setInfos(infos,itemClickListener);
            return dialog;
        }
    }

    public static class EmailDialogInfo {
        public String emailAdd;
        public String description;
        public int resId;
    }


}
