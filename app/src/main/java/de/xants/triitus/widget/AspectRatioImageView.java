/*
 * Copyright (C) 2015 https://github.com/donmahallem
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
