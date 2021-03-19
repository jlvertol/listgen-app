package listgen

import org.apache.poi.ss.usermodel.{Cell, CellType, Row, Sheet, WorkbookFactory}
import java.io.File
import os.Path
import scala.collection.mutable.ArrayBuffer

/*
TODO: 1.- read options from a config file
*/

object Main extends App {

  if (args.length != 5) {
    println("Usage: (java -jar this_command.jar) fileUrl outputFileName sheetNumber rowsToSkip colsToSkip.\r\n" +
      "A valid spreadsheet format must be used as input in fileUrl or the program will crash.\r\n" +
      "A file named ((outputFileName)) with extension .smil will be created as well as an intermediate .txt " +
      "for error-checking purposes, both in the same folder pointed by fileUrl.")
  } else {
    val fileUrl: String = args(0)
    val outputFileName: String = args(1)
    val sheetNumber: Int = args(2).toInt
    val rowsToSkip: Int = args(3).toInt
    val colsToSkip: Int = args(4).toInt

    execute(fileUrl, outputFileName, sheetNumber, rowsToSkip, colsToSkip)
  }

  def execute(fileUrl: String, outputFileName: String, sheetNumber: Int, rowsToSkip: Int, colsToSkip: Int): Unit = {
    val sheet = openSheet(fileUrl, sheetNumber)
    val list = genList(sheet, rowsToSkip, colsToSkip)
    val smil = genSmil(list)
    saveTxt(fileUrl, outputFileName, list)
    saveSmil(fileUrl, outputFileName, smil)
  }

  def stringifyCell(cell: Cell): String = {
    cell.getCellTypeEnum match {
      case CellType._NONE => ""
      case CellType.NUMERIC => cell.getNumericCellValue.toLong.toString
      case CellType.STRING => cell.getStringCellValue
      case CellType.FORMULA => ""
      case CellType.BLANK => ""
      case CellType.BOOLEAN => ""
      case CellType.ERROR => ""
    }
  }

  def openSheet(url: String, sheetNumber: Int): Sheet = {
    val f = new File(url)
    val workbook = WorkbookFactory.create(f)
    workbook.getSheetAt(sheetNumber)
  }

  def genList(sheet: Sheet, rowsToSkip: Int, colsToSkip: Int): Seq[String] = {
    val buff = new ArrayBuffer[String]()
    sheet.forEach(row => {
      if (row.getRowNum >= rowsToSkip) { //count from 0
        val maybeCell: Option[Cell] = Option(row.getCell(colsToSkip, Row.MissingCellPolicy.RETURN_BLANK_AS_NULL))
        maybeCell match {
          case Some(cell) => buff.append(stringifyCell(cell))
          case None =>
        }
      }
    })
    buff.toSeq
  }

  def genSmil(mpgNames: Seq[String]): String = {
    def toLine(x: String): String = "      <ref src=\"/media/hd0/media/" + x + ".mpg\"/>"
    """|<?xml version="1.0"?>
      |<smil xmlns="http://www.w3.org/2001/SMIL20/Language">
      |  <head/>
      |  <body>
      |    <seq>
      |""".stripMargin +
    mpgNames.map(x => if (x.isEmpty) "" else toLine(x)).mkString(sys.props("line.separator")) +
      """
        |    </seq>
        |  </body>
        |</smil>
        |""".stripMargin
  }

  def saveTxt(path: String, filename: String, list: Seq[String]): Unit = {
    val folder = new File(path).getParent
    val dest = folder + s"/$filename.txt"
    new File(dest).delete()
    os.write(Path(dest), list.mkString(sys.props("line.separator")))
  }

  def saveSmil(path: String, filename: String, smil: String): Unit = {
    val folder = new File(path).getParent
    val dest = folder + s"/$filename.smil"
    new File(dest).delete()
    os.write(Path(dest), smil)
  }
}
