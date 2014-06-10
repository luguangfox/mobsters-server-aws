package com.lvl6.mobsters.dynamo.tests.manual;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAutoGeneratedKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.amazonaws.services.dynamodbv2.transactions.Transaction;
import com.amazonaws.services.dynamodbv2.transactions.Transaction.IsolationLevel;
import com.google.common.base.Function;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.lvl6.mobsters.dynamo.setup.Lvl6Transaction;
import com.lvl6.mobsters.dynamo.tests.manual.SingleHashKeyStrategy.ChildOne;
import com.lvl6.mobsters.dynamo.tests.manual.SingleHashKeyStrategy.ParentOne;

/**
 * Strategy class that exercises
 * 
 * @author John
 */
@Component
@Qualifier("VariantOne")
public class SingleHashKeyStrategy implements VariantStrategy<ParentOne, ChildOne>
{
	@Autowired
	UnpartitionedStrategy pcRepo;

	private String currentParentHashKey = null;
	private ParentOne currentParent = null;
	private final ArrayList<ChildOne> currentChildren = new ArrayList<ChildOne>(0);

	@Override
	public String getNextParent( final int expectedChildCount )
	{
		currentChildren.clear();
		currentChildren.ensureCapacity(expectedChildCount);

		currentParent = new ParentOne();
		pcRepo.saveParent(currentParent);

		currentParentHashKey = currentParent.getId();
		return currentParentHashKey;
	}

	@Override
	public BaseParentChildRepository<ParentOne, ChildOne> getRepository()
	{
		return pcRepo;
	}

	@Override
	public ChildDataAttrs addNextChild()
	{
		final ChildOne retVal = new ChildOne();
		// retVal.setUserId(currentParentHashKey);
		currentChildren.add(retVal);
		return retVal;
	}

	@Override
	public void saveChildren()
	{
		pcRepo.saveChildren(currentParentHashKey, currentChildren);
		currentParentHashKey = null;
		currentChildren.clear();
	}

	@DynamoDBTable(tableName = "ParentOne")
	public static class ParentOne
	{
		@DynamoDBAutoGeneratedKey
		@DynamoDBHashKey(attributeName = "id")
		private String id;

		@DynamoDBVersionAttribute
		private Long version;

		@DynamoDBAttribute(attributeName = "name")
		private String name;

		public ParentOne()
		{
			super();
		}

		public ParentOne( final String id /* , String name */)
		{
			super();

			this.id = id;
			// this.name = name;
		}

		public String getId()
		{
			return id;
		}

		/**
		 * Replaces this object's id attribute with a new caller-provided
		 * value..
		 * 
		 * The semantics of this operation, especially in terms of the large
		 * universe of "other" objects, is not terribly well defined. There may
		 * be entity types that are highly intolerant of their Id changing
		 * during the course of their existence, as Id storage is the means of
		 * creating links between a visible object and any other object that
		 * depends on it by reference.
		 * 
		 * @param id
		 */
		public void setId( final String id )
		{
			this.id = id;
		}

		public Long getVersion()
		{
			return version;
		}

		public void setVersion( final Long version )
		{
			this.version = version;
		}

		public String getName()
		{
			return name;
		}

		public void setName( final String name )
		{
			this.name = name;
		}
	}

	@DynamoDBTable(tableName = "ChildOne")
	public static class ChildOne extends ChildDataAttrs
	{
		private String userId;

		private String id;
		private Long version;

		public ChildOne()
		{
			super();
		}

		public ChildOne( final String userId, final String id, final String name,
		    final int monsterId, final int currentExp, final int currentLvl,
		    final int currentHealth, final int numPieces, final boolean isComplete,
		    final Date combineStartTime, final int teamSlotNum, final String sourceOfPieces,
		    final double tradeValue )
		{
			super(name, monsterId, currentExp, currentLvl, currentHealth, numPieces,
			    isComplete, combineStartTime, teamSlotNum, sourceOfPieces, tradeValue);
			this.userId = userId;
			this.id = id;
		}

