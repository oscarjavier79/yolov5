package com.pytorch.yolv5.services;

import ai.djl.MalformedModelException;
import ai.djl.repository.zoo.ModelNotFoundException;

import java.io.IOException;

public interface YoloService {

    String getScores(String image) throws IOException, ModelNotFoundException, MalformedModelException;
}
