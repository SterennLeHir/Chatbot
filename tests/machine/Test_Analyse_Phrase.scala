package machine
import org.junit.Test

import org.junit.Assert._

class Test_Analyse_Phrase {
  var a = new Analyse_Phrase_Impl;
  @Test
  def testSplit1{    
    assertTrue(a.decoupePhrase("").sameElements(List("")))
  }
  
  @Test
  def testSplit2{
    assertTrue(a.decoupePhrase(" ").sameElements(List("")))
  }
  
  @Test
  def testSplit3{
    assertTrue(a.decoupePhrase("bonjour").sameElements(List("bonjour")))
  }
  
  @Test
  def testSplit4{
    assertTrue(a.decoupePhrase("bonjour, ça va ?").sameElements(List("bonjour", "ça", "va")))
  }
  
  @Test
  def testSplit5{
    assertTrue(a.decoupePhrase("bonjour;:!ça?:!va?").sameElements(List("bonjour","ça","va")))
  }
  
  @Test
  def testSplit6{
    assertTrue(a.decoupePhrase("bonjour;:de la les;:!ça?:!va?").sameElements(List("bonjour","ça","va")))
  }
  
  @Test
  def testFiltrageBonjour1{
    assertFalse(a.filtrageBonjour(List("Bonjour"),"Bonjour"))
  }
   @Test
  def testFiltrageBonjour2{
    assertTrue(a.filtrageBonjour(List("Bonjour"),"Bonjour, abue"))
  }
    @Test
  def testFiltrageBonjour3{
    assertTrue(a.filtrageBonjour(List("Salut"),"Salut ezfz"))
  }
  @Test
  def testFiltrageBonjour4{
    assertFalse(a.filtrageBonjour(List(""),""))
  }
  
  //test languageDetection
  val bdd:Map[String, Int] = Map("cherche" -> 0, "search" -> 1, "busco" -> 2, "wo" -> 3, "pepperoni" -> 4)
  println(bdd)

  @Test
  def test1languageDetection{
    a.languageDetection(List("je","cherche", "mairie"), bdd)
    assertEquals(0,a.language)
  }

  
  
  @Test
  def test2languageDetection{
    println("language = " + a.language)
    a.languageDetection(List("search"), bdd)
    assertEquals(1,a.language)
  }

  
  @Test
  def test3languageDetection{
    println("language = " + a.language)
    a.languageDetection(List("yo","busco"), bdd)
    
    assertEquals(2,a.language)
  }
  
  
  @Test
  def test4languageDetection{
    println("language = " + a.language)
    a.languageDetection(List("wo","ist"), bdd)
    assertEquals(3,a.language)
  }
  

  @Test
  def test5languageDetection{
    println("language = " + a.language)
    a.languageDetection(List("buongiorno","pepperoni"), bdd)
    assertEquals(4,a.language)
  }
  

  //test languageConfirmation
  val bdd2:Map[Int, String] = Map(0 -> "oui", 1 -> "yes", 2 -> "si", 3 -> "ja", 4 -> "si")

  
  @Test
  def test1languageConfirmation{
    a.language = 2
    a.checked = false
    a.languageConfirmation(List("si"),bdd2)
    assertEquals(true,a.checked)
  }

  @Test
  def test2languageConfirmation{
    a.language = 0
    a.checked = false
    a.languageConfirmation(List("si"),bdd2)
    assertEquals(false,a.checked)
  }

  @Test
  def test3languageConfirmation{
    a.language = 2
    a.checked = false
    a.languageConfirmation(List("non"),bdd2)
    assertEquals(false,a.checked)
  }
}