package com.ajug.demo.weka

import java.io.{FileInputStream, ObjectInputStream}

/**
  * Created by Ferosh Jacob on 5/16/16.
  */
object ClassifierLoader {
  val modelInObjectFile: ObjectInputStream = new ObjectInputStream(classOf[ProductTypeClassifier].getClassLoader.getResourceAsStream("j48.model"))
  val productClassifier: ProductTypeClassifier = modelInObjectFile.readObject.asInstanceOf[ProductTypeClassifier]

  def classify(text:String) = {
   productClassifier.classifyMessage(text)
  }
}
