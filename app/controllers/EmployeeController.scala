package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import models._
import play.api.libs.json._
@Singleton
class EmployeeController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  implicit val employeeFormatter = Json.format[employee]
  implicit val employeeDeleteFormatter = Json.format[employeeDelete]
  implicit val employeeUpdateFormatter = Json.format[employeeUpdate]


  val empObj = new emp

  def show()=Action{
  val employees = empObj.getAll()
    Ok(Json.toJson(employees))
}

  def add()=Action{ request =>
    val body = request.body
    val jsonObject= body.asJson

    val employeeData = jsonObject.flatMap(Json.fromJson[employee](_).asOpt)

    employeeData match {
      case Some(newEmp) => empObj.add(newEmp.id,newEmp.name,newEmp.city)
            Created(Json.toJson(newEmp))
      case _ => BadRequest
    }

  }

  def update()=Action{request=>
    val body = request.body
    val jsonObject= body.asJson

    val employeeData = jsonObject.flatMap(Json.fromJson[employeeUpdate](_).asOpt)

    employeeData match {
      case Some(newEmp) => empObj.update(newEmp.city,newEmp.id)
        Created(Json.toJson(newEmp))
      case _ => BadRequest
    }
  }

  def delete()=Action{request=>
    val body = request.body
    val jsonObject= body.asJson

    val employeeData = jsonObject.flatMap(Json.fromJson[employeeDelete](_).asOpt)

    employeeData match {
      case Some(newEmp) => empObj.delete(newEmp.id)
        Created(Json.toJson(newEmp))
      case _ => BadRequest
    }
  }

}
