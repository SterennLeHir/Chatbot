package machine
import org.junit.Test
import org.junit.Assert._

class TestErrorsTolerance {
  
  // initialisation des objets sous test
  val t= ErrorsToleranceImpl
  
  
  // tests corrige
  val bdd = List("absence", "retard", "reveil", "trahison", "disgrace")
  @Test
  def test1corrige{
    assertEquals(List(), t.corrige(List(""),bdd))
  }
  
  @Test //a corriger
  def test2corrige{
    assertEquals(List("retard"), t.corrige(List("retord"),bdd))
  }
  
  @Test
  def test3corrige{
    assertEquals(List("reveil"), t.corrige(List("mes", "gros", "réveil"),bdd))
  }

  
  // tests Levenshtein
  @Test
  def test1Levenshtein{
    assertEquals(0, t.levenshteinDist("toto","toto"))
  }
  
  @Test
  def test2Levenshtein{
    assertEquals(1, t.levenshteinDist("tot","toto"))
  }
  @Test
  def test3Levenshtein{
    assertEquals(4, t.levenshteinDist("niche","chien"))
  }
  @Test
  def test4Levenshtein{
    assertEquals(1, t.levenshteinDist("marie","mairie"))
  }
  @Test
  def test5Levenshtein{
    assertEquals(4, t.levenshteinDist("","toto"))
  }
  
}