package com.lvl6.mobsters.controllers;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lvl6.mobsters.eventproto.EventMiniJobProto.SpawnMiniJobRequestProto;
import com.lvl6.mobsters.eventproto.EventMiniJobProto.SpawnMiniJobResponseProto;
import com.lvl6.mobsters.eventproto.EventMiniJobProto.SpawnMiniJobResponseProto.SpawnMiniJobStatus;
import com.lvl6.mobsters.events.EventsToDispatch;
import com.lvl6.mobsters.events.RequestEvent;
import com.lvl6.mobsters.events.request.SpawnMiniJobRequestEvent;
import com.lvl6.mobsters.events.response.SpawnMiniJobResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolRequest;
import com.lvl6.mobsters.noneventproto.NoneventUserProto.MinimumUserProto;
import com.lvl6.mobsters.server.EventController;
import com.lvl6.mobsters.services.minijob.MiniJobService;
import com.lvl6.mobsters.utility.common.TimeUtils;

@Component
public class SpawnMiniJobController extends EventController {

    private static Logger LOG = LoggerFactory.getLogger(SpawnMiniJobController.class);

    @Autowired
    protected MiniJobService miniJobService;

    public SpawnMiniJobController() {}

    @Override
    public RequestEvent createRequestEvent() {
        return new SpawnMiniJobRequestEvent();
    }

    @Override
    public EventProtocolRequest getEventType() {
        return EventProtocolRequest.C_SPAWN_MINI_JOB_EVENT;
    }

    @Override
    protected void processRequestEvent( RequestEvent event, EventsToDispatch eventWriter ) throws Exception
    {
        final SpawnMiniJobRequestProto reqProto =
            ((SpawnMiniJobRequestEvent) event).getSpawnMiniJobRequestProto();
        final MinimumUserProto senderProto = reqProto.getSender();
        final String userIdString = senderProto.getUserUuid();
        final Date clientTime = 
            TimeUtils.createDateFromTime(
            	reqProto.getClientTime());
        int numToSpawn = reqProto.getNumToSpawn();
        int structId = reqProto.getStructId();

        // prepare to send response back to client
        SpawnMiniJobResponseProto.Builder responseBuilder =
            SpawnMiniJobResponseProto.newBuilder();
        responseBuilder.setStatus(SpawnMiniJobStatus.FAIL_OTHER);
        responseBuilder.setSender(senderProto);
        SpawnMiniJobResponseEvent resEvent =
            new SpawnMiniJobResponseEvent(userIdString);
        resEvent.setTag(event.getTag());

        // Check values client sent for syntax errors. Call service only if
        // syntax checks out ok; prepare arguments for service
        responseBuilder.setStatus(SpawnMiniJobStatus.SUCCESS);

        // call service if syntax is ok
        if (responseBuilder.getStatus() == SpawnMiniJobStatus.SUCCESS) {
            try {
                // TODO: Ensure that the user's lastMiniJobGenerated time is updated to "now",
                //       a.k.a. clientTime
                miniJobService.spawnMiniJobsForUser(userIdString, clientTime, numToSpawn, structId);
                // TODO: Ensure that the user's lastMiniJobGenerated time is updated to now,
                //       er, clientTime
            } catch (Exception e) {
                LOG.error(
                    "exception in SpawnMiniJobController processEvent when calling miniJobService",
                    e);
                responseBuilder.setStatus(SpawnMiniJobStatus.FAIL_OTHER);
            }
        }

        resEvent.setSpawnMiniJobResponseProto(responseBuilder.build());

        // write to client
        LOG.info("Writing event: %s", resEvent);
        eventWriter.writeEvent(resEvent);

        // TODO: FIGURE OUT IF THIS IS STILL NEEDED
        // game center id might have changed
        // null PvpLeagueFromUser means will pull from a cache instead
        // UpdateClientUserResponseEvent resEventUpdate =
        // CreateEventProtoUtil.createUpdateClientUserResponseEvent(null, null, user, null, null);
        // resEventUpdate.setTag(event.getTag());
        // eventWriter.writeEvent(resEventUpdate);
    }

    // private void failureCase(
    // RequestEvent event,
    // EventsToDispatch eventWriter,
    // String userId,
    // SpawnMiniJobResponseProto.Builder resBuilder )
    // {
    // eventWriter.clearResponses();
    // resBuilder.setStatus(SpawnMiniJobStatus.FAIL_OTHER);
    // SpawnMiniJobResponseEvent resEvent = new SpawnMiniJobResponseEvent(userId);
    // resEvent.setTag(event.getTag());
    // resEvent.setSpawnMiniJobResponseProto(resBuilder.build());
    // eventWriter.writeEvent(resEvent);
    // }

    public MiniJobService getMiniJobService() {
        return miniJobService;
    }

    public void setMiniJobService( MiniJobService miniJobService ) {
    }

}
