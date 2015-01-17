package exerciseFour

import java.io.File

import akka.actor.ActorSystem
import akka.stream.stage.{Directive, Context, StageState, StatefulStage}
import akka.stream.{FlowMaterializer, Transformer}
import akka.stream.scaladsl._
import org.reactivestreams.{Publisher, Subscriber}
import video.Frame

import scala.collection.immutable.Seq

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
      var last: Frame = _

      val pullFirstFrame = new State {
        override def onPush(elem: Frame, ctx: Context[Frame]): Directive = {
          last = elem
          become(diffFrames)
          ctx.pull()
        }
      }

      val diffFrames = new State {
        override def onPush(f: Frame, ctx: Context[Frame]): Directive = {
          val diffed: Frame = video.frameUtil.diff(last, f)
          last = f
          ctx.push(diffed)
        }
      }


      override def initial = pullFirstFrame
    }

    source.transform[Frame](() => transformer).runWith(sink)
  }
}