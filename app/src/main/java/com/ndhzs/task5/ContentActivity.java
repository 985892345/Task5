package com.ndhzs.task5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.ndhzs.task5.MyDrawerLayout.MyDrawerLayout;
import com.ndhzs.task5.Adapter.Content_RecyclerViewAdapter;
import com.ndhzs.task5.Net.SendNetRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity {

    private MHandler mHandler;
    private RecyclerView mRecycler;

    private static final int SUCCEED = 0;
    private static final int FAIL = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        mHandler = new MHandler(this);
        new SendNetRequest(mHandler).sendGetNetRequest("https://www.wanandroid.com/article/list/0/json");
        new MyDrawerLayout(this);
        mRecycler = findViewById(R.id.content_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private static class MHandler extends Handler {

        private final WeakReference<ContentActivity> weakReference;

        public MHandler(ContentActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            ContentActivity activity= weakReference.get();

            if (activity != null){
                switch (msg.what) {
                    case SUCCEED : {
                        JSONObject js = (JSONObject) msg.obj;
                        try {
                            JSONArray jsonArray = js.getJSONObject("data").getJSONArray("datas");
                            List<String[]> data = new ArrayList<>(jsonArray.length());
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String title = jsonArray.getJSONObject(i).getString("title");
                                String shareUser = jsonArray.getJSONObject(i).getString("superChapterName");
                                String link = jsonArray.getJSONObject(i).getString("link");
                                data.add(new String[]{title, shareUser, link});
                                activity.mRecycler.setAdapter(new Content_RecyclerViewAdapter(activity, data));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                    case FAIL : {
                        Toast.makeText(activity, "网络请求失败！", Toast.LENGTH_LONG).show();
                        break;
                    }
                }
            }
        }
    }
}