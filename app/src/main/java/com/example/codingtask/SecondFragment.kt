package com.example.codingtask

import android.graphics.Bitmap
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs


/**
 * Created by Pramod on 4/17/23.
 */
class SecondFragment : Fragment() {

    private val args: SecondFragmentArgs by navArgs()
    private var imageData: Bitmap? = null
    val FLIP_VERTICAL = 1
    val FLIP_HORIZONTAL = 2

    private lateinit var displayView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageData = args.imageData?.bitmap
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_second, null)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayView = view.findViewById(R.id.imageView)
        displayView.setImageBitmap(flipImage(imageData!!, 2))
    }

    /**
     * Function to flip the image
     *
     * @param  src  source bitmap which should be flip
     * @param  type  type of flip(VERTICAL/HORIZENTAL)
     * @return Bitmap returned the flipped bitmap
     */
    private fun flipImage(src: Bitmap, type:Int): Bitmap {
        val matrix = Matrix()
        if(type == FLIP_VERTICAL) {
            matrix.preScale(1.0f, -1.0f);
        } else if(type == FLIP_HORIZONTAL) {
            matrix.preScale(-1.0f, 1.0f);
        }

        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

}