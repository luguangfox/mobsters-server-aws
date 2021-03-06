package com.lvl6.mobsters.conditions.dynamo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.lvl6.mobsters.conditions.framework.AbstractTypedConditionFactory;
import com.lvl6.mobsters.conditions.framework.IIntConditionFactory;

class IntConditionFactory 
	extends AbstractTypedConditionFactory<Condition>
	implements IIntConditionFactory<Condition>
{
	@Override
	public void inNumberCollection(Collection<? extends Number> matchValues) {
		final ArrayList<AttributeValue> attrValList = 
			new ArrayList<>(matchValues.size());
		for( final Number nextNumber : matchValues ) {
			attrValList.add(
				getNumericAttrVal(nextNumber));
		}
		handleSuccess(
			new Condition()
				.withComparisonOperator(ComparisonOperator.IN)
				.withAttributeValueList(attrValList));
	}

	@Override
	public void inNumbers(Number... matchValues) {
		final ArrayList<AttributeValue> attrValList = 
			new ArrayList<>(matchValues.length);
		for( final Number nextNum : matchValues ) {
			attrValList.add(
				getNumericAttrVal(nextNum));
		}
		handleSuccess(
			new Condition()
				.withComparisonOperator(ComparisonOperator.IN)
				.withAttributeValueList(attrValList));
	}

	@Override
	public void in(int... matchValues) {
		final ArrayList<AttributeValue> attrValList = 
			new ArrayList<>(matchValues.length);
		for( final int nextInt : matchValues ) {
			attrValList.add(
				getNumericAttrVal(nextInt));
		}
		handleSuccess(
			new Condition()
				.withComparisonOperator(ComparisonOperator.IN)
				.withAttributeValueList(attrValList));
	}

	@Override
	public void is(int value) {
		addUnaryCondition(ComparisonOperator.EQ, value);
	}

	@Override
	public void isNot(int value) {
		addUnaryCondition(ComparisonOperator.NE, value);
	}

	@Override
	public void greaterThan(int value) {
		addUnaryCondition(ComparisonOperator.GT, value);
	}

	@Override
	public void greaterThanOrEqualTo(int value) {
		addUnaryCondition(ComparisonOperator.GE, value);
	}

	@Override
	public void lessThan(int value) {
		addUnaryCondition(ComparisonOperator.LT, value);
	}

	@Override
	public void lessThanOrEqualTo(int value) {
		addUnaryCondition(ComparisonOperator.LE, value);
	}

	@Override
	public void between(int lower, int upper) {
		final ArrayList<AttributeValue> attrValList = 
			new ArrayList<>(2);
		attrValList.add(
			getNumericAttrVal(lower));
		attrValList.add(
			getNumericAttrVal(upper));
		handleSuccess(
			new Condition()
				.withComparisonOperator(ComparisonOperator.BETWEEN)
				.withAttributeValueList(attrValList));
	}

	private void addUnaryCondition(final ComparisonOperator operator, final int operand) {
		handleSuccess(
			new Condition()
				.withComparisonOperator(operator)
				.withAttributeValueList(
					Collections.singletonList(
						getNumericAttrVal(operand))));
	}

	private AttributeValue getNumericAttrVal(final int operand) {
		return 
			new AttributeValue()
				.withN(
					Integer.toString(operand));
	}

	private AttributeValue getNumericAttrVal(final Number operand) {
		return 
			new AttributeValue()
				.withN(
					operand.toString());
	}
}