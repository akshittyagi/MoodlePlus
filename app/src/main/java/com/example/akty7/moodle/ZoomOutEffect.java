package com.example.akty7.moodle;

import android.support.v4.view.ViewPager;
import android.view.View;

public class ZoomOutEffect implements ViewPager.PageTransformer {

    private static final float min_Scale = 0.85f;
    private static final float min_Alpha = 0.5f;

    public void transformPage(View view, float position){
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if(position < -1){
            view.setAlpha(0);
        }
        else if (position <= 1){
            float scale = Math.max(min_Scale, 1 - Math.abs(position));
            float verticalMargin = pageHeight * (1 - scale) / 2;
            float horizontalMargin = pageWidth * (1 - scale) / 2;
            if(position < 0){
                view.setTranslationX(horizontalMargin - verticalMargin / 2);
            }
            else{
                view.setTranslationX(verticalMargin - horizontalMargin / 2);
            }
            view.setScaleX(scale);
            view.setScaleY(scale);

            view.setAlpha(min_Alpha +
                    (scale - min_Scale) /
                            (1 - min_Scale) * (1 - min_Alpha));
        }
        else{
            view.setAlpha(0);
        }
    }
}
