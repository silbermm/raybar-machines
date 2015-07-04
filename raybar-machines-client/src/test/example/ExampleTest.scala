package example

import utest._


object ScalaJsTest extends TestSuite {

  // Initialize App
  TutorialApp.setupUI()

  def tests = TestSuite {
    'HelloWorld {
      assert(jQuery("p:contains('Hello World')").length == 1)
    }
  }
}
