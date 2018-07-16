package nl.lightah.countdown

import javafx.event.ActionEvent
import javafx.geometry.Insets
import scalafx.application.JFXApp.PrimaryStage
import scalafx.application.{JFXApp, Platform}
import scalafx.scene.Scene
import scalafx.scene.control.{Button, Label, TextArea}
import scalafx.scene.layout.{BorderPane, HBox}
import scalafx.scene.text.Font

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.io.Source
import scala.util.{Failure, Success}

/**
  * Counting down a random amount of seconds within a defined range.
  *
  * FIMXE: the implementation is not accurate using sleeps.
  *
  * @author Lightah
  */
object CountDown extends JFXApp {

  val start = 60
  val end = 120

  val bufferedSource = Source.fromResource("questions.txt")
  val questions = bufferedSource.getLines().toList
  bufferedSource.close

  @volatile var running = false
  var skip = false
  var rounds = 1;
  val gainControl = -20
  val rndsLbl = new Label
  rndsLbl.font = new Font(64.0)
  val timerLbl = new Label
  timerLbl.font = new Font(64.0)
  val question = new TextArea
  question.wrapText = true
  question.minHeight = 50
  question.font = new Font(64.0)

  stage = new PrimaryStage {
    title = "Count down"

    val pane = new BorderPane

    val startBtn = new Button
    startBtn.font = new Font(32.0)
    startBtn.setText("Start")
    startBtn.setOnAction((e: ActionEvent) => {
      if (!running) {
        println("Starting random count down intervals!")
        running = true
        startRandomInterval
      }
    })

    val skipBtn = new Button
    skipBtn.font = new Font(32.0)
    skipBtn.setText("Skip")
    skipBtn.setOnAction((e: ActionEvent) => {
      if (running) {
        println("Skip...")
        skip = true
      }
    })

    val pauseBtn = new Button
    pauseBtn.font = new Font(32.0)
    pauseBtn.setText("Pause")
    pauseBtn.setOnAction((e: ActionEvent) => {
      if (running)
        println("Pausing after this round...")
      running = false
    })

    val top = new HBox
    top.setPadding(new Insets(15,
      12,
      15,
      12))
    top.setSpacing(10)
    top.setStyle("-fx-background-color: #336699;")
    top.getChildren.addAll(startBtn,
      skipBtn,
      pauseBtn)
    pane.setTop(top)

    val center = new HBox
    center.setPadding(new Insets(15,
      12,
      15,
      12))
    center.setSpacing(10)
    center.getChildren.addAll(rndsLbl,
      timerLbl)
    pane.setCenter(center)

    val bottom = new HBox
    bottom.setPadding(new Insets(15,
      12,
      15,
      12))
    bottom.setSpacing(10)
    bottom.getChildren.addAll(question)
    pane.setBottom(bottom)

    scene = new Scene(pane,
      800,
      600)
  }

  def startRandomInterval: Unit = {
    if (running) {
      println(s"  Starting round $rounds")
      waitAndPlay(getRandomInterval)
    }
  }

  def getRandomInterval: Int = {
    val rnd = new scala.util.Random
    start + rnd.nextInt((end - start) + 1)
  }

  def waitAndPlay(seconds: Int): Unit = {
    val f = Future {
      println(s"    Waiting $seconds seconds")
      Platform.runLater {
        rndsLbl.text = s"Question $rounds"
      }
      for (a <- 1 to seconds) {
        Platform.runLater {
          var remaining = seconds - a
          timerLbl.text = s"(remaining $remaining sec)"

          if (rounds <= questions.size) {
            question.text = questions(rounds - 1)
          }
        }

        if (!skip)
          Thread.sleep(1000)
      }

      if (skip)
        skip = false

      println("    Playing sound")

      Sound.playSound("whistle2.wav")
      Platform.runLater {
        timerLbl.text = ""
      }
      rounds = rounds + 1

      if (rounds > questions.size) {
        running = false
      }
    }

    f.onComplete {
      case Success(value) => startRandomInterval
      case Failure(e) => e.printStackTrace; running = false
    }
  }
}
