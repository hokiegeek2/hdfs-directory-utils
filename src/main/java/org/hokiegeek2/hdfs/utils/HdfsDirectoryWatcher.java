package org.hokiegeek2.hdfs.utils;

import java.io.IOException;
import java.net.URI;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hdfs.DFSInotifyEventInputStream;
import org.apache.hadoop.hdfs.client.HdfsAdmin;
import org.apache.hadoop.hdfs.inotify.Event;
import org.apache.hadoop.hdfs.inotify.Event.CreateEvent;
import org.apache.hadoop.hdfs.inotify.MissingEventsException;

public class HdfsDirectoryWatcher {
	public static void main( String[] args ) throws IOException, InterruptedException, MissingEventsException
	{
	    HdfsAdmin admin = new HdfsAdmin( URI.create("localhost:9000"), new Configuration() );
	    DFSInotifyEventInputStream eventStream = admin.getInotifyEventStream();
	    while( true ) {
	        Event event = eventStream.poll();
            switch( event.getEventType() ) {
                case CREATE:
                    CreateEvent createEvent = (CreateEvent) event;
                    System.out.println( "  path = " + createEvent.getPath() );
                    break;
                default:
                    break;
            }
	    }
	}
}
