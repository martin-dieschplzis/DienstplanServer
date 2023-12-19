package msc.shiftplanning;

import java.io.IOException;
import java.net.InetSocketAddress;
import javax.annotation.PreDestroy;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import net.spy.memcached.MemcachedClient;




@Component
public class MyCache {
	
	
	/*
	 * @author martin.scholz
	 *
	 * implement the memcache cache
	 */

	
	
	
	  //@Value("${memcached-server}")
	  private String MEMCACHED_SERVER = "localhost";
	  
	  //@Value("${memcached-port}")
	  private int MEMCACHED_PORT = 11211;
	  
	  //@Value("${memcached-duration}")
	  private int MEMCACHED_DURATION = 10000000;
	  
	  private MemcachedClient mcc;
	  
	  
	  
	  
	  /*
	   * connect to memcache by creating a new memchache client
	   */
	  private void connect() {
	    try {
	      this.mcc = new MemcachedClient(new InetSocketAddress[] { new InetSocketAddress(this.MEMCACHED_SERVER, this.MEMCACHED_PORT) });
	    } catch (IOException e) {
	      LoggerFactory.getLogger(MyCache.class).error(e.getLocalizedMessage());
	    } 
	  }
	  
	  
	  
	  
	  /*
	   * construtor of the cache
	   */
	  public MemcachedClient Client() {
	    if (this.mcc == null)
	      connect(); 
	    return this.mcc;
	  }
	  
	  
	  
	  
	  /*
	   * stop the memchage server. Afterwards the server must be started on OS level again
	   */
	  @PreDestroy
	  public void cleanUp() throws Exception {
	    if (this.mcc != null) {
	      LoggerFactory.getLogger(MyCache.class).debug("Shutdown memcached server");
	      this.mcc.shutdown();
	      this.mcc = null;
	    } 
	  }
}
