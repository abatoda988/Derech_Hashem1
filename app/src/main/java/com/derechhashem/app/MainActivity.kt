package com.derechhashem.app

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState)
        val root=LinearLayout(this).apply{orientation=LinearLayout.VERTICAL;gravity=Gravity.CENTER;setPadding(32,32,32,32);setBackgroundColor(Color.rgb(11,16,32))}
        fun tv(t:String,s:Float,c:Int)=TextView(this).apply{text=t;textSize=s;setTextColor(c);gravity=Gravity.CENTER}
        root.addView(tv("דרך השם",32f,Color.WHITE)); root.addView(tv("מערכת סינון והגנה",18f,Color.LTGRAY))
        root.addView(tv("🛡️  המכשיר מוגן",22f,Color.WHITE).apply{setPadding(0,60,0,0)})
        setContentView(root)
    }
}
