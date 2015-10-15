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

package de.xants.triitus.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * Applies an Circular Mask to the Image
 * <p/>
 * Created by Don on 14.10.2015.
 */
public class CircleTransformation implements Transformation {
    @Override
    public Bitmap transform(Bitmap source) {
        int dimension = Math.min(source.getWidth(), source.getHeight());
        int originX = (source.getWidth() - dimension) / 2;
        int originY = (source.getHeight() - dimension) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, originX, originY, dimension, dimension);
        Bitmap outputImage = Bitmap.createBitmap(dimension, dimension, source.getConfig());
        if (squaredBitmap != source) {
            source.recycle();
        }
        Canvas canvas = new Canvas(outputImage);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap,
                BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        //SMOOOOOOOOOTH
        paint.setAntiAlias(true);
        paint.setShader(shader);
        final float radius = dimension / 2f;
        canvas.drawCircle(radius, radius, radius, paint);
        squaredBitmap.recycle();
        return outputImage;
    }

    @Override
    public String key() {
        return "CircleTransformation";
    }
}