		@DynamoDBAutoGeneratedKey
		@DynamoDBRangeKey(attributeName = "id")
		public String getId()
		{
			return id;
		}

		public void setId( final String id )
		{
			this.id = id;
		}

		@DynamoDBVersionAttribute
		public Long getVersion()
		{
			return version;
		}

		public void setVersion( final Long version )
		{
			this.version = version;
		}

		@DynamoDBHashKey(attributeName = "userId")
		public String getUserId()
		{
			return userId;
		}

		public void setUserId( final String userId )
		{
			this.userId = userId;
		}

		@Override
		@DynamoDBAttribute
		public String getName()
		{
			return super.getName();
		}

		@Override
		public void setName( final String name )
		{
			super.setName(name);
		}

		@Override
		@DynamoDBAttribute
		public int getMonsterId()
		{
			return super.getMonsterId();
		}

		@Override
		public void setMonsterId( final int monsterId )
		{
			super.setMonsterId(monsterId);
		}

		@Override
		@DynamoDBAttribute
		public int getCurrentExp()
		{
			return super.getCurrentExp();
		}

		@Override
		public void setCurrentExp( final int currentExp )
		{
			super.setCurrentExp(currentExp);
		}

		@Override
		@DynamoDBAttribute
		public int getCurrentLvl()
		{
			return super.getCurrentLvl();
		}

		@Override
		public void setCurrentLvl( final int currentLvl )
		{
			super.setCurrentLvl(currentLvl);
		}

		@Override
		@DynamoDBAttribute
		public int getCurrentHealth()
		{
			return super.getCurrentHealth();
		}

		@Override
		public void setCurrentHealth( final int currentHealth )
		{
			super.setCurrentHealth(currentHealth);
		}

		@Override
		@DynamoDBAttribute
		public int getNumPieces()
		{
			return super.getNumPieces();
		}

		@Override
		public void setNumPieces( final int numPieces )
		{
			super.setNumPieces(numPieces);
		}

		@Override
		@DynamoDBAttribute
		public boolean isComplete()
		{
			return super.isComplete();
		}

		@Override
		public void setComplete( final boolean isComplete )
		{
			super.setComplete(isComplete);
		}

		@Override
		@DynamoDBAttribute
		public Date getCombineStartTime()
		{
			return super.getCombineStartTime();
		}

		@Override
		public void setCombineStartTime( final Date combineStartTime )
		{
			super.setCombineStartTime(combineStartTime);
		}

		@Override
		@DynamoDBAttribute
		public int getTeamSlotNum()
		{
			return super.getTeamSlotNum();
		}

		@Override
		public void setTeamSlotNum( final int teamSlotNum )
		{
			super.setTeamSlotNum(teamSlotNum);
		}

		@Override
		@DynamoDBAttribute
		public String getSourceOfPieces()
		{
			return super.getSourceOfPieces();
		}

		@Override
		public void setSourceOfPieces( final String sourceOfPieces )
		{
			super.setSourceOfPieces(sourceOfPieces);
		}

		@Override
		@DynamoDBAttribute
		public double getTradeInValue()
		{
			return super.getTradeInValue();
		}

		@Override
		public void setTradeInValue( final double tradeValue )
		{
			super.setTradeInValue(tradeValue);
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = (prime * result)
			    + ((id == null) ? 0 : id.hashCode());
			result = (prime * result)
			    + ((userId == null) ? 0 : userId.hashCode());
			return result;
		}

		@Override
		public boolean equals( final Object obj )
		{
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final ChildOne other = (ChildOne) obj;
			if (id == null) {
				if (other.id != null) {
					return false;
				}
			} else if (!id.equals(other.id)) {
				return false;
			}
			if (userId == null) {
				if (other.userId != null) {
					return false;
				}
			} else if (!userId.equals(other.userId)) {
				return false;
			}
			return true;
		}
	}

