package activitytest.example.com.fragmentbestpractice;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NewsTitleFragment extends Fragment {

    private boolean isTwoPane;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_title_frag,container,false);
        //这个是news_title_frag中的主要部分
        RecyclerView newsTitleRecyclerView = (RecyclerView)view.findViewById(R.id.news_title_recycler_view);
        LinearLayoutManager layoutManager  =  new LinearLayoutManager(getActivity());
        newsTitleRecyclerView.setLayoutManager(layoutManager);

        NewsAdapter adapter = new NewsAdapter(getNews());
        newsTitleRecyclerView.setAdapter(adapter);
        return  view;


    }


    //获取新闻返回新闻列表
    private List<News> getNews(){

        List<News> newsList = new ArrayList<>();
        for (int i =1; i <= 50;i++){

            News news = new  News();
            news.setTitle("this is news title " + i);
            news.setContent(getRandomLengthContent("this is new content " + i +"."));
            newsList.add(news);
        }
        return  newsList;
    }


    private String getRandomLengthContent(String content){

        Random random = new Random();
        int length = random.nextInt(20)+1;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length ; i ++){
            builder.append(content);
        }
        return  builder.toString();
    }









    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //如果是双页的，我们在layout-sw600dp中加了frameLayout，Id设置为news_content_layout，以它为判断条件
        if (getActivity().findViewById(R.id.news_content_layout) != null){
            //双页
            isTwoPane = true;
        }else{
            //单页
            isTwoPane = false;
        }
    }

    /*
    ===========================建一个newsAdapter的内部类==============================================
     */
    class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder>{

        private List<News> mNewsList;

        public class ViewHolder extends RecyclerView.ViewHolder {

            TextView newsTitleText;
            public ViewHolder(View view) {
                super(view);
                newsTitleText = (TextView)view.findViewById(R.id.news_title);
            }
        }

        public NewsAdapter(List<News> newsList){
            mNewsList = newsList;

        }


        //cellForItem
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);//viewHolder需要一个view参数作为superView
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //拿到具体数据,从这里可以看到是根据holder得到的position
                    News news = mNewsList.get(holder.getAdapterPosition());
                    if (isTwoPane){
                        //如果是双页模式，则刷新content内容
                        //这里get内部set
                        NewsContentFragment newsContentFragment = (NewsContentFragment)getFragmentManager().findFragmentById(R.id.news_content_fragment);
                        newsContentFragment.refresh(news.getTitle(),news.getContent());


                    }else{

                        //如果是单页模式，则跳转到内容页
                        NewsContentActivity.actionStart(getActivity(),news.getTitle(),news.getContent());

                    }
                }

            });

            return  holder;



        }

        // 绑定数据
        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            News news = mNewsList.get(position);
            holder.newsTitleText.setText(news.getTitle());

        }

        @Override
        public int getItemCount() {
            return mNewsList.size();
        }

    }
     /*
    ================================================================================================
     */






}
