package ji.ruucm.android.margin_animation;

import java.util.*;
import java.util.regex.*;

import ji.ruucm.android.chat.MainActivity;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.media.*;
import android.util.*;

public class MediaAssets {
        private Hashtable<Integer, MediaPlayer> soundPlayers = new Hashtable<Integer, MediaPlayer>();
        private Resources resources;

        private MediaAssets() {
        }

        private static MediaAssets singleton;

        public static MediaAssets getInstance() {
                if (singleton == null) {
                        singleton = new MediaAssets();
                        singleton.prepareAssets();
                }
                return singleton;
        }

        private void prepareAssets() {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inScaled = true;
                Context context = MainActivity.getInstance();
                resources = context.getResources();
        }


        public MediaPlayer getSoundPlayer(int resId) {
                MediaPlayer output = soundPlayers.get(resId);
                if (output == null) {
                        try {
                                output = CreateSoundPlayer(resId);
                                soundPlayers.put(resId, output);
                        } catch (Exception e) {
                                output = null;
                        }
                }
                return output;
        }

        private MediaPlayer CreateSoundPlayer(int resId) {
                return MediaPlayer.create(MainActivity.getInstance(), resId);
        }

}