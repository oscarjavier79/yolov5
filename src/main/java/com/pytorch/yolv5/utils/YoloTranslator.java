package com.pytorch.yolv5.utils;

import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.output.BoundingBox;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.modality.cv.output.Rectangle;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.ndarray.types.DataType;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;

import java.util.ArrayList;
import java.util.List;

public class YoloTranslator implements Translator<Image, DetectedObjects> {

    float confThresh = 0.25f;

    @Override
    public DetectedObjects processOutput(TranslatorContext translatorContext, NDList ndList) throws Exception {
        NDArray result = ndList.singletonOrThrow();
        float[] data = result.toFloatArray();

        List<String> className = new ArrayList<>();
        List<Double> probabilities = new ArrayList<>();
        List<BoundingBox> boxes = new ArrayList<>();

        for(int i=0; i < data.length; i+=6){
            float x1 = data[i];
            float y1 = data[i+1];
            float x2 = data[i+1];
            float y2 = data[i+1];
            float conf = data[i+4];
            int classId = (int) data[i+5];

            if(conf < confThresh) continue;

            float w = x2 - x1;
            float h = y2 - y1;

            BoundingBox box = new Rectangle(x1, y1, w, h);

            String name = classId == 0 ? "mask" : "no_mask";

            className.add(name);
            probabilities.add((double) conf);
            boxes.add(box);

        }
        return new DetectedObjects(className, probabilities, boxes);
    }

    @Override
    public NDList processInput(TranslatorContext translatorContext, Image image) throws Exception {
        NDArray array = image.toNDArray(translatorContext.getNDManager());
        array = array.toType(DataType.FLOAT32, false);
        array = array.div(255f);

        array = array.transpose(2,0,1);

        array = array.expandDims(0);

        return new NDList(array);
    }
}
