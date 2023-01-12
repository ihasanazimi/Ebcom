package com.example.ebcom.utility.customViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.appcompat.widget.AppCompatImageView;
import com.example.ebcom.R;

import org.jetbrains.annotations.Nullable;

public class ToggleImageView extends AppCompatImageView implements View.OnClickListener {

    private static final String TAG = "ToggleImageView";

    public static final int STATE_CHECKED = 1;
    public static final int STATE_UNCHECKED = 2;

    private int mState = STATE_UNCHECKED;

    private int mCheckedRes = 0;
    private int mUncheckedRes = 0;

    private OnStateChangedListener mCallbacks = null;

    public interface OnStateChangedListener {
        void onChecked();
        void onUnchecked();
    }


    public ToggleImageView(Context context) {
        super(context);
    }


    public ToggleImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ToggleImageView,
                0, 0);

        try {
            mCheckedRes = typedArray.getResourceId(R.styleable.ToggleImageView_src_checked, 0);
            mUncheckedRes = typedArray.getResourceId(R.styleable.ToggleImageView_src_unchecked, 0);

        } finally {
            typedArray.recycle();
        }

        if (mUncheckedRes!=0)
            setImage(mUncheckedRes);

        this.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        if (mState == STATE_CHECKED) {
            mState = STATE_UNCHECKED;

            setImage(mUncheckedRes);

            if (mCallbacks != null)
                mCallbacks.onUnchecked();

        } else {
            mState = STATE_CHECKED;

            setImage(mCheckedRes);

            if (mCallbacks != null)
                mCallbacks.onChecked();
        }

    }


    /**
     * Set image from resource
     *
     * @param resID image resource id
     */
    private void setImage(int resID) {

        if (resID != 0) {
            this.setImageResource(resID);

        } else {
            Log.i(TAG, "setImage: No image resource provided");
        }
    }


    public void addStateListener(OnStateChangedListener l) {
        mCallbacks = l;
    }

    public void setChecked(){
        setChecked(false);
    }

    public void setUnchecked(){
        setUnchecked(false);
    }

    public void setChecked(boolean callback){
        mState = STATE_CHECKED;
        setImage(mCheckedRes);

        if (callback)
            mCallbacks.onChecked();
    }

    public void setUnchecked(boolean callback){
        mState = STATE_UNCHECKED;
        setImage(mUncheckedRes);

        if (callback)
            mCallbacks.onUnchecked();
    }

    public int getState(){
        return mState;
    }


}