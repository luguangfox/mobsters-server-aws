package com.lvl6.mobsters.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.lvl6.mobsters.eventproto.EventMonsterProto.UpdateMonsterHealthRequestProto;
import com.lvl6.mobsters.eventproto.EventMonsterProto.UpdateMonsterHealthResponseProto;
import com.lvl6.mobsters.eventproto.EventMonsterProto.UpdateMonsterHealthResponseProto.Builder;
import com.lvl6.mobsters.eventproto.EventMonsterProto.UpdateMonsterHealthResponseProto.UpdateMonsterHealthStatus;
import com.lvl6.mobsters.events.EventsToDispatch;
import com.lvl6.mobsters.events.RequestEvent;
import com.lvl6.mobsters.events.request.UpdateMonsterHealthRequestEvent;
import com.lvl6.mobsters.events.response.UpdateMonsterHealthResponseEvent;
import com.lvl6.mobsters.noneventproto.ConfigEventProtocolProto.EventProtocolRequest;
import com.lvl6.mobsters.noneventproto.NoneventMonsterProto.UserMonsterCurrentHealthProto;
import com.lvl6.mobsters.noneventproto.NoneventUserProto.MinimumUserProto;
import com.lvl6.mobsters.server.EventController;
import com.lvl6.mobsters.services.monster.MonsterService;
import com.lvl6.mobsters.services.monster.MonsterService.ModifyMonstersSpec;
import com.lvl6.mobsters.services.monster.MonsterService.ModifyMonstersSpecBuilder;
import com.lvl6.mobsters.utility.common.CollectionUtils;

@Component
// @Lvl6Controller(reqProto=EventProtocolRequest.C_UPDATE_MONSTER_HEALTH_EVENT,
// respProto=EventProtocolResponse.S_UPDATE_MONSTER_HEALTH_EVENT)
public class UpdateMonsterHealthController extends EventController
{
	private static final Logger LOG =
		LoggerFactory.getLogger(UpdateMonsterHealthController.class);

	@Autowired
	protected MonsterService monsterService;

	public UpdateMonsterHealthController()
	{
		numAllocatedThreads = 4;
	}

	@Override
	public RequestEvent createRequestEvent()
	{
		return new UpdateMonsterHealthRequestEvent();
	}

	@Override
	public EventProtocolRequest getEventType()
	{
		return EventProtocolRequest.C_UPDATE_MONSTER_HEALTH_EVENT;
	}

	@Override
	protected void processRequestEvent(
		final RequestEvent event,
		final EventsToDispatch eventWriter )
	{
		// identify client request.
		final UpdateMonsterHealthRequestProto reqProto =
			((UpdateMonsterHealthRequestEvent) event).getUpdateMonsterHealthRequestProto();
		final MinimumUserProto sender = reqProto.getSender();
		final String userIdString = sender.getUserUuid();

		// prepare to send response back to client
		final Builder responseBuilder = UpdateMonsterHealthResponseProto.newBuilder();
		responseBuilder.setStatus(UpdateMonsterHealthStatus.FAIL_OTHER);

		// Check values client sent for syntax errors. Call service only if
		// syntax checks out ok
		final List<UserMonsterCurrentHealthProto> umchpList = reqProto.getUmchpList();
		// final Table<String, MonsterForUserOp, Object> details =
		// HashBasedTable.create();
		final ModifyMonstersSpecBuilder modBuilder = ModifyMonstersSpec.builder();
		if (StringUtils.hasText(userIdString)
			&& !CollectionUtils.lacksSubstance(umchpList)) {
			// extract the ids so it's easier to get userMonsters from db
			for (final UserMonsterCurrentHealthProto nextMonsterUnit : umchpList) {
				modBuilder.setHealthAbsolute(nextMonsterUnit.getUserMonsterUuid(),
					nextMonsterUnit.getCurrentHealth());
			}

			responseBuilder.setStatus(UpdateMonsterHealthStatus.SUCCESS);
		}

		if (responseBuilder.getStatus() == UpdateMonsterHealthStatus.SUCCESS) {
			monsterService.modifyMonstersForUser(userIdString, modBuilder.build());
		}

		// write to client
		final UpdateMonsterHealthResponseEvent resEvent =
			new UpdateMonsterHealthResponseEvent(userIdString, event.getTag(), responseBuilder);
		LOG.info("Writing event: %s", resEvent);
		eventWriter.writeEvent(resEvent);
	}

	void setMonsterService( final MonsterService monsterService )
	{
		this.monsterService = monsterService;
	}
}
