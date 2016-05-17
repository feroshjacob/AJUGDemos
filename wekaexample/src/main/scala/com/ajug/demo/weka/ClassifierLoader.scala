package com.ajug.demo.weka

import java.io.{FileInputStream, ObjectInputStream}

/**
  * Created by Ferosh Jacob on 5/16/16.
  */
object ClassifierLoader {
  val modelInObjectFile: ObjectInputStream = new ObjectInputStream(new FileInputStream(classOf[ProductTypeClassifier].getClassLoader.getResource("j48.model").getPath))
  val productClassifier: ProductTypeClassifier = modelInObjectFile.readObject.asInstanceOf[ProductTypeClassifier]

  def classify(text:String) = {
   productClassifier.classifyMessage(text)
  }
}
