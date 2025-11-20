package com.pytorch.yolv5.services.impl;

import ai.djl.MalformedModelException;
import ai.djl.inference.Predictor;
import ai.djl.modality.cv.Image;
import ai.djl.modality.cv.ImageFactory;
import ai.djl.modality.cv.output.DetectedObjects;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelNotFoundException;
import ai.djl.repository.zoo.ZooModel;
import ai.djl.translate.TranslateException;
import com.pytorch.yolv5.services.YoloService;
import com.pytorch.yolv5.utils.Utils;
import com.pytorch.yolv5.utils.YoloTranslator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Base64;

@Service
public class YoloServiceImpl implements YoloService {

    @Override
    public String getScores(String image) throws IOException, ModelNotFoundException, MalformedModelException {
        byte[] imageBytes = Base64.getDecoder().decode(image);
        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
        BufferedImage bi = ImageIO.read(bis);
        bis.close();

        Path pathModel = Utils.INSTANCE.getPath("models/mask_yolov5.torchscript");
        Criteria<Image, DetectedObjects> criteria = Criteria.builder()
                .setTypes(Image.class, DetectedObjects.class)
                .optModelPath(pathModel)
                .optTranslator(new YoloTranslator())
                .optEngine("PyTorch")
                .build();

        try(ZooModel<Image, DetectedObjects> model = criteria.loadModel();
            Predictor<Image, DetectedObjects> predictor = model.newPredictor()){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ImageIO.write(bi, "png", baos);
            InputStream inputStream = new ByteArrayInputStream(baos.toByteArray());
            Image img = ImageFactory.getInstance().fromInputStream(inputStream);
            DetectedObjects results = predictor.predict(img);
            System.out.println("results " + results);

        } catch (TranslateException e) {
            throw new RuntimeException(e);
        }
        return "Ok";
    }
}
