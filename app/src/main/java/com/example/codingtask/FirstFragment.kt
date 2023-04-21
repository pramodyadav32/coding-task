package com.example.codingtask

import android.annotation.SuppressLint
import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.pose.Pose
import com.google.mlkit.vision.pose.PoseDetection
import com.google.mlkit.vision.pose.PoseLandmark
import com.google.mlkit.vision.pose.accurate.AccuratePoseDetectorOptions
import java.io.InputStream
import kotlin.math.roundToInt


/**
 * Created by Pramod on 4/17/23.
 */
class FirstFragment : Fragment() {

    private lateinit var btnMirrorImage: Button
    private lateinit var displayView: ImageView
    var currentBitmap: Bitmap? = null

    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_first, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayView = view.findViewById(R.id.imageView)
        val btnNewImage = view.findViewById<Button>(R.id.btnNewImage)
        btnMirrorImage = view.findViewById<Button>(R.id.btnMirrorImage)
        btnMirrorImage.isEnabled = false
        btnMirrorImage.setOnClickListener {
            val additionalFields = ImageData(currentBitmap)
            val action = FirstFragmentDirections.actionFirstFragmentToSecondFragment(additionalFields)
            findNavController().navigate(action)
        }
        var randomNumber: Int
        var inputStream: InputStream = activity!!.assets.open("raised-1.jpg")
        reloadImage(inputStream)
        btnNewImage.setOnClickListener {
            randomNumber = (0..8).random()
            inputStream = if (randomNumber <= 4) {
                activity!!.assets.open("not-raised-$randomNumber.jpg")
            } else {
                val temp = (randomNumber - 4)
                activity!!.assets.open("raised-$temp.jpg")
            }
            reloadImage(inputStream)
        }
    }

    /**
     * Function to initialize the poseDetector's process with new image resources
     *
     * @param  inputStream  input stream of newly loaded image resources
     * @return
     */
    private fun reloadImage(inputStream: InputStream) {
        val options: AccuratePoseDetectorOptions = AccuratePoseDetectorOptions.Builder()
            .setDetectorMode(AccuratePoseDetectorOptions.SINGLE_IMAGE_MODE)
            .build()

        val poseDetector = PoseDetection.getClient(options)

        var bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)

        val aspectRatio: Float = bitmap.width /
                bitmap.height.toFloat()
        val width = 480
        val height = (width / aspectRatio).roundToInt()

        bitmap = Bitmap.createScaledBitmap(
            bitmap, width, height, false
        )

        val image = InputImage.fromBitmap(bitmap, 0)
        poseDetector.process(image).addOnSuccessListener {
            Toast.makeText(activity, "Success ", Toast.LENGTH_LONG).show()
            val canvas = makeFreshCanvas(bitmap)
            val paint = makePaint()
            drawPose(it, canvas, paint)
            displayView.setImageBitmap(bitmap)
            handIsRaised(it)
            currentBitmap = bitmap
        }.addOnFailureListener {
            Toast.makeText(activity, "Failed", Toast.LENGTH_LONG).show()
        }
    }

    private fun makeFreshCanvas(bitmap: Bitmap): Canvas {
        val canvas = Canvas(bitmap)
        canvas.drawBitmap(bitmap, 0f, 0f, null)
        return canvas
    }

    private fun makePaint(): Paint {
        val paint = Paint()
        paint.color = Color.GREEN
        paint.strokeWidth = 4.0f
        return paint
    }

    /**
     * Function to draw the pose lines
     *
     * @param  pose  input stream of newly loaded image resources
     * @param  canvas  canvas object which is already initialized
     * @param  paint  paint object which is already initialized
     * @return
     */
    private fun drawPose(pose: Pose, canvas: Canvas, paint: Paint) {
        /*
        Task 1

        Drawing the pose means drawing lines between all keypoint pairs that are not connected
        i.e. you would draw a line from left shoulder to left elbow, but wouldn't drawn a line
        from left shoulder to left foot.

        You don't have to define this "connectivity" yourself but can use the connection pairs
        defined in CONNECTIVITY_BLAZE.
        */

        // Example to show how a single line is drawn. Remove/replace this if needed
        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)

        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)

        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)

        val leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP)
        val rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP)

        val leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE)
        val rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE)

        val leftAnkle = pose.getPoseLandmark(PoseLandmark.LEFT_ANKLE)
        val rightAnkle = pose.getPoseLandmark(PoseLandmark.RIGHT_ANKLE)

        val leftEye = pose.getPoseLandmark(PoseLandmark.LEFT_EYE)
        val rightEye = pose.getPoseLandmark(PoseLandmark.RIGHT_EYE)



        canvas.drawLine(
            leftShoulder!!.position.x, leftShoulder.position.y,
            rightShoulder!!.position.x, rightShoulder.position.y, paint
        )

        canvas.drawLine(
            rightShoulder.position.x, rightShoulder.position.y,
            rightElbow!!.position.x, rightElbow.position.y, paint
        )

        canvas.drawLine(
            rightElbow.position.x, rightElbow.position.y,
            rightWrist!!.position.x, rightWrist.position.y, paint
        )

        canvas.drawLine(
            leftShoulder.position.x, leftShoulder.position.y,
            leftElbow!!.position.x, leftElbow.position.y, paint
        )

        canvas.drawLine(
            leftElbow.position.x, leftElbow.position.y,
            leftWrist!!.position.x, leftWrist.position.y, paint
        )

        canvas.drawLine(
            rightShoulder.position.x, rightShoulder.position.y,
            rightHip!!.position.x, rightHip.position.y, paint
        )

        canvas.drawLine(
            leftShoulder.position.x, leftShoulder.position.y,
            leftHip!!.position.x, leftHip.position.y, paint
        )

        canvas.drawLine(
            rightHip.position.x, rightHip.position.y,
            rightKnee!!.position.x, rightKnee.position.y, paint
        )

        canvas.drawLine(
            leftHip.position.x, leftHip.position.y,
            rightHip.position.x, rightHip.position.y, paint
        )

        canvas.drawLine(
            leftHip.position.x, leftHip.position.y,
            leftKnee!!.position.x, leftKnee.position.y, paint
        )

        canvas.drawLine(
            rightKnee.position.x, rightKnee.position.y,
            rightAnkle!!.position.x, rightAnkle.position.y, paint
        )

        canvas.drawLine(
            leftKnee.position.x, leftKnee.position.y,
            leftAnkle!!.position.x, leftAnkle.position.y, paint
        )

        canvas.drawLine(
            leftEye!!.position.x, leftEye.position.y,
            rightEye!!.position.x, rightEye.position.y, paint
        )

    }

    /**
     * Function to identify the hand postion (hand is raised or not).
     * If habd is raised it will toast the message hello else bye
     *
     * @param  pose  input stream of newly loaded image resources
     * @return
     */
    private fun handIsRaised(pose: Pose): Boolean {
        // Task 3
//        CONNECTIVITY_BLAZE.get(PoseLandmark.LEFT_SHOULDER);
        val leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER)
        val rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER)
        val leftElbow = pose.getPoseLandmark(PoseLandmark.LEFT_ELBOW)
        val rightElbow = pose.getPoseLandmark(PoseLandmark.RIGHT_ELBOW)

        val leftWrist = pose.getPoseLandmark(PoseLandmark.LEFT_WRIST)
        val rightWrist = pose.getPoseLandmark(PoseLandmark.RIGHT_WRIST)
        if((leftShoulder!!.position.y > leftElbow!!.position.y && leftElbow!!.position.y > leftWrist!!.position.y) ||
            (rightShoulder!!.position.y > rightElbow!!.position.y && rightElbow!!.position.y > rightWrist!!.position.y)){
            Toast.makeText(activity, "Hello!", Toast.LENGTH_LONG).show()
        }else{
            Toast.makeText(activity, "Bye!", Toast.LENGTH_LONG).show()
        }
        btnMirrorImage.isEnabled = true
        return false
    }

    companion object {
        val CONNECTIVITY_BLAZE: List<Pair<Int, Int>> = listOf(
            // face
            (PoseLandmark.LEFT_EAR to PoseLandmark.LEFT_EYE),
            (PoseLandmark.RIGHT_EAR to PoseLandmark.RIGHT_EYE),
            (PoseLandmark.LEFT_EYE to PoseLandmark.NOSE),
            (PoseLandmark.NOSE to PoseLandmark.RIGHT_EYE),

            // left arm
            (PoseLandmark.LEFT_SHOULDER to PoseLandmark.LEFT_ELBOW),
            (PoseLandmark.LEFT_ELBOW to PoseLandmark.LEFT_WRIST),
            (PoseLandmark.LEFT_WRIST to PoseLandmark.LEFT_THUMB),
            (PoseLandmark.LEFT_WRIST to PoseLandmark.LEFT_PINKY),
            (PoseLandmark.LEFT_WRIST to PoseLandmark.LEFT_INDEX),
            (PoseLandmark.LEFT_PINKY to PoseLandmark.LEFT_INDEX),

            // right arm
            (PoseLandmark.RIGHT_SHOULDER to PoseLandmark.RIGHT_ELBOW),
            (PoseLandmark.RIGHT_ELBOW to PoseLandmark.RIGHT_WRIST),
            (PoseLandmark.RIGHT_WRIST to PoseLandmark.RIGHT_THUMB),
            (PoseLandmark.RIGHT_WRIST to PoseLandmark.RIGHT_PINKY),
            (PoseLandmark.RIGHT_WRIST to PoseLandmark.RIGHT_INDEX),
            (PoseLandmark.RIGHT_PINKY to PoseLandmark.RIGHT_INDEX),

            // torso
            (PoseLandmark.LEFT_SHOULDER to PoseLandmark.RIGHT_SHOULDER),
            (PoseLandmark.RIGHT_SHOULDER to PoseLandmark.RIGHT_HIP),
            (PoseLandmark.LEFT_HIP to PoseLandmark.RIGHT_HIP),
            (PoseLandmark.LEFT_HIP to PoseLandmark.LEFT_SHOULDER),

            // right leg
            (PoseLandmark.RIGHT_HIP to PoseLandmark.RIGHT_KNEE),
            (PoseLandmark.RIGHT_KNEE to PoseLandmark.RIGHT_ANKLE),
            (PoseLandmark.RIGHT_ANKLE to PoseLandmark.RIGHT_HEEL),
            (PoseLandmark.RIGHT_HEEL to PoseLandmark.RIGHT_FOOT_INDEX),
            (PoseLandmark.RIGHT_FOOT_INDEX to PoseLandmark.RIGHT_ANKLE),

            // left leg
            (PoseLandmark.LEFT_HIP to PoseLandmark.LEFT_KNEE),
            (PoseLandmark.LEFT_KNEE to PoseLandmark.LEFT_ANKLE),
            (PoseLandmark.LEFT_ANKLE to PoseLandmark.LEFT_HEEL),
            (PoseLandmark.LEFT_HEEL to PoseLandmark.LEFT_FOOT_INDEX),
            (PoseLandmark.LEFT_FOOT_INDEX to PoseLandmark.LEFT_ANKLE)
        )
    }
}