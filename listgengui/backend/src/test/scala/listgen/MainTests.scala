package listgen

import org.apache.poi.ss.usermodel.Sheet
import org.scalatest.funsuite.AnyFunSuite
import os.Path

class MainTests extends AnyFunSuite {
  test("Saves file") {
    val wd: Path = os.pwd
    val filename = "sample list test file"
    val path = (wd / "src" / "resources" / filename.+(".xlsx")).toString()
    val sheet: Sheet = Main.openSheet(path, 1)
    val list = Main.genList(sheet, 2, 4)
    Main.saveTxt(path, filename, list)
  }

  test("Saves Smil") {
    val wd: Path = os.pwd
    val filename = "sample list test file"
    val path = (wd / "src" / "resources" / filename.+(".xlsx")).toString()
    val sheet: Sheet = Main.openSheet(path, 1)
    val list = Main.genList(sheet, 2, 4)
    val smil = Main.genSmil(list)
    Main.saveSmil(path, filename, smil)
  }
}
