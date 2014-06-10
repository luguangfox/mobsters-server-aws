package com.lvl6.mobsters.controllers;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lvl6.mobsters.eventproto.EventMonsterProto.AddMonsterToBattleTeamRequestProto;
import com.lvl6.mobsters.eventproto.EventMonsterProto.AddMonsterToBattleTeamResponseProto;
import com.lvl6.mobsters.eventproto.EventMonsterProto.AddMonsterToBattleTeamResponseProto.AddMonsterToBattleTeamStatus;
import com.lvl6.mobsters.events.EventsToDispatch;
import com.lvl6.mobsters.events.RequestEvent;
import com.lvl6.mobsters.events.request.AddMonsterToBattleTeamRequestEvent;
import com.lvl6.mobsters.events.response.AddMonsterToBattleTeamResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolRequest;
import com.lvl6.mobsters.noneventproto.NoneventUserProto.MinimumUserProto;
import com.lvl6.mobsters.server.EventController;
import com.lvl6.mobsters.services.monster.MonsterService;

@Component
public class AddMonsterToBattleTeamController extends EventController {

    private static Logger LOG = LoggerFactory.getLogger(AddMonsterToBattleTeamController.class);

    @Autowired
    protected MonsterService monsterService;

    /*
     * @Autowired protected EventWriter eventWriter;
     */

    public AddMonsterToBattleTeamController() {}

    @Override
    public RequestEvent createRequestEvent() {
        return new AddMonsterToBattleTeamRequestEvent();
    }

    @Override
    public EventProtocolRequest getEventType() {
        return EventProtocolRequest.C_ADD_MONSTER_TO_BATTLE_TEAM_EVENT;
    }

    @Override
    protected void processRequestEvent( RequestEvent event, EventsToDispatch eventWriter ) throws Exception
    {
        final AddMonsterToBattleTeamRequestProto reqProto =
            ((AddMonsterToBattleTeamRequestEvent) event).getAddMonsterToBattleTeamRequestProto();
        final MinimumUserProto senderProto = reqProto.getSender();
        final String userIdString = senderProto.getUserUuid();
        final int teamSlotNum = reqProto.getTeamSlotNum();
        final String userMonsterId = reqProto.getUserMonsterUuid();

        // prepare to send response back to client
        AddMonsterToBattleTeamResponseProto.Builder responseBuilder =
            AddMonsterToBattleTeamResponseProto.newBuilder();
        responseBuilder.setStatus(AddMonsterToBattleTeamStatus.FAIL_OTHER);
        responseBuilder.setSender(senderProto);
        AddMonsterToBattleTeamResponseEvent resEvent =
            new AddMonsterToBattleTeamResponseEvent(userIdString);
        resEvent.setTag(event.getTag());
        
        
        Set<String> userMonsterIds = null;
        if (StringUtils.hasText(userMonsterId)) {
            // Check values client sent for syntax errors. Call service only if
            // syntax checks out ok; prepare arguments for service
            userMonsterIds = Collections.singleton(userMonsterId);
            
            responseBuilder.setStatus(AddMonsterToBattleTeamStatus.SUCCESS);
        }

        // call service if syntax is ok
        if (responseBuilder.getStatus() == AddMonsterToBattleTeamStatus.SUCCESS) {
            try {
                monsterService.modifyMonstersForUserTeamSlot(userIdString, userMonsterIds, teamSlotNum);
            } catch (Exception e) {
                LOG.error(
                    "exception in AddMonsterToBattleTeamController processEvent when calling userService",
                    e);
                responseBuilder.setStatus(AddMonsterToBattleTeamStatus.FAIL_OTHER);
            }
        }

        resEvent.setAddMonsterToBattleTeamResponseProto(responseBuilder.build());

        // write to client
        LOG.info("Writing event: " + resEvent);
        try {
            eventWriter.writeEvent(resEvent);
        } catch (Exception e) {
            LOG.error("fatal exception in AddMonsterToBattleTeamController processRequestEvent", e);
        }

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
    // AddMonsterToBattleTeamResponseProto.Builder resBuilder )
    // {
    // eventWriter.clearResponses();
    // resBuilder.setStatus(AddMonsterToBattleTeamStatus.FAIL_OTHER);
    // AddMonsterToBattleTeamResponseEvent resEvent = new AddMonsterToBattleTeamResponseEvent(userId);
    // resEvent.setTag(event.getTag());
    // resEvent.setAddMonsterToBattleTeamResponseProto(resBuilder.build());
    // eventWriter.writeEvent(resEvent);
    // }

    public MonsterService getMonsterService() {
        return monsterService;
    }

    public void setMonsterService( MonsterService monsterService ) {
        this.monsterService = monsterService;
    }

}