package exerciseFour

import java.io.File

import akka.actor.ActorSystem
import akka.stream.FlowMaterializer
import akka.stream.scaladsl._
import akka.stream.stage.{StageState, StatefulStage}
import org.reactivestreams.{Publisher, Subscriber}
import video.Frame

object MovementVideo {

  /**
   * run:
   * ./activator 'runMain exerciseFour.ManipulateVideo'
   *
   */
  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = FlowMaterializer()

    // ------------
    // EXERCISE 4.2
    // ------------
    // Fill in the code necessary to create a flow dsl from a screen capture and then add a a transform
    // that will show the movement within the video, rather than the raw picture.
    // Hint:  Use the video.frameUtil.diff method to difference two video frames.

    // TODO - Your code here to consume and manipulate the video stream in a flow dsl.

    val videoStream: Publisher[Frame] = video.FFMpeg.readFile(new File("goose.mp4"), system)
    val source = Source(videoStream)
    val videoSubscriber: Subscriber[Frame] = video.Display.create(system)
    val sink = Sink(videoSubscriber)

    val transformer = new StatefulStage[Frame, Frame] {
      override def initial: StageState[Frame, Frame] = ??? // TODO IMPLEMENT ME
    }

    source.transform[Frame](() => transformer).runWith(sink)
  }
}