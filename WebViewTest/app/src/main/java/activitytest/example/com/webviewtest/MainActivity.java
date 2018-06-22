package activitytest.example.com.webviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        //当需要从一个网页跳转到另一个网页时，我们希望目标网页仍然在当前webview显示
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.baidu.com");

    }
}
