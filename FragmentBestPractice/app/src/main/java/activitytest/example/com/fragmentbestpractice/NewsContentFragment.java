package activitytest.example.com.fragmentbestpractice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class NewsContentFragment extends Fragment {

    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return  view;
    }

    public void refresh(String newsTitle, String newsContent){

        //通过上边view绑定的总布局，得到相应的控件
        View visibilityLayout = view.findViewById(R.id.visibility_layout);
        //这个时候应该是有数据了，让控件显示吧
        visibilityLayout.setVisibility(View.VISIBLE);
        //拿到标题和内容
        TextView newsTitleText = (TextView)view.findViewById(R.id.news_title);
        TextView newsContentText = (TextView)view.findViewById(R.id.news_content);

        //刷新标题和内容
        newsTitleText.setText(newsTitle);
        newsContentText.setText(newsContent);
    }

}
