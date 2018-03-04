package ru.crypto.android.cryptomonitor.ui.view.manager;

import android.graphics.Bitmap;
import android.graphics.Path;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

//https://github.com/florent37/ShapeOfView
public interface ClipManager {

    @NonNull
    Bitmap createMask(int width, int height);

    @Nullable
    Path getShadowConvexPath();

    void setupClipLayout(int width, int height);
}
