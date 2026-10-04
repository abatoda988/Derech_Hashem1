package com.derechhashem.app

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.os.SystemClock
import android.view.accessibility.AccessibilityEvent

class ProtectionAccessibilityService : AccessibilityService() {
    private var lastBlock = 0L
    private val blockedPackages = setOf(
        "com.android.packageinstaller",
        "com.google.android.packageinstaller",
        "com.google.android.gsf.login"
    )
    private val blockedPhrases = listOf(
        "הגדרות אבטחה נוספות",
        "אבטחה נוספת",
        "security settings",
        "more security settings",
        "device admin apps",
        "מנהלי מכשיר"
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        val pkg = event.packageName?.toString() ?: return
        if (pkg == packageName) return
        val now = SystemClock.uptimeMillis()
        if (now - lastBlock < 300) return

        var block = pkg in blockedPackages
        if (!block && (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED ||
                    event.eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED)) {
            val root = rootInActiveWindow
            if (root != null) {
                val text = rootText(root)
                block = blockedPhrases.any { text.contains(it, ignoreCase = true) }
            }
        }
        if (block) {
            lastBlock = now
            performGlobalAction(GLOBAL_ACTION_BACK)
            val i = Intent(this, BlockActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(i)
        }
    }

    private fun rootText(node: android.view.accessibility.AccessibilityNodeInfo): String {
        val sb = StringBuilder()
        fun walk(n: android.view.accessibility.AccessibilityNodeInfo?) {
            if (n == null) return
            n.text?.let { sb.append(it).append(' ') }
            n.contentDescription?.let { sb.append(it).append(' ') }
            for (i in 0 until n.childCount) walk(n.getChild(i))
        }
        walk(node)
        return sb.toString()
    }

    override fun onInterrupt() = Unit
}
