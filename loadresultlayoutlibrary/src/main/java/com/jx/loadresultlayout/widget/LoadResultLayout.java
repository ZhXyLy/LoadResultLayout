package com.jx.loadresultlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.jx.loadresultlayout.R;


/**
 * 加载结果页面
 * <pre>
 * 预览哪个页面：默认success
 * <attr name="load_result_preview" format="enum">
 *      <enum name="loading" value="0x00000000" />
 *      <enum name="success" value="0x00000001" />
 *      <enum name="error" value="0x00000002" />
 *      <enum name="empty" value="0x00000003" />
 * </attr>
 * 设置当前页面的loading页面：
 * <attr name="load_result_loading_view" format="reference" />
 * 设置当前页面的empty页面：
 * <attr name="load_result_empty_view" format="reference" />
 * 设置当前页面的error页面：
 * <attr name="load_result_error_view" format="reference" />
 * 错误页面触发重新加载的ViewId：例如{@code @id/btn_reload}
 * <attr name="load_result_reload_view_id" format="integer" />
 * </pre>
 *
 * @author zhaoxl
 * @date 2018/6/7
 */
public class LoadResultLayout extends ViewGroup {
//    private static final String TAG = "LoadResultLayout";

    public static final int LOADING = 0x00000000;
    public static final int SUCCESS = 0x00000001;
    public static final int ERROR = 0x00000002;

    /**
     * 没网（为了短）
     */
    public static final int NO_NET = 0x00000004;
    public static final int EMPTY = 0x00000003;

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;
    private View mNoNetView;
    private View mSuccessView;
    private int mPreview;

    public LoadResultLayout(Context context) {
        this(context, null);
    }

    public LoadResultLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadResultLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoadResultLayout);
        int loadingLayoutId = LoadResultLayoutConfig.getInstance().getDefaultLoadingLayoutId();
        if (loadingLayoutId == 0) {
            loadingLayoutId = R.layout.load_result_loading_view;
        }
        int loadingViewId = a.getResourceId(R.styleable.LoadResultLayout_load_result_loading_view, loadingLayoutId);
        mLoadingView = inflate(context, loadingViewId, null);

        int emptyLayoutId = LoadResultLayoutConfig.getInstance().getDefaultEmptyLayoutId();
        if (emptyLayoutId == 0) {
            emptyLayoutId = R.layout.load_result_empty_view;
        }
        int emptyViewId = a.getResourceId(R.styleable.LoadResultLayout_load_result_empty_view, emptyLayoutId);
        mEmptyView = inflate(context, emptyViewId, null);

        int errorLayoutId = LoadResultLayoutConfig.getInstance().getDefaultErrorLayoutId();
        if (errorLayoutId == 0) {
            errorLayoutId = R.layout.load_result_error_view;
        }
        int errorViewId = a.getResourceId(R.styleable.LoadResultLayout_load_result_error_view, errorLayoutId);
        mErrorView = inflate(context, errorViewId, null);
        int defaultReloadViewId = LoadResultLayoutConfig.getInstance().getDefaultReloadViewId();
        if (defaultReloadViewId == 0) {
            defaultReloadViewId = R.id.btn_reload;
        }
        int btnReloadId = a.getResourceId(R.styleable.LoadResultLayout_load_result_reload_view_id, defaultReloadViewId);
        View btnReload = mErrorView.findViewById(btnReloadId);
        if (btnReload != null) {
            btnReload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload();
                }
            });
        }

        int noNetLayoutId = LoadResultLayoutConfig.getInstance().getDefaultNoNetLayoutId();
        if (noNetLayoutId == 0) {
            noNetLayoutId = R.layout.load_result_no_net_view;
        }
        int noNetViewId = a.getResourceId(R.styleable.LoadResultLayout_load_result_no_net_view, noNetLayoutId);
        mNoNetView = inflate(context, noNetViewId, null);
        int defaultNoNetReloadViewId = LoadResultLayoutConfig.getInstance().getDefaultReloadNoNetViewId();
        if (defaultNoNetReloadViewId == 0) {
            defaultNoNetReloadViewId = R.id.btn_reload_no_wifi;
        }
        int noNetReloadId = a.getResourceId(R.styleable.LoadResultLayout_load_result_reload_no_net_view_id, defaultNoNetReloadViewId);
        View btnNoNetReload = mNoNetView.findViewById(noNetReloadId);
        if (btnNoNetReload != null) {
            btnNoNetReload.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload();
                }
            });
        }

        mPreview = a.getInt(R.styleable.LoadResultLayout_load_result_preview, SUCCESS);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            final LayoutParams lp = child.getLayoutParams();
            lp.width = LayoutParams.MATCH_PARENT;
            lp.height = LayoutParams.MATCH_PARENT;
            child.setLayoutParams(lp);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            child.layout(l, t - getTop(), r, b - getTop());
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        int childCount = getChildCount();
        if (childCount == 0) {
            return;
        }
        if (childCount > 1) {
            throw new IllegalStateException("只能有一个子View");
        } else {
            mSuccessView = getChildAt(0);
            addView(mEmptyView);
            addView(mErrorView);
            addView(mNoNetView);
            addView(mLoadingView);
        }

        if (isInEditMode()) {
            switch (mPreview) {
                case LOADING:
                    loading();
                    break;
                case ERROR:
                    error();
                    break;
                case NO_NET:
                    noWifi();
                    break;
                case EMPTY:
                    empty();
                    break;
                case SUCCESS:
                default:
                    success();
                    break;
            }
        } else {
            loading();
        }
    }

    private void resetView() {
        mLoadingView.setVisibility(INVISIBLE);
        mSuccessView.setVisibility(INVISIBLE);
        mErrorView.setVisibility(INVISIBLE);
        mNoNetView.setVisibility(INVISIBLE);
        mEmptyView.setVisibility(INVISIBLE);
    }

    public void success() {
        resetView();
        mSuccessView.setVisibility(VISIBLE);
    }

    public void empty() {
        resetView();
        mEmptyView.setVisibility(VISIBLE);
    }

    public void error() {
        resetView();
        mErrorView.setVisibility(VISIBLE);
    }

    public void noWifi() {
        resetView();
        mNoNetView.setVisibility(VISIBLE);
    }

    public void loading() {
        resetView();
        mLoadingView.setVisibility(VISIBLE);
    }

    /**
     * 重新加载
     * 必须设置{@link LoadResultLayout#setReloadListener(OnReloadListener)}才有效
     */
    public void reload() {
        if (mReloadListener != null) {
            loading();
            mReloadListener.onReload();
        }
    }

    public void setReloadListener(OnReloadListener listener) {
        this.mReloadListener = listener;
    }

    public interface OnReloadListener {
        /**
         * 重新加载，失败时和调{@link LoadResultLayout#reload()}时
         */
        void onReload();
    }

    private OnReloadListener mReloadListener;
}
