package models

import com.datastax.driver.core.Row
import utils.CassandraClient

import scala.jdk.CollectionConverters.IterableHasAsScala

case class emp() {
  private val session = CassandraClient.session
  private val table = "employees"
  private val keysapace = "sample"

  private val getAllQ = session.prepare(s"SELECT * FROM $keysapace.$table")
  private val addQ = session.prepare(s"INSERT INTO $keysapace.$table(id,name,city) values (? ,?, ?)")
  private val updateQ = session.prepare(s"UPDATE $keysapace.$table SET city = ? WHERE id = ?")
  private val deleteQ = session.prepare(s"DELETE FROM $keysapace.$table WHERE id = ? ")

  private def toEmployee(r :Row) :employee ={
    employee(r.getInt("id"),r.getString("name"),r.getString("city"))
  }

  def getAll()={
    session.execute(getAllQ.bind()).asScala.map(toEmployee).toSeq
  }

  def add(id :Int,name:String,city:String): Unit={
    session.execute(addQ.bind(id,name,city))
  }

  def update(city :String , id : Int): Unit={
    session.execute(updateQ.bind(city,id))
  }

  def delete(id : Int): Unit={
    session.execute(deleteQ.bind(id))
  }





}
