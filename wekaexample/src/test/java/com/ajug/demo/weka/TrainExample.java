package com.ajug.demo.weka;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

import java.io.*;

/**
 * Created by Ferosh Jacob on 5/17/16.
 */
public class TrainExample {

    /**
     * Main method.
     */

    public static void main(String[] options) {

        try {



            BufferedReader reader =
                    new BufferedReader(new FileReader("files/generated2.arff"));
            ArffLoader.ArffReader arff = new ArffLoader.ArffReader(reader, 1000);
            Instances data = arff.getStructure();
            data.setClassIndex(data.numAttributes() - 1);
            Instance inst;
            ProductTypeClassifier messageCl=new ProductTypeClassifier();

            while ((inst = arff.readInstance(data)) != null) {
                messageCl.updateData(inst.stringValue(0) , inst.classValue()==1.0 ?"yes" : "no");
            }
            messageCl.update();

            ObjectOutputStream modelOutObjectFile =
                    new ObjectOutputStream(new FileOutputStream("j48.model"));
            modelOutObjectFile.writeObject(messageCl);

            modelOutObjectFile.close();
            BufferedReader reader2 =
                    new BufferedReader(new FileReader("files/generated2.arff"));
            ArffLoader.ArffReader arff2 = new ArffLoader.ArffReader(reader2, 1000);
            ObjectInputStream modelInObjectFile =
                    new ObjectInputStream(new FileInputStream(ProductTypeClassifier.class.getClassLoader().getResource("j48.model").getPath()));
            ProductTypeClassifier messageCl2 = (ProductTypeClassifier) modelInObjectFile.readObject();

            data = arff2.getStructure();
            while ((inst = arff2.readInstance(data)) != null) {
                messageCl2.classifyMessage(inst.stringValue(0));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
