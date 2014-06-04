package com.lvl6.mobsters.events;

public abstract class PreDatabaseResponseEvent extends ResponseEvent{
  protected String udid;
  
  public PreDatabaseResponseEvent(String udid) {
	super();
    this.udid = udid;
  }

  public String getUdid() {
    return udid;
  }
}