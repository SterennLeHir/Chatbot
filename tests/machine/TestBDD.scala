package machine

import org.junit.Assert._
import org.junit.Test

class TestBDD {

 // initialisation des objets sous test
  val bdd: BaseDeDonneesImpl.type = BaseDeDonneesImpl
  bdd.initialiser()
  
  //existe
  @Test
  def test1existe(): Unit = {
    assertTrue(bdd.existe("Mairie de Rennes"))
  }

  @Test
  def test2existe(): Unit = {
    assertFalse(bdd.existe("Marie de Rennes"))
  }

  @Test
  def test3existe(): Unit = {
    assertFalse(bdd.existe(" "))
  }

  @Test
  def test4existe(): Unit = {
    assertFalse(bdd.existe(""))
  }

  //chercheAdresse
  
  @Test
  def test1chercheAdresse(): Unit = {
    val (adresse, listeLieux) = bdd.chercheAdresse(List("Mairie de Rennes"))

    assertEquals(Some("Place de la Mairie"), adresse)
  }

  @Test
  def test2chercheAdresse(): Unit = {
    val (adresse, listeLieux) = bdd.chercheAdresse(List("Marie de Rennes"))

    assertEquals(None, adresse)
  }

  @Test
  def test3chercheAdresse(): Unit = {
    val (adresse, listeLieux) = bdd.chercheAdresse(List(""))

    assertEquals(None, adresse)
  }

  @Test
  def test4chercheAdresse(): Unit = {
    val (adresse, listeLieux) = bdd.chercheAdresse(List("marie"))

    assertEquals(None, adresse)
  }

  @Test
  def test5chercheAdresse(): Unit = {
    val (adresse, listeLieux) = bdd.chercheAdresse(List("theatre"))

    assertEquals(List("theatre la paillette", "theatre national de bretagne"), listeLieux)
  }

  @Test
  def test6chercheAdresse(): Unit = {
    val (adresse, listeLieux) = bdd.chercheAdresse(List("theatre"))

    assertEquals(List("theatre la paillette", "theatre national de bretagne"), listeLieux)
  }

  @Test
  def test7chercheAdresse(): Unit = {
    val (adresse, listeLieux) = bdd.chercheAdresse(List("theatre", "bretagne"))

    assertEquals(Some("1, Rue Saint-Hélier"), adresse)
  }
}
