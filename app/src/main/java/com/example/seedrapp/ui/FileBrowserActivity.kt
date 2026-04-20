package com.example.seedrapp.ui
import android.os.*
import androidx.appcompat.app.*
import androidx.recyclerview.widget.*
import com.example.seedrapp.network.*
import com.example.seedrapp.ui.adapter.*
import com.example.seedrapp.utils.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class FileBrowserActivity: AppCompatActivity(){
    private val client = SeedrClient()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        val rv = RecyclerView(this)
        setContentView(rv)
        rv.layoutManager = LinearLayoutManager(this)
        Thread{
            val files = client.getFiles()
            runOnUiThread{
                rv.adapter = FileAdapter(files){
                    val opts = arrayOf("Play","Download")
                    MaterialAlertDialogBuilder(this)
                        .setTitle(it.name)
                        .setItems(opts){_,i->
                            when(i){
                                0-> it.streamUrl?.let{u->MXPlayerHelper.play(this,u)}
                                1-> it.downloadUrl?.let{u->DownloadHelper.download(this,u)}
                            }
                        }.show()
                }
            }
        }.start()
    }
}
