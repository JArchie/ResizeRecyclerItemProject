package com.jarchie.resizeitem.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.jarchie.resizeitem.utils.Utils;
import com.jarchie.resizeitem.webview.AdBrowserLayout;
import com.jarchie.resizeitem.webview.AdBrowserWebViewClient;
import com.jarchie.resizeitem.webview.Base64Drawables;
import com.jarchie.resizeitem.webview.BrowserWebView;

/**
 * 作者：created by Jarchie
 * 时间：2019-11-24 17:39:44
 * 邮箱：jarchie520@gmail.com
 * 说明：WebView通用页面
 */
public final class AdBrowserActivity extends AppCompatActivity {

    private BrowserWebView mAdBrowserWebview;
    private AdBrowserLayout mLayout;
    private View mProgress;
    private Button mBackButton;
    private Base64Drawables mBase64Drawables = new Base64Drawables();

    protected String mUrl;
    private boolean mIsBackFromMarket = false;
    private AdBrowserWebViewClient.Listener mWebClientListener;

    @Override
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        mUrl = getIntent().getStringExtra("link");
        mLayout = new AdBrowserLayout(this.getApplicationContext());
        mProgress = mLayout.getProgressBar();
        mBackButton = mLayout.getBackButton();
        mAdBrowserWebview = mLayout.getWebView();
        setContentView(mLayout);
        initWebView(mAdBrowserWebview);
        if (bundle != null) {
            mAdBrowserWebview.restoreState(bundle);
        } else {
            mAdBrowserWebview.loadUrl(mUrl);
        }
        initButtonListeners(mAdBrowserWebview);

    }

    @Override
    protected final void onPause() {
        super.onPause();
        if (mAdBrowserWebview != null) {
            mAdBrowserWebview.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        if (mAdBrowserWebview != null) {
            mAdBrowserWebview.clearCache(true);
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mIsBackFromMarket) {
            //finish();
        }
        mIsBackFromMarket = true;
        mLayout.getProgressBar().setVisibility(View.INVISIBLE);
    }

    private void initWebView(BrowserWebView webView) {
        mWebClientListener = initAdBrowserClientListener();
        AdBrowserWebViewClient client = new AdBrowserWebViewClient(mWebClientListener);
        webView.setWebViewClient(client);
        webView.getSettings().setBuiltInZoomControls(false);
    }

    private AdBrowserWebViewClient.Listener initAdBrowserClientListener() {
        return new AdBrowserWebViewClient.Listener() {

            @Override
            public void onReceiveError() {
                finish();
            }

            @Override
            public void onPageStarted() {
                mProgress.setVisibility(View.VISIBLE);
            }

            @Override
            @SuppressLint("NewApi")
            public void onPageFinished(boolean canGoBack) {
                mProgress.setVisibility(View.INVISIBLE);
                if (canGoBack) {
                    setImage(mBackButton, mBase64Drawables.getBackActive());
                } else {
                    setImage(mBackButton, mBase64Drawables.getBackInactive());
                }
            }

            @Override
            public void onLeaveApp() {

            }
        };
    }

    @SuppressLint("NewApi")
    private void setImage(Button button, String imageString) {
        if (Build.VERSION.SDK_INT < 16) {
            button.setBackgroundDrawable(Utils.decodeImage(imageString));
        } else {
            button.setBackground(Utils.decodeImage(imageString));
        }
    }

    private void initButtonListeners(final WebView webView) {

        mLayout.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (webView.canGoBack()) {
                    mLayout.getProgressBar().setVisibility(View.VISIBLE);
                    webView.goBack();
                }
            }
        });

        mLayout.getCloseButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mLayout.getRefreshButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLayout.getProgressBar().setVisibility(View.VISIBLE);
                webView.reload();
            }
        });

        mLayout.getNativeButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriString = webView.getUrl();
                if (uriString != null) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uriString));

                    boolean isActivityResolved =
                            getPackageManager().resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY)
                                    != null ? true : false;
                    if (isActivityResolved) {
                        startActivity(browserIntent);
                    }
                }
            }
        });
    }

    @Override
    public final boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mAdBrowserWebview.canGoBack()) {
                mAdBrowserWebview.goBack();
                return true;
            }
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        mIsBackFromMarket = false;
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        mAdBrowserWebview.saveState(outState);
        super.onSaveInstanceState(outState);
    }
}
