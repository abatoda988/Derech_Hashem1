package com.derechhashem.app

import android.app.Activity
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class BlockActivity: Activity(){
 private val h=Handler(Looper.getMainLooper()); private var unlocked=false
 override fun onCreate(b:Bundle?){super.onCreate(b);window.statusBarColor=Color.rgb(11,16,32);window.navigationBarColor=Color.rgb(11,16,32)
  val r=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;gravity=Gravity.CENTER;setPadding(36,40,36,32);setBackgroundColor(Color.rgb(11,16,32));layoutDirection=View.LAYOUT_DIRECTION_RTL}
  val icon=TextView(this).apply{text="🛡️";textSize=58f;gravity=Gravity.CENTER}
  val title=TextView(this).apply{text="הפעולה חסומה";textSize=30f;typeface=Typeface.DEFAULT_BOLD;setTextColor(Color.WHITE);gravity=Gravity.CENTER;setPadding(0,24,0,14)}
  val msg=TextView(this).apply{text="המכשיר מוגן על ידי מערכת סינון דרך השם.";textSize=18f;setTextColor(Color.rgb(210,218,235));gravity=Gravity.CENTER}
  val foot=TextView(this).apply{text="דרך השם • מערכת סינון והגנה";textSize=15f;setTextColor(Color.rgb(150,165,195));gravity=Gravity.CENTER}
  val back=Button(this).apply{text="חזור";textSize=18f;isAllCaps=false;isEnabled=false;alpha=.45f;setOnClickListener{finish()}}
  r.addView(icon);r.addView(title);r.addView(msg);r.addView(View(this),LinearLayout.LayoutParams(1,0,1f));r.addView(foot)
  r.addView(back,LinearLayout.LayoutParams(-2,-2).apply{gravity=Gravity.CENTER;topMargin=18});setContentView(r)
  h.postDelayed({unlocked=true;back.isEnabled=true;back.alpha=1f},2000)
 }
 override fun dispatchTouchEvent(e:MotionEvent):Boolean=if(!unlocked)true else super.dispatchTouchEvent(e)
 override fun onDestroy(){h.removeCallbacksAndMessages(null);super.onDestroy()}
}
