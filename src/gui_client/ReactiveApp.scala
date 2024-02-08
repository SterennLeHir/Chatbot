package gui_client

// Imports needed for working with Swing in Scala
import scala.swing._
import scala.swing.MainFrame

/** Application GUI du projet Avatar
 */
object ReactiveApp extends SimpleSwingApplication {

  def top : MainFrame = new UI
  
}