package org.scalafmt.internal

import java.util.regex.Pattern
import scala.util.matching.Regex

object PlatformCompat {

  @inline
  val trailingSpace =
    Pattern.compile("\\h++$", Pattern.MULTILINE)

  @inline
  val slcDelim = Pattern.compile("\\h++")

  @inline
  val mlcHeader =
    Pattern.compile("^/\\*\\h*+(?:\n\\h*+[*]*+\\h*+)?")
  @inline
  val mlcLineDelim =
    Pattern.compile("\\h*+\n\\h*+[*]*+\\h*+")
  @inline
  val mlcParagraphEnd = Pattern.compile("[.:!?=]$")
  @inline
  val mlcParagraphBeg = Pattern.compile("^(?:[-*@=]|\\d++[.:])")

  @inline
  val leadingAsteriskSpace =
    Pattern.compile("(?<=\n)\\h*+(?=[*][^*])")
  @inline
  val docstringLine =
    Pattern.compile(
      "^(?:\\h*+\\*)?(\\h*+)(.*?)\\h*+$",
      Pattern.MULTILINE
    )
  @inline
  val onelineDocstring = {
    val empty = "\\h*+(\n\\h*+\\*?\\h*+)*"
    Pattern.compile(
      s"^/\\*\\*$empty([^*\n\\h](?:[^\n]*[^\n\\h])?)$empty\\*/$$"
    )
  }
  @inline
  val docstringLeadingSpace = Pattern.compile("^\\h++")

  @inline
  def compileStripMarginPattern(pipe: Char) =
    Pattern.compile(s"(?<=\n)\\h*+(?=\\${pipe})")

  // see: https://ammonite.io/#Save/LoadSession
  @inline
  private val ammonitePattern: Regex = "(?:\\s*\\n@(?=\\s))+".r

  @inline
  val stripMarginPattern =
    Pattern.compile("\n(\\h*+\\|)?([^\n]*+)")

  @inline
  def replaceAllStripMargin(
      stripMarginPattern: Pattern,
      text: String,
      spaces: String,
      pipe: Char
  ): String =
    stripMarginPattern.matcher(text).replaceAll(spaces)

  @inline
  def replaceAllLeadingAsterisk(
      leadingAsteriskSpace: Pattern,
      trimmed: String,
      spaces: String
  ): String =
    leadingAsteriskSpace.matcher(trimmed).replaceAll(spaces)

  @inline
  def splitByAmmonitePattern(code: String): Array[String] =
    ammonitePattern.split(code)
}
