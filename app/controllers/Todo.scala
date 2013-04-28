package controllers

import play.api._
import play.api.mvc._
import play.api.Play._
import play.api.libs.json._
import play.api.data._
import play.api.data.Forms._
import anorm._
import models._
import views._

object Todos extends Controller {

  val todoForm = Form(
    mapping(
      "id" -> ignored(NotAssigned:Pk[Long]),
      "title" -> nonEmptyText,
      "content" -> nonEmptyText
    )(Todo.apply)(Todo.unapply)
  )

  def todos = Action {
    Ok(Json.toJson(Todo.all()))
  }

  def todoList(id: Long) = Action {
    Ok(Json.toJson(Todo.findById(id)))
  }

  def createTodo = Action { implicit request =>
    todoForm.bindFromRequest.fold(
      errors => {
        BadRequest
      },
      values => {
        Ok(Json.toJson(Todo.create(Todo(NotAssigned, values.title, values.content))))
      }
    )
  }

  def deleteTodo(id: Long) = Action {
    var todo = Todo.delete(id)
    Ok("1")
  }

}