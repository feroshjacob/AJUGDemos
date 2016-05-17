package com.ajug.demo.weka;

import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.FastVector;
import weka.core.Instance;
import weka.core.Instances;
import weka.filters.Filter;
import weka.filters.unsupervised.attribute.StringToWordVector;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * Created by Ferosh Jacob on 5/16/16.
 */
public class ClassifierTest {


    /* The training data gathered so far. */
    private Instances m_Data = null;

    /* The filter used to generate the word counts. */
    private StringToWordVector m_Filter = new StringToWordVector();



    public void classify(Classifier classifier, String  text) throws Exception{
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
        m_Filter.setInputFormat(m_Data);

        // Generate word counts from the training data.
        Instances filteredData  = Filter.useFilter(m_Data, m_Filter);
        Instances testset = m_Data.stringFreeStructure();

        // Make message into test instance.
        Instance instance = makeInstance(text, testset);

        // Filter instance.
        m_Filter.input(instance);
        Instance filteredInstance = m_Filter.output();

        // Get index of predicted class value.
        double predicted = classifier.classifyInstance(filteredInstance);

        // Output class value.
        System.err.println("Message classified as : " +
                m_Data.classAttribute().value((int)predicted));
    }
    public static void main(String[] args) throws Exception {

        String query="the visual effect of illumination on objects or scenes as created in pictures; \\\"he could paint the lightest light and the darkest dark\\\" light,lightness'";

        Classifier classifier;
            ObjectInputStream modelInObjectFile =
                    new ObjectInputStream(new FileInputStream( ClassifierTest.class.getClassLoader().getResource("randomForest.model").getPath()));
            classifier = (Classifier) modelInObjectFile.readObject();
            modelInObjectFile.close();
        ClassifierTest classifierTest = new ClassifierTest();
        classifierTest.classify(classifier, query);
        System.out.println("hello");
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
}
