package com.lvl6.mobsters.noneventproto.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lvl6.mobsters.info.PvpLeague;
import com.lvl6.mobsters.noneventproto.NoneventPvpProto.PvpLeagueProto;

@Component
public class NoneventPvpProtoSerializerImpl implements NoneventPvpProtoSerializer 
{

	@SuppressWarnings("unused")
	private static final Logger LOG =
		LoggerFactory.getLogger(NoneventPvpProtoSerializerImpl.class);

	@Override
	public PvpLeagueProto createPvpLeagueProto( PvpLeague pl )
	{
		PvpLeagueProto.Builder plpb = PvpLeagueProto.newBuilder();

		plpb.setLeagueId(pl.getId());

		String aStr = pl.getLeagueName();
		if (null != aStr) {
			plpb.setLeagueName(aStr);
		}

		aStr = pl.getImgPrefix();
		if (null != aStr) {
			plpb.setImgPrefix(aStr);
		}

		plpb.setNumRanks(pl.getNumRanks());

		aStr = pl.getDescription();
		if (null != aStr) {
			plpb.setDescription(aStr);
		}

		plpb.setMinElo(pl.getMinElo());
		plpb.setMaxElo(pl.getMaxElo());

		return plpb.build();
	}



}
