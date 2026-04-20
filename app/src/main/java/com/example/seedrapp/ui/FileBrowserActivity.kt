package com.example.seedrapp.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.seedrapp.R
import com.example.seedrapp.network.SeedrClient
import com.example.seedrapp.ui.adapter.FileAdapter
import com.example.seedrapp.utils.DownloadHelper
import com.example.seedrapp.utils.MXPlayerHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FileBrowserActivity : AppCompatActivity() {

    private val client = SeedrClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val rv = RecyclerView(this)
        setContentView(rv)

        rv.layoutManager = LinearLayoutManager(this)

        loadFiles(rv)
    }

    private fun loadFiles(rv: RecyclerView) {

        Thread {
            try {

                val files = client.getFiles()

                runOnUiThread {

                    if (files.isEmpty()) {
                        Toast.makeText(this, "No files / Not logged in yet", Toast.LENGTH_SHORT).show()
                    }

                    rv.adapter = FileAdapter(files) { file ->

                        if (file.type == "folder") return@FileAdapter

                        val options = arrayOf("Play", "Download", "Copy Link")

                        MaterialAlertDialogBuilder(this)
                            .setTitle(file.name)
                            .setItems(options) { _, which ->

                                when (which) {
                                    0 -> file.streamUrl?.let {
                                        MXPlayerHelper.play(this, it)
                                    } ?: showToast("Stream not ready")

                                    1 -> file.downloadUrl?.let {
                                        DownloadHelper.download(this, it)
                                    } ?: showToast("Download not ready")

                                    2 -> {
                                        showToast("Link copied (not implemented fully)")
                                    }
                                }
                            }
                            .show()
                    }
                }

            } catch (e: Exception) {
                runOnUiThread {
                    e.printStackTrace()
                    Toast.makeText(this, "Error loading files", Toast.LENGTH_SHORT).show()
                }
            }

        }.start()
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}
