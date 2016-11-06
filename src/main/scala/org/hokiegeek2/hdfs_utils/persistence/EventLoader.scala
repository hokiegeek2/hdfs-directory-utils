package org.hokiegeek2.hdfs_utils.persistence

import org.apache.hadoop.hdfs.inotify.Event

class EventLoader {
  def loadEvent(event : Event) = {
    
  }
}

object EventLoader {
  def main (args : Array[String]) = {
    System.out.println("Fernsin")
  }
  
  def apply() = {
    new EventLoader()
  }
}