	@Qualifier("VariantOne")
	@Component
	public static class UnpartitionedStrategy extends
	    BaseParentChildRepository<ParentOne, ChildOne>
	{
		@SuppressWarnings("unused")
        private static Logger LOG = LoggerFactory.getLogger(UnpartitionedStrategy.class);

		public UnpartitionedStrategy()
		{
			super(ParentOne.class, ChildOne.class);
		}

		@Override
		public void saveParent( final ParentOne obj )
		{
			final Lvl6Transaction t1 = repoTxManager.getActiveTransaction();
			if (t1 != null) {
				t1.save(obj);
			} else {
				mapper.save(obj);
			}
		}

		@Override
		public ParentOne loadParent( final String hashKey )
		{
			final Lvl6Transaction t1 = repoTxManager.getActiveTransaction();
			ParentOne retVal;
			if (t1 != null) {
				retVal = t1.load(pClass, hashKey);
			} else {
				retVal = repoTxManager.load(pClass, hashKey, IsolationLevel.COMMITTED);
			}

			return retVal;
		}

		@Override
		public void deleteParent( final ParentOne item )
		{
			final Lvl6Transaction t1 = repoTxManager.getActiveTransaction();
			if (t1 != null) {
				t1.delete(item);
			} else {
				mapper.delete(item);
			}
		}

		@Override
		public void saveChild( final String parentHashKey, final ChildOne obj )
		{
			obj.setUserId(parentHashKey);
			final Transaction t1 = repoTxManager.getActiveTransaction();
			if (t1 != null) {
				t1.save(obj);
			} else {
				mapper.save(obj);
			}
		}

		@Override
		public void saveChildren( final String parentHashKey, final Iterable<ChildOne> children )
		{
			final Transaction t1 = repoTxManager.getActiveTransaction();
			if (t1 != null) {
				for (final ChildOne obj : children) {
					obj.setUserId(parentHashKey);
					t1.save(obj);
				}
			} else {
				for (final ChildOne obj : children) {
					// Avoiding batchSave() because it does not check optimistic
					// lock versions.
					obj.setUserId(parentHashKey);
					mapper.save(obj);
				}
			}
		}

		@Override
		public ChildOne loadChild( final String parentHashKey, final String childId )
		{
			final Lvl6Transaction t1 = repoTxManager.getActiveTransaction();
			ChildOne retVal;
			if (t1 != null) {
				retVal = t1.load(cClass, parentHashKey, childId);
			} else {
				retVal =
				    repoTxManager.load(cClass, parentHashKey, childId, IsolationLevel.COMMITTED);
			}

			return retVal;
		}

		@Override
		public List<ChildOne> loadChildren( final String parentHashKey,
		    final Iterable<String> childIds )
		{
			final Lvl6Transaction t1 = repoTxManager.getActiveTransaction();
			final ArrayList<ChildOne> retVal = new ArrayList<>();
			if (t1 != null) {
				for (final String childRangeKey : childIds) {
					retVal.add(t1.load(cClass, parentHashKey, childRangeKey));
				}
			} else {
				for (final String childRangeKey : childIds) {
					retVal.add(repoTxManager.load(cClass, parentHashKey, childRangeKey,
					    IsolationLevel.COMMITTED));
				}
			}

			return retVal;
		}

		/**
		 * With this implementation, there is no way to achieve a transactional
		 * read because the Query API is not offered and there is no way to
		 * identify the set of records to read without it since no such list is
		 * maintained in object state.
		 */
		@Override
		public List<ChildOne> loadAllChildren( final String parentHashKey )
		{
			final ChildOne hashKey = new ChildOne();
			hashKey.setUserId(parentHashKey);

			final DynamoDBQueryExpression<ChildOne> query =
			    new DynamoDBQueryExpression<ChildOne>().withHashKeyValues(hashKey)
			        .withRangeKeyCondition("id",
			            new Condition().withComparisonOperator(ComparisonOperator.LT)
			                .withAttributeValueList(new AttributeValue("z")))
			        .withConsistentRead(true);
			// LOG.info("Query: {}", query.toString());
			final PaginatedQueryList<ChildOne> retVal = childQuery(query);
			retVal.loadAllResults();
			return retVal;
		}

		private static Function<ChildOne, String> CHILD_TO_ID_FUNCTION =
		    new Function<ChildOne, String>() {
			    @Override
			    public String apply( final ChildOne input )
			    {
				    return input.getId();
			    }
		    };

		@Override
		public List<String> getAllChildren( final String parentHashKey )
		{
			return convertToIds(loadAllChildren(parentHashKey));
		}

		@Override
		public void deleteChild( final String parentHashKey, final ChildOne child )
		{
			// Read->Verify->Write->Commit transaction pattern as
			// alternative to Expected Check Constraints on DeleteItemRequest
			// low-level API call.
			final ChildOne obj = loadChild(parentHashKey, child.getId());
			if (obj != null) {
				final Lvl6Transaction t1 = repoTxManager.getActiveTransaction();
				if (t1 != null) {
					t1.delete(obj);
				} else {
					// TODO: Just because we read obj in a previous transaction
					// with COMMITTED isolation level does not mean its still in
					// a delete-ready state b/c the transaction used to perform
					// that COMMITTED read has ended by now.
					//
					// We _should_ fail here by now, but lets not. There IS a
					// better solution, in the form a of delete/save helpers on
					// TxManager that launch a temporary transaction and use it
					// to encapsulate a read followed by verify and delete steps
					// _before closing the transaction or returning_.
					//
					// The tough part in that chain is generalizing the
					// validation step between load and save without returning
					// the control flow back to the user by method return. What
					// that tells me is that the validation logic needs be
					// captured in a caller-provided lambda, to be given the
					// result of the load and either accepting or rejecting it
					// as suitable for proceeding with the rest of the delete
					// action.
					mapper.delete(obj);
				}
			}
		}

		@Override
		public void deleteChildren( final String parentHashKey,
		    final Iterable<ChildOne> childIds )
		{
			// Read->Verify->Write->Commit transaction pattern as
			// alternative to Expected Check Constraints on DeleteItemRequest
			// low-level API call.
			final List<ChildOne> objList =
			    loadChildren(parentHashKey, FluentIterable.from(childIds)
			        .transform(CHILD_TO_ID_FUNCTION));
			final Lvl6Transaction t1 = repoTxManager.getActiveTransaction();
			if (t1 != null) {
				for (final ChildOne obj : objList) {
					t1.delete(obj);
				}
			} else {
				// TODO: See comment block in deleteChild(). Same applies
				// here too.
				for (final ChildOne obj : objList) {
					mapper.delete(obj);
				}
			}
		}

		@Override
		public List<ChildOne> deleteAllChildren( final String parentHashKey )
		{
			// Read->Verify->Write->Commit transaction pattern as
			// alternative to Expected Check Constraints on DeleteItemRequest
			// low-level API call.
			final List<ChildOne> objList = loadAllChildren(parentHashKey);
			final Lvl6Transaction t1 = repoTxManager.getActiveTransaction();
			if (t1 != null) {
				for (final ChildOne obj : objList) {
					t1.delete(obj);
				}
			} else {
				// TODO: See comment block in deleteChild(). Same applies
				// here too.
				for (final ChildOne obj : objList) {
					mapper.delete(obj);
				}
			}

			return objList;
		}

		@Override
		public List<String> removeAllChildren( final String parentHashKey )
		{
			return convertToIds(deleteAllChildren(parentHashKey));
		}

		private List<String> convertToIds( final Iterable<ChildOne> objIter )
		{
			final ArrayList<String> retVal = new ArrayList<String>();
			Iterables.addAll(retVal, FluentIterable.from(objIter)
			    .transform(CHILD_TO_ID_FUNCTION));
			return retVal;
		}
	}
}