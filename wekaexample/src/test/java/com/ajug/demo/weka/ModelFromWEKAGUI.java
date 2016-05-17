package com.ajug.demo.weka;

import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.StringReader;

/**
 * Created by Ferosh Jacob on 5/17/16.
 */
public class ModelFromWEKAGUI {



    public static Classifier loadClassifier() throws Exception{
        ObjectInputStream modelInObjectFile =
                new ObjectInputStream(ModelFromWEKAGUI.class.getClassLoader().getResourceAsStream("contactlens.j48.model"));
        Classifier classifier = (Classifier) modelInObjectFile.readObject();
        return classifier;
    }
    public static void main(String[] args) throws Exception {

        Classifier classifier=  loadClassifier();
        Instance instance = createInstance("young","myope","no","reduced");
        double classifedClass= classifier.classifyInstance(instance);
        System.out.println(classifedClass);
    }


    public static Instance createInstance(String   age, String prescription, String isAstigmatic, String tearRate) throws IOException {

        String  content =String.format("@relation contact-lenses\n" +
                "\n" +
                "@attribute age \t\t\t{young, pre-presbyopic, presbyopic}\n" +
                "@attribute spectacle-prescrip\t{myope, hypermetrope}\n" +
                "@attribute astigmatism\t\t{no, yes}\n" +
                "@attribute tear-prod-rate\t{reduced, normal}\n" +
                "@attribute contact-lenses\t{soft, hard, none}\n" +
                "\n" +
                "@data\n" +
                "%s,%s,%s,%s,none\n",age,prescription,isAstigmatic,tearRate);

        System.out.println(content);
        BufferedReader reader = new BufferedReader(new StringReader(content));
        ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader);
        Instances data = arff.getData();
        data.setClassIndex(data.numAttributes() - 1);
        return data.instance(0);

    }

}
