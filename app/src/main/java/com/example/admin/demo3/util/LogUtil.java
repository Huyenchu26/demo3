package com.example.admin.demo3.util;

import android.util.Log;

import static com.example.admin.demo3.AppConfigs.TAG;

public class LogUtil {
    // set level to DISABLE to disable log
    public static final int VERBOSE = Log.VERBOSE;
    public static final int DEBUG = Log.DEBUG;
    public static final int INFO = Log.INFO;
    public static final int WARNING = Log.WARN;
    public static final int ERROR = Log.ERROR;

    public static final int DISABLED = ERROR + 1;

    private static int mLevel = VERBOSE;

    public static int getLevel() {
        return mLevel;
    }

    public static void setLevel(int level) {
        mLevel = level;
    }

    public static void init(String tag) {

    }

    // log without prefix
    public static void v(String content) {
        v(null, content);
    }

    public static void d(String content) {
        d(null, content);
    }

    public static void i(String content) {
        i(null, content);
    }

    public static void w(String content) {
        w(null, content);
    }

    public static void e(String content) {
        e(null, content);
    }

    // log with prefix
    public static void v(String prefix, String content) {
        if (mLevel <= VERBOSE) {
            Log.v(TAG, addPrefix(prefix, content));
        }
    }

    public static void d(String prefix, String content) {
        if (mLevel <= DEBUG) {
            Log.d(TAG, addPrefix(prefix, content));
        }
    }

    public static void i(String prefix, String content) {
        if (mLevel <= INFO) {
            Log.i(TAG, addPrefix(prefix, content));
        }
    }

    public static void w(String prefix, String content) {
        if (mLevel <= WARNING) {
            Log.w(TAG, addPrefix(prefix, content));
        }
    }

    public static void e(String prefix, String content) {
        if (mLevel <= ERROR) {
            Log.e(TAG, addPrefix(prefix, content));
        }
    }

    private static String addPrefix(String prefix, String content) {
        if (prefix != null && prefix.length() > 0) {
            content = "[" + prefix + "] " + content;
        }
        return content;
    }

    // log with throwable information
    public static void v(String content, Throwable e) {
        v(null, content, e);
    }

    public static void d(String content, Throwable e) {
        d(null, content, e);
    }

    public static void i(String content, Throwable e) {
        i(null, content, e);
    }

    public static void w(String content, Throwable e) {
        w(null, content, e);
    }

    public static void e(String content, Throwable e) {
        e(null, content, e);
    }


    public static void v(String prefix, String content, Throwable e) {
        if (mLevel >= VERBOSE) {
            Log.v(TAG, addPrefix(prefix, content), e);
        }
    }

    public static void d(String prefix, String content, Throwable e) {
        if (mLevel >= VERBOSE) {
            Log.d(TAG, addPrefix(prefix, content), e);
        }
    }

    public static void i(String prefix, String content, Throwable e) {
        if (mLevel >= VERBOSE) {
            Log.i(TAG, addPrefix(prefix, content), e);
        }
    }

    public static void w(String prefix, String content, Throwable e) {
        if (mLevel >= VERBOSE) {
            Log.w(TAG, addPrefix(prefix, content), e);
        }
    }

    public static void e(String prefix, String content, Throwable e) {
        if (mLevel >= VERBOSE) {
            Log.e(TAG, addPrefix(prefix, content), e);
        }
    }

    public static String getThrowableString(Throwable throwable) {
        if (throwable == null) {
            return "";
        }
        return throwable.getClass().getSimpleName() +
                (throwable.getMessage() == null ? "" : (" " + throwable.getMessage()));
    }
}
