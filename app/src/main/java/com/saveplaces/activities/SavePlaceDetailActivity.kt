package com.saveplaces.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.saveplaces.R
import com.saveplaces.models.SavePlaceModel
import kotlinx.android.synthetic.main.activity_save_place_detail.*

class SavePlaceDetailActivity : AppCompatActivity() {

    /**
     * This function is auto created by Android when the Activity Class is created.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)
        // This is used to align the xml view to this class
        setContentView(R.layout.activity_save_place_detail)

        var savePlaceDetailModel: SavePlaceModel? = null

        if (intent.hasExtra(MainActivity.EXTRA_PLACE_DETAILS)) {
            // get the Serializable data model class with the details in it
            savePlaceDetailModel =
                    intent.getSerializableExtra(MainActivity.EXTRA_PLACE_DETAILS) as SavePlaceModel
        }

        if (savePlaceDetailModel != null) {

            setSupportActionBar(toolbar_save_place_detail)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.title = savePlaceDetailModel.title

            toolbar_save_place_detail.setNavigationOnClickListener {
                onBackPressed()
            }

            iv_place_image.setImageURI(Uri.parse(savePlaceDetailModel.image))
            tv_description.text = savePlaceDetailModel.description
            tv_location.text = savePlaceDetailModel.location
        }

        btn_view_on_map.setOnClickListener {
            val intent = Intent(this@SavePlaceDetailActivity, MapActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_PLACE_DETAILS, savePlaceDetailModel)
            startActivity(intent)
        }
    }
}