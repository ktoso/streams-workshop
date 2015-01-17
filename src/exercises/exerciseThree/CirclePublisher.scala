package exerciseThree

import akka.actor._
import akka.stream.actor._
import video.Frame

// ------------
// EXERCISE 3.1
// ------------
// Fill in the code necessary to produce random circles based on the requested demand.
// The properties of the circles should be retrieved from the the CircleGenerator actor
// which will return the random properties of a circle.  The random circles
// should then be drawn to a Buffered Image and used to create the Frame.
//
// When the Frame is ready it should be sent to the consumer using:
//      onNext(Frame ... )
//
// See video.imageUtils.ImageUtils.createBufferedImage
class CirclePublisher extends ActorPublisher[Frame] {

  override def receive: Receive = {

    case ActorPublisherMessage.Request(elements) =>
      // TODO IMPLEMENT ME

    case ActorPublisherMessage.Cancel =>
      // TODO IMPLEMENT ME
  }

}


object CirclePublisher {

  /**
   * run:
   *   ./activator 'runMain exerciseThree.CircleProducer'
   *
   */
  def main(args: Array[String]): Unit = {
    // ActorSystem represents the "engine" we run in, including threading configuration and concurrency semantics.
    val system = ActorSystem()

    // Fill in the code necessary to construct a UI to consume and display the Frames produced
    // by the Circle producer.

    val display = video.display(system)

    val circleProducer = system.actorOf(Props[CirclePublisher], "circleProducer")
    ActorPublisher(circleProducer).subscribe(display)
  }
}
