package com.ajug.demo.weka;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Ferosh Jacob on 5/16/16.
 */
public class ARFFReaderExample {


    public static void main(String[] args) throws IOException {
        BufferedReader reader =
                new BufferedReader(new FileReader("files/generated2.arff"));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader, 1000);
        Instances data = arff.getStructure();
        data.setClassIndex(data.numAttributes() - 1);
        Instance inst;
        while ((inst = arff.readInstance(data)) != null) {
            System.out.println(inst.stringValue(0) +"->" + inst.classValue() );

        }
    }
}
