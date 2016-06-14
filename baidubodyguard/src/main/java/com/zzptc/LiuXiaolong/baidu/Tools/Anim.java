package com.zzptc.LiuXiaolong.baidu.Tools;

import android.app.Activity;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zzptc.LiuXiaolong.baidu.R;
import com.zzptc.LiuXiaolong.baidu.view.RatingView;

/**
 * Created by lxl97 on 2016/4/22.
 */
public class Anim {

    public static void enterActivityAnim(Activity activity){
        activity.overridePendingTransition(R.anim.alpha_enter_orther_activity,R.anim.alpha_exit_this_activity);
    }

}
