package de.xants.triitus.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import timber.log.Timber;

/**
 * Created by Don on 11.10.2015.
 */
public class AspectRatioImageView extends ImageView {
    public AspectRatioImageView(Context context) {
        super(context);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measuredHeight = MeasureSpec.getSize(heightMeasureSpec);
        Timber.d(" " + measuredWidth + " - " + measuredHeight);
        if (measuredHeight == 0 && measuredWidth == 0) { //Height and width set to wrap_content
            this.setMeasuredDimension(measuredWidth, measuredHeight);
        } else if (measuredHeight == 0) { //Height set to wrap_content
            final int height = Math.round(measuredWidth * 9f / 16f);
            Timber.d("height: " + height);
            this.setMeasuredDimension(measuredWidth, height);
        } else if (measuredWidth == 0) { //Width set to wrap_content
            final int width = Math.round(measuredHeight * 16f / 9f);
            Timber.d("width: " + width);
            this.setMeasuredDimension(width, measuredHeight);
        } else {
            final int height = Math.round(measuredWidth * 9f / 16f);
            Timber.d("height: " + height);
            this.setMeasuredDimension(measuredWidth, height);
        }
    }
}
