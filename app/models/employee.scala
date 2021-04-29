package models

case class employee(id: Int, name: String, city: String)

case class employeeDelete(id :Int)

case class employeeUpdate(city :String,id :Int)
