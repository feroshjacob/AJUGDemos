package com.ajug.demo.weka

import org.scalatest.{Matchers, FunSuite}

/**
  * Created by Ferosh Jacob on 5/17/16.
  */
class ModelFromWekaGUITest extends FunSuite with Matchers {



  object ModelFromWekaGUIWrapper {
    val model = ModelFromWEKAGUI.loadClassifier()

    def classify(age:String, prescription:String, isAstimatism:String, tearRate:String) = {
      model.classifyInstance(ModelFromWEKAGUI.createInstance(age,prescription,isAstimatism,tearRate)) match {
        case  0.0 => "soft"
        case  1.0 => "hard"
        case  2.0 => "none"
      }


    }
  }

  test("contact lens classifier test"){

    import ModelFromWekaGUIWrapper._

    classify("young", "myope", "no", "reduced") shouldBe "none"
    classify("young", "myope", "yes", "normal") shouldBe "hard"
    classify("presbyopic", "myope", "yes", "normal") shouldBe "hard"
    classify("pre-presbyopic", "myope", "yes", "normal") shouldBe "hard"
  }
}
