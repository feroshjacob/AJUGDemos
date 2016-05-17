package com.ajug.demo.weka

import java.io.{FileInputStream, ObjectInputStream}

import org.scalatest.{Matchers, FunSuite}
import weka.classifiers.Classifier
import weka.core.Instance
import weka.filters.unsupervised.attribute.StringToWordVector


/**
  * Created by Ferosh Jacob on 5/16/16.
  */
object ModelLoaderTest extends FunSuite with Matchers {

/*
  def makeInstance(query: String): Instance = {
    val m_Filter = new StringToWordVector();

  }

  test("model loading"){

    val modelName = "randomForest.model"
    val query="the visual effect of illumination on objects or scenes as created in pictures; \\\"he could paint the lightest light and the darkest dark\\\" light,lightness'"
    val modelInObjectFile =
      new ObjectInputStream(new FileInputStream(modelName))

    val classifier =  modelInObjectFile.readObject().asInstanceOf[Classifier]
    classifier.classifyInstance(makeInstance(query))
  }

  */

}
