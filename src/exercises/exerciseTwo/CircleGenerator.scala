package exerciseTwo

import akka.actor.{ActorSystem, Props}

// ------------
// EXERCISE 2
// ------------
// Fill in the code necessary to handle receiving a new message to generate the
// properties for a random circle.  For testing just println when the message is received.
// See video.imageUtils.CircleProperties and video.imageUtils.ImageUtils
class CircleGenerator extends akka.actor.Actor {

  override def receive: Receive = {
    case CircleGenerator.GenerateCircle(w, h) =>
      // TODO IMPLEMENT ME
  }

}

object CircleGenerator {

  case class GenerateCircle(width:Int, height:Int)

  /**
   * run:
   * ./activator 'runMain exerciseTwo.CircleGenerator'
   *
   */
  def main(args: Array[String]): Unit = {
    // ActorSystem represents the "engine" we run in, including threading configuration and concurrency semantics.
    val system = ActorSystem()

    // Fill in the code necessary to create the Actor in the ActorSystem and send it a message.
    // TODO - Your code here.

    val random = scala.util.Random

    val actor = system.actorOf(Props[CircleGenerator], "circleGenerator")
    actor ! GenerateCircle(random.nextInt(), random.nextInt())

    system.shutdown()
    system.awaitTermination()
  }
}