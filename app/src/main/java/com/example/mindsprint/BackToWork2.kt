package com.example.mindsprint

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat


class `BackToWork2` : AppCompatActivity() {

    private var overlayView: View? = null
    private var windowManager: android.view.WindowManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Check if the app has permission to show overlays
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
            // Ask for permission to show overlays
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            startActivityForResult(intent, 1234)
        } else {
            showOverlayWindow()
        }

        // Set the activity layout with the custom view (if needed, you can add this)
        setContentView(R.layout.activity_backtowork)

        // Find the button by its ID
        val closeButton: Button = findViewById(R.id.quit)

        // Set up an OnClickListener for the button to remove the overlay when clicked
        closeButton.setOnClickListener {
            // Remove the overlay view when the "X" button is clicked
            removeOverlay()
        }
    }

    private fun showOverlayWindow() {
        // Create an overlay view with a button that can remove the overlay
        overlayView = View(this)
        overlayView?.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent))

        val layoutParams = android.view.WindowManager.LayoutParams(
            android.view.WindowManager.LayoutParams.MATCH_PARENT,
            android.view.WindowManager.LayoutParams.MATCH_PARENT,
            android.view.WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            android.view.WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            android.graphics.PixelFormat.TRANSLUCENT
        )

        layoutParams.gravity = Gravity.CENTER
        windowManager = getSystemService(Context.WINDOW_SERVICE) as android.view.WindowManager
        windowManager?.addView(overlayView, layoutParams)

        // Set up the overlay view to remove itself when clicked
        overlayView?.setOnClickListener {
            removeOverlay() // Remove the overlay when clicked
        }
    }

    // Function to remove the overlay
    private fun removeOverlay() {
        windowManager?.removeView(overlayView) // Remove the overlay view
    }

    // Handle the result of the permission request
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1234) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Settings.canDrawOverlays(this)) {
                showOverlayWindow() // Show the overlay window
            } else {
                Toast.makeText(this, "Permission required to show overlay", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
