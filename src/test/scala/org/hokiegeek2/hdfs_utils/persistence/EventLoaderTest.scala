package org.hokiegeek2.hdfs_utils.persistence

import org.scalatest.FunSuite
import org.scalatest.BeforeAndAfterAll
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.apache.hadoop.hdfs.inotify.Event
import org.apache.hadoop.hdfs.HdfsConfiguration
import org.apache.hadoop.hdfs.DFSConfigKeys
import org.apache.hadoop.hdfs
import org.apache.hadoop.hdfs.qjournal.MiniQJMHACluster
import org.apache.hadoop.hdfs.MiniDFSCluster
import org.apache.hadoop.hdfs.DFSClient
import org.apache.hadoop.hdfs.DFSInotifyEventInputStream
import org.apache.hadoop.hdfs.DFSTestUtil
import org.apache.hadoop.fs.Path
import org.apache.hadoop.fs.FileSystem

@RunWith(classOf[JUnitRunner])
class EventLoaderTest extends FunSuite with BeforeAndAfterAll {
  var conf : HdfsConfiguration = _
  var cluster : MiniQJMHACluster = _
  var client : DFSClient = _
  var fs : FileSystem = _
  var eis : DFSInotifyEventInputStream = _
  val BLOCK_SIZE : Int = 1048576;
  
  override def beforeAll = {
    conf = new HdfsConfiguration(); 
    conf.setLong(DFSConfigKeys.DFS_BLOCK_SIZE_KEY, BLOCK_SIZE)
    conf.setBoolean(DFSConfigKeys.DFS_NAMENODE_ACLS_ENABLED_KEY, true)
    conf.setLong(DFSConfigKeys.DFS_NAMENODE_ACCESSTIME_PRECISION_KEY, 1)
    conf.setLong("dfs.namenode.fs-limits.min-block-size",1024)
    
    val builder = new MiniQJMHACluster.Builder(conf) 
    builder.getDfsBuilder().numDataNodes(2)
    cluster = builder.build(); 
    
    cluster.getDfsCluster().waitActive(); 
    cluster.getDfsCluster().transitionToActive(0); 
    client = new DFSClient(cluster.getDfsCluster().getNameNode(0).getNameNodeAddress(), conf); 
    
    fs = cluster.getDfsCluster().getFileSystem(0); 
    
    DFSTestUtil.createFile(fs, new Path("/file"), BLOCK_SIZE, 1, 0L); 
    DFSTestUtil.createFile(fs, new Path("/file3"), BLOCK_SIZE, 1, 0L); 
    DFSTestUtil.createFile(fs, new Path("/file5"), BLOCK_SIZE, 1, 0L); 
    eis = client.getInotifyEventStream(); 
  }
  
  def waitForNextEvent() : Event = {
    var next : Event = null; 
    while ((next = eis.poll()) == null) {
      next
    }
    
    next 
  } 
  
  test ("Test createEvent") {
    var event = waitForNextEvent
    print(event.toString())
  }
  
  override def afterAll = {
    cluster.shutdown()
  }
}