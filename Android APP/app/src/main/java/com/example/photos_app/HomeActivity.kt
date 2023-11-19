package com.example.photos_app

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*


enum class ProviderType {
    Email,
    Google
}

class HomeActivity : AppCompatActivity(), View.OnClickListener,
    RecyclerAdaptor.CountOfImagesWhenRemoved {
    var recyclerView: RecyclerView? = null
    var textView: TextView? = null
    var button: Button? = null
    var button_remove: Button? = null
    var list: ArrayList<Uri>? = null
    var adaptor: RecyclerAdaptor? = null
    var colum = arrayOf(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE


    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        list = java.util.ArrayList()
        recyclerView = findViewById(R.id.recycler)
        textView = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        button_remove = findViewById(R.id.button_remove)
        adaptor = RecyclerAdaptor(list!!, applicationContext, this)
        recyclerView?.setLayoutManager(GridLayoutManager(this@HomeActivity, 4))
        recyclerView?.setAdapter(adaptor)
        button?.setOnClickListener(this)
        button_remove?.setOnClickListener(this)
        if (ActivityCompat.checkSelfPermission(
                this, colum[0]
            ) != PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this, colum[1]
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(colum, 123)
            }
        }
        val bundle: Bundle? = intent.extras
        val email: String? = bundle?.getString("email")
        val provider: String? = bundle?.getString("provider")
        setup(email?: "", provider?: "")

        //Guardado de datos

        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("Email", email)
        prefs.putString("Provider", provider)
        prefs.apply()

        //Cambiar a la interfaz del sentiment analysis

        val btn: Button = findViewById(R.id.button_sentiment)
        btn.setOnClickListener {
            val intent: Intent = Intent(this, SentimentModel::class.java)
            startActivity(intent)
            }

        val btn2: Button = findViewById(R.id.button_caption)
        btn2.setOnClickListener {
            val intent : Intent = Intent(this, MainActivity::class.java).apply{
                putExtra("firstImg", list!![0].toString())
            }
            list!!.remove(list!![0])
            startActivity(intent)
        }
    }

    private fun setup(email: String, provider: String) {
        title = "Home Page"
        emailTextView.text = email
        providerTextView.text = provider

        logOutButton.setOnClickListener {

            val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
            prefs.clear()
            prefs.apply()

            FirebaseAuth.getInstance().signOut()
            onBackPressed()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.button -> openGalley()
            R.id.button_remove -> {
                list!!.clear()
                adaptor?.notifyDataSetChanged()
                textView!!.text = "                                         Image(" + list!!.size + ")"
            }
        }
    }

    private fun openGalley() {
        val intent = Intent()
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 123)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 123 && resultCode == RESULT_OK) {
            if (data!!.clipData != null) {
                val x = data.clipData!!.itemCount
                for (i in 0 until x) {
                    if (list!!.size <= 7) {
                        list!!.add(data.clipData!!.getItemAt(i).uri)
                    }
                    else (showAlertImg())

                    adaptor?.notifyDataSetChanged()
                    textView!!.text = "Image(" + list!!.size + ")"
                }
                adaptor!!.notifyDataSetChanged()
                textView!!.text = "                                         Image(" + list!!.size + ")"
            } else if (data.data != null) {
                val imgurl = data.data!!.path
                list!!.add(Uri.parse(imgurl))
            }
        }
    }


    override fun clicked(getSize: Int) {
        textView!!.text = "                                         Image(" + list!!.size + ")"
    }
    private fun showAlertImg() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage("Amount of images exceded (> 8)")
        builder.setPositiveButton("Accept", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


    //Plotear la parte del sentiment analysis
    //override fun onCreate(savedInstanceState: Bundle?) {
        //super.onCreate(savedInstanceState)
        //setContentView(R.layout.sentiment_model)

        //val btn: Button = findViewById(R.id.button_sentiment)
        //btn.setOnClickListener {
            //val intent: Intent = Intent(this, SentimentModel::class.java)
            //startActivity(intent)
        //}
    //}


}