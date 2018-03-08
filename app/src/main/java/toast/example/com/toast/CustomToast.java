package toast.example.com.toast;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Looper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.HashMap;

import static toast.example.com.toast.MainActivity.handler;

/**
 * Created by root on 18-2-7.
 */

public class CustomToast {

    private static HashMap<Context, CustomToast> hashMap = new HashMap<>();
    private final Context mContext;
    private WindowManager mSystemService;
    private WindowManager.LayoutParams mParams;
    private int mDuration;
    private String mContent;


    private CustomToast(Context context, int duration, String content) {
        mContext = context;
        mSystemService = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        mDuration = duration;
        mContent = content;
        setParams();
    }


    public static CustomToast make(Context context, int duration, String content) {
        if (context instanceof Application && context instanceof Activity) {
            if (assertNotDestroyed((Activity) context)) {
                throw new IllegalArgumentException("You cannot start a load for a destroyed activity");
            }
        }
        CustomToast toast = hashMap.get(context);
        if (toast == null) {
            toast = new CustomToast(context, duration, content);
        }
        return toast;

    }


    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    private static boolean assertNotDestroyed(Activity activity) {
        return activity.isDestroyed();

    }

    private void setParams() {
        mParams = new WindowManager.LayoutParams();
        mParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        mParams.format = PixelFormat.TRANSLUCENT;
        mParams.windowAnimations = R.style.custom_animation_toast;
        mParams.type = WindowManager.LayoutParams.TYPE_TOAST;
        mParams.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        mParams.gravity = Gravity.TOP | Gravity.CENTER_HORIZONTAL;
        mParams.y = (int) (3.5f * 11.43f);
    }

    public void show() {
        if (!isOnMainThread()) {
            throw new IllegalArgumentException("You must call this method on the main thread");
        }

        final View mToastView = LayoutInflater.from(mContext).inflate(R.layout.common_toast, null);
        TextView textView = mToastView.findViewById(R.id.toast_text);
        textView.setText(mContent);
        mSystemService.addView(mToastView, mParams);

        mToastView.postDelayed(new Runnable() {
            @Override
            public void run() {
                mSystemService.removeViewImmediate(mToastView);
            }
        }, mDuration);
    }

}
