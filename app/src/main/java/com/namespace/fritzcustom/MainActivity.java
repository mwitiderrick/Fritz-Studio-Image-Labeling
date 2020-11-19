package com.namespace.fritzcustom;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import ai.fritz.core.Fritz;
import ai.fritz.vision.FritzVision;
import ai.fritz.vision.FritzVisionImage;
import ai.fritz.vision.FritzVisionModels;
import ai.fritz.vision.PredictorStatusListener;
import ai.fritz.vision.imagelabeling.FritzVisionLabelPredictor;
import ai.fritz.vision.imagelabeling.FritzVisionLabelPredictorOptions;
import ai.fritz.vision.imagelabeling.FritzVisionLabelResult;
import ai.fritz.vision.imagelabeling.LabelingManagedModel;
import ai.fritz.vision.imagelabeling.LabelingOnDeviceModel;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    Button buttonClick;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Fritz.configure(this, "YOUR_API_KEY");
        textView = findViewById(R.id.textView);
        buttonClick = findViewById(R.id.buttonClick);
    }

    public void labelImage(View view){
        List<String> labels = Arrays.asList("Background", "cat", "dog");
        LabelingOnDeviceModel onDeviceModel = new LabelingOnDeviceModel(
                "file:///android_asset/ThursdayFast.tflite",
                "1604aa7710bd44ecb04caef133073848",
                2,
                labels
        );

        Bitmap image = BitmapFactory.decodeResource(getResources(), R.drawable.cat);
        FritzVisionImage visionImage = FritzVisionImage.fromBitmap(image);

        FritzVisionLabelPredictorOptions options = new FritzVisionLabelPredictorOptions();
        options.confidenceThreshold = 0.1f;

        FritzVisionLabelPredictor predictor = FritzVision.ImageLabeling.getPredictor(
                onDeviceModel, options
        );
        FritzVisionLabelResult labelResult = predictor.predict(visionImage);
        Log.i( "Info", "The Label is" + labelResult.getResultString());
        textView.append("The label is " + labelResult.getResultString());

        Toast.makeText(this, "The Label is " + labelResult.getResultString(), Toast.LENGTH_LONG).show();
    }
}