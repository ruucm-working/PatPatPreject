package com.ruucm.patpat;

import android.graphics.Bitmap;

public interface IAnimatable {

        public abstract boolean AnimationFinished();

        public abstract void Draw(Bitmap canvas);

}
