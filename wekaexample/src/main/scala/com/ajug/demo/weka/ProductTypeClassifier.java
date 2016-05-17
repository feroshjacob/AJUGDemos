package com.ajug.demo.weka;

/**
 * Created by Ferosh Jacob on 5/16/16.
 */

import weka.core.*;
import weka.classifiers.Classifier;
import weka.classifiers.trees.J48;
import weka.core.converters.ArffLoader;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;
import java.io.*;

public class ProductTypeClassifier implements Serializable {

    /* The training data gathered so far. */
    private Instances m_Data = null;

    /* The filter used to generate the word counts. */
    private StringToWordVector m_Filter = new StringToWordVector();

    /* The actual classifier. */
    private Classifier m_Classifier = new J48();

    /* Whether the model is up to date. */
    private boolean m_UpToDate;

    /**
     * Constructs empty training dataset.
     */
    public ProductTypeClassifier() throws Exception {

        String nameOfDataset = "MessageClassificationProblem";

        // Create vector of attributes.
        FastVector attributes = new FastVector(2);

        // Add attribute for holding messages.
        attributes.addElement(new Attribute("Message", (FastVector)null));

        // Add class attribute.
        FastVector classValues = new FastVector(2);
        classValues.addElement("yes");
        classValues.addElement("no");
        attributes.addElement(new Attribute("Class", classValues));

        // Create dataset with initial capacity of 100, and set index of class.
        m_Data = new Instances(nameOfDataset, attributes, 100);
        m_Data.setClassIndex(m_Data.numAttributes() - 1);
    }

    /**
     * Updates model using the given training message.
     */
    public void updateData(String message, String classValue) throws Exception {

        // Make message into instance.
        Instance instance = makeInstance(message, m_Data);

        // Set class value for instance.
        instance.setClassValue(classValue);

        // Add instance to training data.
        m_Data.add(instance);
        m_UpToDate = false;
    }

    /**
     * Classifies a given message.
     */
    public String classifyMessage(String message) throws Exception {

        // Check whether classifier has been built.
        if (m_Data.numInstances() == 0) {
            throw new Exception("No classifier available.");
        }

        // Check whether classifier and filter are up to date.
        if (!m_UpToDate) {

            // Initialize filter and tell it about the input format.
            m_Filter.setInputFormat(m_Data);

            // Generate word counts from the training data.
            Instances filteredData  = Filter.useFilter(m_Data, m_Filter);

            // Rebuild classifier.
            m_Classifier.buildClassifier(filteredData);
            m_UpToDate = true;
        }

        // Make separate little test set so that message
        // does not get added to string attribute in m_Data.
        Instances testset = m_Data.stringFreeStructure();

        // Make message into test instance.
        Instance instance = makeInstance(message, testset);

        // Filter instance.
        m_Filter.input(instance);
        Instance filteredInstance = m_Filter.output();

        // Get index of predicted class value.
        double predicted = m_Classifier.classifyInstance(filteredInstance);

        // Output class value.
        return m_Data.classAttribute().value((int)predicted);
    }

    /**
     * Method that converts a text message into an instance.
     */
    private Instance makeInstance(String text, Instances data) {

        // Create instance of length two.
        Instance instance = new Instance(2);

        // Set value for message attribute
        Attribute messageAtt = data.attribute("Message");
        instance.setValue(messageAtt, messageAtt.addStringValue(text));

        // Give instance access to attribute information from the dataset.
        instance.setDataset(data);
        return instance;
    }

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



    private  void update() throws Exception {
        m_Filter.setInputFormat(m_Data);

        // Generate word counts from the training data.
        Instances filteredData  = Filter.useFilter(m_Data, m_Filter);

        // Rebuild classifier.
        m_Classifier.buildClassifier(filteredData);
        m_UpToDate = true;
    }
}
