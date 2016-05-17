package com.ajug.demo.weka

import org.scalatra._

import com.ajug.demo.weka.ClassifierLoader._
class ProductSenseDisambig extends WekaexampleStack {

  get("/") {
    <html>
      <body>
        <form action="isproductType" method="get">
          <textarea name="description" cols="50" rows="10" style="font-size:25pt;white-space:pre-wrap"
                    placeholder="Enter phrase description"></textarea>
          <br/>
          <input type="submit" name="submit" value="Is product?" style="font-size:2.0em; color:black;background-color:white"/>
          </form>

      </body>
    </html>
  }

  get("/isproductType"){

    val color = classify( params("description")) match {
      case "yes" => ("green", "This is a product")
      case _ => ("red", "This is NOT a product")
    }
    val style="font-size:2.0em; color:"+color._1+";background-color:white"
    <p style={style}> {color._2} </p>
  }

}
